/**
 * Shamelessly copied from: http://stackoverflow.com/questions/541966/android-how-do-i-do-a-lazy-load-of-images-in-listview
 */
package net.yama.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class DrawableManager {

	static Map<String, SoftReference<Drawable>> drawableMap;
	public static DrawableManager instance = new DrawableManager();
	private DrawableManager() {
	    drawableMap = new HashMap<String, SoftReference<Drawable>>();
	}

	public Drawable fetchDrawable(String urlString) {
	    SoftReference<Drawable> drawableRef = drawableMap.get(urlString);
	    if (drawableRef != null) {
	        Drawable drawable = drawableRef.get();
	        if (drawable != null)
	            return drawable;
	        // Reference has expired so remove the key from drawableMap
	        drawableMap.remove(urlString);
	    }
	    
	    try {
	        InputStream is = fetch(urlString);
	        Drawable drawable = Drawable.createFromStream(is, "src");
	        drawableRef = new SoftReference<Drawable>(drawable);
	        drawableMap.put(urlString, drawableRef);
	        return drawableRef.get();
	    } catch (Exception e) {
	        
	        return null;
	    }
	}

	private InputStream fetch(String urlString) throws MalformedURLException, IOException {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
    	HttpGet request = new HttpGet(urlString);
    	HttpResponse response = httpClient.execute(request);
    	return response.getEntity().getContent();
    }

	public void fetchDrawableOnThread(final String urlString, final ImageView imageView) {
	    SoftReference<Drawable> drawableRef = drawableMap.get(urlString);
	    if (drawableRef != null) {
	        Drawable drawable = drawableRef.get();
	        if (drawable != null) {
	            imageView.setImageDrawable(drawableRef.get());
	            return;
	        }
	        // Reference has expired so remove the key from drawableMap
	        drawableMap.remove(urlString);
	    }

	    final Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message message) {
	            imageView.setImageDrawable((Drawable) message.obj);
	        }
	    };

	    Thread thread = new Thread() {
	        @Override
	        public void run() {
	            Drawable drawable = fetchDrawable(urlString);
	            Message message = handler.obtainMessage(1, drawable);
	            handler.sendMessage(message);
	        }
	    };
	    thread.start();
	}
}
