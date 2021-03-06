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

import java.util.List;

import net.yama.android.response.Group;
import net.yama.android.views.components.InfoRowView;
import net.yama.android.views.listeners.GroupLinkClickListener;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class GroupsListAdapter extends AbstractListAdapter {

	
	GroupLinkClickListener linkClickListener;
	
	public GroupsListAdapter(List data, Context ctx) {
		super(data,ctx);
		linkClickListener = new GroupLinkClickListener(context);
	}

	public Object getItem(int position) {
		Group group = (Group) data.get(position);
		return group;
	}

	public long getItemId(int position) {
		Group group = (Group) data.get(position);
		return Long.valueOf(group.getId());
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Group group = (Group) data.get(position);
		InfoRowView view = new InfoRowView(this.context,
										   group.getName(),
										   group.getOrganizerName(),
										   group.getPhotoURL(),
										   Integer.valueOf(group.getId()));
		view.setOnClickListener(linkClickListener);
		return view;
	}

}
