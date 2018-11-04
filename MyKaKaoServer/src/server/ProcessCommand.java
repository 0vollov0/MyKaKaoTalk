package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import addfriendlist.AddFriendList;
import addfriendlist.AddFriendListDAO;
import chatlog.ChatLog;
import chatlog.ChatLogDAO;
import chattingroom.ChattingRoom;
import chattingroom.ChattingRoomDAO;
import friend.FriendDAO;
import singlechat.SingleChat;
import singlechat.SingleChatDAO;
import user.User;
import user.UserDAO;

public class ProcessCommand {
	DataInputStream in;
	DataOutputStream out;

	HashMap<String, DataOutputStream> clients;
	ArrayList<String> friendList;
	ArrayList<String> requestaddfriend;
	ArrayList<String> requestedaddfriend;
	ArrayList<ChattingRoom> chattingroomlist;

	private UserDAO userDAO;
	private FriendDAO friendDAO;
	private SingleChatDAO singlechatDAO;
	private AddFriendListDAO addfriendlistDAO;
	private ChattingRoomDAO chattingroomDAO;
	private ChatLogDAO chatlogDAO;

	ProcessCommand(HashMap<String, DataOutputStream> clients, DataInputStream in, DataOutputStream out) {
		this.clients = clients;
		this.in = in;
		this.out = out;
		userDAO = new UserDAO();
		friendDAO = new FriendDAO();
		singlechatDAO = new SingleChatDAO();
		addfriendlistDAO = new AddFriendListDAO();
		chattingroomDAO = new ChattingRoomDAO();
		chatlogDAO = new ChatLogDAO();
	}

	public void commandLogin(String userID, String userPassword) throws IOException {
		if (userDAO.login(userID, userPassword) == 1) {
			clients.put(userID, out);
			out.writeInt(MyKaKaoServer.LOGIN);
			out.writeInt(1);// 로그인 성공
			friendList = friendDAO.friendList(userID);
			requestaddfriend= addfriendlistDAO.requestList(userID);
			requestedaddfriend = addfriendlistDAO.requestedList(userID);
			chattingroomlist = chattingroomDAO.getChattingRoomList(userID);
			
			out.writeInt(friendList.size());
			for (int i = 0; i < friendList.size(); i++) {
				out.writeUTF(friendList.get(i));
			}
			
			out.writeInt(requestaddfriend.size());
			for (int i = 0; i < requestaddfriend.size(); i++) {
				out.writeUTF(requestaddfriend.get(i));
			}
			
			out.writeInt(requestedaddfriend.size());
			for (int i = 0; i < requestedaddfriend.size(); i++) {
				out.writeUTF(requestedaddfriend.get(i));
			}
			out.writeInt(chattingroomlist.size());
			for (int i = 0; i < chattingroomlist.size(); i++) {
				int roomID = chattingroomlist.get(i).getRoomID();
				out.writeInt(roomID);
				out.writeUTF(chattingroomlist.get(i).getRoomName()+"   ("+chattingroomDAO.getMemberCount(roomID)+")");
				String lastchat;
				ChatLog chatlog = chatlogDAO.getLastChat(roomID);
				if(chatlog.getChatContent() != null) {
					lastchat = getChatLog(chatlog);
				}else {
					lastchat = "대화 내용이 없습니다.";
				}
				if(lastchat.length()>24) {
					lastchat =lastchat.substring(0,24);
				}
				out.writeUTF(lastchat);
			}
			chattingroomlist.clear();
			friendList.clear();
		} else {
			out.writeInt(MyKaKaoServer.LOGIN);
			out.writeInt(0); // 로그인 실패
		}
	}

	public void getChatList(String userID, String friendID) throws IOException {
		ArrayList<String> chatList = getChatLog(singlechatDAO.getChatList(userID, friendID), userID);
		out.writeInt(MyKaKaoServer.GETCHATLIST);
		out.writeInt(chatList.size());
		for (int i = 0; i < chatList.size(); i++) {
			out.writeUTF(chatList.get(i));
		}
		out.writeInt(MyKaKaoServer.SINGLE);
		out.writeUTF(friendID);
		chatList.clear();
	}
	
	public void getChatList(int roomID) throws IOException {
		ArrayList<String> chatList = getChatLog(chatlogDAO.getChatList(roomID));
		out.writeInt(MyKaKaoServer.GETCHATLIST);
		out.writeInt(chatList.size());
		for (int i = 0; i < chatList.size(); i++) {
			out.writeUTF(chatList.get(i));
		}
		out.writeInt(MyKaKaoServer.MULTI);
		out.writeInt(roomID);
		out.writeUTF(chattingroomDAO.getRoomName(roomID));
		chatList.clear();
	}
	
	public void sendMessage(int userCnt) throws IOException {
		for (int i = 0; i < userCnt; i++) {
			friendList.add(in.readUTF());
		}
		sendToFriends(in.readUTF(), friendList, in.readUTF());
		friendList.clear();
	}

	public ArrayList<String> getChatLog(ArrayList<SingleChat> list, String userID) {
		ArrayList<String> chatList = new ArrayList<String>();
		String chat;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getSenderID().equals(userID)) {
				chat = list.get(i).getSenderID() + "\n " + list.get(i).getChatContent() + "\n("
						+ list.get(i).getChatTime() + ")" + "\n\n";
			} else {
				chat = list.get(i).getSenderID() + "\n " + list.get(i).getChatContent() + "\n("
						+ list.get(i).getChatTime() + ")" + "\n\n";
			}
			chatList.add(chat);
		}
		return chatList;
	}
	
	public ArrayList<String> getChatLog(ArrayList<ChatLog> list) {
		ArrayList<String> chatList = new ArrayList<String>();
		String chat;
		for (int i = 0; i < list.size(); i++) {
			chat = list.get(i).getSenderID() + "\n " + list.get(i).getChatContent() + "\n("+ list.get(i).getChatTime() + ")" + "\n\n";
			chatList.add(chat);
		}
		return chatList;
	}
	public String getChatLog(ChatLog chatlog) {
		String chat;
		chat ="["+chatlog.getSenderID() + "]      " + chatlog.getChatContent();
		return chat;
	}

	void sendToFriends(String userID, ArrayList<String> friendList, String msg) {
		SingleChat messagelog = new SingleChat();
		if (friendList.size() == 1) {
			messagelog.setSenderID(userID);
			messagelog.setReceiverID(friendList.get(0));
			messagelog.setChatContent(msg);
			messagelog.setCurrentTime();
			singlechatDAO.storeMessage(messagelog);
			String chat = userID + "\n " + msg + "\n( " + messagelog.getChatTime() + ")" + "\n\n";
			try {
				DataOutputStream friendout = (DataOutputStream) clients.get(friendList.get(0));
				if (friendout != null) {
					friendout.writeInt(MyKaKaoServer.SENDMESSAGE);
					friendout.writeUTF(userID);
					friendout.writeUTF(chat);
				}

				DataOutputStream userout = (DataOutputStream) clients.get(userID);
				userout.writeInt(MyKaKaoServer.SENDMESSAGE);
				userout.writeUTF(friendList.get(0));
				userout.writeUTF(chat);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	void sendMessage(String userID,int roomID, String msg) {
		ChatLog chatlog = new ChatLog();
		chatlog.setSenderID(userID);
		chatlog.setRoomID(roomID);
		chatlog.setChatContent(msg);
		chatlog.setCurrentTime();
		chatlogDAO.storeMessage(chatlog);
		String chat = userID + "\n " + msg + "\n( " + chatlog.getChatTime() + ")" + "\n\n";
		String lastchat = getChatLog(chatlog);
		if(lastchat.length()>24) {
			lastchat =lastchat.substring(0,24);
		}
		ArrayList<String> memberlist = chattingroomDAO.getChattingRoomMembers(roomID);
		try {
			for(int i =0;i<memberlist.size();i++) {
				DataOutputStream memberout = (DataOutputStream) clients.get(memberlist.get(i));
				if (memberout != null) {
					memberout.writeInt(MyKaKaoServer.SENDCHAT);
					memberout.writeInt(roomID);
					memberout.writeUTF(chat);
					memberout.writeUTF(lastchat);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	void join(User user) throws IOException {
		UserDAO userDAO = new UserDAO();
		int DBsatus =  userDAO.join(user);
		out.writeInt(MyKaKaoServer.JOIN);
		out.writeInt(DBsatus);
	}
	
	void requestFriend(AddFriendList addfriendlist) throws IOException {
		int dbs = addfriendlistDAO.requestFriend(addfriendlist);
		out.writeInt(MyKaKaoServer.REQUESTFRIEND);
		if(dbs != -1) {
			out.writeInt(dbs);
			out.writeUTF(addfriendlist.getReceiverID());
			out.writeInt(0);
			DataOutputStream friendout = (DataOutputStream) clients.get(addfriendlist.getReceiverID());
			if (friendout != null) {
				friendout.writeInt(MyKaKaoServer.REQUESTFRIEND);
				friendout.writeInt(dbs);
				friendout.writeUTF(addfriendlist.getSenderID());
				friendout.writeInt(1);
			}
		}else {
			out.writeInt(dbs);
		}
	}
	
	void acceptFriend(AddFriendList addfriendlist) throws IOException {
		addfriendlistDAO.deleteFromAddFriendList(addfriendlist);
		friendDAO.makeFriend(addfriendlist.getSenderID(),addfriendlist.getReceiverID());
		DataOutputStream friendout = (DataOutputStream) clients.get(addfriendlist.getReceiverID());
		if (friendout != null) {
			friendout.writeInt(MyKaKaoServer.ACCEPTFRIEND);
			friendout.writeUTF(addfriendlist.getSenderID());
			friendout.writeInt(1);
		}
		out.writeInt(MyKaKaoServer.ACCEPTFRIEND);
		out.writeUTF(addfriendlist.getReceiverID());
		out.writeInt(0);
//		out.writeInt(addfriendlistDAO.acceptFriend(addfriendlist));
	}
	void declineFriend(AddFriendList addfriendlist) throws IOException {
		addfriendlistDAO.deleteFromAddFriendList(addfriendlist);
		DataOutputStream friendout = (DataOutputStream) clients.get(addfriendlist.getReceiverID());
		if (friendout != null) {
			friendout.writeInt(MyKaKaoServer.DECLINEFRIEND);
			friendout.writeUTF(addfriendlist.getSenderID());
			friendout.writeInt(1);
		}
		out.writeInt(MyKaKaoServer.DECLINEFRIEND);
		out.writeUTF(addfriendlist.getReceiverID());
		out.writeInt(0);
	}
	void makeChattingRoom(String roomname,ArrayList<String> memberlist) throws IOException {
		int roomID = chattingroomDAO.makeChattiingRoom(roomname, memberlist);
		for(int i =0;i<memberlist.size();i++) {
			DataOutputStream memberout = (DataOutputStream) clients.get(memberlist.get(i));
			if (memberout != null) {
				memberout.writeInt(MyKaKaoServer.MAKECHATTINGROOM);
				memberout.writeInt(roomID);
				memberout.writeUTF(roomname+"   ("+chattingroomDAO.getMemberCount(roomID)+")");
				memberout.writeUTF("대화 내용이 없습니다.");
			}
		}
	}
	void exitChattingRoom(int roomID,String userID) throws IOException {
		int temp = chattingroomDAO.exitChattingRoom(roomID, userID);
		
		sendMessage("",roomID,userID+"님이 나가셨습니다.");
		if(temp != -1) {
			ArrayList<String> memberlist = chattingroomDAO.getChattingRoomMembers(roomID);
			for(int i =0;i<memberlist.size();i++) {
				DataOutputStream memberout = (DataOutputStream) clients.get(memberlist.get(i));
				if (memberout != null) {
					memberout.writeInt(MyKaKaoServer.EXITCHATTINGROOM);
					memberout.writeInt(1);
					memberout.writeInt(roomID);
				}
			}
			chattingroomDAO.cleanChattingRoom(roomID);
			out.writeInt(MyKaKaoServer.EXITCHATTINGROOM);
			out.writeInt(0);
			out.writeInt(roomID);
			
		}
	}
	void deleteFriend(String userID,String friendID) throws IOException {
		int result = friendDAO.deleteFrined(userID, friendID);
		out.writeInt(MyKaKaoServer.DELETEFRIEND);
		out.writeInt(result);
		out.writeUTF(friendID);
		DataOutputStream friendout = (DataOutputStream) clients.get(friendID);
		if (friendout != null) {
			friendout.writeInt(MyKaKaoServer.DELETEFRIEND);
			friendout.writeInt(result);
			friendout.writeUTF(userID);
		}
	}
	void getProfilemsg(String userID) throws IOException {
		out.writeInt(MyKaKaoServer.GETPROFILE);
		out.writeUTF(userID);
		out.writeUTF(userDAO.getProfilemsg(userID));
	}
	void modifyProfilemsg(String userID,String profilemsg) throws IOException {
		out.writeInt(MyKaKaoServer.MODIFYPROFILE);
		out.writeInt(userDAO.updateProfile(userID, profilemsg));
	}
}
