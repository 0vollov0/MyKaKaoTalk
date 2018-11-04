package chatlog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ChatLogDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public ChatLogDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/mykakao";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ChatLog> getChatList(int roomID){
		ArrayList<ChatLog> ChatList = new ArrayList<ChatLog>();
		String SQL ="select * from CHATLOG where roomID = ? order by chatTime";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, roomID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ChatLog chatlog = new ChatLog();
				chatlog.setSenderID(rs.getString("senderID"));
				chatlog.setRoomID(roomID);
				chatlog.setChatContent(rs.getString("chatContent"));
				chatlog.setChatTime(rs.getString("chatTime"));
				ChatList.add(chatlog);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return ChatList;
	}
	
	public ChatLog getLastChat(int roomID){
		ChatLog chatlog= new ChatLog();
		String SQL ="select * from CHATLOG where roomID = ? order by chatTime desc limit 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, roomID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				chatlog.setSenderID(rs.getString("senderID"));
				chatlog.setChatContent(rs.getString("chatContent"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatlog;
	}
	
//	public int sendMessage(SingleChat messagelog)
//	{
//		String SQL = "insert into singlechat values(?,?,?,now())";
//		try {
//			pstmt =  conn.prepareStatement(SQL);
//			pstmt.setString(1, messagelog.getSenderID());
//			pstmt.setString(2, messagelog.getReceiverID());
//			pstmt.setString(3, messagelog.getChatContent());
//			return pstmt.executeUpdate();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return -1; // 데이터베이스 오류
//	}
	public int storeMessage(ChatLog messagelog)
	{
		String SQL = "insert into CHATLOG values(?,?,?,now())";
		try {
			pstmt =  conn.prepareStatement(SQL);
			pstmt.setString(1, messagelog.getSenderID());
			pstmt.setInt(2, messagelog.getRoomID());
			pstmt.setString(3, messagelog.getChatContent());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
}
