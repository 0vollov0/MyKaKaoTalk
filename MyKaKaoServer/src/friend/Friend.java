package friend;

public class Friend {
	
	private String userID;
	private String friendID;
	
	public Friend(String userID, String friendID) {
		// TODO Auto-generated constructor stub
		this.userID = userID;
		this.friendID = friendID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getFriendID() {
		return friendID;
	}
	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}
	
}
