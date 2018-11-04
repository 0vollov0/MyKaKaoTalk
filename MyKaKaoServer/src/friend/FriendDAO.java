package friend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FriendDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public FriendDAO()
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

	public int makeFriend(String user1,String user2)
	{
		String SQL = "insert into FRIEND values(?,?),(?,?)";
		try {
			pstmt =  conn.prepareStatement(SQL);
			pstmt.setString(1, user1);
			pstmt.setString(2, user2);
			pstmt.setString(3, user2);
			pstmt.setString(4, user1);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public ArrayList<String> friendList(String userID){
		String SQL ="select friendID from friend where userID =?";
		ArrayList<String> list = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int deleteFrined(String userID,String friendID) {
		String SQL = "delete from FRIEND where userID IN(?,?) and friendID IN(?,?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, friendID);
			pstmt.setString(3, friendID);
			pstmt.setString(4, userID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
