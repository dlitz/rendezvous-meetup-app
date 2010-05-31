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
package net.yama.android.views.contentfactory;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;

public abstract class AbstractContentFactory implements TabContentFactory {

	protected Activity context;
	
	protected AbstractContentFactory(Activity context){
		this.context = context;
	}
	public abstract View createTabContent(String tag);
	
	public View getNullView() {
		TextView temp = new TextView(this.context);
		temp.setText("What did you click on?");
		return temp;
	}
	
	public View getExceptionView(Exception e) {
		TextView temp = new TextView(this.context);
		temp.setText("Exception occured: " + e.getMessage());
		return temp;
	}
	public View getNoDataView(String message) {
		TextView temp = new TextView(this.context);
		temp.setText(message);
		temp.setPadding(5, 5, 5, 5);
		temp.setTextSize(10.0F);
		return temp;
	}

}
