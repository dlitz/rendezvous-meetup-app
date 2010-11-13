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

import java.io.Serializable;

import net.yama.android.util.Constants;

import org.json.JSONObject;

public class Venue implements Serializable {

	private String venueVisibility ;
	private String venueId;
	private String venueName;
	private String venuePhone;
	private String venueAddress1;
	private String venueAddress2;
	private String venueAddress3;
	private String venueCity;
	private String venueState;
	private String venueZip;
	private String venueMapURL;
	private Double venueLatitude;
	private Double venueLongitude;
	
	
	public Venue(JSONObject json) {
		
		this.venueVisibility = json.optString(Constants.RESPONSE_PARAM_VENUE_VISIBILITY);
		this.venueId = json.optString(Constants.RESPONSE_PARAM_VENUE_ID);
		this.venueName = json.optString(Constants.RESPONSE_PARAM_VENUE_NAME);
		this.venuePhone = json.optString(Constants.RESPONSE_PARAM_VENUE_PHONE);
		this.venueAddress1 = json.optString(Constants.RESPONSE_PARAM_VENUE_ADD_1);
		this.venueAddress2 = json.optString(Constants.RESPONSE_PARAM_VENUE_ADD_2);
		this.venueAddress3 = json.optString(Constants.RESPONSE_PARAM_VENUE_ADD_3);
		this.venueCity = json.optString(Constants.RESPONSE_PARAM_VENUE_CITY);
		this.venueState = json.optString(Constants.RESPONSE_PARAM_VENUE_STATE);
		this.venueZip = json.optString(Constants.RESPONSE_PARAM_VENUE_ZIP);
		this.venueMapURL = json.optString(Constants.RESPONSE_PARAM_VENUE_MAP_URL);
		this.venueLatitude = json.optDouble(Constants.RESPONSE_PARAM_VENUE_LATITUDE);
		this.venueLongitude = json.optDouble(Constants.RESPONSE_PARAM_VENUE_LONGITUDE);
			
	}
	public String getVenueVisibility() {
		return venueVisibility;
	}
	public void setVenueVisibility(String venueVisibility) {
		this.venueVisibility = venueVisibility;
	}
	public String getVenueId() {
		return venueId;
	}
	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getVenuePhone() {
		return venuePhone;
	}
	public void setVenuePhone(String venuePhone) {
		this.venuePhone = venuePhone;
	}
	public String getVenueAddress1() {
		return venueAddress1;
	}
	public void setVenueAddress1(String venueAddress1) {
		this.venueAddress1 = venueAddress1;
	}
	public String getVenueAddress2() {
		return venueAddress2;
	}
	public void setVenueAddress2(String venueAddress2) {
		this.venueAddress2 = venueAddress2;
	}
	public String getVenueAddress3() {
		return venueAddress3;
	}
	public void setVenueAddress3(String venueAddress3) {
		this.venueAddress3 = venueAddress3;
	}
	public String getVenueCity() {
		return venueCity;
	}
	public void setVenueCity(String venueCity) {
		this.venueCity = venueCity;
	}
	public String getVenueState() {
		return venueState;
	}
	public void setVenueState(String venueState) {
		this.venueState = venueState;
	}
	public String getVenueZip() {
		return venueZip;
	}
	public void setVenueZip(String venueZip) {
		this.venueZip = venueZip;
	}
	public String getVenueMapURL() {
		return venueMapURL;
	}
	public void setVenueMapURL(String venueMapURL) {
		this.venueMapURL = venueMapURL;
	}
	public Double getVenueLatitude() {
		return venueLatitude;
	}
	public void setVenueLatitude(Double venueLatitude) {
		this.venueLatitude = venueLatitude;
	}
	public Double getVenueLongitude() {
		return venueLongitude;
	}
	public void setVenueLongitude(Double venueLongitude) {
		this.venueLongitude = venueLongitude;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(this.venueName != null ? venueName + " " : "");
		sb.append(this.venueAddress1 != null ? venueAddress1  + " ": "");
		sb.append(this.venueAddress2 != null ? venueAddress2  + " ": "");
		sb.append(this.venueCity != null ? venueCity  + " " : "");
		sb.append(this.venueState != null ? venueState  + " ": "");
		sb.append(this.venueZip != null ? venueZip  + " ": "");
		return sb.toString();
	}
	
}
