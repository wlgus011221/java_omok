package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import gameClient.ClientLobby;
import protocolData.Database;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements PanelInterface {

	private JLabel lblTitle;
    private JLabel idLabel;
    private JLabel passLabel;
    private JLabel lblRe;
    private JLabel lblName;
    private JLabel lblNickName;
    
	private static ClientLobby client;
	private JTextField ID;
	private JPasswordField PW;
	private JButton loginButton;
	private JLabel joinLabel;
	private JLabel IDSearchLabel;
	private JLabel PWSearchLabel;
//	private JButton changePWButton; 
	
	private Database db = new Database();
	
	@SuppressWarnings("serial")
	public LoginPanel() {}
	
	@SuppressWarnings("serial")
	public LoginPanel(final ClientLobby client) {
		this.client = client;
		
		idLabel = new JLabel("아이디");
		ID = new JTextField();
		passLabel = new JLabel("비밀번호");
		PW = new JPasswordField();
		loginButton = new JButton("로그인");
		joinLabel = new JLabel("회원가입");
		IDSearchLabel = new JLabel("ID 찾기");
		PWSearchLabel = new JLabel("PW 찾기");
		
		idLabel = new JLabel("ID");
		idLabel.setForeground(new Color(255, 255, 255));
		idLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		idLabel.setBounds(55, 152, 50, 15);
		add(idLabel);
		
		ID = new JTextField();
		ID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					// 사용자가 입력 가져오기
	                String id = ID.getText();
	                String pw = new String(PW.getPassword());
	                
	                // id를 입력 안 한 경우
	                if (id.equals("")) {
	                	JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
	                	ID.requestFocusInWindow();
	                	return;
	                }
	                
	                // pw를 입력 안 한 경우
	                if (pw.equals("")) {
	                	JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
	                	PW.requestFocusInWindow();
	                	return;
	                }
	                login();
				}
			}
		});
		ID.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		ID.setBounds(85, 151, 170, 21);
		ID.setColumns(10);
		add(ID);
		
		passLabel = new JLabel("PW");
		passLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		passLabel.setForeground(new Color(255, 255, 255));
		passLabel.setBounds(55, 191, 50, 15);		
		add(passLabel);
		
		PW = new JPasswordField();
		PW.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					// 사용자가 입력 가져오기
	                String id = ID.getText();
	                String pw = new String(PW.getPassword());
	                
	                // id를 입력 안 한 경우
	                if (id.equals("")) {
	                	JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
	                	ID.requestFocusInWindow();
	                	return;
	                }
	                
	                // pw를 입력 안 한 경우
	                if (pw.equals("")) {
	                	JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
	                	PW.requestFocusInWindow();
	                	return;
	                }
	                login();
				}
			}
		});
		PW.setColumns(10);
		PW.setBackground(new Color(255, 255, 255));
		PW.setForeground(new Color(0, 0, 0));
		PW.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		PW.setBounds(86, 187, 170, 21);
		add(PW);
		
		loginButton = new JButton("로그인");
		loginButton.setForeground(new Color(255, 255, 255));
		loginButton.setBackground(new Color(206, 182, 222));
		loginButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 사용자가 입력 가져오기
                String id = ID.getText();
                String pw = new String(PW.getPassword());
                
                // id를 입력 안 한 경우
                if (id.equals("")) {
                	JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
                	ID.requestFocusInWindow();
                	return;
                }
                
                // pw를 입력 안 한 경우
                if (pw.equals("")) {
                	JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
                	PW.requestFocusInWindow();
                	return;
                }
                login();
			}
		});
		loginButton.setBounds(273, 148, 75, 60);
		add(loginButton);
		
		joinLabel = new JLabel("회원가입");
		joinLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				joinLabel.setForeground(new Color(0, 0, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				joinLabel.setForeground(new Color(255, 255, 255));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				SignUpFrame frame = new SignUpFrame();
				frame.setVisible(true);
			}
		});
		joinLabel.setForeground(Color.WHITE);
		joinLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		joinLabel.setBounds(285, 218, 50, 15);
		add(joinLabel);
		
		IDSearchLabel = new JLabel("ID 찾기");
		IDSearchLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				IDSearchLabel.setForeground(new Color(0,0,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				IDSearchLabel.setForeground(new Color(255, 255, 255));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				IDSearchFrame frame = new IDSearchFrame();
				frame.setVisible(true);
			}
		});
		IDSearchLabel.setForeground(Color.WHITE);
		IDSearchLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		IDSearchLabel.setBounds(137, 260, 58, 15);		
		add(IDSearchLabel);
		
		PWSearchLabel = new JLabel("PW 찾기");
		PWSearchLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				PWSearchLabel.setForeground(new Color(0, 0, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				PWSearchLabel.setForeground(new Color(255, 255, 255));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				PWSearchFrame frame = new PWSearchFrame();
				frame.setVisible(true);
			}
		});
		PWSearchLabel.setForeground(Color.WHITE);
		PWSearchLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		PWSearchLabel.setBounds(204, 260, 58, 15);
		add(PWSearchLabel);
		
		JLabel lblNewLabel = new JLabel("오목 게임");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("던파 비트비트체 v2", Font.PLAIN, 49));
		lblNewLabel.setBounds(93, 63, 213, 55);
		add(lblNewLabel);
		
		JLabel lblNewLabel_4 = new JLabel("|");
		lblNewLabel_4.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setBounds(191, 260, 50, 15);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("새로 오셨나요?");
		lblNewLabel_3.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(165, 218, 111, 15);
		add(lblNewLabel_3);

		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("image/loginBg.jpg"));
		backGround.setBounds(-124, -113, 591, 444);
		add(backGround);
		
		setLayout(null);
		
	}

	// 로그인 기능
	public void login() {
		/* TextField에 입력된 아이디와 비밀번호를 변수에 초기화 */
		String uid = ID.getText();
		String upass = "";
		for(int i=0; i < PW.getPassword().length; i++) {
			upass = upass + PW.getPassword()[i];
		}
		if(uid.equals("") || upass.equals("")) {
			JOptionPane.showMessageDialog(null, "아이디와 비밀번호 모두 입력해주세요", "로그인 실패", JOptionPane.ERROR_MESSAGE);
			System.out.println("로그인 실패 > 로그인 정보 미입력");
		} else if(uid != null && upass != null) {
			if(uid.equals("admin")&&upass.equals("admin")) {
				JOptionPane.showMessageDialog(null, "관리자 창으로 이동합니다.");
				ManagerFrame managerFrame = new ManagerFrame();
				managerFrame.setVisible(true);
			}
			else if(db.logincheck(uid, upass)) {	//이 부분이 데이터베이스에 접속해 로그인 정보를 확인하는 부분이다.
				System.out.println("로그인 성공");
				JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다");
				System.out.println(db.nickCheck(uid));
				
				if (client == null) {
		            client = new ClientLobby(db.nickCheck(uid));
		        }
				client.setName(db.nickCheck(uid));
				client.changePanel(new LobbyGui(client));
			} else {
				System.out.println("로그인 실패 > 로그인 정보 불일치");
				JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다");
				ID.setText("");
				PW.setText("");
			}
		}
	}
	
	public int[] getFrameSize() {
		int size[] = new int[2];
		size[0] = 405;
		size[1] = 325;
		return size;
	}
	
	public static void main(String[] args) {
		new LoginPanel();
	}
	
	public String getInputText() {
		return null;
	}

	public JList getJList() {
		return null;
	}

	public void setTextToInput(String string) {
	}
	public void setTextToLogWindow(String str) {
	}
	public void setTotalUser(Vector<String> userList) {
	}
	public void setUserList(Vector<String> userList) {
	}
	public void setUserListFrame(UserListFrame userListFrame) {
	}
	public void showMessageBox(String sender, String message, boolean option) {
	}
	public void unShow() {
	}
	public void setPanel(PanelInterface panel) {
	}
	public void setPanel(GameLobbyInter panel) {
	}
	public void setPanel(LobbyGuiInter panel) {
	}
	public void setPanel(RoomGuiInter panel) {
	}
}
