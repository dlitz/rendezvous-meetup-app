/**
 * 
 */
package net.yama.android.views.listeners;

import net.yama.android.response.Photo;
import android.os.Parcel;
import android.os.Parcelable;

class PhotoParcel implements Parcelable {

	Photo parcelData;

	public PhotoParcel(Parcel in) {
		parcelData = (Photo) in.readSerializable();
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(parcelData);
	}

	public static final Parcelable.Creator<PhotoParcel> CREATOR = new Parcelable.Creator<PhotoParcel>() {
		public PhotoParcel createFromParcel(Parcel in) {
			return new PhotoParcel(in);
		}

		public PhotoParcel[] newArray(int size) {
			return new PhotoParcel[size];
		}
	};
}