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
package net.yama.android.views.adapter;

import net.yama.android.response.Photo;
import net.yama.android.util.DrawableManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {

	Photo data;
	Context context;
	
	public GalleryAdapter(Photo data, Context ctx) {
		super();
		this.data = data;
		this.context = ctx;
	}

	public int getCount() {
		return data.getPhotoUrls().size();
	}

	public Object getItem(int position) {
		return data.getPhotoUrls().get(position);
	}

	public long getItemId(int position) {
		return -1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(context);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setPadding(2, 2, 2, 2);
		DrawableManager.instance.fetchDrawableOnThread(data.getPhotoUrls().get(position), i);
		return i;
	}
	
}
