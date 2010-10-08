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
package net.yama.android.util;

import java.util.Comparator;

import net.yama.android.managers.config.ConfigurationManager;
import net.yama.android.response.Group;

/**
 * Puts the groups organized by the user first.
 * @author Rohit Kumbhar (for Stefen Meier)
 */
public class OrganizerGroupComparator implements Comparator<Group> {

	public int compare(Group group1, Group group2) {
		String myId = ConfigurationManager.instance.getMemberId();
		if ((group2.getOrganizerId().equals(myId)) && (!group1.getOrganizerId().equals(myId)))
			return 1;
		else if ((!group2.getOrganizerId().equals(myId)) && (group1.getOrganizerId().equals(myId)))
			return -1;
		else
			return group1.getName().compareTo(group2.getName());
	}
}
