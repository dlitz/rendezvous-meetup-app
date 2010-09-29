package net.yama.android.requests.write;

import net.yama.android.requests.AbstractRequest;
import net.yama.android.util.Constants;

public class WritePhotoComment extends AbstractRequest  {

	private static final String URL = Constants.BASE_API_URL + "2/photo_comment";

	@Override
	public String getRequestURL() {
		return URL;
	}
	
	@Override
	public String getMethod() {
		return "POST";
	}
}
