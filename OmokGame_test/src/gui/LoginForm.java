package gui;

import gameClient.ClientLobby;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import protocolData.Database;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm extends JFrame {
	private static final long serialVersionUID = 0L;
	
	private Container cp = this.getContentPane();
	
	private JPanel panel;
	private JLabel idLabel;
    private JLabel passLabel;
	private JTextField inputId;
	private JPasswordField inputPass;
	private JButton loginButton;
	private JButton joinButton;
//	private JButton changePWButton;
	
	Database db = new Database();
	
	@SuppressWarnings("serial")
	public LoginForm() {
		panel = new JPanel() {};
//			public void paint(Graphics g) {
//				ImageIcon icon = new ImageIcon(
//						URLGetter.getResource("image/loginForm.jpg"));
//				g.drawImage(icon.getImage(),0,0,null,null);
//				this.paintComponents(g);
//			}
//		};
		
		idLabel = new JLabel("아이디");
		passLabel = new JLabel("비밀번호");
		inputId = new JTextField(7);
		inputPass = new JPasswordField(7);
		loginButton = new JButton("로그인");
		joinButton = new JButton("회원가입");
//		changePWButton = new JButton("비밀번호 변경");
		
//		ButtonListener bl = new ButtonListener();
		
//		loginButton.addActionListener(bl);
//		joinButton.addActionListener(bl);
		
		panel.add(idLabel);
		panel.add(inputId);
		panel.add(passLabel);
		panel.add(inputPass);
		panel.add(loginButton);
		panel.add(joinButton);
//		panel.add(changePWButton);
		
		idLabel.setBounds(60,175,70,25);
		inputId.setBounds(130,175,100,25);
		passLabel.setBounds(60, 205, 70, 25);
		inputPass.setBounds(130,205,100,25);
		loginButton.setBounds(255, 175, 90, 25);
		joinButton.setBounds(255, 205, 90, 25);
//		changePWButton.setBounds(255, 235, 70, 25);
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputId.equals("admin")&&inputPass.equals("admin")) {
					dispose();
					new ManagerFrame();
				}
				else if(!inputId.getText().equals("")) {
					new ClientLobby(inputId.getText());
					dispose();
				}
			}
		});
		
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUpFrame signUpFrame = new SignUpFrame();
				signUpFrame.setVisible(true);
				dispose();
			}
		});
		
		/*
		changePWButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePWFrame changePW = new changePWFrame();
				changePW.setVisible(true);
			}
		});
		*/
		
		panel.setLayout(null);
		
		cp.add(panel);
		
		JLabel lblNewLabel = new JLabel("오목 게임");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("던파 비트비트체 v2", Font.PLAIN, 49));
		lblNewLabel.setBounds(93, 63, 213, 55);
		panel.add(lblNewLabel);
		
		JButton IDSearchButton = new JButton("ID 찾기");
		IDSearchButton.setBounds(93, 240, 91, 23);
		panel.add(IDSearchButton);
		
		JButton PWSearchButton = new JButton("PW 찾기");
		PWSearchButton.setBounds(195, 240, 91, 23);
		panel.add(PWSearchButton);
		
		JLabel backLabel = new JLabel("");
		backLabel.setIcon(new ImageIcon("C:\\Users\\wlgus\\Downloads\\sunrise-1030592_1280.jpg"));
		backLabel.setBounds(-29, -42, 492, 345);
		panel.add(backLabel);
		
		this.setSize(405,325);
		this.setVisible(true);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
//	class ButtonListener implements ActionListener{
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			JButton b = (JButton)e.getSource();
//			
//			/* TextField에 입력된 아이디와 비밀번호를 변수에 초기화 */
//			String uid = inputId.getText();
//			String upass = "";
//			for(int i=0; i < inputPass.getPassword().length; i++) {
//				upass = upass + inputPass.getPassword()[i];
//			}
//			
//			/* 회원가입 버튼 이벤트 */
//			if(b.getText().equals("회원가입")) {
////				db.jf.setVisible(true);
//			}
//			
//			
//			/* 로그인 버튼 이벤트 */
//			else if(b.getText().equals("로그인")) {
//				if(uid.equals("") || upass.equals("")) {
//					JOptionPane.showMessageDialog(null, "아이디와 비밀번호 모두 입력해주세요", "로그인 실패", JOptionPane.ERROR_MESSAGE);
//					System.out.println("로그인 실패 > 로그인 정보 미입력");
//				}
//				
//				else if(uid != null && upass != null) {
//					if(db.logincheck(uid, upass)) {	//이 부분이 데이터베이스에 접속해 로그인 정보를 확인하는 부분이다.
//						System.out.println("로그인 성공");
//						JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다");
//						System.out.println(db.nickCheck(uid));
//						new ClientLobby(db.nickCheck(uid));
//						setVisible(false);
//						
//					} else {
//						System.out.println("로그인 실패 > 로그인 정보 불일치");
//						JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다");
//						inputId.setText("");
//						inputPass.setText("");
//					}
//				}
//			}
//		}
//	}
	
	public static void main(String[] args) {
		new LoginForm();
	}
}
