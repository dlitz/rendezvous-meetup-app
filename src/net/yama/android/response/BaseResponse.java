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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.yama.android.util.Constants;

import org.json.JSONObject;

/**
 * @author Rohit Kumbhar
 *
 */
public abstract class BaseResponse  {

	private static final SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
	
	private String id;
	private String name;
	private String link;
	private String photoURL;
	private String city;
	private String state;
	private String country;
	private String zip;
	private Double latitude;
	private Double longitude;
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getLink() {
		return link;
	}



	public void setLink(String link) {
		this.link = link;
	}



	public String getPhotoURL() {
		return photoURL;
	}



	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getZip() {
		return zip;
	}



	public void setZip(String zip) {
		this.zip = zip;
	}



	public Double getLatitude() {
		return latitude;
	}



	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}



	public Double getLongitude() {
		return longitude;
	}



	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}



	public abstract void convertFromJSON(BaseResponse b, JSONObject json);



	public void convertCommon(BaseResponse b, JSONObject json) {
		
		b.setId(json.optString(Constants.RESPONSE_PARAM_ID));
		b.setName(json.optString(Constants.RESPONSE_PARAM_NAME));
		b.setPhotoURL(json.optString(Constants.RESPONSE_PARAM_PHOTO_URL));
		b.setLatitude(json.optDouble(Constants.RESPONSE_PARAM_LATITUDE));
		b.setLongitude(json.optDouble(Constants.RESPONSE_PARAM_LONGITUDE));
		
	}



	public Date formatAndSetDate(String dateString) {
		
		try {
			return format.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
		
	}



	public boolean getBoolean(int optInt) {
		if(optInt == 1)
			return true;
		
		return false;
	}
	
}

