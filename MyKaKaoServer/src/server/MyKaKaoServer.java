package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import addfriendlist.AddFriendList;
import user.User;

public class MyKaKaoServer {
	public static final int LOGIN = 100;
	public static final int JOIN = 101;
	
	public static final int GETCHATLIST = 200;
	public static final int SINGLE = 201;
	public static final int MULTI = 202;
	
	public static final int  SENDMESSAGE = 400;
	public static final int  REQUESTFRIEND = 500;
	public static final int  ACCEPTFRIEND = 600;
	public static final int  DECLINEFRIEND = 700;
	public static final int  MAKECHATTINGROOM = 800;
	public static final int  SENDCHAT = 900;
	public static final int  INVITEMEMBER = 1000;
	public static final int  EXITCHATTINGROOM = 1100;
	public static final int  DELETEFRIEND = 1200;
	public static final int  GETPROFILE = 1300;
	public static final int  MODIFYPROFILE = 1400;
	
	public int command;
	public int sendway;

	HashMap<String, DataOutputStream> clients;
	
	public static User joinuser;
	
	MyKaKaoServer() {
		clients = new HashMap<String, DataOutputStream>();
		Collections.synchronizedMap(clients);
	}

	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(7777);
			System.out.println("서버가 시작되었습니다.");
			while (true) {
				socket = serverSocket.accept();
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "에서 접속하였습니다.");
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MyKaKaoServer().start();
	}

	class ServerReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		ProcessCommand psCommand;

		ServerReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			psCommand = new ProcessCommand(clients, in, out);
		}

		public void run() {
			String ID="";
			try {
				while (in != null) {

					command = in.readInt(); // 명령어 불러오기

					if (command == LOGIN) { // 로그인 후 친구리스트 보내주기
						String userID = in.readUTF();
						ID = userID;
						String userPassword = in.readUTF();
						psCommand.commandLogin(userID, userPassword);
						command = 0;
					}else if (command == JOIN) {
						joinuser = new User(in.readUTF(),in.readUTF(),in.readUTF(),in.readUTF(),in.readUTF());
						psCommand.join(joinuser);
						joinuser= null;
						command = 0;
					} else if (command == GETCHATLIST) {
						sendway = in.readInt();
						if(sendway== SINGLE) {
							String userID = in.readUTF();
							String friendID = in.readUTF();
							psCommand.getChatList(userID, friendID);
						}else if(sendway== MULTI) {
							psCommand.getChatList(in.readInt());
						}
						sendway=0;
						command = 0;
					} else if (command == SENDMESSAGE) {
						int userCnt = in.readInt();
						psCommand.sendMessage(userCnt);
						command = 0;
					}else if (command == SENDCHAT) {
						String userID = in.readUTF();
						int roomID = in.readInt();
						String msg = in.readUTF();
						psCommand.sendMessage(userID, roomID, msg);
						command = 0;
					}else if (command == REQUESTFRIEND) {
						String senderID = in.readUTF();
						String receiverID = in.readUTF();
						AddFriendList addfriendlist = new AddFriendList(senderID,receiverID);
						psCommand.requestFriend(addfriendlist);
						command = 0;
					}else if (command == ACCEPTFRIEND) {
						String senderID = in.readUTF();
						String receiverID = in.readUTF();
						AddFriendList addfriendlist = new AddFriendList(senderID,receiverID);
						psCommand.acceptFriend(addfriendlist);
						command = 0;
					}else if (command == DECLINEFRIEND) {
						String senderID = in.readUTF();
						String receiverID = in.readUTF();
						AddFriendList addfriendlist = new AddFriendList(senderID,receiverID);
						psCommand.declineFriend(addfriendlist);
						command = 0;
					}else if (command == MAKECHATTINGROOM) {
						int max;
						String roomname;
						ArrayList<String> memberlist =new ArrayList<String>();
						
						max = in.readInt();
						roomname=in.readUTF();
						
						for(int i=0;i<max;i++) {
							memberlist.add(in.readUTF());
						}

						psCommand.makeChattingRoom(roomname, memberlist);
						command = 0;
					}else if (command == EXITCHATTINGROOM) {
						int roomID = in.readInt();
						String userID = in.readUTF();
						System.out.println(roomID+userID);
						psCommand.exitChattingRoom(roomID,userID);
						command = 0;
					}else if (command == DELETEFRIEND) {
						String userID = in.readUTF();
						String friendID = in.readUTF();
						psCommand.deleteFriend(userID,friendID);
						command = 0;
					}else if (command == GETPROFILE) {
						String userID = in.readUTF();
						psCommand.getProfilemsg(userID);
						command = 0;
					}else if (command == MODIFYPROFILE) {
						String userID = in.readUTF();
						String profilemsg = in.readUTF();
						psCommand.modifyProfilemsg(userID, profilemsg);
						command = 0;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				clients.remove(ID);
				System.out.println(ID+"님 께서 접속을 종료하셨습니다.");
			}
		}

	}
}
