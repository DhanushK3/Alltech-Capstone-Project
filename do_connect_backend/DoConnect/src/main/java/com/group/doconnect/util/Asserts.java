package com.group.doconnect.util;

public class Asserts {
	public static void test(boolean value) {
		try {
			if (!value) throw new Exception("Testing failed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
