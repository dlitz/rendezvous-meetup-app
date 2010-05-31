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

import java.util.Comparator;

import net.yama.android.util.Constants;

import org.json.JSONObject;

import android.graphics.Color;

public class Rsvp extends BaseResponse {

	public static class RsvpComparator implements Comparator<Rsvp> {

		public int compare(Rsvp rsvp1, Rsvp rsvp2) {
			
			RsvpResponse response1 = rsvp1.getResponse();
			RsvpResponse response2 = rsvp2.getResponse();
			
			if(response1.equals(response2))
				return rsvp1.getName().compareTo(rsvp2.getName());
			
			if(response1.ordinal() > response2.ordinal())
				return 1;
			else
				return -1;
			
		}

	}

	private String memberId;
	private String comment;
	private RsvpResponse response;
	
	
	public enum RsvpResponse {
		YES("Yes","All those who say Ye!",Color.rgb(46,139,87)),
		NO("No","All those who say Nay!",Color.RED),
		MAYBE("Maybe","All those who are sitting on the fence!",Color.rgb(255,165,0));
		
		private String idString;
		private String description;
		private int color;
		
		RsvpResponse(String idString,String description, int color){
			this.idString = idString;
			this.description = description;
			this.color = color;
		}

		public String getIdString() {
			return idString;
		}

		

		public String getDescription() {
			return description;
		}

		public int getColor() {
			return color;
		}
	
	}
	
	public Rsvp() {
	}

	@Override
	public void convertFromJSON(BaseResponse b, JSONObject json) {
		b.convertCommon(b, json);
		Rsvp rsvp = (Rsvp) b;
		rsvp.comment = json.optString(Constants.RESPONSE_PARAM_COMMENT);
		rsvp.memberId = json.optString(Constants.RESPONSE_PARAM_ID);
		rsvp.response = RsvpResponse.valueOf(json.optString(Constants.RESPONSE_PARAM_RSVP_RESPONSE).toUpperCase());
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public RsvpResponse getResponse() {
		return response;
	}

	public void setResponse(RsvpResponse response) {
		this.response = response;
	}
	
	
	
	

}
