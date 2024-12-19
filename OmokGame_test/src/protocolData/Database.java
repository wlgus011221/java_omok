package protocolData;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

public class Database {
	Connection con = DBConnect.makeConnection();
	
	PreparedStatement pstmt = null;
	
	// 로그인 정보를 확인
	public boolean logincheck(String _i, String _p) {
		boolean flag = false;
		
		String id = _i;
		String pw = _p;
		
		try {
			Statement stmt = con.createStatement();
			String checkingStr = "SELECT pw FROM users WHERE id ='" + id +"'";
			ResultSet result = stmt.executeQuery(checkingStr);
						
			int count = 0;
			while(result.next()) {
				if(pw.equals(result.getString("pw"))) {
					flag = true;
					System.out.println("로그인 성공");
				}
				
				else {
					flag = false;
					System.out.println("로그인 실패");
				}
				count++;
			}
		} catch(Exception e) {
			flag = false;
			System.out.println("로그인 실패 > " + e.toString());
		}
		return flag;
	}
	
	// DB에 있는 nickName 가져오기 
	public String nickCheck(String id) {
		String userId = id;
		String nick = null;
		String nicksql = "SELECT nickName FROM users WHERE id='" + userId + "'";
		try {
			pstmt = con.prepareStatement(nicksql);
			ResultSet rst = pstmt.executeQuery();
			if(rst.next()) {
				nick = rst.getString("nickName");
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return nick;
	}
	
	// DB에 있는 id 가져오기 
	public String idCheck(String nickname) {
		String userNick = nickname;
		String id = null;
		String sql = "SELECT id FROM users WHERE nickName='" + userNick + "'";
		try {
			pstmt = con.prepareStatement(sql);
			ResultSet rst = pstmt.executeQuery();
			if(rst.next()) {
				id = rst.getString("id");
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}	
	
	// DB에 있는 id 찾기
	public String idSearch(String name, String email, String phone, String birth) {
		String userName = name;
		String userEmail = email;
		String userPhone = phone;
		String userBirth = birth;
		String id = null;
		String sql = "SELECT id FROM users "
				+ "WHERE userName=? "
				+ "AND email=? "
				+ "AND phone=? "
				+ "AND birth=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, userEmail);
			pstmt.setString(3, userPhone);
			pstmt.setString(4, userBirth);
			ResultSet rst = pstmt.executeQuery();
			if(rst.next()) {
				id = rst.getString("id");
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	// DB에 있는 pw 찾기
	public String pwSearch(String id, String name, String email, String phone, String birth) {
		String userid = id;
		String userName = name;
		String userEmail = email;
		String userPhone = phone;
		String userBirth = birth;
		String pw = null;
		String sql = "SELECT pw FROM users "
				+ "WHERE id=? "
				+ "AND userName=? "
				+ "AND email=? "
				+ "AND phone=? "
				+ "AND birth=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userName);
			pstmt.setString(3, userEmail);
			pstmt.setString(4, userPhone);
			pstmt.setString(5, userBirth);
			ResultSet rst = pstmt.executeQuery();
			if(rst.next()) {
				pw = rst.getString("pw");
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pw;
	}
	
	// 이미지를 가져오는 SQL 쿼리 작성 (이미지가 BLOB 열에 저장된 경우)
	public byte[] getImageData(String id) {
		byte[] imageData = null;
	    
	    try {
	        String sql = "SELECT image FROM users WHERE id = ?";
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, id);
	        ResultSet resultSet = pstmt.executeQuery();

	        if (resultSet.next()) {
	            // 이미지 데이터를 바이트 배열로 가져옴
	            imageData = resultSet.getBytes("image");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return imageData;
	}
	
	/* 회원가입 정보를 추가 */
	public boolean joinCheck(String _i, String _p, String _un, String _email) {
		boolean flag = false;
		
		String id = _i;
		String pw = _p;
		String username = _un;
		String email = _email;
		
		try {
			 Statement stmt = con.createStatement();
			String insertStr = "INSERT INTO users (id, pw, username, email) VALUES('" + id + "', '" + pw + "', '" + username + "', '" + email + "')";
			stmt.executeUpdate(insertStr);
			flag = true;
			System.out.println("회원가입 성공");
		}
			catch(Exception e) {
			flag = false;
			
			System.out.println("회원가입 실패 > " + e.toString());
		}
			
		return flag;
	}
	
	public byte[] loadProfileImage(String userId) {
		byte[] result = null;
	    String sql = "SELECT image FROM users WHERE id = ?";
	    try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
		    if(rs.next()) {
		    	Blob blob = rs.getBlob("image");
		    	if(blob!=null) {
		    		int fileSize = (int)blob.length();
			    	result = blob.getBytes(1, fileSize);
		    	}
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return result;
	}
	
	public byte[] loadProfileImageNickName(String nickName) {
		byte[] result = null;
	    String sql = "SELECT image FROM users WHERE nickName = ?";
	    try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickName);
			ResultSet rs = pstmt.executeQuery();
		    if(rs.next()) {
		    	Blob blob = rs.getBlob("image");
		    	if(blob!=null) {
		    		int fileSize = (int)blob.length();
			    	result = blob.getBytes(1, fileSize);
		    	}
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return result;
	}

	public String loadPassword(String userid) {
		String pw = null;
		String sql = "SELECT pw FROM users WHERE id='" + userid + "'";
		try {
			pstmt = con.prepareStatement(sql);
			ResultSet rst = pstmt.executeQuery();
			if(rst.next()) {
				pw = rst.getString("pw");
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pw;
	}
	
	public int deleteUser(String id) {
		int flag = 0;
		try {
			String sql = "DELETE FROM users WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			flag = pstmt.executeUpdate();
		} catch (Exception ex){
			flag = 0;
			ex.printStackTrace();
		} 
		return flag;
	}
	
}