package addfriendlist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AddFriendListDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public AddFriendListDAO() {
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

	public ArrayList<String> requestList(String senderID){
		String SQL ="select receiverID from ADDFRIENDLIST where senderID = ?";
		ArrayList<String> list = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, senderID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> requestedList(String receiverID){
		String SQL ="select senderID from ADDFRIENDLIST where receiverID = ?";
		ArrayList<String> list = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, receiverID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int requestFriend(AddFriendList addfriendlist) {
		try {
			String SQL = "select userID from user where userID= ?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, addfriendlist.getReceiverID());
			rs = pstmt.executeQuery();
			if(!rs.next()) {
				return -1;
			}
			 SQL ="select * from friend where userID in(?,?) and friendID in(?,?)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, addfriendlist.getSenderID());
			pstmt.setString(2, addfriendlist.getReceiverID());
			pstmt.setString(3, addfriendlist.getReceiverID());
			pstmt.setString(4, addfriendlist.getSenderID());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return -1;
			}
			SQL ="select * from ADDFRIENDLIST where senderID=? and receiverID=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, addfriendlist.getSenderID());
			pstmt.setString(2, addfriendlist.getReceiverID());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return -1;
			}
			SQL ="select * from ADDFRIENDLIST where senderID=? and receiverID=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, addfriendlist.getReceiverID());
			pstmt.setString(2, addfriendlist.getSenderID());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return -1;
			}
			SQL = "insert into ADDFRIENDLIST values(?,?)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, addfriendlist.getSenderID());
			pstmt.setString(2, addfriendlist.getReceiverID());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}

	public int deleteFromAddFriendList(AddFriendList addfriendlist) {
		try {
			String SQL = "delete from ADDFRIENDLIST where senderID in(?,?)  AND receiverID IN(?,?);";
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, addfriendlist.getSenderID());
			pstmt.setString(2, addfriendlist.getReceiverID());
			pstmt.setString(3, addfriendlist.getReceiverID());
			pstmt.setString(4, addfriendlist.getSenderID());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
}
