package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddressSearchFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField searchField;
	private JList<String> list;
	
	private ManagerFrame managerFrame;
	private SignUpFrame signUpFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddressSearchFrame dialog = new AddressSearchFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */	
	public AddressSearchFrame() {
		setBounds(100, 100, 474, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("우편번호 찾기");
		lblNewLabel.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		lblNewLabel.setBounds(29, 23, 86, 15);
		getContentPane().add(lblNewLabel);
		
		searchField = new JTextField();
		
		// 엔터키를 눌러도 검색이 되도록 함
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					String searchKeyword = searchField.getText();
	                List<String> results = searchAddress(searchKeyword);
	                list.setListData(results.toArray(new String[0]));
				}
			}
		});
		searchField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		searchField.setBounds(118, 20, 214, 21);
		getContentPane().add(searchField);
		searchField.setColumns(10);
		
		list = new JList<>();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // 더블클릭시
					int selectedIndex = list.getSelectedIndex();
					if(selectedIndex >= 0) {
						String selectedAddress = list.getModel().getElementAt(selectedIndex);
						SignUpFrame.setzipNoField(selectedAddress);
						SignUpFrame.setAddressField(selectedAddress);
						SignUpFrame.setExtraAddressFiled(selectedAddress);
						dispose();
					}
				}
			}
		});
		list.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		list.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		
		JScrollPane resultArea = new JScrollPane(list);
		resultArea.setBounds(29, 71, 405, 158);
		getContentPane().add(resultArea);
		
		
		resultArea.setViewportView(list);
		
		JButton searchButton = new JButton("찾기");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchKeyword = searchField.getText();
                List<String> results = searchAddress(searchKeyword);
                list.setListData(results.toArray(new String[0]));
			}
		});
		
		searchButton.setFont(new Font("한국기계연구원_Bold", Font.PLAIN, 12));
		searchButton.setBounds(344, 19, 91, 23);
		getContentPane().add(searchButton);
		
	}
	
	private List<String> searchAddress(String keyword) {
        try {
        	// URL을 만들기 위한 StringBuilder
            StringBuilder urlBuilder = new StringBuilder("http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdSearchAllService/retrieveNewAdressAreaCdSearchAllService/getNewAddressListAreaCdSearchAll"); // URL
            // 오픈 API의 요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=yLC4jUdDmceg5q3HPwWJFK0WYx6QNN8ZIz55s0Y%2BPdNG9dEF2R2tO7pQZfGJqGwaoUJMUGHfDCFqKKKOinm3Jg%3D%3D"); // 서비스키
            urlBuilder.append("&" + URLEncoder.encode("srchwrd", "UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8")); // 검색어
            urlBuilder.append("&" + URLEncoder.encode("countPerPage", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8")); // 페이지당 출력될 개수를 지정(최대 50)
            urlBuilder.append("&" + URLEncoder.encode("currentPage", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); // 출력될 페이지 번호

            // URL 객체 생성
            URL url = new URL(urlBuilder.toString());
            // 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 통신을 위한 메소드 SET
            conn.setRequestMethod("GET");
            // 통신을 위한 Content-type SET 
            conn.setRequestProperty("Content-type", "application/json");
            // 통신 응답 코드 확인
//            System.out.println("Response code: " + conn.getResponseCode());
            // 전달받은 데이터를 BufferedReader 객체로 저장
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); // 수정
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8")); // 수정
            }
            // 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            // 객체 해제
            rd.close();
            conn.disconnect();
            
            return parseXMLGetAddress(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            List<String> errorResult = new ArrayList<>();
            errorResult.add("Error: " + e.getMessage());
            return errorResult;
        }
    }

    public static List<String> parseXMLGetAddress(String xml) {
    	List<String> addresses = new ArrayList<>();
    	
    	try {
    		// XML 파싱을 위한 DocumentBuilder 생성
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // XML 문자열 파싱
            Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));
            
            // "lnmAdres" 엘리먼트(주소)를 가져오기
            NodeList lnmAdresList = doc.getElementsByTagName("lnmAdres");
            // "zipNo" 엘리먼트(우편번호)를 가져오기
            NodeList zipNoList = doc.getElementsByTagName("zipNo");
            
            for (int i = 0; i < lnmAdresList.getLength(); i++) {
                Element lnmAdresElement = (Element) lnmAdresList.item(i);
                Element zipNoElement = (Element) zipNoList.item(i);
                
                String address = lnmAdresElement.getTextContent();
                String zipNo = zipNoElement.getTextContent();
                
                addresses.add("우편번호: " + zipNo + ", 주소: " + address);
            }

            if (addresses.isEmpty()) {
                addresses.add("주소 정보를 찾을 수 없음");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addresses.add("주소 정보를 찾을 수 없음");
        }
        return addresses;
    }
}
