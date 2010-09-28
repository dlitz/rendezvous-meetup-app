package net.yama.android.requests;

import net.yama.android.util.Constants;

public class PhotoRequest extends AbstractRequest {

	private static final String URL = Constants.BASE_API_URL + "photos" + Constants.RESPONSE_FORMAT;
	
	public PhotoRequest() {
	}

	@Override
	public String getRequestURL() {
		return URL;
	}

}
