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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.yama.android.response.Rsvp;
import net.yama.android.response.Rsvp.RsvpResponse;
import net.yama.android.views.components.InfoRowView;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class RsvpListAdapter implements ListAdapter{

	List data;
	Context context;
	Map<RsvpResponse, List<Rsvp>> dataMap;
	int yesLabelPosition = 0;
	int noLabelPosition = 0;
	int maybeLabelPosition = 0;
	int dataIndex = 0;
	
	public RsvpListAdapter(List data, Context ctx) {
		this.context = ctx;
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public RsvpListAdapter(Map<RsvpResponse, List<Rsvp>> splitRsvpMap,Context context2) {
		this.dataMap = splitRsvpMap;
		this.context = context2;
		this.data = new ArrayList();
		this.data.addAll(splitRsvpMap.get(RsvpResponse.YES));
		this.data.addAll(splitRsvpMap.get(RsvpResponse.MAYBE));
		this.data.addAll(splitRsvpMap.get(RsvpResponse.NO));
	}

	public boolean areAllItemsEnabled() {
		return true;
	}

	public boolean isEnabled(int position) {
		return true;
	}

	public int getCount() {
		int yesSize = dataMap.get(RsvpResponse.YES).size();
		int noSize = dataMap.get(RsvpResponse.NO).size();
		int maybeSize = dataMap.get(RsvpResponse.MAYBE).size();
		this.yesLabelPosition = 0;
		this.maybeLabelPosition = yesSize + 1;
		this.noLabelPosition = maybeLabelPosition + maybeSize + 1;
		return 3 + yesSize + noSize + maybeSize;
	}

	public Object getItem(int position) {
		Rsvp rsvp = (Rsvp) data.get(position);
		return rsvp;
	}

	public long getItemId(int position) {
		
		if(position == 0)
			return -1;
		else if(position == this.maybeLabelPosition)
			return -2;
		else if(position == this.noLabelPosition)
			return -3;
		
		int index = getRealIndex(position);
		Rsvp rsvp = (Rsvp) data.get(index);
		return Long.valueOf(rsvp.getId());
	}

	public int getItemViewType(int position) {
		return IGNORE_ITEM_VIEW_TYPE;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(position == 0)
			return getTextView(RsvpResponse.YES,-1);
		else if(position == this.maybeLabelPosition)
			return getTextView(RsvpResponse.MAYBE,-2);
		else if(position == this.noLabelPosition)
			return getTextView(RsvpResponse.NO,-3);
		
		Rsvp rsvp = (Rsvp) data.get(getRealIndex(position));
		InfoRowView view = new InfoRowView(this.context,
										   rsvp.getName(),
										   rsvp.getComment(),
										   rsvp.getPhotoURL(),
										   Integer.valueOf(rsvp.getId()));
		view.setNoClicks();
		return view;
		
	}

	private int getRealIndex(int position) {
		int index = position;
		if(position > this.noLabelPosition)
			index = position - 3;
		else if(position > this.maybeLabelPosition)
			index = position - 2;
		else if(position > this.yesLabelPosition)
			index--;
		
		return index;
	}

	private View getTextView(RsvpResponse responseType, int id) {
		InfoRowView view = new InfoRowView(context, responseType.getIdString(),responseType.getDescription(),id);
		view.setClickable(false);
		view.getMainTextView().setTextColor(responseType.getColor());
		return view;
	}

	public int getViewTypeCount() {
		return 1;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isEmpty() {
		return (data == null || data.isEmpty());
	}

	public void registerDataSetObserver(DataSetObserver observer) {
	}

	public void unregisterDataSetObserver(DataSetObserver observer) {
	}


}
