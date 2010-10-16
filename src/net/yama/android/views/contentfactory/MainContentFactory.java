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

import java.util.Collections;
import java.util.List;

import net.yama.android.Rendezvous;
import net.yama.android.managers.DataManager;
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.response.Activity;
import net.yama.android.response.Event;
import net.yama.android.util.OrganizerGroupComparator;
import net.yama.android.util.filters.Filter;
import net.yama.android.util.filters.events.AffirmativeRsvpFilter;
import net.yama.android.util.filters.events.NegativeRsvpFilter;
import net.yama.android.util.filters.events.OrganizerFilter;
import net.yama.android.util.filters.events.WaitingListFilter;
import net.yama.android.views.adapter.ActivityListAdapter;
import net.yama.android.views.adapter.EventListAdapter;
import net.yama.android.views.adapter.GroupsListAdapter;
import net.yama.android.views.components.LoadingView;
import android.view.View;
import android.widget.ListView;

public class MainContentFactory extends AbstractContentFactory {

	Rendezvous context;

	public MainContentFactory(Rendezvous ctx) {
		super(ctx);
		this.context = ctx;
	}

	public View getListView(int selectedIndex) {

		View view = null;

		try {

			switch (selectedIndex) {
			case 0: // Groups view
				view = getGroupsView();
				break;
			case 1: // ALl Events
				view = getEventsView(null);
				break;
			case 2: // Events with RSVP - Yes
				view = getEventsView(AffirmativeRsvpFilter.instance);
				break;
			case 3: // Events with RSVP - No
				view = getEventsView(NegativeRsvpFilter.instance);
				break;
			case 4: // Waitlisted events
				view = getEventsView(WaitingListFilter.instance);
				break;
			case 5: // Events with user as organizer
				view = getEventsView(OrganizerFilter.instance);
				break;
			case 6: // Activity
				view = getActivityView();
				break;	
			default:
				view = getNullView();
				break;
			}

		} catch (ApplicationException e) {
			view = getExceptionView(e);
		}
		return view;
	}

	private View getEventsView(final Filter<Event> filter) {

		LoadingView view = new LoadingView(context) {

			@SuppressWarnings("unchecked")
			@Override
			public View getResultsView() throws ApplicationException {

				List<Event> eventsList = DataManager.getAllEvents();
				List<Event> filteredEvents = null;
				
				if(filter != null)
					filteredEvents = filter.apply(eventsList);
				else
					filteredEvents = eventsList;

				if (filteredEvents.isEmpty())
					return getNoDataView("No meetups scheduled for this group!");

				ListView eventsView = new ListView(context);
				EventListAdapter adapter = new EventListAdapter(filteredEvents, context);
				eventsView.setAdapter(adapter);
				return eventsView;
			}
		};

		return view;
	}

	private View getActivityView() throws ApplicationException {

		LoadingView view = new LoadingView(context) {

			@SuppressWarnings("unchecked")
			@Override
			public View getResultsView() throws ApplicationException {
				List<Activity> activities = DataManager.getAllActivity();
				ListView activityView = new ListView(context);
				ActivityListAdapter adapter = new ActivityListAdapter(activities, context);
				activityView.setAdapter(adapter);
				return activityView;
			}
		};

		return view;
	}

	private View getGroupsView() throws ApplicationException {

		LoadingView view = new LoadingView(context) {
			@SuppressWarnings("unchecked")
			@Override
			public View getResultsView() throws ApplicationException {
				List groupsList = DataManager.getAllGroups();
				Collections.sort(groupsList, new OrganizerGroupComparator());

				ListView groupsView = new ListView(context);
				GroupsListAdapter adapter = new GroupsListAdapter(groupsList, context);
				groupsView.setAdapter(adapter);
				return groupsView;
			}
		};

		return view;

	}

	@Override
	public View createTabContent(String tag) {
		throw new RuntimeException("This method should not be called.");
	}
}
