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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.yama.android.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Rohit Kumbhar
 *
 */
public class Event extends BaseResponse {

	private static final long serialVersionUID = 5728057514216386757L;
	private static final String MY_WAITLIST_STATUS = "my_waitlist_status";
	private String description;
	private Date eventTime;
	private Date updated;
	private Venue venue;
	private String findingInstructions;
	private MyRsvp myrsvp;
	private String eventURL;
	private int rsvpCount;
	private int maybeRsvpCount;
	private int noRsvpCount;
	private int guestLimit;
	private boolean isMeetup;
	private boolean allowMaybeRsvp;
	private float fee;
	private String feeCurrency;
	private String feeDescription;
	private int attendeeCount;
	private String organizerId;
	private String organizerName;
	private String groupName;
	private String groupId;
	private boolean rsvpClosed;
	private int rsvpLimit;
	private String rsvpCutoff;
	private String status;
	private Rsvpable rsvpable;
	private long utcTime;
	private long rsvpOpenTime;
	private long rsvpCutoffTime;
	
	List<EventHost> hosts;
	
	class EventHost implements Serializable{
		private static final long serialVersionUID = 2278018280625348220L;
		EventHost(JSONObject json) {
			this.memberId = json.optString(Constants.RESPONSE_PARAM_MEMBER_ID);
			this.memberName = json.optString(Constants.RESPONSE_PARAM_MEMBER_NAME);
		}
		public String memberId;
		public String memberName;
	}
	
	public enum MyRsvp {
		YES,
		NO,
		MAYBE,
		WAITINGLIST,
		NONE,
		PAST_DEADLINE,
		NOT_OPEN_YET
	}
	
	public enum Rsvpable{
		FULL,
		OPEN,
		PAST_DEADLINE,
		NOT_OPEN_YET,
		CLOSED
	}
	
	public Event(JSONObject json){
		convertFromJSON(this,json);
	}
	
	public Event() {
	}
	
	@Override
	public void convertFromJSON(BaseResponse b, JSONObject json) {
		
			super.convertCommon(b, json);
			Event event = (Event) b;
			event.description = json.optString(Constants.RESPONSE_PARAM_DESCRIPTION);
			event.findingInstructions = json.optString(Constants.RESPONSE_PARAM_FINDING_INS);
			event.eventURL = json.optString(Constants.RESPONSE_PARAM_EVENT_URL);
			event.rsvpCount = json.optInt(Constants.RESPONSE_PARAM_RSVP_COUNT);
			event.maybeRsvpCount = json.optInt(Constants.RESPONSE_PARAM_MAYBE_RSVP_COUNT);
			event.noRsvpCount = json.optInt(Constants.RESPONSE_PARAM_NO_RSVP_COUNT);
			event.guestLimit = json.optInt(Constants.RESPONSE_PARAM_GUEST_LIMIT);
			event.isMeetup = super.getBoolean(json.optInt(Constants.RESPONSE_PARAM_IS_MEETUP));
			event.allowMaybeRsvp = super.getBoolean(json.optInt(Constants.RESPONSE_PARAM_ALLOW_MAYBE_RSVP));
			event.fee = (float) json.optDouble(Constants.RESPONSE_PARAM_FEE);
			event.feeCurrency = json.optString(Constants.RESPONSE_PARAM_FEE_CURRENCY);
			event.feeDescription = json.optString(Constants.RESPONSE_PARAM_FEE_DESC);
			event.attendeeCount = json.optInt(Constants.RESPONSE_PARAM_ATTENDEE_COUNT);
			event.organizerId = json.optString(Constants.RESPONSE_PARAM_ORGANIZER_ID);
			event.organizerName = json.optString(Constants.RESPONSE_PARAM_ORGANIZER_NAME);
			event.groupName = json.optString(Constants.RESPONSE_PARAM_GROUP_NAME);
			event.groupId = json.optString(Constants.RESPONSE_PARAM_GROUP_ID);
			event.rsvpClosed = super.getBoolean(json.optInt(Constants.RESPONSE_PARAM_RSVP_CLOSED));
			event.rsvpLimit = json.optInt(Constants.RESPONSE_PARAM_RSVP_LIMIT);
			event.rsvpCutoff = json.optString(Constants.RESPONSE_PARAM_RSVP_CUTOFF);
			event.status = json.optString(Constants.RESPONSE_PARAM_STATUS);
			event.rsvpable = extractRsvpable(json.optString(Constants.RESPONSE_PARAMS_RSVPABLE));
			event.utcTime = json.optLong(Constants.UTC_TIME);
			event.eventTime = new Date(event.utcTime);
			event.rsvpOpenTime = json.optLong(Constants.RSVP_OPEN_TIME);
			event.rsvpCutoffTime = json.optLong(Constants.RSVP_CUTOFF_TIME);
			setRSVPStatus(json.optString(Constants.RESPONSE_PARAM_RSVP_STATUS),
					json.optString(MY_WAITLIST_STATUS));
			Venue venue = new Venue(json);
			event.venue = venue;
			event.hosts = new ArrayList<EventHost>();
			JSONArray eventHosts = json.optJSONArray(Constants.RESPONSE_PARAM_EVENT_HOSTS);
			for(int i = 0; i < eventHosts.length() ; i++){
				JSONObject hostData = eventHosts.optJSONObject(i);
				event.hosts.add(new EventHost(hostData));
			}
	}

	private Rsvpable extractRsvpable(String rsvpable) {
		
		try{
			if(rsvpable != null && rsvpable.length() > 0)
				return Rsvpable.valueOf(rsvpable.toUpperCase());
		} catch (Exception e) {
			return Rsvpable.CLOSED;
		}
		
		return null;
	}

	private void setRSVPStatus(String myrsvp, String mywaitlist) {
		try{
			
			if(mywaitlist != null && "waitlist".equalsIgnoreCase(mywaitlist))
				this.myrsvp = MyRsvp.WAITINGLIST;
			else if(myrsvp != null && myrsvp.length() > 0)
				this.myrsvp = MyRsvp.valueOf(myrsvp.toUpperCase());
			else
				this.myrsvp = MyRsvp.NONE;
			
		}catch (Exception e) {
			this.myrsvp = MyRsvp.NONE;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public String getFindingInstructions() {
		return findingInstructions;
	}

	public void setFindingInstructions(String findingInstructions) {
		this.findingInstructions = findingInstructions;
	}

	public MyRsvp getMyRsvp() {
		return myrsvp;
	}

	public void setRsvpStatus(MyRsvp rsvpStatus) {
		this.myrsvp = rsvpStatus;
	}

	public String getEventURL() {
		return eventURL;
	}

	public void setEventURL(String eventURL) {
		this.eventURL = eventURL;
	}

	public int getRsvpCount() {
		return rsvpCount;
	}

	public void setRsvpCount(int rsvpCount) {
		this.rsvpCount = rsvpCount;
	}

	public int getMaybeRsvpCount() {
		return maybeRsvpCount;
	}

	public void setMaybeRsvpCount(int maybeRsvpCount) {
		this.maybeRsvpCount = maybeRsvpCount;
	}

	public int getNoRsvpCount() {
		return noRsvpCount;
	}

	public void setNoRsvpCount(int noRsvpCount) {
		this.noRsvpCount = noRsvpCount;
	}

	public int getGuestLimit() {
		return guestLimit;
	}

	public void setGuestLimit(int guestLimit) {
		this.guestLimit = guestLimit;
	}

	public boolean isMeetup() {
		return isMeetup;
	}

	public void setMeetup(boolean isMeetup) {
		this.isMeetup = isMeetup;
	}

	public boolean isAllowMaybeRsvp() {
		return allowMaybeRsvp;
	}

	public void setAllowMaybeRsvp(boolean allowMaybeRsvp) {
		this.allowMaybeRsvp = allowMaybeRsvp;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public String getFeeCurrency() {
		return feeCurrency;
	}

	public void setFeeCurrency(String feeCurrency) {
		this.feeCurrency = feeCurrency;
	}

	public String getFeeDescription() {
		return feeDescription;
	}

	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}

	public int getAttendeeCount() {
		return attendeeCount;
	}

	public void setAttendeeCount(int attendeeCount) {
		this.attendeeCount = attendeeCount;
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

	public List<EventHost> getHosts() {
		return hosts;
	}

	public void setHosts(List<EventHost> hosts) {
		this.hosts = hosts;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isRsvpClosed() {
		return rsvpClosed;
	}

	public void setRsvpClosed(boolean rsvpClosed) {
		this.rsvpClosed = rsvpClosed;
	}

	public int getRsvpLimit() {
		return rsvpLimit;
	}

	public void setRsvpLimit(int rsvpLimit) {
		this.rsvpLimit = rsvpLimit;
	}

	public String getRsvpCutoff() {
		return rsvpCutoff;
	}

	public void setRsvpCutoff(String rsvpCutoff) {
		this.rsvpCutoff = rsvpCutoff;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Rsvpable getRsvpable() {
		return rsvpable;
	}

	public void setRsvpable(Rsvpable rsvpable) {
		this.rsvpable = rsvpable;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		
		Event e = (Event) o;
		return e.getId().equals(this.getId());
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(this.getId());
	}

	public long getUtcTime() {
		return utcTime;
	}

	public void setUtcTime(long utcTime) {
		this.utcTime = utcTime;
	}

	public long getRsvpOpenTime() {
		return rsvpOpenTime;
	}

	public void setRsvpOpenTime(long rsvpOpenTime) {
		this.rsvpOpenTime = rsvpOpenTime;
	}

	public long getRsvpCutoffTime() {
		return rsvpCutoffTime;
	}

	public void setRsvpCutoffTime(long rsvpCutoffTime) {
		this.rsvpCutoffTime = rsvpCutoffTime;
	}
	
	
	
}
