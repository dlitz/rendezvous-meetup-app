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
package net.yama.android.views.contentfactory;

import java.util.List;
import java.util.Map;

import net.yama.android.managers.DataManager;
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.response.Event;
import net.yama.android.response.Rsvp;
import net.yama.android.response.Rsvp.RsvpResponse;
import net.yama.android.util.Constants;
import net.yama.android.util.Helper;
import net.yama.android.views.adapter.RsvpListAdapter;
import net.yama.android.views.components.EventCommentsView;
import net.yama.android.views.components.EventInfoView;
import net.yama.android.views.components.EventRsvpView;
import net.yama.android.views.components.LoadingView;

import org.json.JSONException;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

/**
 * @author Rohit Kumbhar
 *
 */
public class EventInfoContentFactory extends AbstractContentFactory{

	
	
	String eventId;
	/**
	 * 
	 */
	public EventInfoContentFactory(Activity ctx, String eventId) {
		super(ctx);
		this.eventId = eventId;
	}

	public View createTabContent(String tag) {
		View contentView = null;
		try {
		if(Constants.MEETUP_INFO_TAB_ID.equalsIgnoreCase(tag))
			contentView = getEventInfoView();
		else if (Constants.MEETUP_RSVP_TAB_ID.equalsIgnoreCase(tag))
			contentView = getEventRsvpView();
		else if (Constants.MEETUP_ALL_RSVP_ID.equalsIgnoreCase(tag))
			contentView = getWhosComingView();
		else if (Constants.MEETUP_TALK_TAB_ID.equalsIgnoreCase(tag))
			contentView = getEventCommentsView();
		else
			contentView = getNullView();
		} catch (Exception e) {
			contentView = getExceptionView(e);
		}
		
		return contentView;
	}

	private View getEventCommentsView() throws ApplicationException {

		LoadingView view = new LoadingView(context) {
			
			@Override
			public View getResultsView() throws ApplicationException {
				Event event = DataManager.getEvent(eventId);
				EventCommentsView view = new EventCommentsView(context,event);
				return view;
			}
		};
		
		return view;
	}

	private View getWhosComingView() throws ApplicationException, JSONException, IllegalAccessException, InstantiationException {
		
		LoadingView view = new LoadingView(context) {
			
			@SuppressWarnings("unchecked")
			@Override
			public View getResultsView() throws ApplicationException {
				ListView rsvpList = null;
				List<Rsvp> allRsvps = DataManager.getAllRsvps(eventId);
				rsvpList = new ListView(context);
				Map<RsvpResponse, List<Rsvp>> splitRsvpMap = Helper.splitRsvps(allRsvps);
				rsvpList.setAdapter(new RsvpListAdapter(splitRsvpMap, context));
				return rsvpList;
			}
		};
		
		return view;
	}

	private View getEventRsvpView() throws ApplicationException{
		
		LoadingView view = new LoadingView(context) {
			
			@Override
			public View getResultsView() throws ApplicationException {
				Event event = DataManager.getEvent(eventId);
				EventRsvpView rsvpView = new EventRsvpView(context,event);
				return rsvpView;
			}
		};
		return view;
	}

	private View getEventInfoView() throws ApplicationException, JSONException, IllegalAccessException, InstantiationException {
		
		LoadingView view = new LoadingView(context) {
			
			@Override
			public View getResultsView() throws ApplicationException {
				Event event = DataManager.getEvent(eventId);
				EventInfoView infoView = new EventInfoView(context,event);
				return infoView;
			}
		};
		
		return view;
	}
	
}

