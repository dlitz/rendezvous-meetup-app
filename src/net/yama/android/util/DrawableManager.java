/**
 * Shamelessly copied from: http://stackoverflow.com/questions/541966/android-how-do-i-do-a-lazy-load-of-images-in-listview
 */
package net.yama.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import net.yama.android.managers.config.ConfigurationManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

public class DrawableManager {

	// Maintains a cache of already fetched images
	static Map<String, SoftReference<Drawable>> drawableCache;
	
	// Work queue for the fetcher thread
	BlockingQueue<MessageContainer> imageWorkQueue;
	
	// Thread system for getting images. Should be configurable
	private ImageFetcher fetcher;
	Thread imageThread; 
	List<ImageFetcher> imageThreads;
	
	
	public static DrawableManager instance = new DrawableManager();
	private DrawableManager() {
	    drawableCache = new HashMap<String, SoftReference<Drawable>>();
	    imageWorkQueue = new ArrayBlockingQueue<MessageContainer>(50);
	    
	    
	    int fetchingThreadCount = ConfigurationManager.instance.getImageThreads(); 
	    if(fetchingThreadCount == 0) fetchingThreadCount = 3;
	
	    imageThreads = new ArrayList<ImageFetcher>(fetchingThreadCount);
	    for(int i = 0; i < fetchingThreadCount; i++){
	    	fetcher = new ImageFetcher();
		    imageThread = new Thread(fetcher);
		    imageThreads.add(fetcher);
		    imageThread.start();
	    }
	    
	    
	}

	// Handler instance to update UI
	 final Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message message) {
	        	MessageContainer cont = (MessageContainer )message.obj;
	        	ImageView view = cont.getView();
	            Drawable drawable = cont.getDrawable();
	            view.setImageDrawable(drawable);
	        }
	    };
	
	/**
	 * @param urlString
	 * @return
	 */
	public Drawable fetchDrawable(String urlString) {
		
	    SoftReference<Drawable> drawableRef = drawableCache.get(urlString);
	    if (drawableRef != null) {
	        Drawable drawable = drawableRef.get();
	        if (drawable != null)
	            return drawable;
	        // Reference has expired so remove the key from drawableMap
	        drawableCache.remove(urlString);
	    }
	    
	    try {
	        InputStream is = fetch(urlString);
	        
	        Bitmap photo = null;
	        Drawable drawable = null;
	        BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, opts);
            opts.inJustDecodeBounds = false;
            InputStream is2 = fetch(urlString);
            if(opts.outWidth > 1000){
                    opts.inSampleSize = 4;
                    photo = BitmapFactory.decodeStream(is2, null, opts);
            }
            else {
            	photo = BitmapFactory.decodeStream(is2);
            }
            
            drawable = new BitmapDrawable(photo);
            drawableRef = new SoftReference<Drawable>(drawable);
	        drawableCache.put(urlString, drawableRef);
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
	    SoftReference<Drawable> drawableRef = drawableCache.get(urlString);
	    if (drawableRef != null) {
	        Drawable drawable = drawableRef.get();
	        if (drawable != null) {
	            imageView.setImageDrawable(drawableRef.get());
	            return;
	        }
	        // Reference has expired so remove the key from drawableMap
	        drawableCache.remove(urlString);
	    }

	    if(Looper.myLooper() == null)
	    	Looper.prepare();

	    try {
			imageWorkQueue.put(new MessageContainer(imageView,urlString));
		} catch (InterruptedException e) {
			// Can't do anything really
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		
		for(ImageFetcher fetcher : imageThreads)
			fetcher.stop = true;
		
		super.finalize();
	}
	
	/**
	 * Does the actual work of fetching.
	 * @author Rohit Kumbhar
	 */
	class ImageFetcher implements Runnable {
		
		public boolean stop = false;

		public void run() {
			
			while(!stop){
				try {
					MessageContainer work = imageWorkQueue.take();
					Drawable drawable = fetchDrawable(work.getImageUrl());
					work.setDrawable(drawable);
		            Message message = handler.obtainMessage(1, work);
		            handler.sendMessage(message);
		            System.gc();
					
				} catch (InterruptedException e) {
					// What are you gonna do?
				}
			}
		}
	}
	
	
	/**
	 * As the name says, a message container to pass info between caller, fetching threads and handler
	 * @author Rohit Kumbhar
	 *
	 */
	class MessageContainer {
		private ImageView view;
		private Drawable drawable;
		private String imageUrl;
		
		public MessageContainer(ImageView view, Drawable drawable) {
			super();
			this.view = view;
			this.drawable = drawable;
		}
		
		public MessageContainer(ImageView view, String imageUrl) {
			super();
			this.view = view;
			this.imageUrl = imageUrl;
		}


		public ImageView getView() {
			return view;
		}
		public void setView(ImageView view) {
			this.view = view;
		}
		public Drawable getDrawable() {
			return drawable;
		}
		public void setDrawable(Drawable drawable) {
			this.drawable = drawable;
		}
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
	}
	
}
