package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ProfileWindow extends JFrame {
	private static final int SCREEN_WIDTH = 300;
	private static final int SCREEN_HEIGHT = 500;
	private Image screenImage;
	private Graphics screenGraphic;

	private Image backgroundimage = new ImageIcon(getClass().getClassLoader().getResource("img/ProfileWindow.png"))
			.getImage();

	private ImageIcon basicexitbutton = new ImageIcon(
			getClass().getClassLoader().getResource("img/BasicLoginExitButton.png"));
	private ImageIcon enteredexitbutton = new ImageIcon(
			getClass().getClassLoader().getResource("img/EnteredLoginExitButton.png"));
	private ImageIcon profileimage = new ImageIcon(getClass().getClassLoader().getResource("img/ProfileImage2.png"));
	private ImageIcon basicBallonButton_profile = new ImageIcon(getClass().getClassLoader().getResource("img/BasicBalloonButton(profile).png"));
	private ImageIcon enteredBallonButton_profile = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredBalloonButton(profile).png"));
	private ImageIcon clickedBallonButton_profile = new ImageIcon(getClass().getClassLoader().getResource("img/ClickedBalloonButton(profile).png"));
	private ImageIcon basicDeleteFriendButton_profile = new ImageIcon(getClass().getClassLoader().getResource("img/BasicDeleteFriendButton(profile).png"));
	private ImageIcon enteredDeleteFriendButton_profile = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredDeleteFriendButton(profile).png"));
	private ImageIcon clickedDeleteFriendButton_profile = new ImageIcon(getClass().getClassLoader().getResource("img/ClickedDeleteFriendButton(profile).png"));
	private ImageIcon basicCallButton_profile = new ImageIcon(getClass().getClassLoader().getResource("img/BasicCallButton(profile).png"));
	private ImageIcon enteredCallButton_profile = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredCallButton(profile).png"));
	private ImageIcon clickedCallButton_profile = new ImageIcon(getClass().getClassLoader().getResource("img/ClickedCallButton(profile).png"));
	private ImageIcon basicModifyProfileMsgButton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicModifyProfileMsgButton.png"));
	private ImageIcon enteredModifyProfileMsgButton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredModifyProfileMsgButton.png"));
	
	private JLabel topBar = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/TopBar.png")));
	private JButton exitButton = new JButton(basicexitbutton);
	private JButton ballonButtonProfile = new JButton(basicBallonButton_profile);
	private JButton deleteFriendButtonProfile = new JButton(basicDeleteFriendButton_profile);
	private JButton callButtonProfile = new JButton(basicCallButton_profile);
	private JButton modifyProfileMsgButton = new JButton(basicModifyProfileMsgButton);

	private int mouseX, mouseY;
	private JLabel userIDLabel = new JLabel();
	private JLabel profileLabel = new JLabel();
	public ProfileWindow(Point pt,String userID,String profilemsg) {
		setUndecorated(true);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		pt.setLocation(pt.getX() - SCREEN_WIDTH - 20, pt.getY()+100);
		setResizable(false);
		setLocationRelativeTo(null);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		setFocusable(true);
		setLocation(pt);
		setVisible(true);
		
		userIDLabel.setText(userID);
		userIDLabel.setBounds(125,380,100,20);
		userIDLabel.setFont(new Font("Dotum",Font.BOLD, 15));
		add(userIDLabel);
		
		profileLabel.setText(profilemsg);
		profileLabel.setBounds(100,200,150,20);
		profileLabel.setFont(new Font("Dotum",Font.BOLD, 12));
		add(profileLabel);
		if(Main.userID.equals(userID)) {
			modifyProfileMsgButton.setBounds(245, 5, 24, 25);
			modifyProfileMsgButton.setBorderPainted(false);
			modifyProfileMsgButton.setContentAreaFilled(false);
			modifyProfileMsgButton.setFocusPainted(false);
			modifyProfileMsgButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					modifyProfileMsgButton.setIcon(enteredModifyProfileMsgButton);
					modifyProfileMsgButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					modifyProfileMsgButton.setIcon(basicModifyProfileMsgButton);
					modifyProfileMsgButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					String result= JOptionPane.showInputDialog( null, profilemsg,"변경하실 프로필 내용을 입력해주세요.",JOptionPane.PLAIN_MESSAGE);
					Main.profilemsg = result;
					Main.sendCommand = Main.MODIFYPROFILE;
				}
			});
			add(modifyProfileMsgButton);
		}
		
		
		exitButton.setBounds(275, 10, 15, 15);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(enteredexitbutton);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(basicexitbutton);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				close(userID);
			}
		});
		add(exitButton);

		topBar.setBounds(0, 0, 300, 80);
		topBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		topBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(topBar);
		JLabel profile = new JLabel(profileimage);
		profile.setSize(100,100);
		profile.setBounds(100,260,100,100);
		profile.setVisible(true);
		profile.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				profile.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				profile.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {

			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});	
		add(profile);
		
		ballonButtonProfile.setBounds(25,420, 50, 50);
		ballonButtonProfile.setBorderPainted(false);
		ballonButtonProfile.setContentAreaFilled(false);
		ballonButtonProfile.setFocusPainted(false);
		ballonButtonProfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ballonButtonProfile.setIcon(enteredBallonButton_profile);
				ballonButtonProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				ballonButtonProfile.setIcon(basicBallonButton_profile);
				ballonButtonProfile.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ballonButtonProfile.setIcon(clickedBallonButton_profile);
				if(MyKaKaoTalk.chattingRooms.get(userID)== null) {
					Main.sendway = Main.SINGLE;
					Main.sendCommand = Main.GETCHATLIST;
					Main.friendID =userID;
				}
			}
		});
		add(ballonButtonProfile);
		
		deleteFriendButtonProfile.setBounds(125,420, 50, 50);
		deleteFriendButtonProfile.setBorderPainted(false);
		deleteFriendButtonProfile.setContentAreaFilled(false);
		deleteFriendButtonProfile.setFocusPainted(false);
		deleteFriendButtonProfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				deleteFriendButtonProfile.setIcon(enteredDeleteFriendButton_profile);
				deleteFriendButtonProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				deleteFriendButtonProfile.setIcon(basicDeleteFriendButton_profile);
				deleteFriendButtonProfile.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				deleteFriendButtonProfile.setIcon(clickedDeleteFriendButton_profile);
				int result = JOptionPane.showConfirmDialog(null,"친구 목록에서 제거하시겠습니까?","친구목록 제거",JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(result == 0) {
					Main.friendID = userID;
					Main.sendCommand = Main.DELETEFRIEND;
				}
			}
		});
		add(deleteFriendButtonProfile);
		callButtonProfile.setBounds(225,420, 50, 50);
		callButtonProfile.setBorderPainted(false);
		callButtonProfile.setContentAreaFilled(false);
		callButtonProfile.setFocusPainted(false);
		callButtonProfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				callButtonProfile.setIcon(enteredCallButton_profile);
				callButtonProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				callButtonProfile.setIcon(basicCallButton_profile);
				callButtonProfile.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				callButtonProfile.setIcon(clickedCallButton_profile);
			}
		});
		add(callButtonProfile);

		JLabel oneChat = new JLabel("1:1 채팅");
		oneChat.setBounds(25,460,80,40);
		oneChat.setForeground(Color.GRAY);
		oneChat.setFont(new Font("Dotum",Font.BOLD, 13));
		add(oneChat);
		JLabel deletefriend = new JLabel("친구 삭제");
		deletefriend.setBounds(125,460,80,40);
		deletefriend.setForeground(Color.GRAY);
		deletefriend.setFont(new Font("Dotum",Font.BOLD, 13));
		add(deletefriend);
		JLabel call = new JLabel("전화 걸기");
		call.setBounds(225,460,80,40);
		call.setForeground(Color.GRAY);
		call.setFont(new Font("Dotum",Font.BOLD, 13));
		add(call);
	}

	public void paint(Graphics g) {
		screenImage = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(backgroundimage, 0, 0, null);
		paintComponents(g);
		this.repaint();
	}
	public void close(String userID) {
		System.out.println("close");
		MyKaKaoTalk.profilewindows.remove(userID);
		this.setVisible(false);
		this.removeAll();
	}
}
