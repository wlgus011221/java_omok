package protocolData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {
	//1
	private static ManagerDAO _dao;
		
	//2 생성자의 은닉화 선언
	private ManagerDAO() {
		// TODO Auto-generated constructor stub
	}
		
	//3 정적영역에서 클래스의 인스턴스를 생성하여 참조필드에 저장
	static {
		_dao=new ManagerDAO();
	}
		
	//4 참조필드에 저장된 인스턴스를 반환하는 메소드 작성
	public static ManagerDAO getDAO() {
		return _dao;
	}
	
	// 닉네임 검색 -> 단일 행 검색 
	public List<ManagerDTO> selectNickName(String nickName) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ManagerDTO> managerList = new ArrayList<ManagerDTO>();
		try {
			conn = new DBConnect().makeConnection();
			
			String sql="select * from users where nickName=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, nickName);
			
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				ManagerDTO dto=new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				managerList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("[에러] =selectAllManagerList() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
			rs.close();
		}
		return managerList; 
	}
	
	// 아이디로 검색 -> 단일 행 검색
	public List<ManagerDTO> selectId(String id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ManagerDTO> managerList = new ArrayList<ManagerDTO>();
		try {
			conn = new DBConnect().makeConnection();
			
			String sql="select * from users where id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				ManagerDTO dto=new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				managerList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("[에러] =selectAllManagerList() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
			rs.close();
		}
		return managerList; 
	}
	
	// 성함(userName)으로 검색 - 중복 가능하니까 다중 행 검색으로
	public List<ManagerDTO> selectUserName(String userName) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ManagerDTO> managerList = new ArrayList<ManagerDTO>();
		try {
			conn = new DBConnect().makeConnection();
			
			String sql="select * from users where userName=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ManagerDTO dto=new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				managerList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("[에러] =selectAllManagerList() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
			rs.close();
		}
		return managerList; 
	}
	
	// 이메일로 검색 - 중복 가능하니까 다중 행 검색으로
	public List<ManagerDTO> selectEmail(String email) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ManagerDTO> managerList = new ArrayList<ManagerDTO>();
		try {
			conn = new DBConnect().makeConnection();
			
			String sql="select * from users where email=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, email);
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ManagerDTO dto=new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				managerList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("[에러] =selectAllManagerList() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
			rs.close();
		}
		return managerList; 
	}	
	
	// 전화번호로 검색 - 중복 가능하니까 다중 행 검색으로
	public List<ManagerDTO> selectPhone(String phone) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ManagerDTO> managerList = new ArrayList<ManagerDTO>();
		try {
			conn = new DBConnect().makeConnection();
			
			String sql="select * from users where phone=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, phone);
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ManagerDTO dto=new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				managerList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("[에러] =selectAllManagerList() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
			rs.close();
		}
		return managerList; 
	}	
	
	// 성별로 검색 -> 다중행 검색
	public List<ManagerDTO> selectGender(String gender) throws SQLException {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<ManagerDTO> managerList=new ArrayList<ManagerDTO>();
		try {
			conn = new DBConnect().makeConnection();
			
			String sql="select * from users where gender=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, gender);
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ManagerDTO dto = new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				managerList.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("[에러] =selectManagerList() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
			rs.close();
		} 
		return managerList;
	}
	
	// 생년월일로 검색 -> 다중행 검색
	public List<ManagerDTO> selectBirth(String birth) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ManagerDTO> managerList = new ArrayList<ManagerDTO>();
		try {
			conn = new DBConnect().makeConnection();
			
			String sql="select * from users where birth=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, birth);
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ManagerDTO dto=new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				managerList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("[에러] =selectAllManagerList() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
			rs.close();
		}
		return managerList; 
	}

	// 우편번호로 검색 - 중복 가능하니까 다중 행 검색으로
	public List<ManagerDTO> selectZipNo(String zipNo) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ManagerDTO> managerList = new ArrayList<ManagerDTO>();
		try {
			conn = new DBConnect().makeConnection();
			
			String sql="select * from users where zipNo=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, zipNo);
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ManagerDTO dto=new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				managerList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("[에러] =selectAllManagerList() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
			rs.close();
		}
		return managerList; 
	}	

	// 주소로 검색 - 중복 가능하니까 다중 행 검색으로
	public List<ManagerDTO> selectAddress(String address) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ManagerDTO> managerList = new ArrayList<ManagerDTO>();
		try {
			conn = new DBConnect().makeConnection();
			
			String sql="select * from users where address=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, address);
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ManagerDTO dto=new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				managerList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("[에러] =selectAllManagerList() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
			rs.close();
		}
		return managerList; 
	}	

	// 전체 조회 -> 다중행 검색
	public List<ManagerDTO> selectAll() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ManagerDTO> managerList = new ArrayList<ManagerDTO>();
		try {
			conn = new DBConnect().makeConnection();
			
			String sql="select * from users order by userName"; // 성함으로 정렬
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ManagerDTO dto=new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				managerList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("[에러] =selectAllManagerList() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
			rs.close();
		}
		return managerList; 
	}
	
	public ManagerDTO getData(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ManagerDTO dto = null;
		try {
			conn = new DBConnect().makeConnection();
			String sql = "SELECT id, nickName, userName, pw, email, phone, gender, birth, zipNo, address, win, lose "
					+ "FROM users "
					+ "WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ManagerDTO();
//				dto.setId(id);
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
			}
		}
		return dto;
	}
	
	public List<ManagerDTO> getList(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ManagerDTO> list = new ArrayList<>();
		try {
			conn = new DBConnect().makeConnection();
			String sql = "SELECT id, nickName, userName, pw, email, phone, gender, birth, zipNo, address, win, lose "
					+ "FROM users";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				ManagerDTO dto = new ManagerDTO();
//				dto.setId(rs.getString("id"));
//				dto.setNickName(rs.getString("nickName"));
//				dto.setUserName(rs.getString("userName"));
//				dto.setPw(rs.getString("pw"));
//				dto.setEmail(rs.getString("email"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setGender(rs.getString("gender"));
//				dto.setBirth(rs.getString("birth"));
//				dto.setZipNo(rs.getString("zipNo"));
//				dto.setAddress(rs.getString("address"));
//				dto.setWin(rs.getInt("win"));
//				dto.setLose(rs.getInt("lose"));
				list.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			} catch(SQLException e) {
			}
		}
		return list;
	}
}