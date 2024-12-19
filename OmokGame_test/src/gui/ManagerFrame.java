package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import protocolData.Database;
import protocolData.ManagerDAO;
import protocolData.ManagerDTO;

public class ManagerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable userTable;
	Database db = new Database();
	private ManagerDTO dto = new ManagerDTO();
	// 이미지 바이트 배열을 저장할 변수
	byte[] imageBytes = null;
	private boolean isPasswordVisible = false; // 비밀번호 보이기 상태 여부를 저장할 변수
	private JTextField searchField;
	private JComboBox searchComboBox;
	
//	PasswordCellRenderer PWVisible = new PasswordCellRenderer();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerFrame frame = new ManagerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	class PasswordCellRenderer extends DefaultTableCellRenderer {
	    private boolean isPasswordVisible = false;

	    public void setPasswordVisible(boolean isPasswordVisible) {
	        this.isPasswordVisible = isPasswordVisible;
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	        if (isPasswordVisible) {
	            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        } else {
	            String str = String.valueOf(value);
	            String masked = String.join("", Collections.nCopies(str.length(), "*"));
	            return super.getTableCellRendererComponent(table, masked, isSelected, hasFocus, row, column);
	        }
	    }
	}

	/**
	 * Create the frame.
	 */
	public ManagerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1126, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 85, 1067, 375);
		contentPane.add(scrollPane);
		
		userTable = new JTable (new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"아이디", "비밀번호", "닉네임", "성함", "이메일", "전화번호", "성별", "생년월일", "우편번호", "주소", "승", "패"
			}
		));

		userTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		userTable.getColumnModel().getColumn(1).setPreferredWidth(40);
		userTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		userTable.getColumnModel().getColumn(3).setPreferredWidth(25);
		userTable.getColumnModel().getColumn(4).setPreferredWidth(80);
		userTable.getColumnModel().getColumn(5).setPreferredWidth(40);
		userTable.getColumnModel().getColumn(6).setPreferredWidth(1);
		userTable.getColumnModel().getColumn(7).setPreferredWidth(40);
		userTable.getColumnModel().getColumn(8).setPreferredWidth(40);
		userTable.getColumnModel().getColumn(9).setPreferredWidth(100);
		userTable.getColumnModel().getColumn(10).setPreferredWidth(1);
		userTable.getColumnModel().getColumn(11).setPreferredWidth(1);
		
		userTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // 더블 클릭인 경우
		            JTable target = (JTable)e.getSource();
		            int row = target.getSelectedRow(); // 선택된 행
		            // 선택된 행의 데이터를 가져옵니다.
		            String id = (String)target.getValueAt(row, 0);
		            String pw = (String)target.getValueAt(row, 1);
		            String nickName = (String)target.getValueAt(row, 2);
		            String userName = (String)target.getValueAt(row, 3);
		            String email = (String)target.getValueAt(row, 4);
		            String phone = (String)target.getValueAt(row, 5);
		            String gender = (String)target.getValueAt(row, 6);
		            String birth = (String)target.getValueAt(row, 7);
		            String zipNo = (String)target.getValueAt(row, 8);
		            String address = (String)target.getValueAt(row, 9);
		            
		            // 이후 id, pw 등의 값을 이용해 UserInquiryFrame을 생성하고 보여줍니다.
		            UserInquiryFrame inquiryFrame = new UserInquiryFrame(id, pw, nickName, userName, email, phone, gender, birth, zipNo, address);
		            inquiryFrame.setVisible(true);
		        }
			}
		});
		
		// 비밀번호 컬럼의 렌더러 생성
	    PasswordCellRenderer passwordRenderer = new PasswordCellRenderer();
	    userTable.getColumnModel().getColumn(1).setCellRenderer(passwordRenderer);

		userTable.setSurrendersFocusOnKeystroke(true);
		userTable.setFont(new Font("굴림", Font.PLAIN, 12));
		scrollPane.setViewportView(userTable);
		
		JButton AllSearchButton = new JButton("전체 조회");
		AllSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					displayAllManager(0, null);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		AllSearchButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		AllSearchButton.setBounds(478, 24, 88, 23);
		contentPane.add(AllSearchButton);
		
		JButton ExitNewButton = new JButton("종료");
		ExitNewButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		ExitNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		ExitNewButton.setBounds(995, 24, 91, 23);
		contentPane.add(ExitNewButton);
		
		JButton showPWButton = new JButton("비밀번호 보기");
		showPWButton.addActionListener(new ActionListener() {
			private boolean isPasswordVisible = false;

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            isPasswordVisible = !isPasswordVisible;
	            passwordRenderer.setPasswordVisible(isPasswordVisible);
	            showPWButton.setText(isPasswordVisible ? "비밀번호 숨기기" : "비밀번호 보기");
	            userTable.repaint(); // 테이블을 다시 그립니다.
	        }
		});
		showPWButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		showPWButton.setBounds(866, 24, 117, 23);
		contentPane.add(showPWButton);
		
		JButton addUserButton = new JButton("추가");
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUpFrame frame = new SignUpFrame();
				frame.setVisible(true);
			}
		});
		addUserButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		addUserButton.setBounds(763, 24, 91, 23);
		contentPane.add(addUserButton);
		
		searchComboBox = new JComboBox();
		searchComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					String selectedCategory = (String) searchComboBox.getSelectedItem();
					if(selectedCategory.equals("카테고리")||selectedCategory.equals("남")||selectedCategory.equals("여")) {
						searchField.setText("");
						searchField.setEditable(false);
					} else {
						searchField.setText("");
						searchField.setEditable(true);
					}
				}
			}
		});
		searchComboBox.setModel(new DefaultComboBoxModel(new String[] {"카테고리", "아이디", "닉네임", "성함", "이메일", "전화번호", "남", "여", "생년월일", "우편번호", "주소"}));
		searchComboBox.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		searchComboBox.setBounds(23, 24, 117, 23);
		contentPane.add(searchComboBox);
		
		JButton searchButton = new JButton("조회");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					search();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		searchButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		searchButton.setBounds(391, 24, 81, 23);
		contentPane.add(searchButton);
		
		searchField = new JTextField();
		searchField.setEditable(false);
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					try {
						search();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		searchField.setFont(new Font("한국기계연구원_Light", Font.PLAIN, 12));
		searchField.setBounds(152, 25, 227, 21);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("상세조회는 우클 2번");
		lblNewLabel.setFont(new Font("한국기계연구원_Light", Font.PLAIN, 12));
		lblNewLabel.setBounds(23, 60, 177, 15);
		contentPane.add(lblNewLabel);
		
		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("image/loginBg.jpg"));
		backGround.setBounds(-29, -43, 1181, 542);
		getContentPane().add(backGround);
	}
	
	//테이블에 저장된 모든 정보를 검색하여 JTable 컴퍼넌트에 출력하는 메소드
	public void displayAllManager(int mode,String temp) throws SQLException {
		List<ManagerDTO> managerList=null;
		if(mode == 0) { // 전체 조회
			managerList = ManagerDAO.getDAO().selectAll();
		} else if(mode==1) { // 아이디로 조회
			managerList = ManagerDAO.getDAO().selectId(temp);
		} else if(mode==2) { // 닉네임으로 조회
			managerList = ManagerDAO.getDAO().selectNickName(temp);
		} else if(mode==3) { // 성함으로 조회
			managerList = ManagerDAO.getDAO().selectUserName(temp);
		} else if(mode==4) { // 이메일로 조회
			managerList=ManagerDAO.getDAO().selectEmail(temp);
		} else if(mode==5) { // 전화번호로 조회
			managerList=ManagerDAO.getDAO().selectPhone(temp);
		} else if(mode==6) { // 남자로 조회
			managerList=ManagerDAO.getDAO().selectGender(temp);
		} else if(mode==6) { // 여자로 조회
			managerList=ManagerDAO.getDAO().selectGender(temp);
		} else if(mode==7) { // 생년월일로 조회
			managerList=ManagerDAO.getDAO().selectBirth(temp);
		} else if(mode==8) { // 우편번호로 조회
			managerList=ManagerDAO.getDAO().selectZipNo(temp);
		} else if(mode==9) { // 주소로 조회
			managerList=ManagerDAO.getDAO().selectAddress(temp);
		}
		
		if (managerList == null || managerList.isEmpty()) {
		    JOptionPane.showMessageDialog(this, "저장된 정보가 없습니다.");
		    return;
		}
		
		DefaultTableModel model=(DefaultTableModel)userTable.getModel(); 
		
		for (int i = model.getRowCount(); i >0; i--) {
			model.removeRow(0);
		}
		// 가져온 데이터를 테이블 모델에 추가
//		for (ManagerDTO manager : managerList) {
//		    model.addRow(new Object[]{
//		        manager.getId(),
//		        manager.getPw(),
//		        manager.getNickName(),
//		        manager.getUserName(),
//		        manager.getEmail(),
//		        manager.getPhone(),
//		        manager.getGender(),
//		        manager.getBirth(),
//		        manager.getZipNo(),
//		        manager.getAddress(),
//		        manager.getWin(),
//		        manager.getLose()
//		    });
//		}
	}
	
	// 카테고리로 조회
	public void search() throws SQLException {
		String selectedCategory = (String) searchComboBox.getSelectedItem();
		if(selectedCategory.equals("카테고리")) {
			JOptionPane.showMessageDialog(null, "카테고리를 선택해주세요.");
			return;
		}
		
		if(!searchField.getText().equals("")||selectedCategory.equals("남")||selectedCategory.equals("여")) {
			String temp = searchField.getText();
			if(selectedCategory.equals("아이디")) {
				displayAllManager(1, temp);
			} else if(selectedCategory.equals("닉네임")) {
				displayAllManager(2, temp);
			} else if(selectedCategory.equals("성함")) {
				displayAllManager(3, temp);
			} else if(selectedCategory.equals("이메일")) {
				displayAllManager(4, temp);
			} else if(selectedCategory.equals("전화번호")) {
				displayAllManager(5, temp);
			} else if(selectedCategory.equals("남")) {
				displayAllManager(6, "남");
			} else if(selectedCategory.equals("여")) {
				displayAllManager(6, "여");
			} else if(selectedCategory.equals("생년월일")) {
				displayAllManager(7, temp);
			} else if(selectedCategory.equals("우편번호")) {
				displayAllManager(8, temp);
			} else if(selectedCategory.equals("주소")) {
				displayAllManager(9, temp);
			}
		} else {
			JOptionPane.showMessageDialog(null, "조회할 내용을 입력해주세요.");
		}
	}
}