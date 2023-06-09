package customEntities;

import java.util.List;

import base.GenericEntity;

//@{"dataEntity": "tweet"}
public class Tweet extends GenericEntity {

	private String userName;
	private String userLocation;
	private String userDescription;
	private String userCreated;
	private int numberFollowers;
	private int numberFriends;
	private int numberFavourites;
	private boolean userVerified;
	private String date;
	private String content;
	private List<String> assignedHashTags;
	private List<String> containedHashTags;
	private String source;
	private boolean isRetweet;
	
	
	public Tweet(String[] data) {
		this.userName = data[1];
		this.userLocation = data[2];
		this.userCreated = data[3];
		this.numberFollowers = Integer.parseInt(data[4]);
		this.numberFriends = Integer.parseInt(data[5]);
		this.numberFavourites = Integer.parseInt(data[6]);
		this.userVerified = this.parseBoolean(data[7]);
		this.date = data[8];
		this.content = data[9];
		this.assignedHashTags = this.parseArray(data[10]);
		this.source = data[11];
		this.isRetweet = this.parseBoolean(data[12]);
	}
	
	
	public static Tweet createNew(String[] data) {
		return new Tweet(data);
	}
	
	public String getContent() { return this.content; }
	
	public void setContent(String content) { this.content = content; }
	
	public boolean isUserVerified() { return this.userVerified; }
}
