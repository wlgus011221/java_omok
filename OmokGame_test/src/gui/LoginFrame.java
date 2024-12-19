package gui;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gameClient.Client;
import gameClient.ClientLobby;
import protocolData.Database;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ID;
	private JPasswordField PW;
	Database db = new Database();
	private static ClientLobby client;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame(final ClientLobby client) {
		this.client = client;
		setTitle("Omok");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 418, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel.setBounds(55, 152, 50, 15);
		
		JLabel lblNewLabel_1 = new JLabel("PW");
		lblNewLabel_1.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(55, 191, 50, 15);
		
		JLabel lblNewLabel_2 = new JLabel("오목 게임");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("던파 비트비트체 v2", Font.PLAIN, 49));
		lblNewLabel_2.setBounds(110, 42, 213, 55);
		
		contentPane.setLayout(null);
		contentPane.add(lblNewLabel);
		contentPane.add(lblNewLabel_1);
		contentPane.add(lblNewLabel_2);
		
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
		contentPane.add(ID);
		ID.setColumns(10);
		
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
		contentPane.add(PW);
		
		JButton loginButton = new JButton("로그인");
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
		contentPane.add(loginButton);
		
		JLabel lblNewLabel_3 = new JLabel("새로 오셨나요?");
		lblNewLabel_3.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(165, 218, 111, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel IDSearchLabel = new JLabel("ID 찾기");
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
		contentPane.add(IDSearchLabel);
		
		JLabel lblPw = new JLabel("PW 찾기");
		lblPw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblPw.setForeground(new Color(0, 0, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblPw.setForeground(new Color(255, 255, 255));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				PWSearchFrame frame = new PWSearchFrame();
				frame.setVisible(true);
			}
		});
		lblPw.setForeground(Color.WHITE);
		lblPw.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblPw.setBounds(204, 260, 58, 15);
		contentPane.add(lblPw);
		
		JLabel lblNewLabel_4 = new JLabel("|");
		lblNewLabel_4.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setBounds(191, 260, 50, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblSignUp = new JLabel("회원가입");
		lblSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSignUp.setForeground(new Color(0, 0, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblSignUp.setForeground(new Color(255, 255, 255));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				SignUpFrame frame = new SignUpFrame();
				frame.setVisible(true);
			}
		});
		lblSignUp.setForeground(Color.WHITE);
		lblSignUp.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblSignUp.setBounds(285, 218, 50, 15);
		contentPane.add(lblSignUp);
		
		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("image/loginBg.jpg"));
		backGround.setBounds(-124, -113, 591, 444);
		contentPane.add(backGround);
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
				dispose();
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
}
