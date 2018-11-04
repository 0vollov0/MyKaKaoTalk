package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import user.User;

public class MyKaKaoTalk  extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3274177008712479759L;
	private Image screenImage;
	private Graphics screenGraphic;
	
	private Image backgroundimage = new ImageIcon(getClass().getClassLoader().getResource("img/LoginBackGround.png")).getImage();
	
	private ImageIcon basicloginbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicLoginButton.png"));
	private ImageIcon enteredloginbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredLoginButton.png"));
	private ImageIcon enteredjoinbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredJoinButton.png"));
	private ImageIcon basicjoinbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicJoinButton.png"));
	private ImageIcon basicexitbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicLoginExitButton.png"));
	private ImageIcon enteredexitbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredLoginExitButton.png"));
	private ImageIcon basicmanbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicManButton.png"));
	private ImageIcon enteredmanbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredManButton.png"));
	private ImageIcon basicballonbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicBalloonButton.png"));
	private ImageIcon enteredballonbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredBalloonButton.png"));
	private ImageIcon basicaddfriendbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicAddFriendButton.png"));
	private ImageIcon enteredaddfriendbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredAddFriendButton.png"));
	private ImageIcon basicsearchfriendbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicSearchFriendButton.png"));
	private ImageIcon enteredsearchfriendbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredSearchFriendButton.png"));
	private ImageIcon basicacceptbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicAcceptButton.png"));
	private ImageIcon enteredacceptbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredAcceptButton.png"));
	private ImageIcon basicdeclinebutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicDeclineButton.png"));
	private ImageIcon entereddeclinebutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredDeclineButton.png"));
	private ImageIcon profilebarimg = new ImageIcon(getClass().getClassLoader().getResource("img/ProfileBar.png"));
	private ImageIcon basicfriendlistbar = new ImageIcon(getClass().getClassLoader().getResource("img/BasicFriendListBar.png"));
	private ImageIcon enteredfriendlistbar = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredFriendListBar.png"));
	private ImageIcon basicsetchatbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicSetChatButton.png"));
	private ImageIcon enteredsetchatbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredSetChatButton.png"));
	private ImageIcon basicmyprofilebutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicMyProfileButton.png"));
	private ImageIcon enteredmyprofilebutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredMyProfileButton.png"));
	
	private ImageIcon profileimage = new ImageIcon(getClass().getClassLoader().getResource("img/ProfileImage.png"));
	
	private JLabel topBar = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/TopBar.png")));
	private JButton loginButton = new JButton(basicloginbutton);
	private JButton joinButton = new JButton(basicjoinbutton);
	private JButton exitButton = new JButton(basicexitbutton);
	private JButton manButton = new JButton(basicmanbutton);
	private JButton ballonButton = new JButton(basicballonbutton);
	private JButton addfriendButton = new JButton(basicaddfriendbutton);
	private JButton searchfriendButton = new JButton(basicsearchfriendbutton);
	private JButton setchatButton = new JButton(basicsetchatbutton);
	private JButton myprofileButton = new JButton(basicmyprofilebutton);
//	private JButton acceptButton;
//	private JButton declineButton;
	private JTextField inputuserID = new JTextField();
	private JPasswordField inputuserPassword = new JPasswordField();
	private JTextField inputuserName = new JTextField();
	private JTextField inputuserEmail = new JTextField();
	private JTextField inputuserTel = new JTextField();
	
	
	private String warninglogin = "카카오계정 또는 비밀번호를 다시 확인해 주세요.";
	private String warningjoin = "이미 존재하는 아이디이거나 옳바르지 않은 값입니다.";
	private JLabel loginwarningmessage = new JLabel("<html>" + warninglogin.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
	private JLabel joinwarningmessage = new JLabel("<html>" + warningjoin.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
	private JLabel loginLabel = new JLabel("로그인");
	private JLabel joinLabel = new JLabel("회원가입");
	private JLabel profilename = new JLabel();
	private JLabel addfriendID = new JLabel("친구 추가할 ID");
	private JTextField inputfriendID = new JTextField();
	private JLabel profilebar = new JLabel(profilebarimg);
	
	private JPanel friendListPanel = new JPanel();
	private JScrollBar friendListScrollbar;
	
	private JPanel chatListPanel = new JPanel();
	private JScrollBar chatListScrollbar;
	
	private JPanel addfriendListPanel = new JPanel();
	private JScrollBar addfriendListScrollbar;
	
	private int mouseX, mouseY;
	private int friendlistscrollbarflag = 0;
	private int addfriendscrollbarflag = 0;
	private int chatlistscrollbarflag = 0;
	private ArrayList<JLabel> friendnames = new ArrayList<JLabel>();
	private ArrayList<JLabel> requestfriendnames = new ArrayList<JLabel>();
	private ArrayList<JLabel> requestedfriendnames = new ArrayList<JLabel>();
	private ArrayList<JLabel> addfriendnames= new ArrayList<JLabel>();
	private ArrayList<JLabel> chattingroomnames = new ArrayList<JLabel>();
	
	private boolean loginSetting;
	private boolean joinSetting;
	private boolean initFriendListflag;
	private boolean initAddFriendflag;
	private boolean initChatListflag;
	
	public static HashMap<String, ChattingRoom> chattingRooms = new HashMap<String, ChattingRoom>();
	public static HashMap<Integer, ChattingRoom>multichatrooms = new HashMap<Integer, ChattingRoom>();
	public static HashMap<String, ProfileWindow>profilewindows = new HashMap<String, ProfileWindow>();

	public MyKaKaoTalk() {
		setUndecorated(true);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		loginSetting = joinSetting = initFriendListflag = initAddFriendflag= initChatListflag=false;
		setFocusable(true);
		
		loginwarningmessage.setForeground(Color.RED);
		loginwarningmessage.setBounds(55,400,240,40);
		loginwarningmessage.setVisible(false);
		loginwarningmessage.setFont(new Font("Dotum",Font.PLAIN, 15));
		add(loginwarningmessage);
		
		joinwarningmessage.setForeground(Color.RED);
		joinwarningmessage.setBounds(55,520,240,40);
		joinwarningmessage.setVisible(false);
		joinwarningmessage.setFont(new Font("Dotum",Font.PLAIN, 15));
		add(joinwarningmessage);
		
		manButton.setBounds(20, 25, 30, 50);
		manButton.setBorderPainted(false);
		manButton.setContentAreaFilled(false);
		manButton.setFocusPainted(false); 
		manButton.setVisible(false);
		manButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				manButton.setIcon(enteredmanbutton);
				manButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				manButton.setIcon(basicmanbutton);
				manButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				changeFriendList();
			}
		});
    	add(manButton);
    	
    	ballonButton.setBounds(80, 25, 30, 50);
    	ballonButton.setBorderPainted(false);
    	ballonButton.setContentAreaFilled(false);
    	ballonButton.setFocusPainted(false); 
    	ballonButton.setVisible(false);
		ballonButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ballonButton.setIcon(enteredballonbutton);
				ballonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				ballonButton.setIcon(basicballonbutton);
				ballonButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				changeChattingList();
			}
		});
		add(ballonButton);
		
		addfriendButton.setBounds(140, 25, 30, 50);
		addfriendButton.setBorderPainted(false);
		addfriendButton.setContentAreaFilled(false);
		addfriendButton.setFocusPainted(false); 
		addfriendButton.setVisible(false);
    	addfriendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				addfriendButton.setIcon(enteredaddfriendbutton);
				addfriendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				addfriendButton.setIcon(basicaddfriendbutton);
				addfriendButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				changeAddFriend();
			}
		});
		add(addfriendButton);

		profilebar.setBounds(0,100,350,40);
		profilebar.setVisible(false);
		add(profilebar);
		
    	exitButton.setBounds(325, 10, 15, 15);
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
				System.exit(0);
			}
		});
		add(exitButton);
		
		screenLogin();
		
		loginButton.setBounds(55, 350, 240, 40);
		loginButton.setBorderPainted(false);
		loginButton.setContentAreaFilled(false);
		loginButton.setFocusPainted(false); 
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				loginButton.setIcon(enteredloginbutton);
				loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginButton.setIcon(basicloginbutton);
				loginButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
				Main.userID = inputuserID.getText();
				Main.userPassword = inputuserPassword.getText();
				Main.sendCommand = Main.LOGIN;
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
    	add(loginButton);
    
    	topBar.setBounds(0, 0, 350, 100);
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
		
		loginLabel.setBounds(120,560, 50, 20);
		loginLabel.setForeground(new Color(0x7f7519));
		loginLabel.setFont(new Font("Dotum",Font.PLAIN, 14));
		loginLabel.setVisible(true);
		loginLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				loginLabel.setForeground(Color.black);
				loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginLabel.setForeground(new Color(0x7f7519));
				loginLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				screenLogin();
			}
		});
		add(loginLabel);
		
		joinLabel.setBounds(180, 560, 80, 20);
		joinLabel.setForeground(new Color(0x7f7519));
		joinLabel.setFont(new Font("Dotum",Font.PLAIN, 14));
		joinLabel.setVisible(true);
		joinLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				joinLabel.setForeground(Color.black);
				joinLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				joinLabel.setForeground(new Color(0x7f7519));
				joinLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				screenJoin();
			}
		});
		add(joinLabel);
	}
	
	public void paint(Graphics g) {
		screenImage =  createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(backgroundimage, 0, 0, null);
		paintComponents(g);
		this.repaint();
	}
	
	public void screenLogin() {
		if(loginSetting != true) {
			inputuserID.setBounds(55,265,240,40);
			inputuserID.setEnabled(false);
			inputuserID.setText("카카오계정 (이메일 또는 전화번호)");
			inputuserID.setDocument(new JTextFieldLimit(24));
			inputuserID.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(inputuserID.getText().equals("카카오계정 (이메일 또는 전화번호)")) {
						inputuserID.setEnabled(true);
						inputuserID.setText("");
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(inputuserID.getText().equals("")) {
						inputuserID.setEnabled(false);
						inputuserID.setText("카카오계정 (이메일 또는 전화번호)");
					}
				}
				
			});
			add(inputuserID);
			
			inputuserPassword.setBounds(55,305,240,40);
			inputuserPassword.setEnabled(false);
			inputuserPassword.setText("비밀번호 (4~32자)");
			inputuserPassword.setDocument(new JTextFieldLimit(24));
			inputuserPassword.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(inputuserPassword.getText().equals("비밀번호 (4~32자)")) {
						inputuserPassword.setEnabled(true);
						inputuserPassword.setText("");
					}
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(inputuserPassword.getText().equals("")) {
						inputuserPassword.setEnabled(false);
						inputuserPassword.setText("비밀번호 (4~32자)");
					}
				}
			});
			add(inputuserPassword);
			loginSetting=true;
		}
		inputuserName.setVisible(false);
		inputuserEmail.setVisible(false);
		inputuserTel.setVisible(false);
		joinButton.setVisible(false);
		loginButton.setVisible(true);
		joinwarningmessage.setVisible(false);
	}
	
	public void screenJoin() {
		if(joinSetting != true) {
			inputuserName.setBounds(55,345,240,40);
			inputuserName.setEnabled(false);
			inputuserName.setText("이름");
			inputuserName.setDocument(new JTextFieldLimit(16));
			inputuserName.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(inputuserName.getText().equals("이름")) {
						inputuserName.setEnabled(true);
						inputuserName.setText("");
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(inputuserName.getText().equals("")) {
						inputuserName.setEnabled(false);
						inputuserName.setText("이름");
					}
				}
			});
			add(inputuserName);
			
			inputuserEmail.setBounds(55,385,240,40);
			inputuserEmail.setEnabled(false);
			inputuserEmail.setText("E-mail");
			inputuserEmail.setDocument(new JTextFieldLimit(32));
			inputuserEmail.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(inputuserEmail.getText().equals("E-mail")) {
						inputuserEmail.setEnabled(true);
						inputuserEmail.setText("");
					}
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(inputuserEmail.getText().equals("")) {
						inputuserEmail.setEnabled(false);
						inputuserEmail.setText("E-mail");
					}
				}
			});
			add(inputuserEmail);
			
			inputuserTel.setBounds(55,425,240,40);
			inputuserTel.setEnabled(false);
			inputuserTel.setText("전화번호");
			inputuserTel.setDocument(new JTextFieldLimit(16));
			inputuserTel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(inputuserTel.getText().equals("전화번호")) {
						inputuserTel.setEnabled(true);
						inputuserTel.setText("");
					}
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(inputuserTel.getText().equals("")) {
						inputuserTel.setEnabled(false);
						inputuserTel.setText("전화번호");
					}
				}
			});
			add(inputuserTel);
			inputuserName.setVisible(true);
			inputuserEmail.setVisible(true);
			inputuserTel.setVisible(true);
			
			loginButton.setVisible(false);
			
			joinButton.setBounds(55, 470, 240, 40);
			joinButton.setBorderPainted(false);
			joinButton.setContentAreaFilled(false);
			joinButton.setFocusPainted(false); 
			joinButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					joinButton.setIcon(enteredjoinbutton);
					joinButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					joinButton.setIcon(basicjoinbutton);
					joinButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(inputuserID.getText().equals("카카오계정 (이메일 또는 전화번호)") || inputuserPassword.getText().equals("비밀번호 (4~32자)")) {
						joinwarningmessage.setVisible(true);
						return;
					}
					Main.joinuser = new User(inputuserID.getText(),inputuserPassword.getText(),inputuserName.getText(),inputuserEmail.getText(),inputuserTel.getText());
					Main.sendCommand = Main.JOIN;
				}
				@Override
				public void mouseClicked(MouseEvent e) {
//					if(Main.succeedJogin!= -1) {
//						screenLogin();
//					}else {
//						joinwarningmessage.setVisible(true);
//					}
				}
			});
	    	add(joinButton);
	    	joinSetting = true;
		}
		loginButton.setVisible(false);
		inputuserName.setVisible(true);
		inputuserEmail.setVisible(true);
		inputuserTel.setVisible(true);
    	joinButton.setVisible(true);
    	loginwarningmessage.setVisible(false);
	}
	
	public void enterMain() {
		loginwarningmessage.setVisible(false);
		inputuserID.setVisible(false);
		inputuserPassword.setVisible(false);
		inputuserName.setVisible(false);
		inputuserEmail.setVisible(false);
		inputuserTel.setVisible(false);
		loginButton.setVisible(false);
		loginLabel.setVisible(false);
		joinLabel.setVisible(false);
		manButton.setVisible(true);
		ballonButton.setVisible(true);
		addfriendButton.setVisible(true);
		profilebar.setVisible(true);
//		profilename.setVisible(true);
		
		
		backgroundimage =  new ImageIcon(getClass().getClassLoader().getResource("img/MainBackGround.png")).getImage();
		basicexitbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicMainExitButton.png"));
		enteredexitbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredMainExitButton.png"));
		
		makeFriendList();
		changeFriendList();
	}
	
	public void changeFriendList() {
		if(initFriendListflag != true) {
			profilename.setText(Main.userID+"님 환영합니다.");
			profilename.setBounds(10,100,350,40);
			profilename.setForeground(Color.GRAY);
			profilename.setFont(new Font("Dotum",Font.PLAIN, 14));
			profilename.setVisible(false);
			add(profilename);
			
			myprofileButton.setBounds(190, 100, 160, 40);
			myprofileButton.setBorderPainted(false);
			myprofileButton.setContentAreaFilled(false);
			myprofileButton.setFocusPainted(false); 
			myprofileButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					myprofileButton.setIcon(enteredmyprofilebutton);
					myprofileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					myprofileButton.setIcon(basicmyprofilebutton);
					myprofileButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(profilewindows.get(Main.userID) == null) {
						Main.friendID = Main.userID;
						Main.sendCommand = Main.GETPROFILE;
					}
				}
	
			});
			add(myprofileButton);
			initFriendListflag = true;
		}
		
		addfriendListPanel.setVisible(false);
		if(addfriendListScrollbar!=null) {
			addfriendListScrollbar.setVisible(false);
		}
		chatListPanel.setVisible(false);
		if(chatListScrollbar!=null) {
			chatListScrollbar.setVisible(false);
		}
	
		addfriendID.setVisible(false);
		inputfriendID.setVisible(false);
		searchfriendButton.setVisible(false);
		setchatButton.setVisible(false);
		
		profilename.setVisible(true);
		myprofileButton.setVisible(true);
		friendListPanel.setVisible(true);
		friendListScrollbar.setVisible(true);
	}
	
	public void changeChattingList() {
		if(initChatListflag != true) {
			setchatButton.setBounds(190, 100, 160, 40);
			setchatButton.setBorderPainted(false);
			setchatButton.setContentAreaFilled(false);
			setchatButton.setFocusPainted(false); 
			setchatButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					setchatButton.setIcon(enteredsetchatbutton);
					setchatButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setchatButton.setIcon(basicsetchatbutton);
					setchatButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(Main.chatsetting == null) {
						Main.chatsetting = new ChatSetting(getLoacation());
					}
				}
	
			});
			add(setchatButton);
			makeChatList();
			initChatListflag=true;
		}
		friendListPanel.setVisible(false);
		friendListScrollbar.setVisible(false);
		addfriendListPanel.setVisible(false);
		if(addfriendListScrollbar != null) {
			addfriendListScrollbar.setVisible(false);
		}
		
		
		addfriendID.setVisible(false);
		inputfriendID.setVisible(false);
		profilename.setVisible(false);
		myprofileButton.setVisible(false);
		searchfriendButton.setVisible(false);

		setchatButton.setVisible(true);
		chatListPanel.setVisible(true);
		chatListScrollbar.setVisible(true);
	}
	
	public void changeAddFriend() {
		if(initAddFriendflag != true) {
			addfriendID.setBounds(10,100,100,40);
			addfriendID.setForeground(Color.GRAY);
			addfriendID.setFont(new Font("Dotum",Font.PLAIN, 14));
			addfriendID.setVisible(true);
			add(addfriendID);
			
			inputfriendID.setBounds(120,100,150,40);
			inputfriendID.setEnabled(false);
			inputfriendID.setText("추가할 친구ID");
			inputfriendID.setVisible(true);
			inputfriendID.setDocument(new JTextFieldLimit(24));
			inputfriendID.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(inputfriendID.getText().equals("추가할 친구ID")) {
						inputfriendID.setEnabled(true);
						inputfriendID.setText("");
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(inputfriendID.getText().equals("")) {
						inputfriendID.setEnabled(false);
						inputfriendID.setText("추가할 친구ID");
					}
				}
			});
			add(inputfriendID);
			searchfriendButton.setBounds(280, 100, 60, 40);
			searchfriendButton.setBorderPainted(false);
			searchfriendButton.setContentAreaFilled(false);
			searchfriendButton.setFocusPainted(false); 
			searchfriendButton.setVisible(false);
			searchfriendButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					searchfriendButton.setIcon(enteredsearchfriendbutton);
					searchfriendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					searchfriendButton.setIcon(basicsearchfriendbutton);
					searchfriendButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(Main.userID.equals(inputfriendID.getText())) {
						 JOptionPane.showMessageDialog(null, "자기자신은 친구가 될 수 없습니다.");
						return;
					}
					if(inputfriendID.getText().equals("추가할 친구ID")){
						return;
					}
					Main.friendID = inputfriendID.getText();
					Main.sendCommand = Main.REQUESTFRIEND;
				}
				@Override
				public void mouseClicked(MouseEvent e) {
//					remakeAddFriendList();
				}
			});
			add(searchfriendButton);
			makeAddFriendList();
			initAddFriendflag= true;
		}
		friendListPanel.setVisible(false);
		friendListScrollbar.setVisible(false);
		chatListPanel.setVisible(false);
		if(chatListScrollbar!= null) {
			chatListScrollbar.setVisible(false);
		}
		
		profilename.setVisible(false);
		myprofileButton.setVisible(false);
		setchatButton.setVisible(false);
		
		addfriendID.setVisible(true);
		inputfriendID.setVisible(true);
		searchfriendButton.setVisible(true);
		addfriendListPanel.setVisible(true);
		addfriendListScrollbar.setVisible(true);
	}
	
	public void makeFriendList() {
		friendListPanel.setSize(330,500);
		friendListPanel.setBounds(0,140,330,500);
		friendListPanel.setBackground(Color.white);
		
		for(int i=0;i<Main.friendlist.size();i++) {
			makeFriendNameList(i);
		}
		int maxScroll;
		if(friendnames.size() > 4) {
			maxScroll = friendnames.size()-3;
		}else { 
			maxScroll = 1;
		}
		friendListScrollbar = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, maxScroll); 
		friendListScrollbar.setSize(20, 460); 
		friendListScrollbar.setLocation(330,140);
		friendListScrollbar.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				if(friendlistscrollbarflag <friendListScrollbar.getValue()) {
					friendListPanel.remove(friendnames.get(friendlistscrollbarflag));
					friendlistscrollbarflag=friendListScrollbar.getValue();
				}
				else if(friendlistscrollbarflag != 0) {
					friendlistscrollbarflag=friendListScrollbar.getValue();
					friendListPanel.add(friendnames.get(friendlistscrollbarflag), 0);
				}
				}
		});
		add(friendListScrollbar);
		add(friendListPanel);
	}
	public void makeChatList() {
		chatListPanel.setSize(330,500);
		chatListPanel.setBounds(0,140,330,500);
		chatListPanel.setBackground(Color.white);
		for(int i=0;i<Main.chattingroomNamelist.size();i++) {
			makeChattingRoomList(i);
		}
		int maxScroll;
		if(chattingroomnames.size() > 4) {
			maxScroll = Main.chattingroomIDlist.size()-3;
		}else {
			maxScroll = 1;
		}
		chatListScrollbar = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, maxScroll); 
		chatListScrollbar.setSize(20, 460); 
		chatListScrollbar.setLocation(330,140);
		chatListScrollbar.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				if(chatlistscrollbarflag <chatListScrollbar.getValue()) {
					chatListPanel.remove(chattingroomnames.get(chatlistscrollbarflag));
					chatlistscrollbarflag=chatListScrollbar.getValue();
				}
				else if(chatlistscrollbarflag != 0) {
					chatlistscrollbarflag=chatListScrollbar.getValue();
					chatListPanel.add(chattingroomnames.get(chatlistscrollbarflag), 0);
				}
				}
		});
		add(chatListScrollbar);
		add(chatListPanel);
	}
	public void makeAddFriendList() {
		addfriendListPanel.setSize(330,500);
		addfriendListPanel.setBounds(0,140,330,500);
		addfriendListPanel.setBackground(Color.white);
		int i;
		for( i=0;i<Main.requestaddfriend.size();i++) {
			makeRequestFriendList(i);
		}
		for(int j = 0;j<Main.requestedaddfriend.size();j++) {
			makeRequestedFriendList(j,Main.requestaddfriend.size()-1);
		}
		addfriendnames.addAll(requestfriendnames);
		addfriendnames.addAll(requestedfriendnames);
		
		int maxScroll;
		if(addfriendnames.size() >= 4) {
			maxScroll = addfriendnames.size()-3;
		}else {
			maxScroll = 1;
		}
		addfriendListScrollbar = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, maxScroll); 
		addfriendListScrollbar.setSize(20, 460); 
		addfriendListScrollbar.setLocation(330,140);
		addfriendListScrollbar.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				if(addfriendscrollbarflag <addfriendListScrollbar.getValue()) {
					addfriendListPanel.remove(addfriendnames.get(addfriendscrollbarflag));
					addfriendscrollbarflag=addfriendListScrollbar.getValue();
				}
				else if(addfriendscrollbarflag != 0) {
					addfriendscrollbarflag=addfriendListScrollbar.getValue();
					addfriendListPanel.add(addfriendnames.get(addfriendscrollbarflag), 0);
				}
				}
		});
		add(addfriendListScrollbar);
		add(addfriendListPanel);
	}
	public void remakeFriendList() {
		this.remove(friendListScrollbar);
		this.remove(friendListPanel);
		friendListPanel.removeAll();
		friendnames.clear();
		friendlistscrollbarflag = 0;
		friendListScrollbar.removeAll();
		makeFriendList();
	}
	public void makeFriendNameList(int i) {
		JLabel friendname = new JLabel(Main.friendlist.get(i));
		friendname.setSize(330,100);
		friendname.setIcon(basicfriendlistbar);
		friendname.setBounds(0,i*100+100,330,100);
		friendname.setHorizontalTextPosition(JLabel.CENTER);
		friendname.setVerticalTextPosition(JLabel.CENTER);
		JLabel profile = new JLabel(profileimage);
		profile.setSize(50,50);
		profile.setBounds(25,25,50,50);
		String userID = Main.friendlist.get(i);
		profile.addMouseListener(new MouseAdapter() {
			
			public void mouseEntered(MouseEvent e) {
				friendname.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				friendname.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(profilewindows.get(userID) == null) {
					Main.friendID = userID;
					Main.sendCommand = Main.GETPROFILE;
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});	
		friendname.add(profile);
		friendname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				friendname.setIcon(enteredfriendlistbar);
				friendname.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				friendname.setIcon(basicfriendlistbar);
				friendname.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(chattingRooms.get( friendname.getText())== null) {
					Main.sendway = Main.SINGLE;
					Main.sendCommand = Main.GETCHATLIST;
					Main.friendID = friendname.getText();
				}
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});	
		
		friendnames.add(friendname);
		friendListPanel.add(friendnames.get(i));		
	}
	public void remakeChatList() {
		this.remove(chatListScrollbar);
		this.remove(chatListPanel);
		chatListPanel.removeAll();
		chattingroomnames.clear();
		chatlistscrollbarflag = 0;
		chatListScrollbar.removeAll();
		makeChatList();
	}
	public void makeChattingRoomList(int i) {
		String str = Main.chattingroomNamelist.get(i) + "\n" ;
		JLabel chattingroomname = new JLabel("<html><H3>" +str.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</H3>"+Main.lastchatting.get(i)+"</html>");
		chattingroomname.setSize(330,100);
		chattingroomname.setIcon(basicfriendlistbar);
		chattingroomname.setBounds(0,i*100+100,330,100);
		chattingroomname.setHorizontalTextPosition(JLabel.CENTER);
		chattingroomname.setVerticalTextPosition(JLabel.CENTER);
		int roomID = Main.chattingroomIDlist.get(i);
		chattingroomname.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				chattingroomname.setIcon(enteredfriendlistbar);
				chattingroomname.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				chattingroomname.setIcon(basicfriendlistbar);
				chattingroomname.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(multichatrooms.get(roomID) == null) {
					Main.roomID = roomID;
					Main.sendway = Main.MULTI;
					Main.sendCommand = Main.GETCHATLIST;
				}
				
			}
		});
		chattingroomnames.add(chattingroomname);
		chatListPanel.add(chattingroomnames.get(i));
	}
	public void remakeAddFriendList() {
		this.remove(addfriendListScrollbar);
		this.remove(addfriendListPanel);
		addfriendListPanel.removeAll();
		addfriendnames.clear();
		requestfriendnames.clear();
		requestedfriendnames.clear();
		addfriendscrollbarflag = 0;
		addfriendListScrollbar.removeAll();
		makeAddFriendList();
	}
	public void makeRequestFriendList(int i) {
		JLabel friendname = new JLabel("["+Main.requestaddfriend.get(i)+"] 친구전송 완료");
		friendname.setSize(330,100);
		friendname.setIcon(basicfriendlistbar);
		friendname.setBounds(0,i*100+100,330,100);
		friendname.setHorizontalTextPosition(JLabel.CENTER);
		friendname.setVerticalTextPosition(JLabel.CENTER);
		friendname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				friendname.setIcon(enteredfriendlistbar);
				friendname.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				friendname.setIcon(basicfriendlistbar);
				friendname.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		requestfriendnames.add(friendname);
		addfriendListPanel.add(requestfriendnames.get(i));
		addfriendListPanel.repaint();
	}
	public void makeRequestedFriendList(int i,int startpoint) {
		JLabel friendname = new JLabel(Main.requestedaddfriend.get(i));
		friendname.setSize(330,100);
		friendname.setIcon(basicfriendlistbar);
		friendname.setBounds(0,(i+startpoint)*100+100,330,100);
		friendname.setHorizontalTextPosition(JLabel.CENTER);
		friendname.setVerticalTextPosition(JLabel.CENTER);
		friendname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				friendname.setIcon(enteredfriendlistbar);
				friendname.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				friendname.setIcon(basicfriendlistbar);
				friendname.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		JButton acceptButton = new JButton(basicacceptbutton);

		acceptButton.setBounds(255,30, 40, 40);
		acceptButton.setBorderPainted(false);
		acceptButton.setContentAreaFilled(false);
		acceptButton.setFocusPainted(false); 
		acceptButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				acceptButton.setIcon(enteredacceptbutton);
				acceptButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				acceptButton.setIcon(basicacceptbutton);
				acceptButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Main.friendID = friendname.getText();
				Main.sendCommand= Main.ACCEPTFRIEND;
			}
		});
		friendname.add(acceptButton);
		
		JButton declineButton= new JButton(basicdeclinebutton);
		declineButton.setBounds(300,30, 40, 40);
		declineButton.setBorderPainted(false);
		declineButton.setContentAreaFilled(false);
		declineButton.setFocusPainted(false); 
		declineButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				declineButton.setIcon(entereddeclinebutton);
				declineButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				declineButton.setIcon(basicdeclinebutton);
				declineButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Main.friendID = friendname.getText();
				Main.sendCommand= Main.DECLINEFRIEND;
			}
		});
		friendname.add(declineButton);
		requestedfriendnames.add(friendname);
		addfriendListPanel.add(requestedfriendnames.get(i));
	}

	public void commandLogin(int succeedLogin) {
		if (succeedLogin == 1) {
			enterMain();
		} else if (succeedLogin == 0 || succeedLogin == -1) {
			loginwarningmessage.setVisible(true);
		} else if (succeedLogin == -2) {
		}
	}
	public void commandJoin(int succeedJogin) {
		if(succeedJogin!= -1) {
			screenLogin();
		}else {
			joinwarningmessage.setVisible(true);
		}
	}
	public void commandSendMessage(String friendID, String msg) {
		chattingRooms.get(friendID).appendChatarea(msg);
	}
	public void commandSendChat(int roomID, String msg) {
		multichatrooms.get(roomID).appendChatarea(msg);
		remakeChatList();
	}
	public Point getLoacation() {
		return this.getLocation();
	}
	public void openChattingRoom(int roomID,String chattingroomname) {
		multichatrooms.put(roomID, new ChattingRoom(roomID,chattingroomname,getLocation()));
	}
	public void openChattingRoom(String chattingroomname) {
		chattingRooms.put(chattingroomname, new ChattingRoom(-1,chattingroomname,getLoacation()));
	}
	public void openProfileWindow(String userID,String profilemsg) {
		profilewindows.put(userID, new ProfileWindow(getLoacation(),userID,profilemsg));
	}
	public void reopenProfileWindow(String userID) {
		closeProfileWindow(userID);
		if(profilewindows.get(userID) == null) {
			Main.friendID = userID;
			Main.sendCommand = Main.GETPROFILE;
		}
	}
	public void closeProfileWindow(String userID) {
		profilewindows.get(userID).close(userID);
	}
}

