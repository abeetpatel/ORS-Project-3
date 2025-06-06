package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.CourseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of Course model
 * @author Abeet Patel
 *
 */
public class CourseModelJDBCImpl implements CourseModelInt {

	private static Logger log = Logger.getLogger(CourseModelJDBCImpl.class);
	Connection con = null;
	PreparedStatement ps = null;

	/**
	 * create id
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public long nextPK() throws DatabaseException {
		long pk = 0;
		log.debug("model nextPk start");

		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement("select max(ID) from st_course");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			ps.close();
			con.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting in pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("model nextpk end");
		return pk + 1;

	}

	/**
	 * add new course
	 * 
	 * @param b
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(CourseDTO b) throws ApplicationException, DuplicateRecordException {
		long pk = 0;
		log.debug("model add start");
		CourseDTO duplicataRole = findByName(b.getCourseName());
		// Check if create Role already exist
		if (duplicataRole != null) {
			throw new DuplicateRecordException("Course already exists");
		}

		String query = "insert into st_course values(?,?,?,?,?,?,?,?)";
		try {
			pk = nextPK();
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setLong(1, pk);
			ps.setString(2, b.getCourseName());
			ps.setString(3, b.getDuration());
			ps.setString(4, b.getDescription());

			ps.setString(5, b.getCreatedBy());
			ps.setString(6, b.getModifiedBy());
			ps.setTimestamp(7, b.getCreatedDatetime());
			ps.setTimestamp(8, b.getModifiedDatetime());

			int a = ps.executeUpdate();
			System.out.println("ok:");
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
	 * update course information
	 * 
	 * @param b
	 * @throws ApplicationException
	 */
	public void update(CourseDTO dto) throws ApplicationException {
		System.out.println("hello" + dto.getId());
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement("UPDATE st_course SET COURSE_NAME=?,DESCRIPTION=?,DURATION=?"
					+ ",CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?," + "MODIFIED_DATETIME=? WHERE ID=?");
			con.setAutoCommit(false);

			ps.setString(1, dto.getCourseName());
			ps.setString(2, dto.getDescription());
			ps.setString(3, dto.getDuration());
			ps.setString(4, dto.getCreatedBy());
			ps.setString(5, dto.getModifiedBy());
			ps.setTimestamp(6, dto.getCreatedDatetime());
			ps.setTimestamp(7, dto.getModifiedDatetime());
			ps.setLong(8, dto.getId());
			ps.executeUpdate();
			System.out.println("DATA UPDATE ");
			ps.close();
			con.commit();
		} catch (Exception e) {
			throw new ApplicationException("exception in course model update...." + e.getMessage());
		}
		JDBCDataSource.closeConnection(con);

	}

	/**
	 * delete course information in table
	 * 
	 * @param b
	 * @throws ApplicationException
	 */
	public void delete(CourseDTO b) throws ApplicationException {
		log.debug("model delete start");
		String query = " delete  from st_course where ID=?";
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(query);
			ps.setLong(1, b.getId());
			int a = ps.executeUpdate();

			System.out.print("ok " + a);
			ps.close();
			con.commit();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete course");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model delete Started");
	}

	public List list() throws ApplicationException {

		return list(0, 0);
	}

	/**
	 * to show course list
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_course");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		CourseDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setDuration(rs.getString(3));
				dto.setDescription(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));

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

	public List search(CourseDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * search list of course detail
	 * 
	 * @param cdto1
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List search(CourseDTO cdto1, int pageNo, int pageSize) throws ApplicationException {
		log.debug("model search start");
		StringBuffer sql = new StringBuffer("select * from st_course where 1=1");
		if (cdto1 != null) {

			if (cdto1.getId() > 0) {
				sql.append(" AND ID = " + cdto1.getId());
			}
			if ((cdto1.getCourseName() != null) && (cdto1.getCourseName().length() > 0)) {
				sql.append(" AND COURSE_NAME like '" + cdto1.getCourseName() + "%'");
			}
			if ((cdto1.getDescription() != null) && (cdto1.getDescription().length() > 0)) {
				sql.append(" AND DESCRIPTION like '" + cdto1.getDescription() + "%'");

			}
			if ((cdto1.getDuration() != null) && (cdto1.getDuration().length() > 0)) {
				sql.append(" AND DURATION like '" + cdto1.getDuration() + "%'");

			}
		}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<CourseDTO> ar = new ArrayList<CourseDTO>();
		Connection con = null;
		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CourseDTO dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setDuration(rs.getString(3));
				dto.setDescription(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));
				ar.add(dto);
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search course");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model search End");

		return ar;

	}

	/**
	 * find information by pk
	 * 
	 * @param pk
	 * @return dto
	 * @throws ApplicationException
	 */
	public CourseDTO findByPK(long pk) throws ApplicationException {
		log.debug("model findby pk start");
		CourseDTO dto = null;
		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_course where ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setDuration(rs.getString(3));
				dto.setDescription(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));
			}
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting courseBYPk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("model findBy pk end");

		return dto;

	}

	/**
	 * find course by name
	 * 
	 * @param Name
	 * @return dto
	 * @throws ApplicationException
	 */
	public CourseDTO findByName(String Name) throws ApplicationException {
		// log.debug("Model findByPk Start");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_course WHERE COURSE_NAME=?");
		CourseDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, Name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setDuration(rs.getString(3));
				dto.setDescription(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

}
