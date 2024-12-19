package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import protocolData.Database;
import javax.swing.JComboBox;

public class IDSearchFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userNameField;
	private JTextField emailField;
	private JTextField phone1Field;
	Database db = new Database();
	private JTextField phone2Field;
	private JTextField phone3Field;
	private JTextField domainField;
	private JLabel lblNewLabel_3_1;
	private JComboBox yearBox;
	private JComboBox monthBox;
	private JComboBox dayBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IDSearchFrame frame = new IDSearchFrame();
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
	public IDSearchFrame() {
		setTitle("ID 찾기");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 415, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("성함");
		lblNewLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel.setBounds(30, 54, 74, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("이메일");
		lblNewLabel_1.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(30, 99, 50, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("전화번호");
		lblNewLabel_2.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(30, 145, 50, 15);
		contentPane.add(lblNewLabel_2);
		
		userNameField = new JTextField();
		userNameField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		userNameField.setBounds(116, 51, 140, 21);
		contentPane.add(userNameField);
		userNameField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		emailField.setBounds(116, 96, 60, 21);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		phone1Field = new JTextField();
		phone1Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		phone1Field.setText("010");
		phone1Field.setBounds(116, 142, 33, 21);
		contentPane.add(phone1Field);
		phone1Field.setColumns(10);
		
		JButton searchButton = new JButton("찾기");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = userNameField.getText();
				String userEmail = emailField.getText() + "@" + domainField.getText();
				String userPhone = phone1Field.getText() + phone2Field.getText() + phone3Field.getText();
				String birth = yearBox.getSelectedItem()+ "."+ monthBox.getSelectedItem() + "." + dayBox.getSelectedItem();
				if(userName.equals("") || userEmail.equals("") || userPhone.equals("")) {
					JOptionPane.showMessageDialog(null, "userName, 이메일, 전화번호 모두 입력해주세요.");
				} else {
					String id = db.idSearch(userName, userEmail, userPhone, birth);
					if(id != null) {
						JOptionPane.showMessageDialog(null, "ID는 " + id + "입니다.");
					} else {
						JOptionPane.showMessageDialog(null, "존재하지 않는 정보입니다. 다시 확인하세요");
					}
				}
			}
		});
		searchButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		searchButton.setBounds(155, 262, 91, 23);
		contentPane.add(searchButton);
		
		phone2Field = new JTextField();
		phone2Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		phone2Field.setBounds(152, 142, 50, 21);
		contentPane.add(phone2Field);
		phone2Field.setColumns(10);
		
		phone3Field = new JTextField();
		phone3Field.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		phone3Field.setColumns(10);
		phone3Field.setBounds(206, 142, 50, 21);
		contentPane.add(phone3Field);
		
		JLabel lblNewLabel_3 = new JLabel("@");
		lblNewLabel_3.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(178, 99, 23, 15);
		contentPane.add(lblNewLabel_3);
		
		domainField = new JTextField();
		domainField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		domainField.setColumns(10);
		domainField.setBounds(197, 93, 84, 21);
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
		domainBox.setBounds(289, 93, 96, 21);
		contentPane.add(domainBox);
		
		lblNewLabel_3_1 = new JLabel("생년월일");
		lblNewLabel_3_1.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel_3_1.setBounds(30, 189, 50, 15);
		contentPane.add(lblNewLabel_3_1);
		
		yearBox = new JComboBox();
		yearBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		yearBox.setModel(new DefaultComboBoxModel(new String[] {"1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013"}));
		yearBox.setBounds(116, 183, 67, 23);
		contentPane.add(yearBox);
		
		monthBox = new JComboBox();
		monthBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		monthBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		monthBox.setBounds(191, 183, 67, 23);
		contentPane.add(monthBox);
		
		dayBox = new JComboBox();
		dayBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		dayBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dayBox.setBounds(265, 183, 67, 23);
		contentPane.add(dayBox);
		
		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("image/loginBg.jpg"));
		backGround.setBounds(-124, -113, 591, 444);
		contentPane.add(backGround);
	}
}
