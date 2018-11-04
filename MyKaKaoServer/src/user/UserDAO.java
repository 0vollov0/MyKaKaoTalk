package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO()
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
	public int login(String userID, String userPassword) {
		String SQL = "select userPassword from USER where userID=?";
		try {
			pstmt =conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(rs.getString(1).equals(userPassword)) {
					return 1; //로그인 성공
				}
				else
				{
					return 0; // 비밀번호 불일치
				}
			}
			return -1; // 아이디가 없음
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -2;//데이터베이스 오류
	}
	
	public int join(User user)
	{
		String SQL = "insert into USER(userID,userPassword,userName,userEmail,userTel) values(?,?,?,?,?)";
		try {
			pstmt =  conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserEmail());
			pstmt.setString(5, user.getUserTel());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public int updateProfile(String userID,String profilemsg) {
		String SQL = "update user set profile = ? where userID = ?";	
		try {
			pstmt =  conn.prepareStatement(SQL);
			pstmt.setString(1, profilemsg);
			pstmt.setString(2, userID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
		}
	public String getProfilemsg(String userID){
		String SQL ="select profile from user where userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
