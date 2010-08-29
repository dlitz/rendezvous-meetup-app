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

public class Activity extends BaseResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8637767408815099672L;
	private String title;
	private String itemType;
	private String groupId;
	private String groupName;
	private String memberName;
	private String memberId;
	private Date published;
	
	// photo_comment
	private String albumName;
	private String comment;
	
	// edit_rsvp,new_rsvp
	private String rsvpResponse;
	private String rsvpComment;
	
	// new_member
	private String bio;
	
	public Activity() {
	}

	@Override
	public void convertFromJSON(BaseResponse b, JSONObject json) {
		super.convertCommon(b, json);
		
		Activity activity = (Activity) b;
		activity.groupId = json.optString(Constants.RESPONSE_PARAM_GROUP_ID);
		activity.groupName = json.optString(Constants.RESPONSE_PARAM_GROUP_NAME);
		activity.itemType = json.optString(Constants.ITEM_TYPE);
		activity.memberId = json.optString(Constants.PARAM_MEMBER_ID);
		activity.memberName = json.optString(Constants.RESPONSE_PARAM_MEMBER_NAME);
		activity.title = json.optString(Constants.TITLE);
		
		activity.albumName = json.optString(Constants.ALBUM_NAME);
		activity.comment = json.optString(Constants.RESPONSE_PARAM_COMMENT);
		
		activity.rsvpComment = json.optString(Constants.RSVP_COMMENT);
		activity.rsvpResponse = json.optString(Constants.RSVP_RESPONSE_ACTIVITY);
		
	}
	
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRsvpResponse() {
		return rsvpResponse;
	}

	public void setRsvpResponse(String rsvpResponse) {
		this.rsvpResponse = rsvpResponse;
	}

	public String getRsvpComment() {
		return rsvpComment;
	}

	public void setRsvpComment(String rsvpComment) {
		this.rsvpComment = rsvpComment;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

}
