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

public class Constants {

	public static String PREF_FILE_NAME = "JV3498752EAIRGBAARGER";
	public static final String OAUTH_CONSUMER_KEY = "BFBBF99F342CC089A51438BA257DA188";
	public static final String OAUTH_CONSUMER_SECRET = "64D3BADF7920EA14DBB553AA2BC4F33F";
	public static final String OAUTH_CONSUMER_NAME = "Rendezvous";
	
	public static final String BASE_API_URL = "http://api.meetup.com/";
	public static final String RESPONSE_FORMAT = ".json";
	public static final String API_KEY = "API_KEY";
	public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
	public static final String ACCESS_TOKEN_SECRET = "ACCESS_TOKEN_SECRET";
	public static final String MEMBER_ID = "MEMBER_ID";
	
	public static final String PARAM_KEY = "key";
	public static final String PARAM_RELATION = "relation";
	public static final String PARAM_MEMBER_ID = "member_id";

	public static final String RESPONSE_PARAM_ID = "id";
	public static final String RESPONSE_PARAM_NAME = "name";
	public static final String RESPONSE_PARAM_LINK = "link";
	public static final String RESPONSE_PARAM_PHOTO_URL = "photo_url";
	public static final String RESPONSE_PARAM_CITY = "city";
	public static final String RESPONSE_PARAM_STATE = "state";
	public static final String RESPONSE_PARAM_COUNTRY = "country";
	public static final String RESPONSE_PARAM_BIO = "bio";
	public static final String RESPONSE_PARAM_ZIP = "zip";
	public static final String RESPONSE_PARAM_VISIBILITY = "visibility";
	public static final String RESPONSE_PARAM_ORGANIZER_PROFILE_URL = "organizerProfileURL";
	public static final String RESPONSE_PARAM_DESCRIPTION = "description";
	public static final String RESPONSE_PARAM_MEMBERS = "members";
	public static final String RESPONSE_PARAM_ORGANIZER_NAME = "organizer_name";
	public static final String RESPONSE_PARAM_ORGANIZER_ID = "organizer_id";
	
	public static final String OAUTH_REQUEST_URL = "http://www.meetup.com/oauth/request/";
	public static final String OAUTH_AUTHORIZE_URL = "http://www.meetup.com/authorize/";
	public static final String OAUTH_ACCESS_URL = "http://www.meetup.com/oauth/access/";
	public static final String CALLBACK_URL = "yama-android-app:///";
	public static final String APP_MODE = "OAUTH";
	
	public static final String GROUPS_TAB_ID = "groups_tab";
	public static final String MEETUPS_TAB_ID = "meetups_tab";
	public static final String MEETUP_INFO_TAB_ID = "meetup_info_tab";
	public static final String MEETUP_RSVP_TAB_ID = "meetups_rsvp_tab";
	public static final String MEETUP_ALL_RSVP_ID = "meetups_all_rsvp_tab";
	public static final String MEETUP_TALK_TAB_ID = "meetup_talk_tab";
	
	public static final String GROUP_INFO_TAB_ID = "group_info_tab";
	public static final String GROUP_MEETUPS_TAB_ID = "group_meetups_tab";
	public static final String GROUP_PHOTOS_TAB_ID = "group_photos_tab";
	
	
	public static final String PARAM_ORDER = "order";
	public static final String ORDER_NAME = "name";
	public static final String ORDER_TIME = "time";
	
	
	public static final String RESPONSE_PARAM_LATITUDE = "lat";
	public static final String RESPONSE_PARAM_LONGITUDE = "lon";
	public static final String RESPONSE_PARAM_TIME = "time";
	public static final String RESPONSE_PARAM_FINDING_INS = "how_to_find_us";
	public static final String RESPONSE_PARAM_RSVP_STATUS = "myrsvp ";
	public static final String RESPONSE_PARAM_EVENT_URL = "event_url";
	public static final String RESPONSE_PARAM_RSVP_COUNT = "rsvpcount";
	public static final String RESPONSE_PARAM_MAYBE_RSVP_COUNT = "maybe_rsvpcount";
	public static final String RESPONSE_PARAM_NO_RSVP_COUNT = "no_rsvpcount";
	public static final String RESPONSE_PARAM_GUEST_LIMIT = "guest_limit";
	public static final String RESPONSE_PARAM_IS_MEETUP = "ismeetup";
	public static final String RESPONSE_PARAM_ALLOW_MAYBE_RSVP = "allow_maybe_rsvp";
	public static final String RESPONSE_PARAM_FEE = "fee";
	public static final String RESPONSE_PARAM_FEE_CURRENCY = "feecurrency";
	public static final String RESPONSE_PARAM_FEE_DESC = "feedesc";
	public static final String RESPONSE_PARAM_ATTENDEE_COUNT = "attendee_count";
	public static final String RESPONSE_PARAM_MEMBER_ID = "member_id";
	public static final String RESPONSE_PARAM_MEMBER_NAME = "member_name";
	public static final String RESPONSE_PARAM_VENUE_VISIBILITY = "venue_visibility";
	public static final String RESPONSE_PARAM_VENUE_ID = "venue_id";
	public static final String RESPONSE_PARAM_VENUE_NAME = "venue_name";
	public static final String RESPONSE_PARAM_VENUE_PHONE = "venue_phone";
	
	public static final String RESPONSE_PARAM_VENUE_ADD_1 = "venue_address1";
	public static final String RESPONSE_PARAM_VENUE_ADD_2 = "venue_address2";
	public static final String RESPONSE_PARAM_VENUE_ADD_3 = "venue_address3";
	
	public static final String RESPONSE_PARAM_VENUE_CITY = "venue_city";
	public static final String RESPONSE_PARAM_VENUE_STATE = "venue_state";
	public static final String RESPONSE_PARAM_VENUE_ZIP = "venue_zip";
	public static final String RESPONSE_PARAM_VENUE_MAP_URL = "venue_map";
	public static final String RESPONSE_PARAM_VENUE_LATITUDE = "venue_lat";
	public static final String RESPONSE_PARAM_VENUE_LONGITUDE = "venue_lon";
	public static final String RESPONSE_PARAM_EVENT_HOSTS = "event_hosts";
	public static final String EVENT_ID_KEY = "event_id";
	public static final String GROUP_DATA_KEY = "cached_group_data";
	public static final String EVENT_DATA_KEY = "cached_event_data";
	public static final String CACHE_TIMESTAMP_KEY = "cached_timestamp";
	public static final String PARAM_AFTER = "after";
	public static final String PARAM_BEFORE = "before";
	public static final String RESPONSE_PARAM_GROUP_NAME = "group_name";
	public static final String RESPONSE_PARAM_GROUP_ID = "group_id";
	public static final String RESPONSE_PARAM_RSVP_CLOSED = "rsvp_closed";
	public static final String RESPONSE_PARAM_RSVP_LIMIT = "rsvp_limit";
	public static final String RESPONSE_PARAM_RSVP_CUTOFF = "rsvp_cutoff";
	public static final String RESPONSE_PARAM_STATUS = "status";
	public static final String RESPONSE_PARAMS_RSVPABLE = "rsvpable";
	public static final String RESPONSE_PARAM_COMMENT = "comment";
	public static final String ALL_RSVPS_KEY = "ALL_RSVPS_KEY";
	public static final String RESPONSE_PARAM_RSVP_RESPONSE = "response";
	public static final String RSVP_RESPONSE = "rsvp";
	public static final String RSVP_GUESTS = "guests";
	public static final String RSVP_COMMENTS = "comments";
	public static final String DEFAULT_AFTER_PERIOD = "1m";
	public static final String REQUEST_AFTER_PERIOD_KEY = "REQUEST_AFTER_PERIOD_KEY";
	public static final String RESPONSE_PARAM_EVENT_COMMENT_ID = "event_comment_id";
	public static final String EVENT_COMMENTS_KEY = "EVENT_COMMENTS_KEY";
	public static final int PREFS_MENU = 1;
	public static final int RESET_ACC_MENU = 2;
	public static final int PREFS_RESET_CACHE = 3;
	public static final String STARTUP_TAB = "startupTab";
	public static final String FETCH_EVENTS_FROM_PREF_KEY = "fetchEventsFrom";
	public static final String DEFAULT_BEFORE_PERIOD = "-1w";
	public static final String FETCH_EVENTS_TO_PREF_KEY = "fetchEventsTo";
	public static final String PHOTO_ALBUM_ID = "photo_album_id";
	public static final String TITLE = "title";
	public static final String PHOTO_COUNT = "photo_count";
	public static final String CREATED_TS = "created";
	public static final String UPDATED_TS = "updated";
	public static final String ALBUM_ID = "album_id";
	public static final String ALBUM_TITLE = "albumtitle";
	public static final String POSTING_MEMBER_URL = "member_url";
	public static final String DESCR = "descr";
	public static final String CAPTIONS = "captions";
	public static final String PHOTO_ALBUMS_GROUP_KEY = "PHOTO_ALBUMS_GROUP_KEY_";
	public static final String SELECTED_ALBUM_ID = "SELECTED_ALBUM_ID";
	public static final String PHOTO_IN_ALBUM = "PHOTO_IN_ALBUM";
	public static final String ALBUM_PHOTO_URLS = "photo_urls";
	public static final String THUMBNAIL_URLS = "thumb_urls";
	public static final String PHOTOS_IN_GROUP = "PHOTOS_IN_GROUP_";
	public static final String SELECTED_GROUP_ID = "SELECTED_GROUP_ID";
	public static final int TAKE_A_PICTURE = 4;
	public static final int CAMERA_INTENT_ID = 5;
	public static final String TEMP_IMAGE = "temp-image.jpg";
	public static final String TEMP_IMAGE_FILE_PATH = "TEMP_IMAGE_FILE_PATH";
	public static final String PHOTO = "photo";
	public static final String IMAGE_CAPTION = "caption";

}
