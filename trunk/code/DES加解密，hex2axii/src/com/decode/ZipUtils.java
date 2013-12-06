package com.decode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class ZipUtils {

	// 解压缩
	public static byte[] uncompress(byte[] bb) throws IOException {
		if (bb == null || bb.length == 0) {
			return bb;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(bb);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toByteArray();
	}

}
