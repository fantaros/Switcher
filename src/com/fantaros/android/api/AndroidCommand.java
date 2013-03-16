package com.fantaros.android.api;

import java.io.DataOutputStream;

import android.util.Log;

public final class AndroidCommand {
	private static final String TAG = "Android Command";
	private static final String VERSION = "0.1";

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
						"echo AutoCommand v" + VERSION);
			}
			DataOutputStream output = new DataOutputStream(
					androidCommand.getOutputStream());
			output.writeBytes(command + "\n");
			output.flush();
			output.writeBytes("exit\n");
			output.flush();
			androidCommand.waitFor();
			return androidCommand.exitValue();
		} catch (Throwable e) {
			Log.e(TAG, "Command failed : The original command is " + command, e);
			return -1;
		}
	}

	protected static boolean isRooted() {
		int i = exec("echo ROOT_TEST", true);
		if (i != -1) {
			return true;
		} else {
			return false;
		}
	}
}
