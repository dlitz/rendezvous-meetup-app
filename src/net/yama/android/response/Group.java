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
package net.yama.android.response;

import java.util.Date;

import net.yama.android.util.Constants;

import org.json.JSONObject;

public class Group extends BaseResponse {

	private Date updated;
	private Date created;
	private String urlName;
	private int numberOfMembers;
	private String description;
	private String organizerProfileURL;
	private String visibility;
	private String organizerId;
	private String organizerName;
	// We need to deal with topics

	public Group() {
	}
	
	public Group(JSONObject json){
		convertFromJSON(this,json);
	}
	
	
	@Override
	public void convertFromJSON(BaseResponse b, JSONObject json) {
		
		super.convertCommon(b, json);
		Group group = (Group) b;
		group.setLink(json.optString(Constants.RESPONSE_PARAM_LINK));
		group.numberOfMembers = json.optInt(Constants.RESPONSE_PARAM_MEMBERS);
		group.description = json.optString(Constants.RESPONSE_PARAM_DESCRIPTION);
		group.organizerProfileURL = json.optString(Constants.RESPONSE_PARAM_ORGANIZER_PROFILE_URL);
		group.visibility = json.optString(Constants.RESPONSE_PARAM_VISIBILITY);
		group.organizerId = json.optString(Constants.RESPONSE_PARAM_ORGANIZER_ID);
		group.organizerName = json.optString(Constants.RESPONSE_PARAM_ORGANIZER_NAME);
		group.setCity(json.optString(Constants.RESPONSE_PARAM_CITY));
		group.setState(json.optString(Constants.RESPONSE_PARAM_STATE));
		group.setCountry(json.optString(Constants.RESPONSE_PARAM_COUNTRY));
		group.setZip(json.optString(Constants.RESPONSE_PARAM_ZIP));
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public int getNumberOfMembers() {
		return numberOfMembers;
	}

	public void setNumberOfMembers(int numberOfMembers) {
		this.numberOfMembers = numberOfMembers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganizerProfileURL() {
		return organizerProfileURL;
	}

	public void setOrganizerProfileURL(String organizerProfileURL) {
		this.organizerProfileURL = organizerProfileURL;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getOrganizerId() {
		return organizerId;
	}

	public void setOrganizerId(String organizerId) {
		this.organizerId = organizerId;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}
	
}
