package ltt.intership.data;

public class mSocialMedia {
	private String name;
	private int imgSrc;
	
	public mSocialMedia(String name, int src){
		this.name = name;
		this.imgSrc = src;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getImgSrc(){
		return this.imgSrc;
	}
}
