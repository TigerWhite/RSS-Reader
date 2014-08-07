package ltt.intership.androidlayout;

import java.util.ArrayList;
import java.util.List;

import android.widget.RelativeLayout;

public class MetroLayout {

	private List<RelativeLayout.LayoutParams> listParams;
	private int oneUnitWidth, oneUnitHeight;
	// private int anchor1, anchor2;
	private static final int NO_ANCHOR_PARAM = 0;

	// private int marginArg1, marginArg2, marginArg3, marginArg4;
	public MetroLayout(int oneUnitWidth, int oneUnitHeight) {
		// super(context);
		this.oneUnitWidth = oneUnitWidth;
		this.oneUnitHeight = oneUnitHeight;
	}

//	public void setListParams() {
//		List<RelativeLayout.LayoutParams> tempList = metroLayoutParamsMapCreates();
//		this.listParams = tempList;
//	}
//	public List<RelativeLayout.LayoutParams> getListParams(){
//		Log.d("tag", "size: "+ listParams.size());
//		return listParams;
//	}

	public List<RelativeLayout.LayoutParams> getMetroLayoutParamsMap() {
		List<RelativeLayout.LayoutParams> tempList = new ArrayList<RelativeLayout.LayoutParams>();
		tempList.add(setMetroLayoutParams(2, 2, 0, 0, 0, 0, 10, 10));
		tempList.add(setMetroLayoutParams(1, 1, 0, 1, 10, -20, 0, 10));
		tempList.add(setMetroLayoutParams(1, 1, 2, 1, 10, 10, 0, 20));
		tempList.add(setMetroLayoutParams(1, 1, 1, 0, 0, 10, 10, 10));
		tempList.add(setMetroLayoutParams(2, 1, 1, 4, 10, 10, 0, 10));
		tempList.add(setMetroLayoutParams(2, 1, 4, 0, 0, 10, 10, 10));
		tempList.add(setMetroLayoutParams(1, 1, 5, 6, 10, 10, 0, 10));
		tempList.add(setMetroLayoutParams(1, 1, 6, 0, -20, 10, 10, 10));
		tempList.add(setMetroLayoutParams(1, 1, 7, 8, 10, 10, 10, 10));
		tempList.add(setMetroLayoutParams(1, 1, 7, 9, 10, 10, 0, 0));
		
		tempList.add(setMetroLayoutParams(2, 2, 8, 0, 0, 10, 10, 10));
		tempList.add(setMetroLayoutParams(1, 1, 8, 11, 10, 10, 0, 10));
		tempList.add(setMetroLayoutParams(1, 1, 12, 11, 10, 10, 0, 10));
		tempList.add(setMetroLayoutParams(1, 1, 11, 0, 0, 10, 10, 10));
		tempList.add(setMetroLayoutParams(2, 1, 11, 14, 10, 10, 0, 10));
		tempList.add(setMetroLayoutParams(2, 1, 14, 0, 0, 10, 10, 10));
		tempList.add(setMetroLayoutParams(1, 1, 15, 16, 10, 10, 0, 10));
		tempList.add(setMetroLayoutParams(1, 1, 16, 0, -20, 10, 10, 10));
		tempList.add(setMetroLayoutParams(1, 1, 17, 18, 10, 10, 10, 10));
		tempList.add(setMetroLayoutParams(1, 1, 17, 19, 10, 10, 0, 0));

		return tempList;
	}

	private RelativeLayout.LayoutParams setMetroLayoutParams(int widthRate,
			int heightRate, int anchor1, int anchor2, int marginArg1,
			int marginArg2, int marginArg3, int marginArg4) {
		RelativeLayout.LayoutParams temp = new RelativeLayout.LayoutParams(
				oneUnitWidth * widthRate, oneUnitWidth * heightRate);
		if (anchor1 == NO_ANCHOR_PARAM && anchor2 == NO_ANCHOR_PARAM) {
			temp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			temp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		}
		temp.addRule(RelativeLayout.BELOW, anchor1);
		temp.addRule(RelativeLayout.RIGHT_OF, anchor2);
		temp.setMargins(marginArg1, marginArg2, marginArg3, marginArg4);
		return temp;
	}
}
