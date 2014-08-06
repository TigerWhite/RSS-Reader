package com.ecs.android.sample.oauth2;

public class MyItemComment {
	private String kind;
	private String etag;
	private String title;

	private String kind_items;
	private String etag_items;
	private String verb_items;
	private String id_items;
	private String published_items;
	private String updated_items;

	private String displayName_actor;
	private String id_actor;
	private String url_actor;
	private String url_image_actor;

	private String objectType_object;
	private String content_object;
	
	private String selfLink;
	private String id_inReplyTo;
	private String url_inReplyTo;
	
	private String totalItems_plusoners;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKind_items() {
		return kind_items;
	}

	public void setKind_items(String kind_items) {
		this.kind_items = kind_items;
	}

	public String getEtag_items() {
		return etag_items;
	}

	public void setEtag_items(String etag_items) {
		this.etag_items = etag_items;
	}

	public String getVerb_items() {
		return verb_items;
	}

	public void setVerb_items(String verb_items) {
		this.verb_items = verb_items;
	}

	public String getId_items() {
		return id_items;
	}

	public void setId_items(String id_items) {
		this.id_items = id_items;
	}

	public String getPublished_items() {
		return published_items;
	}

	public void setPublished_items(String published_items) {
		this.published_items = published_items;
	}

	public String getUpdated_items() {
		return updated_items;
	}

	public void setUpdated_items(String updated_items) {
		this.updated_items = updated_items;
	}

	public String getDisplayName_actor() {
		return displayName_actor;
	}

	public void setDisplayName_actor(String displayName_actor) {
		this.displayName_actor = displayName_actor;
	}

	public String getId_actor() {
		return id_actor;
	}

	public void setId_actor(String id_actor) {
		this.id_actor = id_actor;
	}

	public String getUrl_actor() {
		return url_actor;
	}

	public void setUrl_actor(String url_actor) {
		this.url_actor = url_actor;
	}

	public String getUrl_image_actor() {
		return url_image_actor;
	}

	public void setUrl_image_actor(String url_image_actor) {
		this.url_image_actor = url_image_actor;
	}

	public String getObjectType_object() {
		return objectType_object;
	}

	public void setObjectType_object(String objectType_object) {
		this.objectType_object = objectType_object;
	}

	public String getContent_object() {
		return content_object;
	}

	public void setContent_object(String content_object) {
		this.content_object = content_object;
	}

	public String getSelfLink() {
		return selfLink;
	}

	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}

	public String getId_inReplyTo() {
		return id_inReplyTo;
	}

	public void setId_inReplyTo(String id_inReplyTo) {
		this.id_inReplyTo = id_inReplyTo;
	}

	public String getUrl_inReplyTo() {
		return url_inReplyTo;
	}

	public void setUrl_inReplyTo(String url_inReplyTo) {
		this.url_inReplyTo = url_inReplyTo;
	}

	public String getTotalItems_plusoners() {
		return totalItems_plusoners;
	}

	public void setTotalItems_plusoners(String totalItems_plusoners) {
		this.totalItems_plusoners = totalItems_plusoners;
	}

	public String toString() {
		return displayName_actor+": "
				+ content_object;
	}
	
}
