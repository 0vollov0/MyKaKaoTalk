package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class ChatSetting extends JFrame {
	private Image screenImage;
	private Graphics screenGraphic;

	private Image backgroundimage = new ImageIcon(getClass().getClassLoader().getResource("img/MainBackGround.png"))
			.getImage();

	private ImageIcon basicexitbutton = new ImageIcon(
			getClass().getClassLoader().getResource("img/BasicLoginExitButton.png"));
	private ImageIcon enteredexitbutton = new ImageIcon(
			getClass().getClassLoader().getResource("img/EnteredLoginExitButton.png"));
	private ImageIcon basicfriendlistbar = new ImageIcon(
			getClass().getClassLoader().getResource("img/BasicFriendListBar.png"));
	private ImageIcon enteredfriendlistbar = new ImageIcon(
			getClass().getClassLoader().getResource("img/EnteredFriendListBar.png"));
	private ImageIcon basicmakechattingroombutton = new ImageIcon(
			getClass().getClassLoader().getResource("img/BasicMakeChattingRoomButton.png"));
	private ImageIcon enteredmakechattingroombutton = new ImageIcon(
			getClass().getClassLoader().getResource("img/EnteredMakeChattingRoomButton.png"));

	private JLabel topBar = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/TopBar.png")));
	private JLabel explainbar = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/ExplainSetChattingRoomBar.png")));
	private JButton exitButton = new JButton(basicexitbutton);
	private JButton makechattingroomButton = new JButton(basicmakechattingroombutton);

	private JTextField setchattingRoom = new JTextField();

	private JPanel friendListPanel = new JPanel();
	private JScrollBar friendListScrollbar;

	private int mouseX, mouseY;
	private int friendlistscrollbarflag = 0;

	private ArrayList<JLabel> friendnames = new ArrayList<JLabel>();
	private ArrayList<String> memberlist = new ArrayList<String>();

	public ChatSetting(Point pt) {
		setUndecorated(true);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		pt.setLocation(pt.getX() - Main.SCREEN_WIDTH - 50, pt.getY());
		setResizable(false);
		setLocationRelativeTo(null);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		setFocusable(true);
		setLocation(pt);
		setVisible(true);
		memberlist.add(Main.userID);
		setchattingRoom.setBounds(30, 55, 240, 40);
		setchattingRoom.setEnabled(false);
		setchattingRoom.setText("채팅방 이름을 정해주세요.");
		setchattingRoom.setDocument(new JTextFieldLimit(24));
		setchattingRoom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (setchattingRoom.getText().equals("채팅방 이름을 정해주세요.")) {
					setchattingRoom.setEnabled(true);
					setchattingRoom.setText("");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (setchattingRoom.getText().equals("")) {
					setchattingRoom.setEnabled(false);
					setchattingRoom.setText("채팅방 이름을 정해주세요.");
				}
			}
		});
		add(setchattingRoom);

		makechattingroomButton.setBounds(280, 55, 60, 40);
		makechattingroomButton.setBorderPainted(false);
		makechattingroomButton.setContentAreaFilled(false);
		makechattingroomButton.setFocusPainted(false);
		makechattingroomButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				makechattingroomButton.setIcon(enteredmakechattingroombutton);
				makechattingroomButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				makechattingroomButton.setIcon(basicmakechattingroombutton);
				makechattingroomButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(memberlist.size() >1) {
					if(setchattingRoom.getText().equals("채팅방 이름을 정해주세요.")) {
						return;
					}else {
						Main.sendCommand = Main.MAKECHATTINGROOM;
					}
				}else {
					
				}
			}
		});
		add(makechattingroomButton);

		explainbar.setBounds(0, 100, 350, 40);
		explainbar.setVisible(true);
		add(explainbar);

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
				close();
			}
		});
		add(exitButton);

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
		makeFriendList();
	}

	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(backgroundimage, 0, 0, null);
		paintComponents(g);
		this.repaint();
	}

	public void makeFriendList() {
		friendListPanel.setSize(330, 500);
		friendListPanel.setBounds(0, 140, 330, 500);
		friendListPanel.setBackground(Color.white);

		for (int i = 0; i < Main.friendlist.size(); i++) {
			JLabel friendname = new JLabel(Main.friendlist.get(i));
			friendname.setSize(330, 100);
			friendname.setIcon(basicfriendlistbar);
			friendname.setBounds(0, i * 100 + 100, 330, 100);
			friendname.setHorizontalTextPosition(JLabel.CENTER);
			friendname.setVerticalTextPosition(JLabel.CENTER);
			friendname.addMouseListener(new MouseAdapter() {
				boolean imgflag = false;
				@Override
				public void mouseEntered(MouseEvent e) {
					if (imgflag != true) {
						friendname.setIcon(enteredfriendlistbar);
					}
					friendname.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if (imgflag != true) {
						friendname.setIcon(basicfriendlistbar);
					}
					friendname.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if (imgflag != true) {
						friendname.setIcon(enteredfriendlistbar);
						imgflag = true;
						memberlist.add(friendname.getText());
					} else {
						friendname.setIcon(basicfriendlistbar);
						memberlist.remove(friendname.getText());
						imgflag = false;
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});
			friendnames.add(friendname);
			friendListPanel.add(friendnames.get(i));
		}
		int maxScroll;
		if (friendnames.size() > 4) {
			maxScroll = friendnames.size() - 3;
		} else {
			maxScroll = 1;
		}
		friendListScrollbar = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, maxScroll);
		friendListScrollbar.setSize(20, 460);
		friendListScrollbar.setLocation(330, 140);
		friendListScrollbar.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				if (friendlistscrollbarflag < friendListScrollbar.getValue()) {
					friendListPanel.remove(friendnames.get(friendlistscrollbarflag));
					friendlistscrollbarflag = friendListScrollbar.getValue();
				} else if (friendlistscrollbarflag != 0) {
					friendlistscrollbarflag = friendListScrollbar.getValue();
					friendListPanel.add(friendnames.get(friendlistscrollbarflag), 0);
				}
			}
		});
		add(friendListScrollbar);
		add(friendListPanel);
	}

	public void close() {
		this.setVisible(false);
		this.removeAll();
		Main.chatsetting = null;
	}
	public String getRoomName() {
		return setchattingRoom.getText();
	}
	public ArrayList<String> getMemberList(){
		return memberlist;
	}
	public int getMemberSize() {
		return memberlist.size();
	}
}
