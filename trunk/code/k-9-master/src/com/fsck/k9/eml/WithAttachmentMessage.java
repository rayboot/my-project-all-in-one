package com.fsck.k9.eml;

import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.text.TextUtils;

public class WithAttachmentMessage {

	/**
	 * 根据传入的文件路径创建附件并返回
	 */
	public MimeBodyPart createAttachment(String fileName) throws Exception {
		MimeBodyPart attachmentPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(fileName);
		attachmentPart.setDataHandler(new DataHandler(fds));
		attachmentPart.setFileName(fds.getName());
		return attachmentPart;
	}

	/**
	 * 根据传入的邮件正文body和文件路径创建图文并茂的正文部分
	 */
	public MimeBodyPart createContent(String body, String fileName)
			throws Exception {
		// 用于保存最终正文部分
		MimeBodyPart contentBody = new MimeBodyPart();
		// 用于组合文本和图片，"related"型的MimeMultipart对象
		MimeMultipart contentMulti = new MimeMultipart("related");

		// 正文的文本部分
		MimeBodyPart textBody = new MimeBodyPart();
		textBody.setContent(body, "text/html;charset=gbk");
		contentMulti.addBodyPart(textBody);

		// 正文的图片部分
		MimeBodyPart jpgBody = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(fileName);
		jpgBody.setDataHandler(new DataHandler(fds));
		jpgBody.setContentID("logo_jpg");
		contentMulti.addBodyPart(jpgBody);

		// 将上面"related"型的 MimeMultipart 对象作为邮件的正文
		contentBody.setContent(contentMulti);
		return contentBody;
	}

	/**
	 * 只传正文
	 */
	public MimeBodyPart createContent(String body) throws Exception {
		// 用于保存最终正文部分
		MimeBodyPart contentBody = new MimeBodyPart();
		// 用于组合文本和图片，"related"型的MimeMultipart对象
		MimeMultipart contentMulti = new MimeMultipart("related");

		// 正文的文本部分
		MimeBodyPart textBody = new MimeBodyPart();
		textBody.setContent(body, "text/html;charset=gbk");
		contentMulti.addBodyPart(textBody);

		// 将上面"related"型的 MimeMultipart 对象作为邮件的正文
		contentBody.setContent(contentMulti);
		return contentBody;
	}

	/**
	 * 根据传入的 Seesion 对象创建混合型的 MIME消息
	 */
	public MimeMessage createMessage(Session session, String from, String to,
			String subject, String body, List<String> filenames)
			throws Exception {
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);
		// 将邮件中各个部分组合到一个"mixed"型的 MimeMultipart 对象
		MimeMultipart allPart = new MimeMultipart("mixed");

		// 创建邮件的各个 MimeBodyPart 部分
		if (filenames != null && filenames.size() > 0) {
			for (String filename : filenames) {
				if (TextUtils.isEmpty(filename)) {
					continue;
				}
				allPart.addBodyPart(createAttachment(filename));
			}
		}
		MimeBodyPart content = createContent(body);
		allPart.addBodyPart(content);

		// 将上面混合型的 MimeMultipart 对象作为邮件内容并保存
		msg.setContent(allPart);
		msg.saveChanges();
		return msg;
	}
}
