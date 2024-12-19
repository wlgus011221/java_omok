package protocolData;

import java.util.Vector;

// 채팅 메시지와 관련된 데이터 표시
public class ChatData implements Protocol {
	// 메시지 상태 식별하는 상수
	public static final short ENTER = 1000;
	public static final short LOGIN_CHECK = 1100;
	public static final short LOGIN_ERROR = 1200;
	public static final short MESSAGE = 2000;
	public static final short MESSAGE_SLIP = 2100;
	public static final short EXIT = 3000;
	public static final short SEND_USER_LIST = 4000;
	public static final short SEND_TOTAL_USER = 4100;
	
	private static final long serialVersionUID = 1L;
	
	// 메시지를 보낸 사용자의 이름과 메시지를 저장하는 변수
	private String name = null,
						   message = null;
	
	// 수신자 이름 저장하는 변수
	private String receiver;
	
	// 메시지 상태를 나타내는 변수
	private short state = 0;

	// 사용자 목록을 저장하는 벡터 자료구조
	private Vector<String> userList = null;
	
	// 사용자 이름, 메시지 내용 및 상태를 전달하여 객체 생성
	public ChatData(String name, String message, short state) {
		this.name = name;
		this.message = message;
		this.state = state;
		if ( ChatData.SEND_USER_LIST == state)
			userList = new Vector<String>(); // userList 초기화
	}
	
	public ChatData(String receiverName, String name, String message, short state) {
		this(name, message, state);
		receiver = receiverName;
	}
	
	// 메시지 내용을 설정
	public void setMessage(String message) {
		this.message = message;
	}

	// 상태 반환
	public short getState() {
		return state;
	}

	// 사용자 목록 반환
	public Vector<String> getUserList() {
		return userList;
	}
 
	// 사용자 이름 반환
	public String getName() {
		return name;
	}

	// 메시지 반환
	public String getMessage() {
		return message;
	}

	// 사용자 목록 설정
	public void setUserList(Vector<String> userList) {
		this.userList =  null;
		this.userList =  userList;
	}

	// 사용자 이름 설정
	public void setName(String name) {
		this.name = name;
	}
	
	// 객체의 문자열 표현을 반환. 사용자 이름, 메시지 내용, 상태, 사용자 목록을 문자열로 반환
	public String toString() {
		return name + " " + message + " " + state + "\n" + userList;
	}

	// 수신자 이름 반환
	public String getReceiver() {
		return receiver;
	}

	// 메시지 상태(프로토콜) 반환
	public short getProtocol() {
		return state;
	}

	// 메시지 상태 설정
	public void setProtocol(short exit) {
		state = exit;
	}
}

