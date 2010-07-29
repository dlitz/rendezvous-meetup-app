package net.yama.android.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import net.yama.android.Rendezvous;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class CrashHandler implements Thread.UncaughtExceptionHandler{

	private Thread.UncaughtExceptionHandler previousHandler;
	private Rendezvous application;
	private Dialog dialog;
	
	private CrashHandler(Rendezvous application) {
		this.application = application;
		previousHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	public static void init(Rendezvous application){
		new CrashHandler(application);
	}
	
	public void uncaughtException(final Thread t, final Throwable ex) {

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		String subject = "Rendezvous Crash Report";
		String body = getCrashReport(ex);
		sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"rohit.kumbhar+rendezvous@gmail.com" });
		sendIntent.putExtra(Intent.EXTRA_TEXT, body);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		sendIntent.setType("message/rfc822");
		CrashHandler.this.application.startActivity(sendIntent);
		previousHandler.uncaughtException(t, ex);

	}

	private String getCrashReport(Throwable e) {
		
		StringBuffer body = new StringBuffer();
		body.append("I'm sorry Rendezvous crashed. Please send in this crash report. It'll help me fix the problem.\n\n");
		body.append("** Crash Report **\n");
		try {
			PackageInfo pi = this.application.getPackageManager().getPackageInfo(this.application.getPackageName(), 0);
			body.append("Package Name: ").append(pi.packageName).append("\n");
			body.append("Package Version: ").append(pi.versionCode).append("\n");
			body.append("Phone Model: ").append(android.os.Build.MODEL).append("\n");
			body.append("Phone Manufacturer: ").append(android.os.Build.MANUFACTURER).append("\n");
			body.append("Android Version:").append(android.os.Build.VERSION.RELEASE).append("\n");
		} catch (NameNotFoundException e1) {
		}
		
		StringWriter stack = new StringWriter();
		PrintWriter writer = new PrintWriter(stack);
		e.printStackTrace(writer);
		
		body.append("\n\nStacktrace:\n\n");
		body.append(stack.toString()).append("\n");
		
		if(e.getCause() != null){
			Throwable cause = e.getCause();
			stack = new StringWriter();
			writer = new PrintWriter(stack);
			cause.printStackTrace(writer);
			
			body.append("\n\nCause Stacktrace:\n\n");
			body.append(stack.toString()).append("\n");
		}
		
		body.append("** Crash Report **\n");
		return body.toString();
	}
}
