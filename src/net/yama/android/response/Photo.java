package net.yama.android.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.yama.android.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Photo extends BaseResponse {
	
	private String albumId;
	private String postingMemberUrl;
	private String albumTitle;
	private Date created;
	private String groupId;
	private String coverPhotoUrl;
	private String albumPageLink;
	private String albumDescription;
	
	private List<String> photoUrls;
	private List<String> captions;
	private List<String> thumbnailUrls;
	
	
	public Photo() {
	
	}
	
	
	@Override
	public void convertFromJSON(BaseResponse b, JSONObject json) {
		
		Photo photo = (Photo) b;
		photo.albumTitle = json.optString(Constants.ALBUM_TITLE);
		photo.albumId = json.optString(Constants.ALBUM_ID);
		photo.groupId = json.optString(Constants.RESPONSE_PARAM_GROUP_ID);
		photo.postingMemberUrl = json.optString(Constants.POSTING_MEMBER_URL);
		photo.albumDescription = json.optString(Constants.DESCR);
		photo.created = super.formatAndSetDate(json.optString(Constants.CREATED_TS));
		photo.coverPhotoUrl = json.optString(Constants.RESPONSE_PARAM_PHOTO_URL);
		photo.albumPageLink = json.optString(Constants.RESPONSE_PARAM_LINK);
		
		// Convert captions
		captions = new ArrayList<String>();
		JSONArray captionsArray = json.optJSONArray(Constants.CAPTIONS);
		for(int i = 0; i < captionsArray.length(); i++){
			String caption = null;
			try {
				caption = (String) captionsArray.get(i);
				captions.add(caption);
			} catch (JSONException e) {
				// Ignore caption
			}
		}
		
		// Convert photoUrls
		photoUrls = new ArrayList<String>();
		JSONArray photoUrlsArray = json.optJSONArray(Constants.ALBUM_PHOTO_URLS);
		for(int i = 0; i < photoUrlsArray.length(); i++){
			String photoUrl = null;
			try {
				photoUrl = (String) photoUrlsArray.get(i);
				photoUrls.add(photoUrl);
			} catch (JSONException e) {
				// Ignore photo
			}
		}
		
		// Convert thumbnails
		thumbnailUrls = new ArrayList<String>();
		JSONArray thumnailUrlsArray = json.optJSONArray(Constants.THUMBNAIL_URLS);
		for(int i = 0; i < thumnailUrlsArray.length(); i++){
			String thumbUrl = null;
			try {
				thumbUrl = (String) thumnailUrlsArray.get(i);
				thumbnailUrls.add(thumbUrl);
			} catch (JSONException e) {
				// Ignore thumbnail
			}
		}
	}


	public String getAlbumId() {
		return albumId;
	}


	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}


	public String getPostingMemberUrl() {
		return postingMemberUrl;
	}


	public void setPostingMemberUrl(String postingMemberUrl) {
		this.postingMemberUrl = postingMemberUrl;
	}


	public String getAlbumTitle() {
		return albumTitle;
	}


	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getCoverPhotoUrl() {
		return coverPhotoUrl;
	}


	public void setCoverPhotoUrl(String coverPhotoUrl) {
		this.coverPhotoUrl = coverPhotoUrl;
	}


	public String getAlbumPageLink() {
		return albumPageLink;
	}


	public void setAlbumPageLink(String albumPageLink) {
		this.albumPageLink = albumPageLink;
	}


	public String getAlbumDescription() {
		return albumDescription;
	}


	public void setAlbumDescription(String albumDescription) {
		this.albumDescription = albumDescription;
	}


	public List<String> getPhotoUrls() {
		return photoUrls;
	}


	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}


	public List<String> getCaptions() {
		return captions;
	}


	public void setCaptions(List<String> captions) {
		this.captions = captions;
	}


	public List<String> getThumbnailUrls() {
		return thumbnailUrls;
	}


	public void setThumbnailUrls(List<String> thumbnailUrls) {
		this.thumbnailUrls = thumbnailUrls;
	}


	
}
