package gui;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gameClient.ClientInterface;
import protocolData.Database;


@SuppressWarnings("serial")
public class GameLobby extends JPanel implements GameLobbyInter, PanelInterface {

	private UserListFrame userListFrame;
	Database db = new Database();
    ImageIcon basic = new ImageIcon("image/image.jpg");
	
	/*
	 * Component of Gamer Panel
	 */
	private JLabel m_roomInfo, m_userInfo1, m_userInfo2;
	
	private JPanel m_gamerPanel = new JPanel() {
		public void paint(Graphics g) {
			this.paintComponents(g);
		}
	};

	/*
	 * Component of User List Panel
	 */
	private JPanel m_userListPanel = new JPanel() {
		public void paint(Graphics g) {
			this.paintComponents(g);
		}
	};

	private JList m_userList = new JList();

	private JScrollPane m_scPaneUserList;

	/*
	 * Component of Chat Window
	 */
	private JPanel m_logWinPanel = new JPanel() {
		public void paint(Graphics g) {
			this.paintComponents(g);
		}
	};

	private JTextArea m_logWindow = new JTextArea(5, 20);

	private JTextField m_textInput = new JTextField();

	private ImageButton m_sendButton = new ImageButton("image/gameLobbySendButton.jpg", "SEND", "image/gameLobbySendButtonOver.jpg");

	private JScrollPane m_scPaneLogWin;
	private JScrollBar m_vScroll;

	/*
	 * Component of Info Panel
	 */
	private JPanel m_infoPanel = new JPanel() {
		public void paint(Graphics g) {
			this.paintComponents(g);
		}
	};

	private ImageButton m_exitButton = new ImageButton("image/gameLobbyExitButton.jpg", "나가기", "image/gameLobbyExitButtonOver.jpg");
	private ImageButton m_totalUserButton = new ImageButton("image/gameTotalUserButton.jpg","Total User", "image/gameTotalUserButtonOver.jpg");
	
	protected AbstractButton m_startButton;
	
	/*
	 * 임시...
	 */
	private Vector<String> vc = new Vector<String>();
	
	
	private ClientInterface client;
	private EventExecute event;

	
	private boolean isRoomKing = true;
	private JLabel m_gamer1;
	private JLabel m_gamer2;
	/*
	 *  Constructor
	 */
	public GameLobby(ClientInterface client, boolean isRoomKing) {
		
		this.client = client;
		this.event = new EventExecute(this, this.client);
		this.isRoomKing = isRoomKing;
		
		execute();
		
		System.out.println("게임방 생성.");
	    
	}
 	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Network FIve Eyes Ver. 1.0");
		Container cp = frame.getContentPane();
		cp.add(new GameLobby(null, true));
		
		frame.setSize(340,440);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		System.out.println("Exit");
	}
	
	private void execute() {
		add(generatorGamerPanel());
		add(generatorUserListPanel());
		add(generatorChatWindowPanel());
		add(generatorInfoPanel());
		setLayout(null);
	}
	
	public void paint(Graphics g) {
//		ImageIcon icon = new ImageIcon(
//				URLGetter.getResource("image/gameLobbyBg.jpg"));
		ImageIcon icon = new ImageIcon(
				URLGetter.getResource("image/LoginBg.jpg"));
		g.drawImage(icon.getImage(),0,0,null,null);
		this.paintComponents(g);
	}
	
	private JPanel generatorGamerPanel() {
		m_roomInfo = new JLabel();
		m_userInfo1 = new JLabel();
		m_userInfo2 = new JLabel();
	    
	    m_gamer1 = new JLabel();
	    m_gamer2 = new JLabel();
		m_gamerPanel.add(m_roomInfo);
		m_roomInfo.setBounds(5,5,200,10);
		
		m_gamerPanel.add(m_userInfo1);
		m_userInfo1.setBounds(50,200,100,10);
		m_gamerPanel.add(m_gamer1);
		m_gamer1.setIcon(basic);
		m_gamer1.setBounds(40,50, 100,130);
	    
		m_gamerPanel.add(m_userInfo2);
		m_userInfo2.setBounds(210,200,100,30);
		m_gamerPanel.add(m_gamer2);
		m_gamer2.setIcon(basic);
		m_gamer2.setBounds(170, 50, 100, 130);
		
		m_gamerPanel.setLayout(null);
		m_gamerPanel.setBounds(5,5,300,245);
	    
		return m_gamerPanel;
	}
	
    // 이미지 불러오기
 	private void loadImageFromDatabase(String nickName, int user) {
         // 데이터베이스에서 이미지를 로드하는 코드
         byte[] imageBytes = db.loadProfileImageNickName(nickName);

         if(user==1) {
        	 if(imageBytes != null) {
        		 // 이미지를 표시하는 코드
                 ImageIcon imageIcon = new ImageIcon(imageBytes);
                 // 라벨 크기 구하기
               	int labelWidth = m_gamer1.getWidth();
               	int labelHeight = m_gamer1.getHeight();
               	
               	Image img = imageIcon.getImage();
               	int newWidth = img.getWidth(null);
               	int newHeight = img.getHeight(null);
               	
               	// 라벨보다 이미지가 크면 크기 조정
               	if(img.getWidth(null)>labelWidth||img.getHeight(null)>labelHeight) {
               		double widthRatio = (double)labelWidth/img.getWidth(null);
               		double heightRatio = (double)labelHeight/img.getHeight(null);
               		double scaleFactor = Math.min(widthRatio, heightRatio);
               		
               		newWidth = (int)(img.getWidth(null)*scaleFactor);
               		newHeight = (int)(img.getHeight(null)*scaleFactor);
               	}
                   Image scaledImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                   imageIcon = new ImageIcon(scaledImage);
                   m_gamer1.setIcon(imageIcon); 
        	 } else {
        		 m_gamer1.setIcon(basic);
        	 }
         } else if(user==2) { // gamer2
        	 if(imageBytes != null) {
        		 // 이미지를 표시하는 코드
                 ImageIcon imageIcon = new ImageIcon(imageBytes);
                 // 라벨 크기 구하기
               	int labelWidth = m_gamer2.getWidth();
               	int labelHeight = m_gamer2.getHeight();
               	
               	Image img = imageIcon.getImage();
               	int newWidth = img.getWidth(null);
               	int newHeight = img.getHeight(null);
               	
               	// 라벨보다 이미지가 크면 크기 조정
               	if(img.getWidth(null)>labelWidth||img.getHeight(null)>labelHeight) {
               		double widthRatio = (double)labelWidth/img.getWidth(null);
               		double heightRatio = (double)labelHeight/img.getHeight(null);
               		double scaleFactor = Math.min(widthRatio, heightRatio);
               		
               		newWidth = (int)(img.getWidth(null)*scaleFactor);
               		newHeight = (int)(img.getHeight(null)*scaleFactor);
               	}
                   Image scaledImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                   imageIcon = new ImageIcon(scaledImage);
                   m_gamer2.setIcon(imageIcon); 
        	 } else {
        		 m_gamer2.setIcon(basic);
        	 }
         }
    }
	
	private JPanel generatorUserListPanel() {
		m_userList.setListData(vc);

//		m_scPaneUserList = new JScrollPane(m_userList,
//				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		m_scPaneUserList.setBorder(new TitledBorder("관전자"));
//		m_scPaneUserList.setBounds(5, 5, 220, 90);
		
		
		
//		m_userListPanel.add(m_scPaneUserList);
		m_userListPanel.setLayout(null);
		m_userListPanel.setBounds(270, 5, 230, 130);
//		m_userListPanel.setBorder(new TitledBorder("Sdfsd"));
		
		return m_userListPanel;
	}

	private JPanel generatorChatWindowPanel() {
		m_scPaneLogWin = new JScrollPane(m_logWindow,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		m_scPaneLogWin.setBorder(new TitledBorder("Chatting Window"));
		m_logWindow.setEditable(false);
		m_scPaneLogWin.setBounds(23,5,209,100);
		m_logWindow.setLineWrap(true);
		
		m_vScroll = m_scPaneLogWin.getVerticalScrollBar();
		m_vScroll.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
//				System.out.println("************** EVENT!!!" + e.getValueIsAdjusting());
				if(!e.getValueIsAdjusting()) m_vScroll.setValue(m_vScroll.getMaximum());
//				m_vScroll.removeAdjustmentListener(this);
				
			}
		});
		
		m_logWinPanel.add(m_textInput);
		m_textInput.setBounds(23, 110, 139, 30);
		m_logWinPanel.add(m_sendButton);
		m_sendButton.setBounds(172, 110, 60, 30);
		

		m_logWinPanel.add(m_scPaneLogWin);
		m_logWinPanel.setLayout(null);
		m_logWinPanel.setBounds(5, 250, 260, 200);
//		m_logWinPanel.setBorder(new TitledBorder("Sdfsd"));
		
		m_textInput.addActionListener(event);
		m_sendButton.addActionListener(event);
		
		return m_logWinPanel;
	}

	private JPanel generatorInfoPanel() {
//		if(isRoomKing)
			m_startButton = new ImageButton("image/startButton.jpg", "START!", "image/startButtonOver.jpg");
//		else
//			m_startButton = new ImageButton("image/readyButton.jpg", "READY", "image/readyButtonOver.jpg");
		m_infoPanel.add(m_startButton);
		m_startButton.setBounds(5,50,60,40);
		m_infoPanel.add(m_exitButton);
		m_exitButton.setBounds(5,95,60,40);
		
		m_infoPanel.add(m_totalUserButton);
		m_totalUserButton.setBounds(5,5,60,40);
		m_totalUserButton.addActionListener(event);
		
		m_infoPanel.setLayout(null);
		m_infoPanel.setBounds(245, 250, 230,200);
		
//		m_infoPanel.setBorder(new TitledBorder("Sdfsd"));
		
		m_startButton.addActionListener(event);
		m_exitButton.addActionListener(event);
		
//		setClickable(false);
		
		return m_infoPanel;
	}

	public void setClickable(boolean b) {
		m_startButton.setEnabled(b);
	}

	public void setTextToLogWindow(String str) {
		m_logWindow.append(str);
	}

	public void setUserList(Vector<String> userList) {
	    Vector<String> temp = new Vector<String>();

	    if (userList.size() >= 2) {
	        setRoomKing(userList.get(0));
	        setCrhallenger(userList.get(1));

	        int i = 0;
	        for (String user : userList)
	            if (i++ > 1) temp.add(user);
	    } else {
	        // 만약 유저가 2명 미만이라면 모두 관전자로 처리
	        temp.add("관전자");
	        temp.addAll(userList);
	    }

	    m_userList.setListData(temp);
	}

	public void showMessageBox(String sender, String message, boolean option) {
		new SlipFrame(sender, message, false);
	}


	public JList getJList() {
		return m_userList;
	}

	public void unShow() {
		this.setVisible(false);
	}
	
	public String getInputText() {
		return m_textInput.getText();
	}


	public void setTextToInput(String string) {
		m_textInput.setText(string);
	}

	public void setRoomKing(String name) {
		m_userInfo1.setText(name);
		loadImageFromDatabase(name, 1);
		this.repaint();
	}

	public void setCrhallenger(String name) {
		m_userInfo2.setText(name);
		loadImageFromDatabase(name, 2);
		this.repaint();
	}
	
	public void setSpector(String name) {
		setTextToLogWindow("[" + name + "]님이 관전중입니다.");
	}

	public void setGameRoomInf(String info) {
		m_roomInfo.setText(info);
	}

	public void setStartButton(boolean isRoomKing) {
		if (isRoomKing) {
			// when user is king of room, this execute..
			m_startButton.setText("START");
			
			this.setButtonEnable(true);
			
		} else {
			m_startButton.setText("START");
		}
		
	}

	public void setButtonEnable(boolean clickable) {
//		m_startButton.setEnabled(clickable);
		m_startButton.setEnabled(true);
		System.out.println("Clikable******************");
	}

	public void setTotalUser(Vector<String> userList) {
		userListFrame.setUserList(userList);
	}

	public void setUserListFrame(UserListFrame userListFrame) {
		this.userListFrame = userListFrame;
	}
	
	public String getGameInfo() {
		return m_roomInfo.getText() + "|" + m_userInfo1.getText() + "|" + m_userInfo2.getText();
	}

	public void setPanel(PanelInterface panel) {
		// TODO Auto-generated method stub
		
	}

	public int[] getFrameSize() {
		int size[] = {340,440};
		return size;
	}

	public void setPanel(GameLobbyInter panel) {
	}
	public void setPanel(LobbyGuiInter panel) {
	}
	public void setPanel(RoomGuiInter panel) {
	}
}