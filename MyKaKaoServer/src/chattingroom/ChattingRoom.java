package chattingroom;

public class ChattingRoom {

	int roomID;
	String roomName;
	String member;
	public ChattingRoom(int roomID,String roomName){
		this.roomID = roomID;
		this.roomName = roomName;
	}
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}

}
