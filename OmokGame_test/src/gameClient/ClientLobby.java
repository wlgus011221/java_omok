package gameClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Vector;

import javax.swing.JOptionPane;

/*
 * Version : 1.01
 * 
 * 09/02 패치 : 바둑알 덧그리기 수정, Lobby에서 채팅창 강제개행 수정.
 * 
 */

import gui.GameLobby;
import gui.GameLobbyInter;
import gui.GameRoomGui;
import gui.GuiInterface;
import gui.InfoFrame;
import gui.LobbyGui;
import gui.LobbyGuiInter;
import gui.LoginFrame;
import gui.LoginPanel;
import gui.MainFrame;
import gui.ManagerFrame;
import gui.PanelInterface;
import gui.RoomGuiInter;
import protocolData.ChatData;
import protocolData.Database;
import protocolData.GameData;
import protocolData.GameLobbyData;
import protocolData.LobbyData;
import protocolData.Protocol;

public class ClientLobby implements ClientInterface {

	// private final String serverIP = "127.0.0.1";
	private final static double VERSION = 1.01;
	
	/* 서버 주소를 내 컴퓨터에서 하기 위해서 localhost로 바꿈 */
	private String serverIP = "127.0.0.1";  // localhost.
//	private final String serverIP = "13.124.24.196";
//	private String serverIP = "203.255.3.51";  
	
	private String name;

	private GuiInterface m_Frame;

	private LobbyGuiInter m_lobby; 

	private GameLobbyInter m_gameLobby;

	private RoomGuiInter m_gameRoom;
	private InfoFrame infoFrame;
	private Socket socket;

	private ObjectOutputStream out;

	private ObjectInputStream netIn;

	private Protocol data;

	private String receiver;

	private static ClientLobby client;

	private boolean isInRoom = false;
	private boolean isRoomKing;
	private boolean isLogin = false;
	
	Database db = new Database();

	public ClientLobby(String id) {
//		this.serverIP = JOptionPane.showInputDialog("SERVER IP 를 입력하세요"); // 열려 있는 서버 주소를 입력하면 들어가짐

		System.out.println("start! serverIP " + serverIP);

		m_Frame = new MainFrame(this);
		m_Frame.setPanel(new LoginPanel(this));
		
		try {
			while(!isLogin) Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		// 서버 연결
		try {
			socket = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(serverIP, 50002);
			socket.connect(socketAddress, 3_000); // 최대 연결 대기 시간 3초로 설정
			out = new ObjectOutputStream(socket.getOutputStream()); // 데이터 보내기
			netIn = new ObjectInputStream(socket.getInputStream()); // 데이터 받기

			m_lobby = new LobbyGui(this); // 현재 클래스의 인스턴스 전달
			m_Frame.setPanel(m_lobby);
			
			sendMessage(VERSION + "", ChatData.ENTER);

			working();

		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, 
					"서버가 꺼져있습니다. SERVER를 확인하세요!!", "Notice!", JOptionPane.DEFAULT_OPTION);
			System.exit(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 예외 발생 여부와 관계없이 실행되는 블록
			try { 
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 클라이언트가 서버와의 통신을 수행하고 서버로부터 수신한 데이터를 해당 데이터 유형에 따라 처리하는 메서드
	public void working() {
		try {
			while (true) {
				data = (Protocol) netIn.readObject(); // netIn에서 데이터를 읽어 Protocol 인터페이스로 형변환 후 data 변수에 저장. 서버로부터 객체 읽기
				System.out.println("From Server : " + data);

				if (data instanceof ChatData)
					analysisChatData((ChatData) data);

				else if (data instanceof LobbyData)
					analysisLobbyData((LobbyData) data);

				else if (data instanceof GameLobbyData)
					analysisGameLobbyData((GameLobbyData) data);

				else if (data instanceof GameData)
					analysisGameData((GameData) data);

				else {
					System.out.println("맞는 데이터형이 없음.");
				}

			}

		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("data read fail");
			e.printStackTrace();
		}
	}

	// 서버로부터 수신한 ChatData 처리하고 사용자에게 적절한 메시지 표시
	private void analysisChatData(ChatData data) {
		switch (data.getProtocol()) {
		// 입장한 사용자 채팅 로그에 표시
		case ChatData.ENTER:
			data.setMessage("님이 입장하셨습니다.");
			setTextToLog(data);
			break;
		// 클라이언트 버전과 서버의 버전과 다를 때 실행
		case ChatData.LOGIN_CHECK:
			JOptionPane.showConfirmDialog(null, 
					"버젼이 다릅니다~!!!! \n http://26.192.38.68/test/b.html 에서 다운받으세요~!", "Notice!", JOptionPane.DEFAULT_OPTION);
			System.exit(0);
			break;
		// 다른 사용자가 방을 나가면 채팅 로그에 메시지 표시
		case ChatData.EXIT:
			setTextToLog(data);
			break;
		// 일반 채팅 표시
		case ChatData.MESSAGE:
			if ("/^^".equals(data.getMessage())) {
                // "/^^" 입력 시 이미지를 전송하는 코드 추가
				data.setMessage("😊");
                setTextToLog(data);
            } else if("/ㅠㅠ".equals(data.getMessage())){
            	data.setMessage("😭");
                setTextToLog(data);
            } else if("/good".equals(data.getMessage())||"/굿".equals(data.getMessage())){
            	data.setMessage("👍");
                setTextToLog(data);
            } else if("/hi".equals(data.getMessage())||"/bye".equals(data.getMessage())||"/인사".equals(data.getMessage())){
            	data.setMessage("👋");
                setTextToLog(data);
            } else {
                setTextToLog(data);
            }
            break;
		// 받은 쪽지 표시
		case ChatData.MESSAGE_SLIP:
			m_Frame.showMessageBox(data.getName(), data.getMessage(), false);
			break;
		// 사용자 목록을 서버에서 받아서 클라이언트에 업데이트
		case ChatData.SEND_USER_LIST:
			System.out.println("in send user list");
			m_Frame.setUserList(data.getUserList());
			break;
		// 전체 사용자 목록을 서버에서 받아서 클라이언트에 업데이트
		case ChatData.SEND_TOTAL_USER:
			m_Frame.setTotalUser(data.getUserList());
			break;

		default:
			setTextToLog(data);
			break;
		}
	}

	// 서버와 클라이언트 간의 방 관리 및 입장, 퇴장, 게임 로비 설정
	private void analysisLobbyData(LobbyData data) {
		switch (data.getProtocol()) {
		// 새로운 방 만드는 경우
		case LobbyData.CREATE_ROOM:
			setRoomKing(true);
			
			changePanel(new GameLobby(this, true)); // 클라이언트의 인스턴스, 방장 여부
			setGameLobbyInstance(); // 게임 로비 인스턴스 설정
			m_gameLobby.setRoomKing(name);

			m_gameLobby.setStartButton(true);

			m_gameLobby.setGameRoomInf("[" + data.getRoomNumber() + "번방] "
					+ data.getRoomName());

			break;
		// 방 입장하는 경우
		case LobbyData.ENTER_TO_ROOM:
			changePanel(new GameLobby(this,false)); // 클라이언트의 인스턴스, 방장 여부
			setGameLobbyInstance(); // 게임 로비 인스턴스 설정
			m_gameLobby.setCrhallenger(this.name);

			m_gameLobby.setStartButton(false);

			Vector<String> temp = new Vector<String>();

			for (String user : data.getUserList()) // 사용자 목록을 temp 벡터에 복사. 게임 로비에서 방에 있는 다른 사용자를 나타낼 목록을 생성
				temp.add(user);

			m_gameLobby.setUserList(temp);

			m_gameLobby.setGameRoomInf("[" + data.getRoomNumber() + "번방] "
					+ data.getRoomName());
			
			showEnterMessage(data); // 방 입장 메시지 표시
			
			break;
		// 서버로부터 방 목록을 수신한 경우
		case LobbyData.SEND_ROOMLIST:
			((RoomGuiInter) m_Frame).setRoomList(data.getRoomList());

			break;
		// 방장이 방을 나갈 경우
		case LobbyData.EXIT_GAME:
			break;

		default:
			break;
		}

	}
	
	// 방 입장 메시지
	private void showEnterMessage(Protocol data) {
		m_Frame.setTextToLogWindow("[ " + data.getName() + " ] 님이 입장하셨습니다 \n");
	}
	
	// 방 퇴장 메시지
	private void showExitMessage(Protocol data) {
		m_Frame.setTextToLogWindow("[ " + data.getName() + " ] 님이 나가셨습니다 \n");
	}

	// 클라이언트의 상태와 UI 관리
	private void analysisGameLobbyData(GameLobbyData data) {
		switch (data.getProtocol()) {
		// 준비 취소하는 경우 - 실행 결과에는 준비 버튼이 없음
		case GameLobbyData.CANCEL_READY:
			m_gameLobby.setButtonEnable(false);

			break;
		// 방장이 방을 나가는 경우
		case GameLobbyData.EXIT_ROOM:
			JOptionPane.showConfirmDialog(null, "Room Master exit this room!",
					"Notice!", JOptionPane.DEFAULT_OPTION);

			isInRoom = false;
			changePanel(new LobbyGui(this)); // 클라이언트의 UI를 로비 화면으로 전환
			setLobbyInstance();

			break;
		// 모든 게이머가 준비 완료한 경우 (방장만 실행) - 준비 버튼이 따로 없어서 방장이 아니더라도 게임 시작이 가능
		case GameLobbyData.GAME_READY:
			m_gameLobby.setButtonEnable(true); // 게임 시작 버튼 활성화

			break;
		// 게임이 시작된 경우
		case GameLobbyData.GAME_START:
			changePanel(new GameRoomGui(this));
			setGameRoomGui();

			setTextToLog(data);
			
			m_gameRoom.setGameRoomInfo(m_gameLobby.getGameInfo());

			break;

		default:
			break;
		}

	}
	
	// 클라이언트의 게임 상태와 UI 관리
	private void analysisGameData(GameData data) {

		switch (data.getProtocol()) {
		// 게임을 나가는 경우
		case GameData.EXIT_THEGAME:
			setRoomKing(false);
			System.out.println("##### gameData in");
			
			if (data.isBlack()) { // 방장이 나간 경우
				changePanel(new GameLobby(this, true));
				setGameLobbyInstance();
				m_gameLobby.setStartButton(false);
				
				JOptionPane.showConfirmDialog(null, "Room Master exit this Game!",
						"Notice!", JOptionPane.DEFAULT_OPTION);
			
			} else {
				showExitMessage(data);
			}
			System.out.println("##### gameData out");
			break;

		// 돌의 위치를 수신
		case GameData.SEND_STONE_LOCATION:
			m_gameRoom.drawStone(data.getStoneLocation(), data.isBlack()); // 게임 화면에 돌을 그림
			break;
			
		// 게임 결과를 수신한 경우
		case GameData.SEND_RESULT:
			if (data.isBlack()) 
				JOptionPane.showConfirmDialog(null, "검은돌이 이겼습니다!", "Notice!",
						JOptionPane.DEFAULT_OPTION);
				
			else
				JOptionPane.showConfirmDialog(null, "하얀돌이 이겼습니다!", "Notice!",
						JOptionPane.DEFAULT_OPTION);
			
			if(isRoomKing())
				sendMessage("", GameData.SEND_RESULT);				
			
			newGame();
			
			break;
		
		// 무르기 버튼을 누른 경우
		case GameData.REQUEST_RETURN:
			
			// inner switch
			switch (JOptionPane.showConfirmDialog(null, 
					"한수 물리시겠습니까?", "Notice!", JOptionPane.YES_NO_OPTION)) {
			
			case 0:
				sendMessage("YES", GameData.RESPONSE_RETURN);
				break;

			case 1:
				sendMessage("NO", GameData.RESPONSE_RETURN);
				break;

			default:
				break;
			}
			// inner switch
			
			break;
			
		// 무르기 요청에 응답한 경우
		case GameData.RESPONSE_RETURN:
			m_gameRoom.backOneStep(data.isBlack() ? 2 : 1);
//			setTextToLog("한수무르기를 승낙 하셨습니다.");
			break;

		// 게임 메시지를 수신한 경우
		case GameData.SEND_GAME_MESSAGE:
			infoFrame = new InfoFrame(data.getMessage());
			infoFrame.setVisible(true);
			
			break;
	
		default:
			break;
		}
	}

	private void newGame() {
		m_gameRoom.newGame();
	}

//	private void setTextToLog(Protocol data) {
//		m_Frame.setTextToLogWindow("[ " + data.getName() + " ] " + data.getMessage()
//				+ "\n");
//	}
	
	private void setTextToLog(Protocol data) {
		String name = data.getName();
	    String message = data.getMessage();
	    StringBuilder alignedMessage = new StringBuilder();
	    String formattedName = String.format("[ %s ]", name);
	    String formattedMessage = message;

	    switch (data.getProtocol()) {
	        case ChatData.ENTER:
	        case ChatData.EXIT:
	            // 가운데 정렬
	            formattedMessage = String.format("[ %s ] %s", name, message);
	            alignedMessage.append(centerAlign(formattedMessage)).append("\n");
	            break;

	        case ChatData.MESSAGE_SLIP:
	            // 왼쪽 정렬
	            formattedMessage = String.format("[ %s ]\n: %s", name, message);
	            alignedMessage.append(formattedMessage).append("\n");
	            break;

	        case ChatData.MESSAGE:
	            // 클라이언트의 이름을 가져옴
	            String clientName = this.name; // 클라이언트의 이름
	            // 이름이 클라이언트 이름과 같은지 확인하여 오른쪽 정렬
	            if (name.equals(clientName)) {
	                formattedMessage = String.format(": " + message);
	                alignedMessage.append(rightAlign(formattedName, 58)).append("\n");
	                alignedMessage.append(rightAlign(formattedMessage, 58)).append("\n");
	            } else {
	                formattedMessage = String.format("[ %s ]\n: %s", name, message);
	                alignedMessage.append(formattedMessage).append("\n");
	            }
	            break;

	        default:
	            formattedMessage = String.format("[ %s ]\n %s", name, message);
	            alignedMessage.append(formattedMessage).append("\n");
	            break;
	    }

	    m_Frame.setTextToLogWindow(alignedMessage.toString());
   }

	// 문자열 가운데 정렬 함수
	private String centerAlign(String text) {
	    int width = 5; // 원하는 너비 설정
	    return String.format("%" + width + "s", text);
	}

	private String rightAlign(String text, int width) {
	    int textWidth = text.length();

	    if (textWidth >= width) {
	        return text; // 텍스트가 원하는 너비보다 길 경우 그대로 반환
	    } else {
	        StringBuilder builder = new StringBuilder();
	        int spacesToAdd = width - textWidth; // 추가해야 하는 공백 개수 계산
	        for (int i = 0; i < spacesToAdd; i++) {
	            builder.append(" "); // 추가해야 하는 공백만큼 공백 추가
	        }
	        builder.append(text); // 텍스트 추가
	        return builder.toString(); // 최종 문자열 반환
	    }
	}

	private void setGameRoomGui() {
		this.m_gameRoom = (RoomGuiInter) this.m_Frame;
	}

	private void setGameLobbyInstance() {
		this.m_gameLobby = (GameLobbyInter) this.m_Frame;
	}

	private void setLobbyInstance() {
		this.m_lobby = (LobbyGuiInter) this.m_Frame;
	}

	/*
	 * User send when All Messsage call
	 */
	// 서버 간에 주고받는 데이터를 생성하고 송신하는 메서드
	public void sendMessage(String message, short state) {
	    try {
	        if (state == ChatData.MESSAGE_SLIP) {
	            data = new ChatData(receiver, name, message, ChatData.MESSAGE_SLIP);
	        } else if (state == LobbyData.CREATE_ROOM) {
	            // message is Room name.
	            isInRoom = true;
	            data = new LobbyData(name, message, state);
	        } else if (state == LobbyData.ENTER_TO_ROOM) {
	            // message is Room name.
	            isInRoom = true;
	            data = new LobbyData(name, message, state);
	        } else if (state == GameLobbyData.EXIT_ROOM) {
	            isInRoom = false;
	            data = new GameLobbyData(name, message, state);
	            data.setName(name);
	            changePanel(new LobbyGui(this));
	            setLobbyInstance();
	        } else if (state == GameLobbyData.GAME_START) {
	            data = new GameLobbyData(name, message, state);
	        } else if (state == GameLobbyData.GAME_READY) {
	            data = new GameLobbyData(name, message, state);
	        } else if (state == GameLobbyData.CANCEL_READY) {
	            data = new GameLobbyData(name, message, state);
	        } else if (state == GameData.EXIT_THEGAME) {
	            changePanel(new GameLobby(this, true));
	            setGameLobbyInstance();
	            m_gameLobby.setStartButton(true);
	            data = new GameData("", state);
	        } else if (state == ChatData.SEND_TOTAL_USER) {
	            data = new ChatData(name, null, state);
	        } else if (state == GameData.REQUEST_RETURN) {
	            data = new GameData("", state);
	        } else if (state == GameData.RESPONSE_RETURN) {
	            data = new GameData(message, state);
	        } else if (state == GameData.SEND_RESULT) {
	            data = new GameData(message, state);
	        } else {
	            data = new ChatData(name, message, state);
	        }

	        out.writeObject(data); // 생성된 데이터 서버로 송신
	    } catch (IOException e) {
	        System.out.println("data write fail!");
	        e.printStackTrace();
	    }
	}

	// 서버에 돌의 위치 정보를 전송
	public void sendMessage(int[] location) {
		this.data = new GameData(location, GameData.SEND_STONE_LOCATION);

		try {
			out.writeObject(this.data);
		} catch (IOException e) {
			System.out.println("Exception : 362라인.");
			e.printStackTrace();
		}
	}

	/*
	 * When Enter the Game Lobby, this method execute.
	 */
	// 쪽지 보내기
	public void sendSlip(String to, String message, short state) {
		receiver = to;
		sendMessage(message, state);
	}

	public GuiInterface getGui() {
		return m_Frame; // 현재 사용중인 프레임 반환
	}

	// GUI 변경
	public void changeGui(GuiInterface gui) {
		m_Frame.unShow();
		m_Frame = gui;
	}
	
	// 화면전환
	public void changePanel(GuiInterface panel) {
		if (panel instanceof LoginPanel)
			m_Frame.setPanel((PanelInterface)panel);
		else if (panel instanceof LobbyGui)
			m_Frame.setPanel((LobbyGuiInter)panel);
		else if (panel instanceof GameLobby)
			m_Frame.setPanel((GameLobbyInter)panel);
		else if (panel instanceof GameRoomGui)
			m_Frame.setPanel((RoomGuiInter)panel);
		else if (panel instanceof ManagerFrame)
			m_Frame.setPanel((PanelInterface)panel);
		else {
			System.out.println("맞는 데이터형이 없음.");
		}
		
	}

	public static void main(String[] args) {
		try {
			ClientLobby client = new ClientLobby("test");
			LoginFrame loginFrame = new LoginFrame(client);
			loginFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSlipTo(String slipTo) {
		this.receiver = slipTo;
	}

	public boolean isRoomKing() {
		return isRoomKing;
	}
	

	public void setRoomKing(boolean isRoomKing) {
		this.isRoomKing = isRoomKing;
	}

	public void setName(String text) {
		this.name = text;
		isLogin = true;
	}
	
	public String getNickName() {
        return this.name;
    }
}
