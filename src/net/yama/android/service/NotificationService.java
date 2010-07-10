/*******************************************************************
 * Copyright (c) 2010 Rohit Kumbhar
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************/
package net.yama.android.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.yama.android.R;
import net.yama.android.Rendezvous;
import net.yama.android.managers.DataManager;
import net.yama.android.managers.config.ConfigurationManager;
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.response.Event;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Service to notify user of new events
 * @author Rohit Kumbhar
 */
public class NotificationService extends Service {
	
	private static final int STICKY_SERVICE_2_0 = 1;
	private final NotificationBinder binder = new NotificationBinder();
	private NotificationManager notificationManager;
	private Timer timer;
	
	/**
	 * 
	 */
	public NotificationService() {
		// Nothing to do yet
	}


	@Override
    public void onCreate() {
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }
	
	@Override
	public void onStart(Intent intent, int startId) {
	    handleCommand(intent);
	}

	private void handleCommand(Intent intent) {

		// Timer is already started
		if(timer != null)
			return;
		
		 // Display a notification about us starting.  We put an icon in the status bar.
		TimerTask checkNewMeetups = new CheckNewMeetups();
		timer = new Timer();
		timer.schedule(checkNewMeetups, 0, ConfigurationManager.instance.getNotificationsCheckInterval());
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
	    handleCommand(intent);
	    return STICKY_SERVICE_2_0;
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(timer != null)
			timer.purge();
		
		timer = null;
	}
	
	 /**
     * Show a notification while this service is running.
     */
    public void showNotification(String message) {

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.notification, message, System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, Rendezvous.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.meetupNotification), message, contentIntent);
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.flags |=  Notification.FLAG_AUTO_CANCEL;
        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        notificationManager.notify(R.string.app_name, notification);
    }
	
	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}
	
	public class NotificationBinder extends Binder {
		NotificationService getService() {
            return NotificationService.this;
        }
	}
	
	
	/**
	 * Checks for new meetups
	 * @author Rohit Kumbhar
	 *
	 */
	private class CheckNewMeetups extends TimerTask {

		Set<String> cachedEvents;
		
		@Override
		public void run() {

			
			// Only if config is enabled
			if(!ConfigurationManager.instance.isNotificationsFlag())
				return;
			
			
			List allEvents = null;
			try {
				allEvents = DataManager.getAllEvents();
			} catch (ApplicationException e) {
				return;
			}

			if (cachedEvents == null) {
				cachedEvents = new HashSet<String>();
				removePastEvents(allEvents,cachedEvents);
			} else {
				
				Set<String> newerEvents = new HashSet<String>();
				removePastEvents(allEvents, newerEvents);
				Set<String> newEventIds = new HashSet<String>();
				
				boolean isNew = true;
				for(String eventId : newerEvents){
					isNew = true;
					for(String cachedEventId : cachedEvents){
						if(eventId.equals(cachedEventId)) {
							isNew = false;
							break;
						}
					}
					
					if(isNew)
						newEventIds.add(eventId);
				}
				
				if(!newEventIds.isEmpty()){
					// We need a notification
					notify(allEvents,newEventIds);
					cachedEvents.clear();
					cachedEvents.addAll(newerEvents);
				}
			}
		}
		
		private void notify(List allEvents, Set<String> newEventIds) {
			
			CharSequence suffix = null;
			if(newEventIds.size() == 1)
				suffix = getText(R.string.newEvent);
			else
				suffix = getText(R.string.newEvents);
			
			String message = String.valueOf(newEventIds.size()) + " " + suffix;
			showNotification(message);
		}

		/**
		 * Removes all events occuring in the past
		 * @param allEvents
		 */
		private void removePastEvents(List<Event> allEvents, Set<String> store) {
			
			Iterator<Event> iter = allEvents.iterator();
			Date now = new Date();
			while (iter.hasNext()) {
				Event event = (Event) iter.next();
				if(event.isMeetup() && event.getEventTime().after(now))
					store.add(event.getId());
				else
					iter.remove();
			}
		}
	}

}
