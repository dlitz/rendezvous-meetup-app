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

import net.yama.android.managers.connection.ApplicationException;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public abstract class LoadingView extends FrameLayout {

	Context context;
	final Handler handler = new Handler();
	View afterResultsView = null;
	View errorView = null;
	
	// Create runnable for posting
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			
			try {
				dialog.dismiss();
			}catch (Exception e) {
				// meh
			}
			
			if (afterResultsView != null)
				addView(afterResultsView);
			else
				addView(errorView);
		}

	};
	private ProgressDialog dialog;

	public LoadingView(Context context) {
		super(context);
		this.context = context;
		dialog = ProgressDialog.show(context, "", "Loading. Please wait...", true);
		doWork();
		
	}
	
	private void doWork() {

		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					if(Looper.myLooper() == null)
						Looper.prepare();
					
					afterResultsView = getResultsView();
				} catch (Exception e) {
					TextView v = new TextView(context);
					v.setText(e.getMessage());
					errorView = v;
				}finally {
					handler.post(mUpdateResults);
				}
			}
		});
		t.start();
	}
	public abstract View getResultsView() throws ApplicationException;

}
