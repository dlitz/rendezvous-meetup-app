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
package net.yama.android.views.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import net.yama.android.response.Event;
import net.yama.android.views.components.InfoRowView;
import net.yama.android.views.listeners.EventListClickListener;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class EventListAdapter extends AbstractListAdapter{

	
	EventListClickListener listener;
	static SimpleDateFormat subTextFormat = new SimpleDateFormat("MM/dd hh:mm a");
	
	public EventListAdapter(List data, Context ctx) {
		super(data, ctx);
		this.listener = new EventListClickListener(ctx);
	}

	public Object getItem(int position) {
		Event event = (Event) data.get(position);
		return event;
	}

	public long getItemId(int position) {
		Event event = (Event) data.get(position);
		return Long.valueOf(event.getId());
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Event event = (Event) data.get(position);
		InfoRowView view = new InfoRowView(this.context,
										   event.getName(),
										   event.getGroupName() + " - " + subTextFormat.format(event.getEventTime()),
										   event.getPhotoURL(),
										   Integer.valueOf(event.getId()));
		view.setOnClickListener(listener);
		return view;
	}
}
