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
package net.yama.android.views.activity;

import java.util.ArrayList;
import java.util.List;

import net.yama.android.R;
import net.yama.android.util.Constants;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

/**
 * Preferences for the application
 * @author Rohit Kumbhar
 */
public class RendezvousPreferences extends PreferenceActivity {

	List<String> calEntries = new ArrayList<String>();
	List<String> calEntryValues = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		loadCalendars();
		ListPreference calPref = (ListPreference) findPreference(Constants.REMINDER_CAL_ID_KEY);
		CharSequence[] entryArray = new String[calEntries.size()];
		calPref.setEntries( calEntries.toArray(entryArray));
		
		CharSequence[] entryValuesArray = new String[calEntryValues.size()];
		calPref.setEntryValues((CharSequence[]) calEntryValues.toArray(entryValuesArray));
		
		if(calEntryValues.size() == 1){
			calPref.setValue(calEntryValues.get(0));
		}
		
	}

	private void loadCalendars() {
		String[] projection = new String[] { "_id", "name" };
		Uri calendars = Uri.parse("content://calendar/calendars");

		Cursor managedCursor = managedQuery(calendars, projection, "selected=1", null, null);

		if (managedCursor.moveToFirst()) {
			String calName;
			String calId;
			int nameColumn = managedCursor.getColumnIndex("name");
			int idColumn = managedCursor.getColumnIndex("_id");
			do {
				calName = managedCursor.getString(nameColumn);
				calId = managedCursor.getString(idColumn);
				calEntries.add(calName);
				calEntryValues.add(calId);
			} while (managedCursor.moveToNext());
		}
		
		
	}
}
