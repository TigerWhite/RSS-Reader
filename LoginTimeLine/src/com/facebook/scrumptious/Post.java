package com.facebook.scrumptious;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

public class Post {
	private String srcFrom="";
	private String nameFrom="";
	private String srcTo="";
	private String nameTo="";
	private String message="";

	private String picture="";
	private String link="";
	private String source="";
	private String caption="";
	private String description="";
	private Date time;
	
	private String numberLike="";
	private String numberComments="";
	private String numberShares="";
	
	List<Comment> comm=new ArrayList<Comment>();
	List<Like> like=new ArrayList<Like>();
	public String getNumberLike() {
		return numberLike;
	}
	public void setNumberLike(String numberLike) {
		this.numberLike = numberLike;
	}
	public String getNumberComments() {
		return numberComments;
	}
	public void setNumberComments(String numberComments) {
		this.numberComments = numberComments;
	}
	public String getNumberShares() {
		return numberShares;
	}
	public void setNumberShares(String numberShares) {
		this.numberShares = numberShares;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSrcFrom() {
		return srcFrom;
	}
	public void setSrcFrom(String srcFrom) {
		this.srcFrom = srcFrom;
	}
	public String getNameFrom() {
		return nameFrom;
	}
	public void setNameFrom(String nameFrom) {
		this.nameFrom = nameFrom;
	}
	public String getSrcTo() {
		return srcTo;
	}
	public void setSrcTo(String srcTo) {
		this.srcTo = srcTo;
	}
	public String getNameTo() {
		return nameTo;
	}
	public void setNameTo(String nameTo) {
		this.nameTo = nameTo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public void setComment(String src,String name, String mess){
		Comment c=new Comment();
		c.setSrc(src);
		c.setName(name);
		c.setMessage(mess);
		comm.add(c);
		
	
	}
	public void setLike(String src, String name){
		Like l=new Like();
		l.setSrc(src);
		l.setName(name);
		like.add(l);
		
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}

