package ltt.intership.custom;

import java.util.ArrayList;

import ltt.intership.R;
import ltt.intership.data.mSocialMedia;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class mSpinnerAdapter extends ArrayAdapter<mSocialMedia> {
	private Activity activity;
	ArrayList<mSocialMedia> data = null;

	public mSpinnerAdapter(Activity context, int resource, ArrayList<mSocialMedia> data) {
		super(context, resource, data);
		this.activity = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			row = inflater.inflate(R.layout.spinner_layout, parent, false);
		}

		mSocialMedia item = data.get(position);

		if (item != null) { // Parse the data from each object and set it.
			TextView media = (TextView) row.findViewById(R.id.rssItem_tv_media);
			media.setText(item.getName());

			ImageView img = (ImageView) row.findViewById(R.id.rssItem_imageIcon);
			img.setImageResource(item.getImgSrc());
		}

		return row;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "";
	}
}
