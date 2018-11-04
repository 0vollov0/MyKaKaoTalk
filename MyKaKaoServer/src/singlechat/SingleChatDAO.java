package singlechat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SingleChatDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public SingleChatDAO() {
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

	public ArrayList<SingleChat> getChatList(String userID,String friendID){
		ArrayList<SingleChat> singleChatList = new ArrayList<SingleChat>();
		String SQL ="select * from singlechat where senderID IN (?,?) AND receiverID IN (?,?) order by chatTime;";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, friendID);
			pstmt.setString(3, userID);
			pstmt.setString(4, friendID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				SingleChat singlechat = new SingleChat();
				singlechat.setSenderID(rs.getString("senderID"));
				singlechat.setReceiverID(rs.getString("receiverID"));
				singlechat.setChatContent(rs.getString("chatContent"));
				singlechat.setChatTime(rs.getString("chatTime"));
				singleChatList.add(singlechat);
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
		return singleChatList;
	}
	
	public int sendMessage(SingleChat messagelog)
	{
		String SQL = "insert into singlechat values(?,?,?,now())";
		try {
			pstmt =  conn.prepareStatement(SQL);
			pstmt.setString(1, messagelog.getSenderID());
			pstmt.setString(2, messagelog.getReceiverID());
			pstmt.setString(3, messagelog.getChatContent());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	public int storeMessage(SingleChat messagelog)
	{
		String SQL = "insert into singlechat values(?,?,?,now())";
		try {
			pstmt =  conn.prepareStatement(SQL);
			pstmt.setString(1, messagelog.getSenderID());
			pstmt.setString(2, messagelog.getReceiverID());
			pstmt.setString(3, messagelog.getChatContent());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
}
