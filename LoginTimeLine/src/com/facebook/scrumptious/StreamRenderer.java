/*
 * Copyright 2010 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.scrumptious;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

class StreamRenderer {
	List<Post> model = new ArrayList<Post>();

	private StringBuilder sb;

	public static String render(JSONObject data) {
		StreamRenderer renderer = new StreamRenderer();
		return renderer.doRender(data);
	}

	private StreamRenderer() {
		this.sb = new StringBuilder();
	}

	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
	}

	private String getResult() {
		return sb.toString();
	}

	private String doRender(JSONObject data) {

		try {
			JSONArray posts = data.getJSONArray("data");

			for (int i = 0; i < posts.length(); i++) {
				renderPost(posts.getJSONObject(i));
				
			}
//			for(int i=0;i<model.size();i++){
//				if(model.get(i).comm.get(location)){
//					Log.d("comment",""+model.get(i).comm.get(0).getMessage());
//					Log.d("=============",""+model.get(i).getTime().toString());
//				}
//				
//			}
			return getResult();
		} catch (JSONException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
			return "";
		}
	}

	private void renderPost(JSONObject post) throws JSONException {
		Post p = new Post();
		renderFrom(post, p);
		renderTo(post, p);
		renderMessage(post, p);
		renderAttachment(post, p);
		renderTimeStamp(post, p);
		renderLikes(post, p);
		renderComments(post, p);
		model.add(p);
	}

	private void renderFrom(JSONObject post, Post p) throws JSONException {
		JSONObject from = post.getJSONObject("from");
		String fromName = from.getString("name");
		String fromId = from.getString("id");
		p.setSrcFrom("src=http://graph.facebook.com/" + fromId + "/picture");
		p.setNameFrom(fromName);

	}

	private void renderTo(JSONObject post, Post p) throws JSONException {
		JSONObject to = post.optJSONObject("to");
		if (to != null) {
			JSONObject toData = to.getJSONArray("data").getJSONObject(0);
			String toName = toData.getString("name");
			String toId = toData.getString("id");
			p.setSrcTo("src=http://graph.facebook.com/" + toId + "/picture");
			p.setNameTo(toName);
		}
	}

	private void renderMessage(JSONObject post, Post p) {
		String message = post.optString("message");
		p.setMessage(message);
	}

	private void renderAttachment(JSONObject post, Post p) {
		String name = post.optString("name");
		String link = post.optString("link");
		String picture = post.optString("picture");
		String source = post.optString("source"); // for videos
		String caption = post.optString("caption");
		String description = post.optString("description");

		String[] fields = new String[] { name, link, picture, source, caption,
				description };
		boolean hasAttachment = false;
		for (String field : fields) {
			if (field.length() != 0) {
				hasAttachment = true;
				break;
			}
		}

		if (!hasAttachment) {
			return;
		}

		if (link != null) {
			p.setLink(link);
		}
		if (caption != "") {
			p.setCaption(caption);
		}

		if (picture != "") {
			p.setPicture(picture);
		}

		if (description != "") {
			p.setDescription(description);
		}

	}

	private void renderTimeStamp(JSONObject post, Post p) {
		String dateStr = post.optString("created_time");
		SimpleDateFormat formatter = getDateFormat();
		ParsePosition pos = new ParsePosition(0);
		Date date=formatter.parse(dateStr,pos);
		p.setTime(date);
	}

	private void renderLikes(JSONObject post, Post p) throws JSONException {

		JSONObject likes = post.optJSONObject("likes");
		if (likes != null) {
			JSONArray data = likes.optJSONArray("data");

			if (data.length() > 0) {
				String desc = data.length() == 1 ? " person likes this"
						: " people like this";

				p.setNumberLike(new Integer(data.length()).toString() + desc);
				for (int j = 0; j < data.length(); j++) {
					JSONObject like = data.getJSONObject(j);
					renderLike(like, p);
				}
			}

		}

	}

	private void renderLike(JSONObject like, Post p) {

		String authorId = like.optString("id");
		String authorName = like.optString("name");
		String src = "http://graph.facebook.com/" + authorId + "/picture";
	
		p.setLike(src, authorName);
	}

	private void renderComments(JSONObject post, Post p) throws JSONException {
		JSONObject comments = post.optJSONObject("comments");
		if (comments != null) {
			JSONArray data = comments.optJSONArray("data");

			if (data != null) {
				String desc = data.length() == 1 ? " person comments this"
						: " people comment this";

				p.setNumberComments(new Integer(data.length()).toString()
						+ desc);
				for (int j = 0; j < data.length(); j++) {

					JSONObject comment = data.getJSONObject(j);
					renderComment(comment, p);
				}
			}
		}

	}

	private void renderComment(JSONObject comment, Post p) {
		JSONObject from = comment.optJSONObject("from");

		String src = "";
		String authorName = "";
		String authorId = "";
		if (from == null) {
			Log.w("StreamRenderer",
					"Comment missing from field: " + comment.toString());
		} else {
			authorId = from.optString("id");
			authorName = from.optString("name");
			src = "http://graph.facebook.com/" + authorId + "/picture";
		}

		String message = comment.optString("message");
		p.setComment(src, authorName, message);
	}

}