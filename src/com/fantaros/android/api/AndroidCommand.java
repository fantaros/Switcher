package com.fantaros.android.api;

import java.io.DataOutputStream;

public final class AndroidCommand {
	public static String lastError = "";
	
	public static int execRooted(String command) {
		return exec(command, true);
	}

	public static int exec(String command) {
		return exec(command, false);
	}

	protected static int exec(String command, boolean isNeedRoot) {
		try {
			Process androidCommand = null;
			if (isNeedRoot) {
				androidCommand = Runtime.getRuntime().exec("su");
			} else {
				androidCommand = Runtime.getRuntime().exec(
						"echo AutoCommand v0.1");
			}
			DataOutputStream output = new DataOutputStream(
					androidCommand.getOutputStream());
			output.writeBytes(command + "\n");
			output.flush();
			output.writeBytes("exit\n");
			output.flush();
			androidCommand.waitFor();
			return androidCommand.exitValue();
		} catch (Exception e) {
			e.printStackTrace();
			lastError = e.getLocalizedMessage();
			return -1;
		}
	}

	protected static boolean isRooted() {
		int i = exec("echo test Root", true);
		if (i != -1) {
			return true;
		} else {
			return false;
		}
	}
}
