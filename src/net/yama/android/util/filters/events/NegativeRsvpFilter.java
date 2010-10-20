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
package net.yama.android.util.filters.events;

import java.util.ArrayList;
import java.util.List;

import net.yama.android.response.Event;
import net.yama.android.response.Event.MyRsvp;
import net.yama.android.util.filters.Filter;

/**
 * Returns a list of events that the user has RSVPed NO.
 * @author Rohit Kumbhar
 */
public class NegativeRsvpFilter implements Filter<Event> {

	public static NegativeRsvpFilter instance  = new NegativeRsvpFilter();
	private NegativeRsvpFilter(){
		
	}
	
	public List<Event> apply(List<Event> list) {
		List<Event> filterResult = new ArrayList<Event>();
		
		for(Event e : list){
			if(e.getMyRsvp() != null && MyRsvp.NO.equals(e.getMyRsvp()))
				filterResult.add(e);
		}
		return filterResult;
	}

}
