package net.yama.android.util;

import net.yama.android.Rendezvous;

public class CrashHandler implements Thread.UncaughtExceptionHandler{

	private Thread.UncaughtExceptionHandler previousHandler;
	private Rendezvous application;
	
	private CrashHandler(Rendezvous application) {
		this.application = application;
		previousHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	public static void init(Rendezvous application){
		new CrashHandler(application);
	}
	
	public void uncaughtException(final Thread t, final Throwable ex) {
		Helper.logCrashReport(ex,application);
		previousHandler.uncaughtException(t, ex);

	}
}
