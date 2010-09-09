package net.yama.android.response;

import java.util.Date;

import net.yama.android.util.Constants;

import org.json.JSONObject;

public class EventRating extends BaseResponse {

	public static final String EVENT_REVIEW = "review";
	public static final String EVENT_RATING = "rating";
	private static final long serialVersionUID = -5250865356375663072L;
	private String eventId;
	private String groupId;
	private int rating;
	private String review;
	private Date reviewTime;
	
	@Override
	public void convertFromJSON(BaseResponse b, JSONObject json) {
		
		this.eventId = json.optString(Constants.EVENT_ID_KEY);
		this.groupId = json.optString(Constants.RESPONSE_PARAM_GROUP_ID);
		this.rating = json.optInt(EVENT_RATING);
		this.review = json.optString(EVENT_REVIEW);

	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	
	

}
