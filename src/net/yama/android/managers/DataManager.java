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
package net.yama.android.managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.yama.android.managers.config.ConfigurationManager;
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.managers.connection.ConnectionManagerFactory;
import net.yama.android.requests.EventCommentRequest;
import net.yama.android.requests.EventRequest;
import net.yama.android.requests.GroupRequest;
import net.yama.android.requests.MembersRequest;
import net.yama.android.requests.PhotoRequest;
import net.yama.android.requests.RsvpRequest;
import net.yama.android.requests.write.WriteEventComment;
import net.yama.android.requests.write.WriteRsvp;
import net.yama.android.response.Event;
import net.yama.android.response.EventComment;
import net.yama.android.response.Group;
import net.yama.android.response.Member;
import net.yama.android.response.Photo;
import net.yama.android.response.Rsvp;
import net.yama.android.util.Constants;
import net.yama.android.util.Helper;
import android.util.Log;

/**
 * Manages fetching and caching of data
 * @author Rohit Kumbhar
 */
public class DataManager {

	private static Map<String, Object> cache = new HashMap<String, Object>(); 
	
	private static void storeInCache(String key, Object value){
		
		String cacheEnabled = ConfigurationManager.instance.getCachingEnabled();
		if(cacheEnabled != null && !Boolean.parseBoolean(cacheEnabled))
			return;
		
		if(Constants.GROUP_DATA_KEY.equals(key) || Constants.EVENT_DATA_KEY.equals(key))
			cache.put(Constants.CACHE_TIMESTAMP_KEY, Long.valueOf(System.currentTimeMillis()));
		
		cache.put(key, value);
	}
	
	private static Object getFromCache(String key){
		
		if(Constants.GROUP_DATA_KEY.equals(key) || Constants.EVENT_DATA_KEY.equals(key)){
			long now = System.currentTimeMillis();
			Long cacheUpdateTimestamp = (Long) cache.get(Constants.CACHE_TIMESTAMP_KEY);
			
			if(cacheUpdateTimestamp != null && (now - cacheUpdateTimestamp) > 7200000) //TODO: Implement cache invalidation timeout
				clearGroupAndEventCache();
		}
			
		
		return cache.get(key);
	}
	
	private static void removeFromCache(String key) {
		cache.remove(key);
	}
	
	public static void clearGroupAndEventCache(){
		cache.remove(Constants.GROUP_DATA_KEY);
		cache.remove(Constants.EVENT_DATA_KEY);
		cache.remove(Constants.CACHE_TIMESTAMP_KEY);
	}
	
	
	public static Member getMemberInformation() throws ApplicationException {
		
		MembersRequest selfInfo = new MembersRequest();
		selfInfo.setParameters(Constants.PARAM_RELATION,"self");
		
		String responseBody = null;
	    Member currentMember = null;
	    
		try {
			responseBody = ConnectionManagerFactory.getConnectionManager().makeRequest(selfInfo);
			List members = Helper.getListFromResult(responseBody, Member.class);
			currentMember = (Member) members.get(0);
			ConfigurationManager.instance.saveMemberId(currentMember.getId());

		} catch (Exception e) {
			Log.e("DataManager", "Exception occured in getMemberInformation()", e);
			throw new ApplicationException(e);
		}
        
        
        return currentMember;
	}


	/**
	 * Gets all rsvps for a event id
	 * @param eventId
	 * @return
	 * @throws ApplicationException 
	 */
	public static List getAllRsvps(String eventId) throws ApplicationException {
		String eventCacheKey = Constants.ALL_RSVPS_KEY + "_" + eventId;
		List allRSVPs = (List) getFromCache(eventCacheKey);
		try {
			
			if(allRSVPs == null){
				
				RsvpRequest rsvpRequest = new RsvpRequest();
				rsvpRequest.addParameter(Constants.EVENT_ID_KEY, eventId);
				String response = ConnectionManagerFactory.getConnectionManager().makeRequest(rsvpRequest);
				allRSVPs = Helper.getListFromResult(response, Rsvp.class);
				storeInCache(eventCacheKey, allRSVPs);
			}
			
		}catch (Exception e) {
			Log.e("DataManager::getAllRsvps()", e.getMessage(), e);
			throw new ApplicationException(e);
		}
			
		return allRSVPs;
	}
	
	/**
	 * Returns all the groups that user is a member of
	 * @return
	 * @throws ApplicationException
	 */
	public static List getAllGroups() throws ApplicationException {
		
		List groupsList = (List) getFromCache(Constants.GROUP_DATA_KEY);
		
		try {
			if(groupsList == null) {
				GroupRequest request = new GroupRequest();
				request.addParameter(Constants.PARAM_MEMBER_ID, ConfigurationManager.instance.getMemberId());
				request.addParameter(Constants.PARAM_ORDER, Constants.ORDER_NAME);
				String response = ConnectionManagerFactory.getConnectionManager().makeRequest(request);
				groupsList = Helper.getListFromResult(response, Group.class);
				storeInCache(Constants.GROUP_DATA_KEY, groupsList);
			}
		} catch (Exception e) {
			Log.e("DataManager::getAllGroups()", e.getMessage(), e);
			throw new ApplicationException(e);
		}
		
		return groupsList;
	}
	
	
	public static List getAllEvents() throws ApplicationException{
		
		List eventsList = (List) getFromCache(Constants.EVENT_DATA_KEY);
		 
		try {
			if(eventsList == null) {
				EventRequest request = new EventRequest();
				request.addParameter(Constants.PARAM_MEMBER_ID, ConfigurationManager.instance.getMemberId());
				request.addParameter(Constants.PARAM_ORDER, Constants.ORDER_TIME);
				request.addParameter(Constants.PARAM_AFTER, ConfigurationManager.instance.getRequestAfterPeriod());
				request.addParameter(Constants.PARAM_BEFORE, ConfigurationManager.instance.getRequestBeforePeriod());
				request.addParameter(Constants.PARAM_TEXT_FORMAT, Constants.TEXT_FORMAT_PLAIN);
				String response = ConnectionManagerFactory.getConnectionManager().makeRequest(request);
				eventsList = Helper.getListFromResult(response, Event.class);
				storeInCache(Constants.EVENT_DATA_KEY, eventsList);
			}
		} catch (Exception e) {
			Log.e("DataManager::getAllEvents()", e.getMessage(), e);
			throw new ApplicationException(e);
		}
		
		return eventsList;
	}

	/**
	 * Returns the event instance for the given event id
	 * @param eventId
	 * @return
	 * @throws ApplicationException
	 */
	public static Event getEvent(String eventId) throws ApplicationException {
		
		Event theEvent = null;
		List events = getAllEvents();
		Iterator iter = events.iterator();
		while (iter.hasNext()) {
			Event event = (Event) iter.next();
			if(eventId.equals(event.getId())){
				theEvent = event;
				break;
			}
		}
		return theEvent;

	}

	/**
	 * Returns the rsvp status of the current user for the given event.
	 * @param eventId
	 * @return
	 * @throws ApplicationException
	 */
	public static Rsvp getMyRsvp(String eventId) throws ApplicationException {
		
		String myMemberId = ConfigurationManager.instance.getMemberId();
		List allRsvps = getAllRsvps(eventId);
		Iterator iter = allRsvps.iterator();
		while (iter.hasNext()) {
			Rsvp rsvp = (Rsvp) iter.next();
			if(rsvp.getMemberId().equals(myMemberId))
				return rsvp;
		}
		
		return null;
	}
	
	public static List getAllEventComments(String eventId) throws ApplicationException{
		
		List eventComments = (List) getFromCache(Constants.EVENT_COMMENTS_KEY + "_" + eventId);
		if(eventComments == null){
			EventCommentRequest request = new EventCommentRequest();
			request.addParameter(Constants.EVENT_ID_KEY, eventId);
			String response = ConnectionManagerFactory.getConnectionManager().makeRequest(request);
			try {
				eventComments = Helper.getListFromResult(response, EventComment.class);
				storeInCache(Constants.EVENT_COMMENTS_KEY + "_" + eventId, eventComments);
			} catch (Exception e) {
				Log.e("DataManager::getAllEventComments()", e.getMessage(), e);
				throw new ApplicationException(e);
			}
			
		}
	
		return eventComments;
	}
	
	public static Group getGroup(String groupId) throws ApplicationException{
		Group theGroup = null;
		List allGroups = getAllGroups();
		Iterator iter = allGroups.iterator();
		while (iter.hasNext()) {
			Group g = (Group) iter.next();
			if(groupId.equals(g.getId())){
				theGroup = g;
				break;
			}
		}
		return theGroup;
	}
	

	public static void addEventComment(WriteEventComment request) throws ApplicationException {
		String response = ConnectionManagerFactory.getConnectionManager().makeRequest(request);
		removeFromCache(Constants.EVENT_COMMENTS_KEY + "_" + request.getParameterMap().get(Constants.EVENT_ID_KEY));
	}

	public static void updateRsvp(WriteRsvp request) throws ApplicationException {
		ConnectionManagerFactory.getConnectionManager().makeRequest(request);
		removeFromCache(Constants.ALL_RSVPS_KEY + "_" + request.getParameterMap().get(Constants.EVENT_ID_KEY));
	}

	public static void nuke() {
		cache.clear();
	}

	public static List getPhotosForGroup(String groupId) throws ApplicationException{
		
		List photos = (List) getFromCache(Constants.PHOTOS_IN_GROUP + groupId);
		
		if(photos == null){
			PhotoRequest request = new PhotoRequest();
			request.addParameter(Constants.RESPONSE_PARAM_GROUP_ID, groupId);
			String response = ConnectionManagerFactory.getConnectionManager().makeRequest(request);
			try {
				photos = Helper.getListFromResult(response, Photo.class);
				storeInCache(Constants.PHOTOS_IN_GROUP + groupId, photos);
			} catch (Exception e) {
				Log.e("DataManager::getPhotosForGroup()", e.getMessage(), e);
				throw new ApplicationException(e);
			}
		}
		
		return photos;
	}
	
	public static void removeCachedPhotosForGroup(String groupId){
		removeFromCache(Constants.PHOTOS_IN_GROUP + groupId);
	}
}
