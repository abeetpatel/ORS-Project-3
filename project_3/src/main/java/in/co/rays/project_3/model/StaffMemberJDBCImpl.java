package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.StaffMemberDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class StaffMemberJDBCImpl implements StaffMemberModelInt {

	private static Logger log = Logger.getLogger(FacultyModelJDBCImpl.class);
	Connection con = null;

	/**
	 * new id create
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public long nextPK() throws DatabaseException {
		long pk = 0;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(ID) from st_staffMember");
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				pk = (int) r.getLong(1);
			}
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting in pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk + 1;
	}

	/**
	 * add new faculty
	 * 
	 * @param dto
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(StaffMemberDTO dto) throws ApplicationException, DuplicateRecordException {
		long pk = 0;

		java.util.Date d = dto.getJoiningDate();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);
		System.out.println(date);
		try {
			pk = nextPK();
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con
					.prepareStatement("insert into st_faculty values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, dto.getFullName());

			//ps.setString(4, dto.getQualification());
			// ps.setString(5, CollegeName);
			// ps.setString(6, CourseName);
			
			
			int a = ps.executeUpdate();
			System.out.println("insert data" + a);
			ps.close();
			con.commit();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model add End");
		return 0;

	}

	/**
	 * find faculty by email id
	 * 
	 * @param emailId
	 * @return dto
	 * @throws ApplicationException
	 */
	

	/**
	 * delete faculty
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(StaffMemberDTO dto) throws ApplicationException {
		try {
			System.out.println(dto.getId());
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("delete from st_faculty where ID=?");
			ps.setLong(1, dto.getId());
			System.out.println("Delete data successfully");
			ps.executeUpdate();
			ps.close();
			con.commit();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete faculty");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model delete Started");

	}

	/**
	 * update faculty information
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public void update(StaffMemberDTO dto) throws DatabaseException, ApplicationException {
		long pk = nextPK();
		java.util.Date d = dto.getJoiningDate();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);
		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(
					"update st_faculty set FIRST_NAME=?,LAST_NAME=?,GENDER=?,JOINING_DOB=?,QUALIFICATION=?,EMAIL_ID=?,MOBILE_NO=?,COLLEGE_ID=?,COLLEGE_NAME=?,SUBJECT_ID=?,SUBJECT_NAME=?,COURSE_ID=?,COURSE_NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=?");
			ps.setString(1, dto.getFullName());
			
			ps.setLong(18, dto.getId());
			System.out.println("update data successfully");
			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating faculty ");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	/**
	 * find information with the help of pk
	 * 
	 * @param pk
	 * @return
	 * @throws ApplicationException
	 */
	public StaffMemberDTO findByPK(long pk) throws ApplicationException {

		StaffMemberDTO dto = null;
		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_faculty where ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new StaffMemberDTO();
				dto.setId(rs.getLong(1));
				dto.setFullName(rs.getString(2));
				
			}
			ps.close();
			con.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting faculty by pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("model findBy pk end");

		return dto;

	}

	public List list() throws ApplicationException {

		return list(0, 0);
	}

	/**
	 * to show list of faculty
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_faculty");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		StaffMemberDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new StaffMemberDTO();
				dto.setId(rs.getLong(1));
				dto.setFullName(rs.getString(2));
				
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}

	public List search(StaffMemberDTO dto1) throws ApplicationException {
		return search(dto1, 0, 0);
	}

	/**
	 * to search list of faculty
	 * 
	 * @param dto1
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List search(StaffMemberDTO dto1, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");
		if (dto1 != null) {
			if (dto1.getId() > 0) {
				sql.append(" AND ID = " + dto1.getId());
			}
			if ((dto1.getFullName() != null) && (dto1.getFullName().length() > 0)) {
				sql.append(" AND FULL_NAME like '" + dto1.getFullName() + "%'");
			}
			if ((dto1.getDivision() != null) && (dto1.getDivision().length() > 0)) {
				sql.append(" AND DIVISION like '" + dto1.getDivision() + "%'");
			}
			
		}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<StaffMemberDTO> ar = new ArrayList<StaffMemberDTO>();
		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StaffMemberDTO dto = new StaffMemberDTO();
				dto.setId(rs.getLong(1));
				dto.setFullName(rs.getString(2));
				

				ar.add(dto);
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search faculty");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model search End");

		return ar;

	}
}
