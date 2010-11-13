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
package net.yama.android.views.components;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.yama.android.response.Event;
import net.yama.android.response.Venue;
import net.yama.android.util.Constants;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * @author Rohit Kumbhar
 *
 */
public class EventInfoView extends TableLayout {

	static final SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM");
	static final SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
	
	/**
	 * @param context
	 */
	public EventInfoView(Context context, Event event) {
		super(context);
		
		TextView titleView = new TextView(context);
		titleView.setText(event.getName());
		titleView.setTypeface(Typeface.DEFAULT_BOLD);
		titleView.setPadding(2, 2, 2, 2);
		titleView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		this.addView(titleView,0);
		
		TableRow dateVenueDisplay = new TableRow(context);
		DateDisplay dateDisplay = new DateDisplay(context, event.getEventTime());
		dateDisplay.setPadding(10, 10, 10, 10);
		VenueDisplay venueDisplay = new VenueDisplay(context, event.getVenue());
		venueDisplay.setPadding(10, 0, 1, 0);
		dateVenueDisplay.addView(dateDisplay);
		dateVenueDisplay.addView(venueDisplay);
		this.addView(dateVenueDisplay,1);
		
		TextView descriptionTitle = new TextView(context);
		descriptionTitle.setText("Description");
		descriptionTitle.setTypeface(Typeface.DEFAULT_BOLD);
		descriptionTitle.setPadding(2, 2, 2, 2);
		this.addView(descriptionTitle,2);
		
		ScrollView scrollView = new ScrollView(context);
		TextView descriptionView = new TextView(context);
		descriptionView.setText(sanitize(event.getDescription()));
		descriptionView.setPadding(2, 2, 2, 2);
		descriptionView.setScrollContainer(true);
		descriptionView.setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);
		Linkify.addLinks(descriptionView, Constants.LINK_MASK);
		scrollView.addView(descriptionView);
		
		this.addView(scrollView,3);
	}

	// XXX - Duplicated in EventInfoView
	private CharSequence sanitize(String description) {
		description = description.replaceAll("\r\n", "\n");
		description = description.replaceAll("<br>", "\n");
		description = description.replaceAll("<br />", "\n");
		return description;
	}

	private boolean isNotEmpty(String str) {
		return (str != null && str.length() > 0);
	}

	public class VenueDisplay extends TextView{
		public VenueDisplay(Context context, Venue venue) {
			super(context);

			StringBuilder venueString = new StringBuilder();

			// Address text
			int addressStart, addressEnd;
			addressStart = addressEnd = 0;
			String venueAddressString = getVenueAddressString(venue);
			if(isNotEmpty(venueAddressString)) {
				venueString.append("Location:\n");
				addressStart = venueString.length();
				if (isNotEmpty(venue.getVenueName())) {
					venueString.append(venue.getVenueName());
					venueString.append("\n");
				}
				venueString.append(venueAddressString);
				addressEnd = venueString.length();
			}

			// Phone text
			int phoneStart, phoneEnd;
			phoneStart = phoneEnd = 0;
			if(isNotEmpty(venue.getVenuePhone())) {
				venueString.append("Phone: ");
				phoneStart = venueString.length();
				venueString.append(venue.getVenuePhone());
				phoneEnd = venueString.length();
			}

			// Skip if we did nothing
			if(venueString.length() == 0) {
				setText("Location:\nNot chosen yet.");
				return;
			}

			// Set text
			setText(venueString.toString(), BufferType.SPANNABLE);

			// Add address link
			if (addressStart != addressEnd) {
				URLSpan urlSpan = new URLSpan(getVenueGeoURL(venue));
				Spannable text = (Spannable) getText();
				text.setSpan(urlSpan, addressStart, addressEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			// Add phone link
			if (phoneStart != phoneEnd) {
				URLSpan urlSpan = new URLSpan(getVenueTelURL(venue));
				Spannable text = (Spannable) getText();
				text.setSpan(urlSpan, phoneStart, phoneEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			// Make links clickable
			setMovementMethod(LinkMovementMethod.getInstance());
		}

		private String getVenueAddressString(Venue venue) {
			StringBuilder addressString = new StringBuilder();

			if(isNotEmpty(venue.getVenueAddress1()))
				addressString.append(venue.getVenueAddress1()).append("\n");

			if(isNotEmpty(venue.getVenueAddress2()))
				addressString.append(venue.getVenueAddress2()).append("\n");

			if(isNotEmpty(venue.getVenueAddress3()))
				addressString.append(venue.getVenueAddress3()).append("\n");

			if(isNotEmpty(venue.getVenueCity()))
				addressString.append(venue.getVenueCity()).append(", ");

			if(isNotEmpty(venue.getVenueState()))
				addressString.append(venue.getVenueState()).append("\n");

			if(addressString.length() > 0)
				return addressString.toString();
			else
				return null;
		}

		private String getVenueGeoURL(Venue venue) {
			StringBuilder sb = new StringBuilder();
			sb.append("geo:");
			if (venue.getVenueLatitude() != null && venue.getVenueLongitude() != null) {
				sb.append(venue.getVenueLatitude().toString());
				sb.append(",");
				sb.append(venue.getVenueLongitude().toString());
			} else {
				sb.append("0,0");
			}

			// XXX - This is an Android-specific, non-RFC 5870-conformant hack to make
			// Google Maps show a pin for the location, which is needed to allow getting
			// directions and in-car navigation.
			sb.append("?q=");
			sb.append(URLEncoder.encode(getVenueAddressString(venue)));

			return sb.toString();
		}

		private String getVenueTelURL(Venue venue) {
			if (isNotEmpty(venue.getVenuePhone()))
				return "tel:" + venue.getVenuePhone();
			else
				return null;
		}
	}


	public class DateDisplay extends TableLayout{
		
		public DateDisplay(Context context, Date theDate) {
			super(context);
			
			Calendar c = Calendar.getInstance();
			c.setTime(theDate);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			String month = monthFormatter.format(theDate);
			String dayStr = String.valueOf(day);  
			String timeStr = timeFormatter.format(theDate);
			
			TextView monthView = new TextView(context);
			monthView.setText(month);
			monthView.setTextSize(14.0F);
			monthView.setTypeface(Typeface.DEFAULT_BOLD);
			monthView.setGravity(Gravity.CENTER_HORIZONTAL);
			monthView.setTextColor(Color.WHITE);
			monthView.setBackgroundColor(Color.rgb(204, 0, 0));
			monthView.setMaxHeight(15);
			
			TextView dayView = new TextView(context);
			dayView.setText(dayStr);
			dayView.setTextSize(25.0F);
			dayView.setBackgroundColor(Color.WHITE);
			dayView.setTextColor(Color.DKGRAY);
			dayView.setGravity(Gravity.CENTER_HORIZONTAL);
			monthView.setMinHeight(25);
			
			TextView timeView = new TextView(context);
			timeView.setText(timeStr);
			timeView.setTextSize(14.0F);
			timeView.setTextColor(Color.WHITE);
			timeView.setGravity(Gravity.CENTER_HORIZONTAL);
			timeView.setMinHeight(15);
			
			this.addView(monthView, 80,30);
			this.addView(dayView, 80,60);
			this.addView(timeView, 80,30);
			this.setPadding(20,20,20,20);
			
		}
		
	}

}
