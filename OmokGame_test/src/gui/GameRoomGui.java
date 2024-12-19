package gui;

import gameClient.ClientInterface;
import protocolData.Database;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GameRoomGui extends JPanel implements RoomGuiInter {

	private UserListFrame userListFrame;
	
	/*
	 *  Left Panel
	 */
	private JLabel m_titleLabel;
	private GameBoardCanvas m_board = new GameBoardCanvas();
	private JTextField m_input;
	private JPanel m_canvasPanel;

	/*
	 * Right Panel 
	 */
	private JPanel m_rightPanel;
	private JTextArea m_log;
	private JList m_userList;
	private JLabel m_gamer1,m_gamer2;
	private JLabel m_gamerCaptin, m_gamerCrhallenger;
	private ImageButton m_exitButton, m_totalUserButton;
	private ImageButton m_backButton;
	private JScrollPane m_logScrollPan;
	private JScrollBar m_vScroll;
	
	private ClientInterface client;
	private EventExecute event;
	
	Database db = new Database();
    ImageIcon basic = new ImageIcon("image/image.jpg");
    
	public GameRoomGui(ClientInterface client) {
		
		this.client = client;
		this.event = new EventExecute(this, this.client);
		
		execute();
		
	}
	
	public void paint(Graphics g) {
		ImageIcon icon = new ImageIcon(
				URLGetter.getResource("image/loginBG.jpg"));
		g.drawImage(icon.getImage(),0,0,null,null);
		this.paintComponents(g);
	}
	
	private void execute() {
		addCanvas();
		addRightPanel();
	}

	private void addCanvas() {
		m_canvasPanel = new JPanel() {
			public void paint(Graphics g) {
				this.paintComponents(g);
			}
		};
		
		m_canvasPanel.add(m_board);
		m_canvasPanel.setLayout(null);
		m_board.setBounds(5, 5, 590, 593);
		
		add(m_canvasPanel);
		m_canvasPanel.setBounds(5,5,600,700);
//		m_canvasPanel.setBorder(new TitledBorder("sdf"));
		
		setLayout(null);
		
		
	}
	
	private void addRightPanel() {
		// userList don't add.
		m_rightPanel = new JPanel() {
			public void paint(Graphics g) {
				this.paintComponents(g);
			}
		};
		
		m_titleLabel = new JLabel("::: Game Infomation.");
		m_rightPanel.add(m_titleLabel);
		m_titleLabel.setBounds(5,10,200, 25);
		
		m_log = new JTextArea(4,50);
		m_gamer1 = new JLabel();
		m_rightPanel.add(m_gamer1);
		m_gamer1.setBounds(5,220,150,25);
		m_gamer2 = new JLabel();
		m_rightPanel.add(m_gamer2);
		m_gamer2.setBounds(145,220,150,25);

		m_gamerCaptin = new JLabel();
		m_gamerCaptin.setBounds(5, 130, 100, 100);
		m_gamerCrhallenger = new JLabel();
		m_gamerCrhallenger.setBounds(120, 130, 100, 100);
//		m_exitButton = new ImageButton("EXIT GAME");
		m_exitButton = new ImageButton("image/exitButton.jpg", "EXIT GAME", "image/exitButtonOver.jpg");
		
		// 상태창
		m_logScrollPan = new JScrollPane(m_log, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		m_rightPanel.add(m_logScrollPan);
		m_logScrollPan.setBounds(3,280,210,195);
//		m_logScrollPan.setBorder(new TitledBorder("상태창"));
		m_log.setEditable(false);
		m_log.setLineWrap(true);
		m_log.setText("");
		
		m_vScroll = m_logScrollPan.getVerticalScrollBar();
		m_vScroll.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
//				System.out.println("************** EVENT!!!" + e.getValueIsAdjusting());
				if(!e.getValueIsAdjusting()) 
					m_vScroll.setValue(m_vScroll.getMaximum());
//				m_vScroll.removeAdjustmentListener(this);
				
			}
		});

		
		m_input = new JTextField(10);
		m_rightPanel.add(m_input);
		m_input.setBounds(4, 482, 210, 30);
		m_input.addActionListener(event);
		
		m_totalUserButton = new ImageButton("image/totalUserButton.jpg", "Total User", "image/totalUserButtonOver.jpg");
		m_rightPanel.add(m_totalUserButton);
		m_totalUserButton.setBounds(5,544,65,44);
		m_totalUserButton.addActionListener(event);
		
		m_backButton = new ImageButton("image/backButton.jpg", "한수 물리기", "image/backButtonOver.jpg");
		m_backButton.addActionListener(event);
		
		m_rightPanel.add(m_backButton);
		m_backButton.setBounds(73,544,65,44);
		
		m_rightPanel.add(m_gamerCaptin);
		m_gamerCaptin.setBounds(2,75,100,130);
//		m_gamerCaptin.setIcon(new ImageIcon(StoneFactory.getInstance().getStone(0)));
		
		m_rightPanel.add(m_gamerCrhallenger);
		m_gamerCrhallenger.setBounds(112,75,100,130);
//		m_gamerCrhallenger.setIcon(new ImageIcon(StoneFactory.getInstance().getStone(1)));
		
		m_rightPanel.add(m_exitButton);
		m_exitButton.setBounds(142, 544, 65, 44);
		m_exitButton.addActionListener(event);
		
		m_rightPanel.setLayout(null);
//		m_rightPanel.setBorder(new TitledBorder("sdf"));
		
		add(m_rightPanel);
		m_rightPanel.setBounds(615,5,210,600);
	}

	public void setTextToLogWindow(String str) {
		m_log.append(str);
	}

	public void setUserList(Vector<String> userList) {
		Vector<String> temp = new Vector<String>();
		
		setRoomKing(userList.get(0));

		if(userList.size() < 2)
			setCrhallenger("Pleas wait....");
		else
			setCrhallenger(userList.get(1));
		
		int i = 0;
		for (String user : userList)
			if (i++ > 1) temp.add(user);
		
//		m_userList.setListData(temp);

	}

	private void setCrhallenger(String user) {
		m_gamerCrhallenger.setText(user);
		loadImageFromDatabase(user, 2);
		this.repaint();
	}

	private void setRoomKing(String user) {
		m_gamerCaptin.setText(user);
		loadImageFromDatabase(user, 1);
		this.repaint();
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
		return m_input.getText();
	}


	public void setTextToInput(String string) {
		m_input.setText(string);
	}

	public void setRoomList(Vector<String> roomList) {
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
               	int labelWidth = m_gamerCaptin.getWidth();
               	int labelHeight = m_gamerCaptin.getHeight();
               	
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
                   m_gamerCaptin.setIcon(imageIcon); 
        	 } else {
        		 m_gamerCaptin.setIcon(basic);
        	 }
         } else if(user==2) { // gamer2
        	 if(imageBytes != null) {
        		 // 이미지를 표시하는 코드
                 ImageIcon imageIcon = new ImageIcon(imageBytes);
                 // 라벨 크기 구하기
               	int labelWidth = m_gamerCrhallenger.getWidth();
               	int labelHeight = m_gamerCrhallenger.getHeight();
               	
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
                   m_gamerCrhallenger.setIcon(imageIcon); 
        	 } else {
        		 m_gamerCrhallenger.setIcon(basic);
        	 }
         }
    }
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Network FIve Eyes Ver. 1.0");
		Container cp = frame.getContentPane();
		cp.add(new GameRoomGui(null));
		
		frame.setSize(850, 650);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		System.out.println("Exit");
	}

	
	
	
	/* ========================================================
	 * Canvas __ GameBoard!
	 */

	class GameBoardCanvas extends Canvas {

		/*
		 * Image Size is 590,593
		 */

		private static final long serialVersionUID = 1L;

		private ArrayList<StoneHistory> historyOfStone = new ArrayList<StoneHistory>();

		private Toolkit toolkit = Toolkit.getDefaultToolkit();

		private MediaTracker tracker;

		private Image image, lastStone;

		private BufferedImage bi;

		private int width, height;

		private int[] points;
		
		private int[] lastPoint = new int[2];

		private StoneHistory aStoneInfo;

		private boolean isRedraw = false;
		
		private int whatStone = 0;
		
		private boolean isSpectator = false;

		public GameBoardCanvas() {
			imageLoad();
			traker();

			/*
			 * Mouse Click! Event catch!!
			 * 
			 */
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int[] point = new int[2];
					
					if (!isSpectator && (16 < e.getX() && e.getX() < 555) && 20 < e.getY() && e.getY() < 560) {
			            System.out.println("마우스 클릭!");
			            point = FindCrossPoint.find(e.getX(), e.getY());
			            if (!isStoneDraw(point)) {
			                client.sendMessage(point);
			            }
			        }
				}
			});
		}
		
		public void setSpectatorMode(boolean isSpectator) {
		    this.isSpectator = isSpectator;
		}
		
		public void addHistory(int[] points, int kindOfStone) {
			aStoneInfo = new StoneHistory(points, StoneFactory.getInstance().getStone(kindOfStone));
			historyOfStone.add(aStoneInfo);
			
			repaint();
		}
		
		// Version 1.01 update
		protected boolean isStoneDraw(int[] points) {

			for (StoneHistory temp : historyOfStone) {
				if(temp.points[0] == points[0] && temp.points[1] == points[1])
					return true;
			}
			return false;
		}
		
		public void subHistory(int count) {
				historyOfStone.remove(historyOfStone.size()-1);

				if (count == 2) subHistory(1);
		}

		public void drawHistory() {
			isRedraw = true;
			repaint();
		}

		public void paint(Graphics g) {
			g.drawImage(bi, 0, 0, this);
//			System.out.println("paint()!");
//			update(g);
		}

		/*
		 * when mouse click, stone is draw.
		 */
		public void update(Graphics g) {

//			System.out.println("update()");

			drawStones(g);
		}


		/*
		 * private Methid~!
		 * 
		 */
		private void drawStones(Graphics g) {
			
			if (!isRedraw) {
				if (lastStone != null) 
					g.drawImage(lastStone, lastPoint[0], lastPoint[1], this);
				
				System.out.println("add()" + historyOfStone);
				lastPoint[0] = aStoneInfo.points[0];
				lastPoint[1] = aStoneInfo.points[1];
				lastStone = aStoneInfo.getStone();
				
				g.drawImage(aStoneInfo.getStone(), lastPoint[0], lastPoint[1], this);

			} else {
				for (StoneHistory temp : historyOfStone) {
					g.drawImage(temp.getStone(), temp.points[0], temp.points[1], this);
					lastPoint[0] = temp.points[0];
					lastPoint[1] = temp.points[1];
				}
				
				isRedraw = false;
			}

			if (lastStone != null) {
				g.setColor(Color.RED);
				g.fillRect(lastPoint[0]+8, lastPoint[1]+8, 7,7);
			}
		}
		
		private StoneHistory getLastStone() {
			return historyOfStone.get(historyOfStone.size() - 1);
		}

		private void traker() {
			try {
				tracker = new MediaTracker(this);
				tracker.addImage(image, 0);
				tracker.waitForID(0);
				width = image.getWidth(null);
				height = image.getHeight(null);
				bi = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics gg = bi.getGraphics();
				gg.drawImage(image, 0, 0, this);
				gg.dispose();
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		private void imageLoad() {
			image = toolkit.createImage(URLGetter.getResource("image/badook_board.jpg"));
		}

		public void removeStones() {
			historyOfStone = new ArrayList<StoneHistory>();
		}
	}


	
	/*
	 *   Class Of a Stone
	 * 
	 */
	private class StoneHistory {
		int[] points = new int[2];

		Image stone;

		public StoneHistory(int[] points, Image stone) {
			this.points = points;
			this.stone = stone;
		}

		public Image getStone() {
			return this.stone;
		}

		public String toString() {
			return "" + points[0] + " " + points[1];
		}
	}

	public void drawStone(int[] stoneLocation, boolean isBlack) {
		if (isBlack)
			m_board.addHistory(stoneLocation, BLACK_STONE);
		else m_board.addHistory(stoneLocation, WHITE_STONE);
		
	}

	public void setTotalUser(Vector<String> userList) {
		userListFrame.setUserList(userList);
	}

	public void setUserListFrame(UserListFrame userListFrame) {
		this.userListFrame = userListFrame;
	}

	public void backOneStep(int n) {
		m_board.subHistory(n);
		
		m_board.setVisible(false);
		m_board.setVisible(true);
		
		m_board.drawHistory();
	}

	public void setGameRoomInfo(String info) {
		StringTokenizer token = new StringTokenizer(info, "|");
		
		m_titleLabel.setText(token.nextToken());
		m_gamer1.setText(token.nextToken());
		m_gamer2.setText(token.nextToken());
	}

	public void newGame() {
		m_board.removeStones();
		m_board.lastStone = null;
		m_board.lastPoint = new int[2];

		m_board.setVisible(false);
		m_board.setVisible(true);
	}

	public int[] getFrameSize() {
		int[] size = {850, 650};
		return size;
	}
	public void setPanel(PanelInterface panel) {
	}
	public void setPanel(GameLobbyInter panel) {
	}
	public void setPanel(LobbyGuiInter panel) {
	}
	public void setPanel(RoomGuiInter panel) {
	}

	public GameBoardCanvas getM_board() {
		return m_board;
	}
}

class StoneFactory { 
	/*
	 *  FlyWeight  Design Patten....
	 */
	
	public static StoneFactory singleton = new StoneFactory();
	
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	private Image[] stones = { toolkit.createImage(URLGetter.getResource("image/BlackStone.gif")),
			                                    toolkit.createImage(URLGetter.getResource("image/WhiteStone.gif"))	};
	
	private StoneFactory() {}
	
	public static StoneFactory getInstance() {
		return singleton;
	}
	
	public Image getStone(int index) {
		return stones[index];
	}
}