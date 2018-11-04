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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChattingRoom extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5325304607557608932L;
	private Image screenImage;
	private Graphics screenGraphic;

	private Image backgroundimage = new ImageIcon(getClass().getClassLoader().getResource("img/ChattingRoomBackGround.png"))
			.getImage();

	private JLabel topBar = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/TopBar.png")));
	private JLabel roomName;

	JTextArea sendarea = new JTextArea();
	JScrollPane sendscroll;

	private  JTextArea chatarea = new JTextArea();
	JScrollPane chatscroll;

	private ImageIcon basicexitbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicLoginExitButton.png"));
	private ImageIcon enteredexitbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredLoginExitButton.png"));
	private ImageIcon basicchattingroomexitbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicChattingRoomExitButton.png"));
	private ImageIcon enteredchattingroomexitbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredChattingRoomExitButton.png"));
	private JButton exitButton = new JButton(basicexitbutton);
	private JButton chattingroomexitbutton = new JButton(basicchattingroomexitbutton);

	private ImageIcon basicsendbutton = new ImageIcon(getClass().getClassLoader().getResource("img/BasicSendButton.png"));
	private ImageIcon enteredsendbutton = new ImageIcon(getClass().getClassLoader().getResource("img/EnteredSendButton.png"));

	private JButton SendButton = new JButton(basicsendbutton);

	private int mouseX, mouseY;
	
	
	public ChattingRoom(int roomID ,String roomname,Point pt) {
		setUndecorated(true);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		pt.setLocation(pt.getX()-Main.SCREEN_WIDTH-50, pt.getY());
		setLocation(pt);
		setVisible(true);
		
		roomName = new JLabel(roomname);
		roomName.setFont(new Font("Dotum", Font.BOLD, 14));
		roomName.setBounds(20, 30, 300, 30);
		add(roomName);

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
				Main.chattingRooms.remove(roomname);
				close(roomID, roomname);
			}
		});
		add(exitButton);
		if(roomID != -1) {
			chattingroomexitbutton.setBounds(320, 40, 30, 40);
			chattingroomexitbutton.setBorderPainted(false);
			chattingroomexitbutton.setContentAreaFilled(false);
			chattingroomexitbutton.setFocusPainted(false);
			chattingroomexitbutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					chattingroomexitbutton.setIcon(enteredchattingroomexitbutton);
					chattingroomexitbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					chattingroomexitbutton.setIcon(basicchattingroomexitbutton);
					chattingroomexitbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mousePressed(MouseEvent e) {
					int result = JOptionPane.showConfirmDialog(null,"정말 채팅방에서 나가시겠습니까?","채팅방 나가기",JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(result == 0 ) {
						Main.roomID = roomID;
						Main.sendCommand = Main.EXITCHATTINGROOM;
						close(roomID,roomname);
					}
				}
			});
			add(chattingroomexitbutton);
		}
		
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

		sendarea.setBounds(5, 5, 300, 100);
		sendarea.setFont(new Font("Dotum",Font.PLAIN, 14));
		sendarea.setLineWrap(true);
		sendarea.setDocument(new JTextFieldLimit(100));
		sendscroll = new JScrollPane(sendarea);
		sendscroll.setBounds(0, 500, 300, 100);
		this.getContentPane().add(sendscroll);

		chatarea.setBounds(5, 5, 350, 400);
		chatarea.setEditable(false);
		chatarea.setBackground(new Color(0xb2c7d9));
		chatarea.setFont(new Font("Dotum", Font.BOLD, 14));
		chatarea.setLineWrap(true);
		
		addLog(Main.chatList); // textarea에 채팅 로그 추가
		chatscroll = new JScrollPane(chatarea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatscroll.setBounds(0, 100, 350, 400);
		this.getContentPane().add(chatscroll);

		SendButton.setBounds(300, 500, 50, 100);
		SendButton.setBorderPainted(false);
		SendButton.setContentAreaFilled(false);
		SendButton.setFocusPainted(false);
		SendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				SendButton.setIcon(enteredsendbutton);
				SendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				SendButton.setIcon(basicsendbutton);
				SendButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(!sendarea.getText().equals("")) {
					Main.sendMessage = sendarea.getText();
					if(roomID == -1) {
						Main.friendID = roomName.getText();
						Main.sendCommand =  Main.SENDMESSAGE;
					}else{
						Main.roomID = roomID;
						Main.sendCommand =  Main.SENDCHAT;
					}
					sendarea.setText("");
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.recvMessage = null;
			}
		});
		add(SendButton);

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

	public void close(int roomID,String roomname) {
		this.setVisible(false);
		if(roomID == -1) {
			MyKaKaoTalk.chattingRooms.remove(roomname);
		}else {
			MyKaKaoTalk.multichatrooms.remove(roomID);
		}
	}

	public void addLog(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			chatarea.append(list.get(i));
		}
		list.clear();
	}
	
	public void appendChatarea(String msg) {
		chatarea.append(msg);
		chatarea.setCaretPosition(chatarea.getDocument().getLength());
	}
}
