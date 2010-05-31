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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.yama.android.managers.DataManager;
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.response.Event;
import net.yama.android.response.Group;
import net.yama.android.util.Constants;
import net.yama.android.views.adapter.EventListAdapter;
import net.yama.android.views.components.GroupInfoView;
import net.yama.android.views.components.LoadingView;
import android.app.Activity;
import android.view.View;
import android.widget.ListView;

public class GroupInfoContentFactory extends AbstractContentFactory{

	String groupId;
	public GroupInfoContentFactory(Activity applicationContext, String groupId) {
		super(applicationContext);
		this.groupId = groupId;
	}

	public View createTabContent(String tag) {
		
		View contentView = null;
		try {
			if(Constants.GROUP_INFO_TAB_ID.equalsIgnoreCase(tag))
				contentView = getGroupInfoView();
			else if (Constants.GROUP_MEETUPS_TAB_ID.equalsIgnoreCase(tag))
				contentView = getGroupEventsView();
			else
				contentView = getNullView();
		} catch (Exception e) {
			contentView = getExceptionView(e);
		}
		
		return contentView;
	}

	@SuppressWarnings("unchecked")
	private View getGroupEventsView() throws ApplicationException {
		
		LoadingView view = new LoadingView(context) {
			
			@Override
			public View getResultsView() throws ApplicationException {
				
				List eventsList = DataManager.getAllEvents();
				List relevantEvents = filterEventsForGroup(eventsList);
				
				if(relevantEvents.isEmpty())
					return getNoDataView("No meetups scheduled for this group!");
				
				ListView eventsView  = new ListView(context);
				EventListAdapter adapter = new EventListAdapter(relevantEvents,context);
				eventsView.setAdapter(adapter);
				return eventsView;
			}
		};
		
		return view;
	}

	private List filterEventsForGroup(List eventsList) {
		List relevantEvents = new ArrayList();
		Iterator eventsIter = eventsList.iterator();
		while (eventsIter.hasNext()) {
			Event e = (Event) eventsIter.next();
			if(e.getGroupId().equals(this.groupId))
				relevantEvents.add(e);
		}
		return relevantEvents;
	}

	private View getGroupInfoView() {

		LoadingView view = new LoadingView(context) {
			
			@Override
			public View getResultsView() throws ApplicationException {
				
				Group group = DataManager.getGroup(groupId);
				GroupInfoView groupInfoView = new GroupInfoView(context, group);
				return groupInfoView;
			}
		};
		
		return view;
	}

}
