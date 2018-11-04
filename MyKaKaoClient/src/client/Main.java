package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import user.User;

public class Main {
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
	
	public static final int SCREEN_WIDTH = 350;
	public static final int SCREEN_HEIGHT = 600;
	public static String userID = null;
	public static String userPassword = null;
	public static String friendID = null;
	public static String message = null;
	public static int sendCommand = 0;
	public static int receiverCommand = 0;
	public static int succeedLogin = 0;
	public static int succeedJogin = -1;
	public static int succeedrequestfriend = -1;
	public static int succeedacceptfriend = -1;
	public static int sendway = 0;
	public static int roomID = -1;
	public static String sendMessage = null;
	public static String recvMessage = null;
	public static String profilemsg = null;
	
	public static User joinuser;
	public static ArrayList<String> friendlist = new ArrayList<String>();
	public static ArrayList<String> chatList = new ArrayList<String>();
	public static ArrayList<String> requestaddfriend = new ArrayList<String>();
	public static ArrayList<String> requestedaddfriend = new ArrayList<String>();
	public static ArrayList<String> chattingroomNamelist = new ArrayList<String>();
	public static ArrayList<Integer> chattingroomIDlist = new ArrayList<Integer>();
	public static ArrayList<String> lastchatting = new ArrayList<String>();
	
	public static HashMap<String, ChattingRoom> chattingRooms = new HashMap<String, ChattingRoom>();
	public static HashMap<String, String> profiles = new HashMap<String, String>();
	
	private static MyKaKaoTalk mykakaotalk ;
	public static ChatSetting chatsetting  = null;

	
	public static void main(String[] args) {
		try {
			String serverIp = "127.0.0.1";		
			Socket socket = new Socket(serverIp,7777);
			
			mykakaotalk= new MyKaKaoTalk();
			
			Thread sender = new Thread(new ClientSender(socket));
			Thread receiver = new Thread(new ClientReceiver(socket));
			
			sender.start();
			receiver.start();
		}catch(Exception e) {
			
		}
	}

	static class ClientSender extends Thread{
		Socket socket;
		DataOutputStream out;
		
		ClientSender(Socket socket){
			this.socket= socket;
			try {
				out = new DataOutputStream(socket.getOutputStream());
			}catch(Exception e) {
				
			}
		}
		
		public void run() {
			try {
				while(out!= null) {
					System.out.print("");
					if(sendCommand != 0 ) {
						out.writeInt(sendCommand);
						if(sendCommand == LOGIN) {
							out.writeUTF(userID);
							out.writeUTF(userPassword);
							sendCommand =0;
						}else if(sendCommand == JOIN) {
							out.writeUTF(joinuser.getUserID());
							out.writeUTF(joinuser.getUserPassword());
							out.writeUTF(joinuser.getUserName());
							out.writeUTF(joinuser.getUserEmail());
							out.writeUTF(joinuser.getUserTel());
							sendCommand =0;
						}else if(sendCommand == GETCHATLIST) {
							out.writeInt(sendway);
							if(sendway == SINGLE) {
								out.writeUTF(userID);
								out.writeUTF(friendID);
							}else if(sendway == MULTI) {
								out.writeInt(roomID);
								roomID = -1;
							}
							sendway= 0;
							sendCommand =0;
						}else if(sendCommand == SENDMESSAGE) {
							out.writeInt(1); // 한명 한테 보내기
							out.writeUTF(friendID);
							out.writeUTF(userID);
							out.writeUTF(sendMessage);
							friendID = null;
							sendMessage=null;
							sendCommand =0;
						}else if(sendCommand == SENDCHAT) {
							out.writeUTF(userID);
							out.writeInt(roomID);
							out.writeUTF(sendMessage);
							
							roomID = -1;
							sendMessage=null;
							sendCommand =0;
						}else if(sendCommand == REQUESTFRIEND) {
							out.writeUTF(userID);
							out.writeUTF(friendID);
							friendID = null;
							sendCommand =0;
						}else if(sendCommand == ACCEPTFRIEND) {
							out.writeUTF(userID);
							out.writeUTF(friendID);
							friendID = null;
							sendCommand =0;
						}else if(sendCommand == DECLINEFRIEND) {
							out.writeUTF(userID);
							out.writeUTF(friendID);
							friendID = null;
							sendCommand =0;
						}else if(sendCommand == MAKECHATTINGROOM) {
							ArrayList<String> list = chatsetting.getMemberList();
							int max = chatsetting.getMemberSize();
							out.writeInt(max);
							out.writeUTF(chatsetting.getRoomName());
							for(int i=0;i<max;i++) {
								out.writeUTF(list.get(i));
							}
							sendCommand =0;
						}else if(sendCommand == EXITCHATTINGROOM) {
							out.writeInt(roomID);
							out.writeUTF(userID);
							System.out.println(roomID+userID);
							roomID = -1;
							sendCommand =0;
						}else if(sendCommand == DELETEFRIEND) {
							out.writeUTF(userID);
							out.writeUTF(friendID);
							friendID = null;
							sendCommand =0;
						}else if(sendCommand == GETPROFILE) {
							out.writeUTF(friendID);
							friendID = null;
							sendCommand =0;
						}else if(sendCommand == MODIFYPROFILE) {
							out.writeUTF(userID);
							out.writeUTF(profilemsg);
							profilemsg = null;
							sendCommand =0;
						}
					}
				}
			}catch(Exception e) {
				
			}
		}
	}
	
	static class ClientReceiver extends Thread{
		Socket socket;
		DataInputStream in;
		
		ClientReceiver(Socket socket){
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
			}catch(Exception e) {
				
			}
		}
		public void run() {
			while(in!= null) {
				try {
					receiverCommand = in.readInt();
					if(receiverCommand == LOGIN) {
						succeedLogin = in.readInt();
						if(succeedLogin == 1) {
							int max = in.readInt();
							for(int i=0;i<max;i++) {
								friendlist.add(in.readUTF());
							}
							max = in.readInt();
							for(int i=0;i<max;i++) {
								requestaddfriend.add(in.readUTF());
							}
							
							max = in.readInt();
							for(int i=0;i<max;i++) {
								requestedaddfriend.add(in.readUTF());
							}
							max = in.readInt();
							for(int i=0;i<max;i++) {
								chattingroomIDlist.add(in.readInt());
								chattingroomNamelist.add(in.readUTF());
								lastchatting.add(in.readUTF());
							}
						}
						mykakaotalk.commandLogin(succeedLogin);
					}else if(receiverCommand == JOIN) {
						succeedJogin = in.readInt();
						mykakaotalk.commandJoin(succeedJogin);
					}else if(receiverCommand == GETCHATLIST) {
						int max = in.readInt();
						for(int i=0;i<max;i++) {
							chatList.add(in.readUTF());
						}
						if(in.readInt() == MULTI) {
							int roomID = in.readInt();
							String chattingroomname = in.readUTF();
							mykakaotalk.openChattingRoom(roomID, chattingroomname);
						}else {
							String chattingroomname = in.readUTF();
							mykakaotalk.openChattingRoom(chattingroomname);
						}
					}else if(receiverCommand == SENDMESSAGE) {
						String friendID = in.readUTF();
						String msg = in.readUTF();
						mykakaotalk.commandSendMessage(friendID, msg);
					}else if(receiverCommand == SENDCHAT) {
						int roomID = in.readInt();
						String msg = in.readUTF();
						String lastchat = in.readUTF();
						lastchatting.set(chattingroomIDlist.indexOf(roomID), lastchat);
						mykakaotalk.commandSendChat(roomID, msg);
					}else if(receiverCommand == REQUESTFRIEND) {
							if(in.readInt() == -1) {
								 JOptionPane.showMessageDialog(null, "존재하지 않는 ID이거나 이미 친구입니다.");
							}
							else {
								String friendID = in.readUTF();
								if(in.readInt() == 0) {
									requestaddfriend.add(friendID);
									mykakaotalk.makeRequestFriendList(requestaddfriend.size()-1);
								}else {
									requestedaddfriend.add(friendID);
									mykakaotalk.makeRequestedFriendList(requestedaddfriend.size()-1,requestaddfriend.size()-1);
								}
							}
					}else if(receiverCommand == ACCEPTFRIEND) {
							String friendID = in.readUTF();
							if(in.readInt() == 0) {
								requestedaddfriend.remove(friendID);
							}else {
								requestaddfriend.remove(friendID);
							}
							friendlist.add(friendID);
							mykakaotalk.remakeAddFriendList();
							mykakaotalk.remakeFriendList();
					}else if(receiverCommand == DECLINEFRIEND) {
						String friendID = in.readUTF();
						if(in.readInt() == 0) {
							requestedaddfriend.remove(friendID);
						}else {
							requestaddfriend.remove(friendID);
						}
						mykakaotalk.remakeAddFriendList();
					}else if(receiverCommand == MAKECHATTINGROOM) {
						chattingroomIDlist.add( in.readInt());
						chattingroomNamelist.add(in.readUTF());
						lastchatting.add(in.readUTF());
						if(chatsetting!= null) {
							chatsetting.close();
						}
						mykakaotalk.remakeChatList();
						}else if(receiverCommand == EXITCHATTINGROOM) {
							if(in.readInt() == 1) {
								int index = in.readInt();
								index = chattingroomIDlist.lastIndexOf(index);
								String str = chattingroomNamelist.get(index).substring(chattingroomNamelist.get(index).length()-2).substring(0, 1);
								int i = Integer.parseInt(str)-1;
								str = chattingroomNamelist.get(index).substring(0, chattingroomNamelist.get(index).length()-3);
								chattingroomNamelist.set(index,  str+"("+i+")");
								mykakaotalk.remakeChatList();
							}else {
								int index = in.readInt();
								System.out.println(index);
								index = chattingroomIDlist.lastIndexOf(index);
								chattingroomIDlist.remove(index);	
								chattingroomNamelist.remove(index);
								lastchatting.remove(index);
								mykakaotalk.remakeChatList();
							}
						}else if(receiverCommand == DELETEFRIEND) {
							if(in.readInt()!= -1) {
								String friendID = in.readUTF();
								friendlist.remove(friendID);
								mykakaotalk.closeProfileWindow(friendID);
								mykakaotalk.remakeFriendList();
							}
						}else if(receiverCommand == GETPROFILE) {
							String friendID = in.readUTF();
							String profilemsg = in.readUTF();
							mykakaotalk.openProfileWindow(friendID, profilemsg);
						}else if(receiverCommand == MODIFYPROFILE) {
							int result = in.readInt();
							System.out.println(result);
							if(result != -1) {
								mykakaotalk.reopenProfileWindow(userID);
							}
						}
					receiverCommand =0;
				}catch(Exception e) {
					
				}
			}
		}
	}
}


