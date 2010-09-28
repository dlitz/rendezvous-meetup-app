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

import org.json.JSONObject;

public class PhotoComment extends BaseResponse {

	private static final long serialVersionUID = -7967804725849702228L;
	private String photoCommentId;
	private String comment;
	private String memberName;
	private Date createdDate;
	
	@Override
	public void convertFromJSON(BaseResponse b, JSONObject json) {
		PhotoComment pc = (PhotoComment) b;
		pc.comment = json.optString("comment");
		pc.photoCommentId = json.optString("photo_comment_id");
		pc.createdDate = new Date(json.optLong("created"));
		
		JSONObject member = json.optJSONObject("member");
		pc.memberName = member.optString("name");
		
	}

	public String getPhotoCommentId() {
		return photoCommentId;
	}

	public void setPhotoCommentId(String photoCommentId) {
		this.photoCommentId = photoCommentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
