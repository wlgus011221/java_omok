package protocolData;

import lombok.Data;

@Data
public class ManagerDTO {
	private String id;
	private String nickName;
	private String userName;
	private String pw;
	private String email;
	private String phone;
	private String gender;
	private String birth;
	private String zipNo;
	private String address;
	private int win;
	private int lose;
	private byte[] imageBytes = null;
	
	public ManagerDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ManagerDTO(String id, String nickName, String userName, String pw, String email, String phone, String gender, String birth, String zipNo, String address, int win, int lose, byte[] imageBytes) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.userName = userName;
		this.pw = pw;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.birth = birth;
		this.zipNo = zipNo;
		this.address = address;
		this.win = win;
		this.lose = lose;
		this.imageBytes = imageBytes;
	}
}
