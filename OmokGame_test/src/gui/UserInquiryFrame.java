package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import protocolData.DBConnect;
import protocolData.Database;
import javax.swing.ButtonGroup;

public class UserInquiryFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private static String id;
	private static String pw;
	private static String nickName;
	private static String userName;
	private static String email;
	private static String phone;
	private static String gender;
	private static String birth;
	private static String zipNo;
	private static String address;
	byte[] imageBytes = null;
	private JTextField nickNameField;
	private JPasswordField rePwField;
	private JPasswordField pwField;
	private JTextField emailField;
	private JTextField zipNoField;
	private JTextField addressField;
	private JTextField address2Field;
	private JTextField extraAddressField;
	private JTextField phone1Field;
	private JTextField phone2Field;
	private JTextField phone3Field;
	private JTextField userNameField;
	private JTextField domainField;
	private ImageIcon basic = new ImageIcon("image/image.jpg");
    private JLabel profileLabel;
    private boolean isNickNameChecked = false;
    DBConnect dbC = new DBConnect();
    private JLabel IDLabel;
    Database db = new Database();
    private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 UserInquiryFrame frame = new UserInquiryFrame(id, pw, nickName, userName, email, phone, gender, birth, zipNo, address);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param address 
	 * @param zipNo 
	 * @param birth 
	 * @param gender 
	 * @param phone 
	 * @param email 
	 * @param userName 
	 * @param nickName 
	 * @param pw 
	 * @param id 
	 */
	public UserInquiryFrame(String id, String pw, String nickName, String userName, String email, String phone, String gender, String birth, String zipNo, String address) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 610, 643);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("닉네임");
		lblNewLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel.setBounds(12, 102, 67, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("아이디");
		lblNewLabel_1.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(12, 65, 50, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("비밀번호");
		lblNewLabel_2.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(12, 146, 67, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("이메일");
		lblNewLabel_3.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(12, 415, 50, 15);
		contentPane.add(lblNewLabel_3);
		
		nickNameField = new JTextField(nickName);
		nickNameField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		nickNameField.setColumns(10);
		nickNameField.setBounds(104, 102, 216, 21);
		contentPane.add(nickNameField);
		
		JLabel lblNewLabel_4 = new JLabel("비밀번호 재확인");
		lblNewLabel_4.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(12, 203, 108, 15);
		contentPane.add(lblNewLabel_4);
		
		rePwField = new JPasswordField();
		rePwField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		rePwField.setBounds(104, 200, 216, 21);
		contentPane.add(rePwField);
		
		JLabel lblNewLabel_5 = new JLabel("우편번호");
		lblNewLabel_5.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_5.setBounds(12, 463, 50, 15);
		contentPane.add(lblNewLabel_5);
		
		pwField = new JPasswordField(pw);
		pwField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		pwField.setBounds(104, 144, 216, 21);
		contentPane.add(pwField);
		
		JLabel pwStrengthLabel = new JLabel("비밀번호 강도 : ");
		pwStrengthLabel.setFont(new Font("한국기계연구원_Light", Font.PLAIN, 11));
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
		pwStrengthLabel.setBounds(12, 171, 308, 15);
		contentPane.add(pwStrengthLabel);
		
		emailField = new JTextField(email.substring(0, email.indexOf("@")));
		emailField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		emailField.setColumns(10);
		emailField.setBounds(104, 412, 96, 21);
		contentPane.add(emailField);
		
		zipNoField = new JTextField(zipNo);
		zipNoField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		zipNoField.setColumns(10);
		zipNoField.setBounds(104, 460, 114, 21);
		contentPane.add(zipNoField);
		
		JButton addressSearchButton = new JButton("우편번호 찾기");
		addressSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddressSearchFrame frame = new AddressSearchFrame();
				frame.setVisible(true);
			}
		});
		addressSearchButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		addressSearchButton.setBounds(230, 458, 114, 23);
		contentPane.add(addressSearchButton);
        
		addressField = new JTextField(address.substring(0, address.indexOf(",")));
		addressField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		addressField.setColumns(10);
		addressField.setBounds(104, 491, 451, 21);
		contentPane.add(addressField);
		
		address2Field = new JTextField(address.substring(address.indexOf(",")+2, address.indexOf("(")));
		address2Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		address2Field.setColumns(10);
		address2Field.setBounds(104, 522, 216, 21);
		contentPane.add(address2Field);
		
		extraAddressField = new JTextField(address.substring(address.indexOf("(")+1, address.indexOf(")")));
		extraAddressField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		extraAddressField.setColumns(10);
		extraAddressField.setBounds(332, 522, 223, 21);
		contentPane.add(extraAddressField);
		
		JLabel lblNewLabel_6 = new JLabel("전화번호");
		lblNewLabel_6.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_6.setBounds(12, 330, 50, 15);
		contentPane.add(lblNewLabel_6);
		
		phone1Field = new JTextField(phone.substring(0, 3));
		phone1Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		phone1Field.setColumns(10);
		phone1Field.setBounds(104, 327, 67, 21);
		contentPane.add(phone1Field);
		
		phone2Field = new JTextField(phone.substring(3, 7));
		phone2Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		phone2Field.setColumns(10);
		phone2Field.setBounds(179, 327, 67, 21);
		contentPane.add(phone2Field);
		
		phone3Field = new JTextField(phone.substring(7, 11));
		phone3Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		phone3Field.setColumns(10);
		phone3Field.setBounds(253, 327, 67, 21);
		contentPane.add(phone3Field);
		
		JLabel lblNewLabel_7 = new JLabel("성별");
		lblNewLabel_7.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_7.setBounds(12, 375, 50, 15);
		contentPane.add(lblNewLabel_7);
		
		JRadioButton manRadioButton = new JRadioButton("남");
		buttonGroup.add(manRadioButton);
		manRadioButton.setFont(new Font("한국기계연구원_Light", Font.PLAIN, 12));
		manRadioButton.setBounds(104, 371, 50, 23);
		contentPane.add(manRadioButton);
		
		JRadioButton womanRadioButton = new JRadioButton("여");
		buttonGroup.add(womanRadioButton);
		womanRadioButton.setFont(new Font("한국기계연구원_Light", Font.PLAIN, 12));
		womanRadioButton.setBounds(179, 371, 55, 23);
		contentPane.add(womanRadioButton);
		
		if (gender.equals("남")) {
			manRadioButton.setSelected(true);
		} else if(gender.equals("여")) {
			womanRadioButton.setSelected(true);
		}
		
		JLabel lblNewLabel_8 = new JLabel("영문/숫자/특수문자 혼용하여 8자리 이상");
		lblNewLabel_8.setFont(new Font("한국기계연구원_Light", Font.PLAIN, 12));
		lblNewLabel_8.setBounds(351, 146, 233, 15);
		contentPane.add(lblNewLabel_8);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(375, 184, 180, 158);
		contentPane.add(panel);
		
		profileLabel = new JLabel(basic);
		profileLabel.setForeground(Color.BLACK);
		profileLabel.setBackground(Color.WHITE);
		profileLabel.setBounds(0, 0, 180, 158);
		loadImageFromDatabase(id);
		panel.add(profileLabel);
		
		JLabel lblNewLabel_4_1 = new JLabel("성함");
		lblNewLabel_4_1.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_4_1.setBounds(12, 246, 53, 15);
		contentPane.add(lblNewLabel_4_1);
		
		userNameField = new JTextField(userName);
		userNameField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		userNameField.setColumns(10);
		userNameField.setBounds(104, 243, 216, 21);
		contentPane.add(userNameField);
		
		JLabel lblNewLabel_3_1 = new JLabel("생년월일");
		lblNewLabel_3_1.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 11));
		lblNewLabel_3_1.setBounds(12, 286, 50, 15);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_9 = new JLabel("@");
		lblNewLabel_9.setBounds(205, 414, 23, 15);
		contentPane.add(lblNewLabel_9);
		
		domainField = new JTextField();
		domainField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		domainField.setColumns(10);
		domainField.setBounds(224, 411, 96, 21);
		contentPane.add(domainField);
		
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
		domainBox.setBounds(327, 411, 96, 21);
		contentPane.add(domainBox);
		String userDomain = email.substring(email.indexOf("@")+1);
		for(int i=0; i<domainBox.getItemCount(); i++) {
			if(domainBox.getItemAt(i).equals(userDomain)) {
				domainBox.setSelectedIndex(i);
				break;
			}
		}
		
		JComboBox yearBox = new JComboBox();
		yearBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		yearBox.setModel(new DefaultComboBoxModel(new String[] {"1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013"}));
		yearBox.setBounds(104, 281, 67, 23);
		contentPane.add(yearBox);
		
		String userYear = birth.split("\\.")[0];
		String userMonth = birth.split("\\.")[1];
		String userDay = birth.split("\\.")[2];
		
		for(int i=0; i<yearBox.getItemCount(); i++) {
			if(yearBox.getItemAt(i).equals(userYear)){
				yearBox.setSelectedIndex(i);
				break;
			}
		}
		
		JComboBox monthBox = new JComboBox();
		monthBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		monthBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		monthBox.setBounds(179, 281, 67, 23);
		contentPane.add(monthBox);
		
		for(int i=0; i<monthBox.getItemCount(); i++) {
			if(monthBox.getItemAt(i).equals(userMonth)){
				monthBox.setSelectedIndex(i);
				break;
			}
		}
		
		JComboBox dayBox = new JComboBox();
		dayBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		dayBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dayBox.setBounds(253, 281, 67, 23);
		contentPane.add(dayBox);
		
		for(int i=0; i<dayBox.getItemCount(); i++) {
			if(dayBox.getItemAt(i).equals(userDay)){
				dayBox.setSelectedIndex(i);
				break;
			}
		}
		
		JButton nicknameCheckButton = new JButton("중복확인");
		nicknameCheckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
     			String nickname = nickNameField.getText();
     			Connection conn = null;
     			try {
     				conn = dbC.makeConnection();
     				// 데이터베이스에서 중복 확인을 위한 쿼리 작성
     				String checkQuery = "SELECT COUNT(*) FROM users WHERE nickName = ?";
     				PreparedStatement pstmt = conn.prepareStatement(checkQuery);
     				pstmt.setString(1, nickname);
     				ResultSet resultSet = pstmt.executeQuery();

     				if (resultSet.next()) {
     					int count = resultSet.getInt(1);
     					if(!nickname.equals("admin")) {
     						if(!nickname.equals(nickName)) {
     							if (count > 0) {
         							// 중복된 UserName이 존재함
         							JOptionPane.showMessageDialog(null, "사용중인 닉네임입니다.");
         						} else {
         							// 중복 없음
         							JOptionPane.showMessageDialog(null, "사용 가능한 닉네임입니다.");
         							// 중복 확인 완료, isUserNameChecked를 true로 설정
         							isNickNameChecked = true;
                                 }
     						} else {
     							JOptionPane.showMessageDialog(null, "기존 닉네임입니다.");
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
		nicknameCheckButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		nicknameCheckButton.setBounds(332, 101, 91, 23);
		contentPane.add(nicknameCheckButton);
		
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
                    	int labelWidth = profileLabel.getWidth();
                    	int labelHeight = profileLabel.getHeight();
                    	
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
                    	profileLabel.setIcon(imageIcon);
                    	
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
		imageUploadButton.setBounds(429, 352, 126, 23);
		contentPane.add(imageUploadButton);
		
		JButton updateButton = new JButton("수정");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentNickName = nickNameField.getText();
				String currentPassword = new String(pwField.getPassword());
				String oldPassword = db.loadPassword(id);
				String currentUserName = userNameField.getText();
				
				// 사용자 입력 가져오기
                String nickname = nickNameField.getText();
                String name = userNameField.getText();
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
				
				Connection conn = null;
				// 중복 확인을 하지 않은 경우
		        if (!isNickNameChecked && currentNickName.equals(nickName)) {
		            JOptionPane.showMessageDialog(null, "닉네임 중복을 확인하세요.");
		            return; // 이후 코드 실행 중지
		        }
		      
		        // 비밀번호가 안전하지 않은 경우
				String pwStrength = isValidPassword(currentPassword);
		        if (!pwStrength.equals("안전한 비밀번호입니다.")) {
		        	JOptionPane.showMessageDialog(null, "비밀번호를 다시 설정하세요.");
		            return; // 이후 코드 실행 중지
		        }
		        
		        // 비밀번호와 비밀번호 재입력이 일치하지 않는 경우
		        if(!currentPassword.equals(new String(rePwField.getPassword()))) {
		        	JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
		            return; // 이후 코드 실행 중지
		        }
		        
		        // 비밀번호가 기존 비밀번호와 같은 경우
		        if (currentPassword.equals(oldPassword)) {
		            JOptionPane.showMessageDialog(null, "기존 비밀번호와 같습니다. 수정하는 것을 추천합니다.");
		        }
		        
				try {
                    Class.forName("oracle.jdbc.OracleDriver");
                    conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "omok123", "omok123");

                    String sql = "UPDATE users "
        					+ "SET nickName=?, userName=?, pw=?, email=?, phone=?, gender=?, birth=?, zipNo=?, address=?, image=? "
        					+ "WHERE id=?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, currentNickName);
                    pstmt.setString(2, currentUserName);
                    pstmt.setString(3, currentPassword);
                    pstmt.setString(4, email);
                    pstmt.setString(5, phone);
                    pstmt.setString(6, selectedValue);
                    pstmt.setString(7, birth);
                    pstmt.setString(8, zipNoField.getText());
                    pstmt.setString(9, address);
                    if (imageBytes == null) {
                        // 이미지를 업로드하지 않은 경우
                        imageBytes = db.loadProfileImage(id);
                        if(imageBytes == null) {
                            // 기존 이미지 데이터가 없는 경우
                            pstmt.setNull(10, java.sql.Types.BLOB);
                        } else {
                            // 기존 이미지 데이터를 그대로 사용
                            pstmt.setBytes(10, imageBytes);
                        }
                    } else {
                        // 새로 업로드한 이미지를 사용
                        pstmt.setBytes(10, imageBytes);
                    }
                    pstmt.setString(11, id);
                    ResultSet resultSet = pstmt.executeQuery();
                    conn.close();
                    
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "수정에 실패했습니다.");
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
			}
		});
		updateButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		updateButton.setBounds(161, 573, 81, 23);
		contentPane.add(updateButton);
		
		JButton OKNewButton = new JButton("확인");
		OKNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		OKNewButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		OKNewButton.setBounds(359, 573, 81, 23);
		contentPane.add(OKNewButton);
		
		JLabel lblNewLabel_10 = new JLabel("상세 조회");
		lblNewLabel_10.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 14));
		lblNewLabel_10.setBounds(12, 21, 159, 15);
		contentPane.add(lblNewLabel_10);
		
		JButton btnNewButton = new JButton("삭제");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(db.deleteUser(id)!=0) {
					JOptionPane.showMessageDialog(null, "삭제했습니다.");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "삭제 실패했습니다.");
				}
			}
		});
		btnNewButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		btnNewButton.setBounds(260, 573, 81, 23);
		contentPane.add(btnNewButton);
		
		JLabel IDLabel = new JLabel(id);
		IDLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 14));
		IDLabel.setBounds(104, 65, 180, 15);
		contentPane.add(IDLabel);
		
		ImageIcon originalIcon = new ImageIcon("image/eye.png");
		Image originalImage = originalIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(23, 18, java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
		
		ImageIcon originalIcon2 = new ImageIcon("image/eye-crossed.png");
		Image originalImage2 = originalIcon2.getImage();
		Image resizedImage2 = originalImage2.getScaledInstance(23, 18, java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon2 = new ImageIcon(resizedImage2);
		
		JLabel showPwLabel = new JLabel("");
		showPwLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (pwField.getEchoChar() == '\u2022') {
		            pwField.setEchoChar((char) 0); // 비밀번호 표시
		            showPwLabel.setIcon(resizedIcon2);
		        } else {
		            pwField.setEchoChar('\u2022'); // 비밀번호 숨기기
		            showPwLabel.setIcon(resizedIcon);
		        }
			}
		});
		showPwLabel.setIcon(resizedIcon);
		showPwLabel.setBounds(327, 146, 23, 15);
		contentPane.add(showPwLabel);
		
		JLabel showRePwLabel = new JLabel("");
		showRePwLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (rePwField.getEchoChar() == '\u2022') {
		            rePwField.setEchoChar((char) 0); // 비밀번호 표시
		            showRePwLabel.setIcon(resizedIcon2);
		        } else {
		            rePwField.setEchoChar('\u2022'); // 비밀번호 숨기기
		            showRePwLabel.setIcon(resizedIcon);
		        }
			}
		});
		showRePwLabel.setIcon(resizedIcon);
		showRePwLabel.setBounds(327, 203, 23, 15);
		contentPane.add(showRePwLabel);
	}
	
    // 이미지 불러오기
 	private void loadImageFromDatabase(String id) {
        Database db = new Database(); 
 		// 데이터베이스에서 이미지를 로드하는 코드
         byte[] imageBytes = db.loadProfileImage(id);

         if (imageBytes != null) {
             // 이미지를 표시하는 코드
             ImageIcon imageIcon = new ImageIcon(imageBytes);
             
             // 라벨 크기 구하기
         	int labelWidth = profileLabel.getWidth();
         	int labelHeight = profileLabel.getHeight();
         	
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
             profileLabel.setIcon(imageIcon);

         } else {
         	profileLabel.setIcon(basic);
         }
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
}
