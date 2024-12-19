package protocolData;

import java.io.Serializable;

public interface Protocol extends Serializable {
	
	public short getProtocol();
	public String getName();
	public String getMessage();
	public void setMessage(String message);
	public void setName(String name);
}

/* Serializable 인터페이스는 마커 인터페이스로도 알려져 있으며, 클래스가 직렬화 가능하다는 것을 나타내기 위해 사용됨
 * 파일에 객체를 저장하거나 객체를 네트워크를 통해 전송할 때 직렬화가 필요함
 */