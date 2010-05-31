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
package net.yama.android.views.components;

import net.yama.android.R;
import net.yama.android.util.DrawableManager;
import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * @author Rohit Kumbhar
 *
 */
public class InfoRowView extends TableLayout {

	TextView mainTextView;
	TextView subTextView;
	ImageView image;
	
	public InfoRowView(Context context, String mainText, String subText, int id) {
		super(context);
		setupComponents(id,context, mainText, subText,null);
	}
	
	public InfoRowView(Context context, String mainText, String subText, String imageUrl, int id) {
		super(context);
		setupComponents(id,context, mainText, subText,imageUrl);
	}

	private void setupComponents(int id, Context context, String mainText,String subText, String imageUrl) {
		
		this.setId(id);
		this.setPadding(2, 2, 2, 2);
		
		image = new ImageView(context);
		image.setMaxHeight(50);
		image.setMaxWidth(50);
		image.setMinimumWidth(50);
		image.setMinimumHeight(50);
		image.setAdjustViewBounds(true);
		image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		image.setScaleType(ImageView.ScaleType.FIT_CENTER);
		if(imageUrl != null && imageUrl.length() > 0)
			DrawableManager.instance.fetchDrawableOnThread(imageUrl, image);
		else 
			image.setImageResource(R.drawable.no_photo);
		
		mainTextView = new TextView(context);
		mainTextView.setText(mainText);
		mainTextView.setTypeface(Typeface.DEFAULT_BOLD);
		mainTextView.setTextSize(15.0F);
		
		subTextView = new TextView(context);
		subTextView.setText(subText);
		
		if(id > 0){
			LinearLayout layoutText = new LinearLayout(context);
			layoutText.setOrientation(LinearLayout.VERTICAL);
			layoutText.addView(mainTextView);
			layoutText.addView(subTextView);
			layoutText.setPadding(5, 0, 1, 0);
			
			LinearLayout layout = new LinearLayout(context);
			layout.addView(image,50,50);
			layout.addView(layoutText);
			this.addView(layout); 
		} else {
			this.addView(mainTextView);
			this.addView(subTextView);
		}
	}
	public TextView getMainTextView() {
		return mainTextView;
	}

	public void setMainTextView(TextView mainTextView) {
		this.mainTextView = mainTextView;
	}

	public TextView getSubTextView() {
		return subTextView;
	}

	public void setSubTextView(TextView subTextView) {
		this.subTextView = subTextView;
	}
	
	public void setNoClicks() {
		this.mainTextView.setClickable(false);
		this.subTextView.setClickable(false);
		super.setClickable(false);
	}
	
}
