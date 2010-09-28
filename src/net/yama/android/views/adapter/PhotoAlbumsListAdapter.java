package net.yama.android.views.adapter;

import java.util.List;

import net.yama.android.response.Photo;
import net.yama.android.views.activity.GalleryActivity;
import net.yama.android.views.components.InfoRowView;
import net.yama.android.views.listeners.PhotoAlbumClickListener;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class PhotoAlbumsListAdapter extends AbstractListAdapter{
	
	PhotoAlbumClickListener listClickListener;
	
	public PhotoAlbumsListAdapter(List data,Context ctx, String groupId) {
		super(data, ctx);
		listClickListener = new PhotoAlbumClickListener(context, GalleryActivity.class, groupId,data);
	}

	public Object getItem(int position) {
		Photo photoAlbum = (Photo) data.get(position);
		return photoAlbum;
	}

	public long getItemId(int position) {
		Photo photoAlbum = (Photo) data.get(position);
		return Long.valueOf(photoAlbum.getAlbumId());
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Photo photoAlbum = (Photo) data.get(position);
		String desc = Integer.toString(photoAlbum.getPhotoUrls().size()) + " photos";
		InfoRowView view = new InfoRowView(this.context, photoAlbum.getAlbumTitle(),
										   desc,
										   photoAlbum.getCoverPhotoUrl(),
										   Integer.valueOf(photoAlbum.getAlbumId()));
		view.setOnClickListener(listClickListener);
		return view;
	}
	
}
