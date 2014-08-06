//package com.ecs.android.sample.oauth2;
//
//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//public class MyAdapter extends ArrayAdapter<MyItem> {
//	private Context context = null;
//	private int resourceLayoutID;
//	private ArrayList<MyItem> myItemList = null;
//
//	public MyAdapter(Context context, int resourceLayoutID,
//			ArrayList<MyItem> myItemList) {
//		super(context, resourceLayoutID, myItemList);
//		this.context = context;
//		this.resourceLayoutID = resourceLayoutID;
//		this.myItemList = myItemList;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		View row = convertView;
//		ItemViewHolder holder = new ItemViewHolder();
//		if (row == null) {
//			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//			row = inflater.inflate(resourceLayoutID, parent, false);
//
//			holder.tvTitle = (TextView) row.findViewById(R.id.tvTitle);
//			holder.tvUpdated = (TextView) row.findViewById(R.id.tvUpdated);
//			holder.tvId = (TextView) row.findViewById(R.id.tvId);
//			holder.tvUrl = (TextView) row.findViewById(R.id.tvUrl);
//			
//			holder.tvdislayName = (TextView) row.findViewById(R.id.tvdislayName);
//			holder.tvId1 = (TextView) row.findViewById(R.id.tvId1);
//			holder.tvUrl1 = (TextView) row.findViewById(R.id.tvUrl1);
//			holder.tvImageUrl = (TextView) row.findViewById(R.id.tvImageUrl);
//
//			row.setTag(holder);
//		} else {
//			holder = (ItemViewHolder) row.getTag();
//		}
//		MyItem rowItem = myItemList.get(position);
//		
////		holder.tvTitle.setText(rowItem.getTitle());
////		holder.tvUpdated.setText(rowItem.getUpdated());
////		holder.tvId.setText(rowItem.getId_items());
////		holder.tvUrl.setText(rowItem.getUrl_items());
//		
//		holder.tvTitle.setText(rowItem.getTitle_provider());
//		holder.tvUpdated.setText(rowItem.getKind_access());
//		holder.tvId.setText(rowItem.getDescription_access());
//		holder.tvUrl.setText(rowItem.getType_items_access());
//		
//		holder.tvdislayName.setText(rowItem.getObjectType_object());
//		holder.tvId1.setText(rowItem.getContent_object());
//		holder.tvUrl1.setText(rowItem.getUrl_actor());
//		holder.tvImageUrl.setText(rowItem.getUrl_image_actor());
//		
//		return row;
//	}
//
//	private class ItemViewHolder {
//		public TextView tvTitle;
//		public TextView tvUpdated;
//		public TextView tvId;
//		public TextView tvUrl;
//		
//		public TextView tvdislayName;
//		public TextView tvId1;
//		public TextView tvUrl1;
//		public TextView tvImageUrl;
//	}
//
//}
