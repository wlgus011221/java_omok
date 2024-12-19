package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import protocolData.DBConnect;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JToggleButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignUpFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nicknameField;
	private JTextField idField;
	private JPasswordField pwField;
	private JTextField emailField;
	private JPasswordField repwField;
	private static JTextField zipNoField;
	private static JTextField addressField;
	private static JTextField address2Field;
	private static JTextField extraAddressField;
	private JTextField phone1Field;
	private JTextField phone2Field;
	private JTextField phone3Field;
	private JTextField nameField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	// 이미지 바이트 배열을 저장할 변수
	byte[] imageBytes = null;
	
	// 중복 확인 상태를 나타내는 변수
	private boolean isNickNameChecked = false;
	private boolean isIDChecked = true;
	
	DBConnect db = new DBConnect();
	private JTextField domainField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpFrame frame = new SignUpFrame();
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
	public SignUpFrame() {
		setTitle("Sign Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("닉네임");
		lblNewLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel.setBounds(30, 37, 67, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("아이디");
		lblNewLabel_1.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(30, 79, 50, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("비밀번호");
		lblNewLabel_2.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(30, 123, 67, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("이메일");
		lblNewLabel_3.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(30, 392, 50, 15);
		contentPane.add(lblNewLabel_3);
		
		nicknameField = new JTextField();
		nicknameField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		nicknameField.setBounds(122, 37, 216, 21);
		contentPane.add(nicknameField);
		nicknameField.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("비밀번호 재확인");
		lblNewLabel_4.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(30, 180, 108, 15);
		contentPane.add(lblNewLabel_4);
		
		repwField = new JPasswordField();
		repwField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		repwField.setBounds(122, 177, 216, 21);
		contentPane.add(repwField);
		
		JLabel lblNewLabel_5 = new JLabel("우편번호");
		lblNewLabel_5.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_5.setBounds(30, 440, 50, 15);
		contentPane.add(lblNewLabel_5);
		
		idField = new JTextField();
		idField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		idField.setBounds(122, 78, 216, 21);
		contentPane.add(idField);
		idField.setColumns(10);
		
		
		JLabel pwStrengthLabel = new JLabel("비밀번호 강도 : ");
		pwStrengthLabel.setFont(new Font("한국기계연구원_Light", Font.PLAIN, 11));
		pwStrengthLabel.setBounds(30, 148, 308, 15);
		contentPane.add(pwStrengthLabel);
		
		pwField = new JPasswordField();
		pwField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		pwField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				char[] passwordChars = pwField.getPassword();
				String password = new String(passwordChars);
				String pwStrength = isValidPassword(password);
				pwStrengthLabel.setText("비밀번호 강도 : " + pwStrength);
				if (pwStrength.equals("안전한 비밀번호입니다.")) {
					pwStrengthLabel.setForeground(new Color(47, 157, 39));
				} else {
					pwStrengthLabel.setForeground(new Color(255, 0, 0));
				}
			}
		});
		
		pwField.setBounds(122, 121, 216, 21);
		contentPane.add(pwField);
		
		emailField = new JTextField();
		emailField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		emailField.setBounds(122, 389, 96, 21);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		zipNoField = new JTextField();
		zipNoField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		zipNoField.setText("우편번호");
		zipNoField.setToolTipText("");
		zipNoField.setBounds(122, 437, 114, 21);
		contentPane.add(zipNoField);
		zipNoField.setColumns(10);
		
		JButton addressSearchButton = new JButton("우편번호 찾기");
		addressSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddressSearchFrame addressFrame = new AddressSearchFrame();
				addressFrame.setVisible(true);
			}
		});
		addressSearchButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		addressSearchButton.setBounds(248, 435, 114, 23);
		contentPane.add(addressSearchButton);
		
		addressField = new JTextField();
		addressField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		addressField.setText("도로명주소");
		addressField.setBounds(122, 468, 451, 21);
		contentPane.add(addressField);
		addressField.setColumns(10);
		
		address2Field = new JTextField();
		address2Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		address2Field.setText("상세주소");
		address2Field.setBounds(122, 499, 216, 21);
		contentPane.add(address2Field);
		address2Field.setColumns(10);
		
		extraAddressField = new JTextField();
		extraAddressField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		extraAddressField.setText("참고항목");
		extraAddressField.setBounds(350, 499, 223, 21);
		contentPane.add(extraAddressField);
		extraAddressField.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("전화번호");
		lblNewLabel_6.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_6.setBounds(30, 307, 50, 15);
		contentPane.add(lblNewLabel_6);
		
		phone1Field = new JTextField();
		phone1Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		phone1Field.setText("010");
		phone1Field.setBounds(122, 304, 67, 21);
		contentPane.add(phone1Field);
		phone1Field.setColumns(10);
		
		phone2Field = new JTextField();
		phone2Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		phone2Field.setBounds(197, 304, 67, 21);
		contentPane.add(phone2Field);
		phone2Field.setColumns(10);
		
		phone3Field = new JTextField();
		phone3Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		phone3Field.setBounds(271, 304, 67, 21);
		contentPane.add(phone3Field);
		phone3Field.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("성별");
		lblNewLabel_7.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_7.setBounds(30, 352, 50, 15);
		contentPane.add(lblNewLabel_7);
		
		JRadioButton manRadioButton = new JRadioButton("남");
		manRadioButton.setFont(new Font("한국기계연구원_Light", Font.PLAIN, 12));
		buttonGroup.add(manRadioButton);
		manRadioButton.setSelected(true);
		manRadioButton.setBounds(122, 348, 50, 23);
		contentPane.add(manRadioButton);
		
		JRadioButton womanRadioButton = new JRadioButton("여");
		womanRadioButton.setFont(new Font("한국기계연구원_Light", Font.PLAIN, 12));
		buttonGroup.add(womanRadioButton);
		womanRadioButton.setBounds(197, 348, 55, 23);
		contentPane.add(womanRadioButton);
		
		JLabel lblNewLabel_8 = new JLabel("영문/숫자/특수문자 혼용하여 8자리 이상");
		lblNewLabel_8.setFont(new Font("한국기계연구원_Light", Font.PLAIN, 12));
		lblNewLabel_8.setBounds(350, 123, 243, 15);
		contentPane.add(lblNewLabel_8);
		
		JPanel panel = new JPanel();
		panel.setBounds(393, 161, 180, 158);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_4_1 = new JLabel("성함");
		lblNewLabel_4_1.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_4_1.setBounds(30, 223, 53, 15);
		contentPane.add(lblNewLabel_4_1);
		
		nameField = new JTextField();
		nameField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		nameField.setBounds(122, 220, 216, 21);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("생년월일");
		lblNewLabel_3_1.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_3_1.setBounds(30, 263, 50, 15);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_9 = new JLabel("@");
		lblNewLabel_9.setBounds(223, 391, 23, 15);
		contentPane.add(lblNewLabel_9);
		
		domainField = new JTextField();
		domainField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		domainField.setBounds(242, 388, 96, 21);
		contentPane.add(domainField);
		domainField.setColumns(10);
		
		JComboBox domainBox = new JComboBox();
		domainBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		domainBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					String selectedDomain = (String) domainBox.getSelectedItem();
					if(selectedDomain.equals("직접입력")) {
						domainField.setText("");
						domainField.setEditable(true);
					} else {
						domainField.setText(selectedDomain);
						domainField.setEditable(false);
					}
				}
			}
		});
		domainBox.setModel(new DefaultComboBoxModel(new String[] {"직접입력", "naver.com", "daum.net", "gmail.com", "hanmail.net", "nate.com", "yahoo.com"}));
		domainBox.setBounds(345, 388, 96, 21);
		contentPane.add(domainBox);
		
		JComboBox yearBox = new JComboBox();
		yearBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		yearBox.setModel(new DefaultComboBoxModel(new String[] {"1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013"}));
		yearBox.setBounds(122, 258, 67, 23);
		contentPane.add(yearBox);
		
		JComboBox monthBox = new JComboBox();
		monthBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		monthBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		monthBox.setBounds(197, 258, 67, 23);
		contentPane.add(monthBox);
		
		JComboBox dayBox = new JComboBox();
		dayBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		dayBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dayBox.setBounds(271, 258, 67, 23);
		contentPane.add(dayBox);
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // 프로그램 종료 방지

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // SignUpFrame 창 닫기
            }
        });
        
        // 중복확인 기능
     	JButton nicknameCheckButton = new JButton("중복확인");
     	nicknameCheckButton.addActionListener(new ActionListener() {
     		public void actionPerformed(ActionEvent e) {
     			// 사용자가 입력한 UserName 가져오기
     			String nickname = nicknameField.getText();
     			Connection conn = null;
     			try {
//     				Class.forName("oracle.jdbc.OracleDriver");
//     				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "omok123", "omok123");

     				conn = db.makeConnection();
     				// 데이터베이스에서 중복 확인을 위한 쿼리 작성
     				String checkQuery = "SELECT COUNT(*) FROM users WHERE nickName = ?";
     				PreparedStatement pstmt = conn.prepareStatement(checkQuery);
     				pstmt.setString(1, nickname);
     				ResultSet resultSet = pstmt.executeQuery();

     				if (resultSet.next()) {
     					int count = resultSet.getInt(1);
     					if(!nickname.equals("admin")) {
     						if (count > 0) {
     							// 중복된 UserName이 존재함
     							JOptionPane.showMessageDialog(null, "사용중인 닉네임입니다.");
     						} else {
     							// 중복 없음
     							JOptionPane.showMessageDialog(null, "사용 가능한 닉네임입니다.");
     							// 중복 확인 완료, isUserNameChecked를 true로 설정
     							isNickNameChecked = true;
                             }
     					}else {
     						JOptionPane.showMessageDialog(null, "사용 불가능한 닉네임입니다.");
     					}
     				}
     				conn.close();
     			} catch (SQLException ex) {
     				ex.printStackTrace();
     				JOptionPane.showMessageDialog(null, "중복 확인에 실패했습니다.");
     			}
     		}
     	});
     	
     	JButton IDCheckButton = new JButton("중복확인");
     	IDCheckButton.addActionListener(new ActionListener() {
     		public void actionPerformed(ActionEvent e) {
     			// 사용자가 입력한 ID 가져오기
     			String id = idField.getText();

     			Connection conn = null;
     			try {
//     				Class.forName("oracle.jdbc.OracleDriver");
//     				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "omok123", "omok123");

     				conn = db.makeConnection();
     				// 데이터베이스에서 중복 확인을 위한 쿼리 작성
     				String checkQuery = "SELECT COUNT(*) FROM users WHERE id = ?";
     				PreparedStatement pstmt = conn.prepareStatement(checkQuery);
     				pstmt.setString(1, id);
     				ResultSet resultSet = pstmt.executeQuery();

     				if (resultSet.next()) {
     					int count = resultSet.getInt(1);
     					if(!id.equals("admin")) {
     						if (count > 0) {
     							// 중복된 UserName이 존재함
     							JOptionPane.showMessageDialog(null, "이미 사용중인 ID입니다.");
     						} else {
     							// 중복 없음
     							JOptionPane.showMessageDialog(null, "사용 가능한 ID입니다.");
     							// 중복 확인 완료, isUserNameChecked를 true로 설정
     							isIDChecked = true;
     						}
     					} else {
     						JOptionPane.showMessageDialog(null, "사용 불가한 ID입니다.");
     					}
     				}
     				conn.close();
     			} catch (SQLException ex) {
     				ex.printStackTrace();
     				JOptionPane.showMessageDialog(null, "중복 확인에 실패했습니다.");
     			}
     		}
     	});
     	IDCheckButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
     	IDCheckButton.setBounds(350, 75, 91, 23);
     	contentPane.add(IDCheckButton);
     		
     	nicknameCheckButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
     	nicknameCheckButton.setBounds(350, 36, 91, 23);
     	contentPane.add(nicknameCheckButton);
     	
		ImageIcon basic = new ImageIcon("image/image.jpg");
		JLabel imageLabel = new JLabel(basic);
		imageLabel.setBackground(new Color(255, 255, 255));
		imageLabel.setForeground(new Color(0, 0, 0));
		imageLabel.setBounds(0, 0, 180, 158);
		panel.add(imageLabel);
		
		JButton imageUploadButton = new JButton("이미지업로드");
		imageUploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                    	BufferedImage img = ImageIO.read(selectedFile);
                    	
                    	// 라벨 크기 구하기
                    	int labelWidth = imageLabel.getWidth();
                    	int labelHeight = imageLabel.getHeight();
                    	
                    	int newWidth = labelWidth;
                    	int newHeight = labelHeight;
                    	
                    	// 라벨보다 이미지가 크면 크기 조정
                    	if(img.getWidth()>labelWidth||img.getHeight()>labelHeight) {
                    		double widthRatio = (double)labelWidth/img.getWidth();
                    		double heightRatio = (double)labelHeight/img.getHeight();
                    		double scaleFactor = Math.min(widthRatio, heightRatio);
                    		
                    		newWidth = (int)(img.getWidth()*scaleFactor);
                    		newHeight = (int)(img.getHeight()*scaleFactor);
                    	}
                    	
                    	Image scaledImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    	ImageIcon imageIcon = new ImageIcon(scaledImage);
                    	imageLabel.setIcon(imageIcon);
                    	
                    	// 이미지 파일을 바이트 배열로 읽어오기
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(img,  "jpg",  baos);
                        imageBytes = baos.toByteArray();
                    } catch(IOException ex) {
                    	ex.printStackTrace();
                    }        
                }
			}
		});
		imageUploadButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		imageUploadButton.setBounds(447, 329, 126, 23);
		contentPane.add(imageUploadButton);
		
		JButton cancelButton = new JButton("취소");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		cancelButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		cancelButton.setBounds(214, 550, 81, 23);
		contentPane.add(cancelButton);
		
		// DB에 저장
		JButton OKNewButton = new JButton("확인");
		OKNewButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		OKNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 닉네임이 빈칸인 경우
				if (nicknameField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "닉네임을 입력해주세요.");
		            nicknameField.requestFocusInWindow();
					return; // 이후 코드 실행 중지
				}
				
				// 아이디가 빈칸인 경우
				if (idField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
		            idField.requestFocusInWindow();
					return; // 이후 코드 실행 중지
				}
				
				// 비밀번호가 빈칸인 경우
				if (new String(pwField.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
		            pwField.requestFocusInWindow();
					return; // 이후 코드 실행 중지
				}
				
				// 비밀번호 재확인이 빈칸인 경우
				if (new String(repwField.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(null, "비밀번호 재확인을 해주세요.");
		            repwField.requestFocusInWindow();
					return; // 이후 코드 실행 중지
				}
				
				// 성함이 빈칸인 경우
				if (nameField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "성함를 입력해주세요.");
		            nameField.requestFocusInWindow();
					return; // 이후 코드 실행 중지
				}
				
				// 이메일이 빈칸인 경우
				if (emailField.getText().equals("")||domainBox.getSelectedItem().equals("직접입력")) {
					JOptionPane.showMessageDialog(null, "이메일을 입력해주세요.");
		            emailField.requestFocusInWindow();
					return; // 이후 코드 실행 중지
				}
				
				// 전화번호가 빈칸인 경우
				if (phone1Field.getText().equals("")||phone2Field.getText().equals("")||phone3Field.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요.");
		            if (phone1Field.getText().equals("")) {
		            	phone1Field.requestFocusInWindow();
		            } else if (phone2Field.getText().equals("")){
		            	phone2Field.requestFocusInWindow();
		            } else if (phone3Field.getText().equals("")) {
		            	phone3Field.requestFocusInWindow();
		            }
					return; // 이후 코드 실행 중지
				}
				
				// 우편번호가 빈칸이거나 "우편번호" 경우
				if (zipNoField.getText().equals("")||zipNoField.getText().equals("우편번호")) {
					JOptionPane.showMessageDialog(null, "우편번호를 찾아주세요.");
					return; // 이후 코드 실행 중지
				}
				
				// 상세주소를 입력하지 않은 경우
				if (address2Field.getText().equals("")||address2Field.getText().equals("상세주소")) {
					JOptionPane.showMessageDialog(null, "상세주소를 입력해주세요.");
		            address2Field.requestFocusInWindow();
					return; // 이후 코드 실행 중지
				}
				
				// 중복 확인을 하지 않은 경우
		        if (!isNickNameChecked) {
		            JOptionPane.showMessageDialog(null, "닉네임 중복을 확인하세요.");
		            return; // 이후 코드 실행 중지
		        }
		        
		        if(!isIDChecked) {
		        	JOptionPane.showMessageDialog(null, "아이디 중복을 확인하세요.");
		            return; // 이후 코드 실행 중지
		        }
		        
		        // 비밀번호가 안전하지 않은 경우
		        char[] passwordChars = pwField.getPassword();
				String password = new String(passwordChars);
				String pwStrength = isValidPassword(password);
		        if (!pwStrength.equals("안전한 비밀번호입니다.")) {
		        	JOptionPane.showMessageDialog(null, "비밀번호를 다시 설정하세요.");
		            return; // 이후 코드 실행 중지
		        }
		        
				// 사용자 입력 가져오기
                String nickname = nicknameField.getText();
                String id = idField.getText();
                // char[] password = PW.getPassword();
                char[] rePassword = repwField.getPassword();
                String name = nameField.getText();
                String birth = yearBox.getSelectedItem() + "." + monthBox.getSelectedItem() + "." + dayBox.getSelectedItem();
                String email = emailField.getText() + "@" + domainBox.getSelectedItem();
                String phone = phone1Field.getText() + phone2Field.getText() + phone3Field.getText();
                ButtonModel gender = buttonGroup.getSelection();
                String selectedValue = "";
                if(gender == manRadioButton.getModel()) {
                	selectedValue = "남";
                } else {
                	selectedValue = "여";
                }
                String zipNo = zipNoField.getText();
                String address = addressField.getText() + ", " + address2Field.getText() + " (" + extraAddressField.getText() + ")";
                
                // 비밀번호와 비밀번호 재입력이 일치하는 경우
                if(new String(password).equals(new String(rePassword))) {
                	Connection conn = null;
                    // 데이터베이스 연결 설정
                    try {
//                        Class.forName("oracle.jdbc.OracleDriver");
//                    	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "omok", "omok");

                    	conn = db.makeConnection();
                    	
                        // SQL 쿼리 작성
                        String sql = "INSERT INTO users (id, nickName, userName, pw, email, phone, gender, birth, zipNo, address, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, id);
                        pstmt.setString(2, nickname);
                        pstmt.setString(3, name);
                        pstmt.setString(4, new String(password));
                        pstmt.setString(5, email);
                        pstmt.setString(6, phone);
                        pstmt.setString(7, selectedValue);
                        pstmt.setString(8, birth);
                        pstmt.setString(9, zipNo);
                        pstmt.setString(10, address);
                        pstmt.setBytes(11, imageBytes); // 이미지를 바이트 배열로 저장

                        // SQL 쿼리 실행
                        int rows = pstmt.executeUpdate();
                        if (rows > 0) {
                            // 성공 메시지 표시
                            JOptionPane.showMessageDialog(null, "회원 가입이 완료되었습니다.");
            				dispose();
                        } else {
                            // 실패 메시지 표시
                            JOptionPane.showMessageDialog(null, "회원 가입에 실패했습니다.");
                        }
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // 실패 메시지 표시
                        JOptionPane.showMessageDialog(null, "회원 가입에 실패했습니다.");
                    }
                } else {
                	JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다. 다시 입력하세요.");
                }
			}
		});
		
		OKNewButton.setBounds(319, 550, 81, 23);
		contentPane.add(OKNewButton);
		
		ImageIcon originalIcon = new ImageIcon("image/eye.png");
		Image originalImage = originalIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(23, 18, java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
		
		ImageIcon originalIcon2 = new ImageIcon("image/eye-crossed.png");
		Image originalImage2 = originalIcon2.getImage();
		Image resizedImage2 = originalImage2.getScaledInstance(23, 18, java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon2 = new ImageIcon(resizedImage2);
		
		JLabel showPwButton = new JLabel("");
		showPwButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (repwField.getEchoChar() == '\u2022') {
		            repwField.setEchoChar((char) 0); // 비밀번호 표시
		            showPwButton.setIcon(resizedIcon2);
		        } else {
		            repwField.setEchoChar('\u2022'); // 비밀번호 숨기기
		            showPwButton.setIcon(resizedIcon);
		        }
			}
		});
		showPwButton.setIcon(resizedIcon);
		showPwButton.setBounds(339, 177, 23, 18);
		contentPane.add(showPwButton);
	}

	// 비밀번호 안전성 검사
	private String isValidPassword(String password) {
		// 유효성 검사 로직을 수행
		// 입력된 패스워드가 유효하면 "Password is valid"를 반환하고, 그렇지 않으면 에러 메시지를 반환

		// 라벨 변경을 위한 변수 선언
		String str = "";

		// 최소 8자, 최대 20자 상수 선언
		final int MIN = 8;
		final int MAX = 20;

        // 영어, 숫자, 특수문자 포함한 MIN to MAX 글자 정규식
        final String REGEX = "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{" + MIN + "," + MAX + "})$";

        // 3자리 연속 문자 정규식
        final String SAMEPT = "(\\w)\\1\\1";
        
        // 공백 문자 정규식
        final String BLANKPT = "(\\s)";

        // 정규식 검사 객체
        Matcher matcher;

        // 공백 체크
        if (password == null || "".equals(password)) {
            str = "비밀번호를 입력해주세요";
            return str;
        }

		// ASCII 문자 비교를 위한 UpperCase
        String tmpPw = new String(password).toUpperCase();

        // 문자열 길이
        int strLen = tmpPw.length();

        // 글자 길이 체크
        if (strLen > 20 || strLen < 8) {
            str = "글자길이를 8-20자리로 맞춰주세요.";
            return str;
        }

        // 공백 체크
        matcher = Pattern.compile(BLANKPT).matcher(tmpPw);
        if (matcher.find()) {
        	str = "공백이 존재합니다.";
        	return str;
        }

        // 비밀번호 정규식 체크
        matcher = Pattern.compile(REGEX).matcher(tmpPw);
        if (!matcher.find()) {
        	str = "올바른 비밀번호가 아닙니다.";
        	return str;
	    }

        // 동일한 문자 3개 이상 체크
        matcher = Pattern.compile(SAMEPT).matcher(tmpPw);
        if (matcher.find()) {
        	str = "3개 이상 동일한 문자가 존재합니다.";
        	return str;
	    }

        // 연속된 문자 / 숫자 3개 이상 체크
        if (true) {
            // ASCII CHar를 담을 배열 선언
        	int[] tmpArray = new int[strLen];

        	// Make Array
        	for (int i = 0; i < strLen; i++) {
        		tmpArray[i] = tmpPw.charAt(i);
		    }

            // Validation Array
        	for (int i = 0; i < strLen - 2; i++) {
        		// 첫 글자 A-Z / 0-9
		        if ((tmpArray[i] > 47 && tmpArray[i + 2] < 58) || (tmpArray[i] > 64 && tmpArray[i + 2] < 91)) {
		        	// 배열의 연속된 수 검사
		            // 3번째 글자 - 2번째 글자 = 1, 3번째 글자 - 1번째 글자 = 2
		        	if (Math.abs(tmpArray[i + 2] - tmpArray[i + 1]) == 1 && Math.abs(tmpArray[i + 2] - tmpArray[i]) == 2) {
		                char c1 = (char) tmpArray[i];
		                char c2 = (char) tmpArray[i + 1];
		                char c3 = (char) tmpArray[i + 2];

		                str = "연속된 문자/숫자가 존재합니다.";
		                return str;
		                }
		            }
		        }
		        str = "안전한 비밀번호입니다.";
		        return str;
		    }
		    return str;
	}

	public static void setzipNoField(String address) {
		try {
			String zipNo = address.substring(6, 11);
			zipNoField.setText(zipNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setAddressField(String address) {
		try {
			String inputAddress = address.substring(17, address.indexOf("("));
			addressField.setText(inputAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setExtraAddressFiled(String address) {
		try {
			String inputExtraAddress = address.substring(address.indexOf("(")+1, address.indexOf(")"));
			extraAddressField.setText(inputExtraAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
