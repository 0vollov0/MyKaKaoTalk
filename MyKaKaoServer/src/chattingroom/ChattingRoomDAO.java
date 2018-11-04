package chattingroom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChattingRoomDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public ChattingRoomDAO()
	{
		try {
			String dbURL = "jdbc:mysql://localhost:3306/mykakao";
			String dbID ="root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public int makeChattiingRoom(String roomname,ArrayList<String> memberlist)
	{
		String SQL = "insert into CHATTINGROOM values";
		int roomID = getMaxRoomID()+1;
		for(int i =0 ; i<memberlist.size();i++) {
			SQL=SQL+"("+roomID+","+"'"+roomname+"','"+memberlist.get(i)+"'),";
		}
		SQL=SQL.substring(0,SQL.length()-1);
		try {
			pstmt =  conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			return roomID; 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public int exitChattingRoom(int roomID,String userID)
	{
		String SQL = "delete from CHATTINGROOM where roomID = ? and member = ?";
		
		try {
			pstmt =  conn.prepareStatement(SQL);
			pstmt.setInt(1,roomID);
			pstmt.setString(2, userID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public void cleanChattingRoom(int roomID) {
		String SQL = "select * from CHATTINGROOM where roomID = ?";
		try {
			pstmt =  conn.prepareStatement(SQL);
			pstmt.setInt(1,roomID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				
			}else {
				cleanChatLog(roomID);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int cleanChatLog(int roomID) {
		String SQL = "delete from CHATLOG where roomID = ?";
		try {
			pstmt =  conn.prepareStatement(SQL);
			pstmt.setInt(1,roomID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public int getMaxRoomID() {
		String SQL ="select MAX(roomID) from CHATTINGROOM";
		int maxroomID = 0;
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				maxroomID = rs.getInt(1);
				System.out.println(maxroomID);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return maxroomID;
	}
	
	public ArrayList<ChattingRoom> getChattingRoomList(String userID){
		String SQL ="select roomID,roomName from CHATTINGROOM where member=?";
		ArrayList<ChattingRoom> list = new ArrayList<ChattingRoom>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new ChattingRoom(rs.getInt(1),rs.getString(2)));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getMemberCount(int roomID) {
		String SQL ="select  count(*) as membercount from CHATTINGROOM where roomID = ? group by roomID";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, roomID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ArrayList<String> getChattingRoomMembers(int roomID) {
		String SQL ="select member from chattingroom where roomID = ?";
		ArrayList<String> list = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,roomID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getRoomName(int roomID) {
		String SQL ="select distinct roomName from CHATTINGROOM where roomID=?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, roomID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
