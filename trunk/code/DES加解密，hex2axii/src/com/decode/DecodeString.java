package com.decode;

public class DecodeString {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		byte[] s1 = DESEncrypt.encrypt("18019945610".getBytes(),
				"smartcity".getBytes());
		String hexString = SecurityTools.byte2HexStr(s1);
		System.out.println("src : " + hexString);

		// TODO Auto-generated method stub
		byte[] d1 = SecurityTools.hex2byte(hexString.getBytes());
		byte[] d2 = DESEncrypt.decrypt(d1, "smartcity".getBytes());
		System.out.println("res : " + new String(d2));

	}
}
