package gui;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import playSound.MusicPlayer;
import playSound.MyPlayer;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class test extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private MusicPlayer player = null;  // player를 멤버 변수로 선언
    private boolean isPlaying = false;  // 음악 재생 상태 추적을 위한 변수
    MyPlayer mp = new MyPlayer("C:\\Users\\wlgus\\OneDrive\\바탕 화면\\대학\\3-2\\23-2 자바프로그래밍2\\자바 오목 프로젝트\\City Walk - John Patitucci.mp3");
	
    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
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
	public test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 543, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon originalIcon = new ImageIcon("image/play-pause.png");
		Image originalImage = originalIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(23, 18, java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isPlaying) {
                    player.stopMusic();  // 음악이 재생 중이면 정지
                    player = null;  // player 객체를 null로 설정
                    isPlaying = false;
                } else {
                    // 음악이 정지 상태이면 새로운 MyPlayer 객체를 생성하고 재생
                    MyPlayer mp = new MyPlayer("C:\\Users\\wlgus\\OneDrive\\바탕 화면\\대학\\3-2\\23-2 자바프로그래밍2\\자바 오목 프로젝트\\City Walk - John Patitucci.mp3");
                    player = new MusicPlayer(mp);
                    player.start();
                    isPlaying = true;
                }
			}
		});
		lblNewLabel.setBounds(12, 10, 50, 15);
		contentPane.add(lblNewLabel);
		
		lblNewLabel.setIcon(resizedIcon);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 190, 100);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(165, 25, 200, 160);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(347, 27, 129, 160);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel profileLabel = new JLabel();
		profileLabel.setBounds(0, 0, 129, 160);
		panel_2.add(profileLabel);
		
		
	}
}
