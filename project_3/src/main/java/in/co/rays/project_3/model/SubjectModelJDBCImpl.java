package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.CourseDTO;
import in.co.rays.project_3.dto.SubjectDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of Subject model
 * @author Anshul Prajapati
 *
 */
public class SubjectModelJDBCImpl implements SubjectModelInt {

	private static Logger log = Logger.getLogger(SubjectModelJDBCImpl.class);
	Connection con = null;

	/**
	 * create id
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public long nextPK() throws DatabaseException {
		long pk = 0;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(ID) from st_subject");
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

	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		long pk = 0;
		/*
		 * CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
		 * SubjectModelInt model2 =
		 * ModelFactory.getInstance().getSubjectModel();
		 * 
		 * CourseDTO cBean = model1.findByPK(dto.getCourseId());
		 * dto.setCourseName(cBean.getCourseName());
		 * 
		 * SubjectDTO duplicataSub = model2.findByName(dto.getSubjectName());
		 * System.out.println("subject duplicate    "+duplicataSub); // Check if
		 * create Subject already exist if (duplicataSub!= null &&
		 * duplicataSub.getSubjectName()!=null) { throw new
		 * DuplicateRecordException("Subject already exists");}
		 */

		try {
			pk = nextPK();
			System.out.println(pk);
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setLong(2, pk);
			ps.setString(3, dto.getCourseName());
			ps.setString(4, dto.getDescription());
			ps.setLong(5, dto.getCourseId());
			ps.setString(6, dto.getSubjectName());
			ps.setString(7, dto.getCreatedBy());
			ps.setString(8, dto.getModifiedBy());
			ps.setTimestamp(9, dto.getCreatedDatetime());
			ps.setTimestamp(10, dto.getModifiedDatetime());
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
		return pk;

	}

	/**
	 * delete subject
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(SubjectDTO dto) throws ApplicationException {
		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("delete from st_subject where ID=?");
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
			throw new ApplicationException("Exception : Exception in delete subject");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model delete Started");

	}

	/**
	 * find subject by email id
	 * 
	 * @param email
	 * @return bean
	 * @throws ApplicationException
	 */
	public SubjectDTO findByEmailId(String email) throws ApplicationException {
		SubjectDTO dto = null;
		Connection con = null;
		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_subject where EMAIL_ID=?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new SubjectDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setDescription(rs.getString(4));
				dto.setCourseId(rs.getLong(5));
				dto.setSubjectName(rs.getString(6));

			}
			ps.close();
			con.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by emailId");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model findBy EmailId End");

		return dto;

	}

	public void update(SubjectDTO dto) throws ApplicationException {

		/*
		 * CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
		 * CourseDTO cBean = model1.findByPk(dto.getCourseId());
		 * dto.setCourseName(cBean.getCourseName());
		 * System.out.println("update.....+"+dto.getDescription()+",...."+dto.
		 * getId()+",,,"+dto.getCourseName());
		 */
		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(
					"update st_subject set SUBJECT_NAME=?,COURSE_NAME=?,COURSE_ID=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=?");

			ps.setString(1, dto.getSubjectName());
			ps.setString(2, dto.getCourseName());
			ps.setLong(3, dto.getCourseId());
			ps.setString(4, dto.getDescription());
			ps.setString(5, dto.getCreatedBy());
			ps.setString(6, dto.getModifiedBy());
			ps.setTimestamp(7, dto.getCreatedDatetime());
			ps.setTimestamp(8, dto.getModifiedDatetime());
			ps.setLong(9, dto.getId());
			System.out.println("update data successfully");
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
			throw new ApplicationException("Exception in updating subject ");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	/**
	 * find subject by pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public SubjectDTO findByPK(long pk) throws ApplicationException {

		SubjectDTO dto = null;
		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_subject where ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new SubjectDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setDescription(rs.getString(4));
				dto.setCourseId(rs.getLong(5));
				dto.setSubjectName(rs.getString(6));
			}
			ps.close();
			con.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting subject by pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("model findBy pk end");

		return dto;

	}

	/**
	 * find subject by name
	 * 
	 * @param name
	 * @return bean
	 * @throws ApplicationException
	 */
	public SubjectDTO findByName(String name) throws ApplicationException {
		SubjectDTO dto = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement("SELECT * FROM st_subject WHERE SUBJECT_NAME=?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			con.commit();

			System.out.println("before rs.next  findbyname");
			while (rs.next()) {
				System.out.println("inside rs.next  findbyname");
				dto = new SubjectDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setDescription(rs.getString(4));
				dto.setCourseId(rs.getLong(5));
				dto.setSubjectName(rs.getString(6));
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			throw new ApplicationException("exception in subject model findByName...." + e.getMessage());
		}

		System.out.println("before return  findbyname");
		JDBCDataSource.closeConnection(con);
		return dto;
	}

	public List list() throws ApplicationException {

		return list(0, 0);
	}

	/**
	 * list of subject
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_subject");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		SubjectDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new SubjectDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setDescription(rs.getString(4));
				dto.setCourseId(rs.getLong(5));
				dto.setSubjectName(rs.getString(6));
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

	public List search(SubjectDTO bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * search subject
	 * 
	 * @param dto1
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApplicationException
	 */
	public List search(SubjectDTO dto1, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_subject where 1=1");
		if (dto1 != null) {
			if (dto1.getId() > 0) {
				sql.append(" AND ID = " + dto1.getId());
			}
			if ((dto1.getSubjectName() != null) && (dto1.getSubjectName().length() > 0)) {
				sql.append(" AND SUBJECT_NAME like '" + dto1.getSubjectName() + "%'");
			}
			if ((dto1.getCourseName() != null) && (dto1.getCourseName().length() > 0)) {
				sql.append(" AND COURSE_NAME like '" + dto1.getCourseName() + "%'");

			}
			if (dto1.getCourseId() > 0) {
				sql.append(" AND COURSE_ID = " + dto1.getCourseId());
			}
			if (dto1.getSubjectId() > 0) {
				sql.append(" AND SUBJECT_ID = " + dto1.getSubjectId());
			}
			if ((dto1.getDescription() != null) && (dto1.getDescription().length() > 0)) {
				sql.append(" AND DESCRIPTION like '" + dto1.getDescription() + "%'");

			}
		}
		List list = new ArrayList();
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SubjectDTO dto = new SubjectDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setDescription(rs.getString(4));
				dto.setCourseId(rs.getLong(5));
				dto.setSubjectName(rs.getString(6));
				list.add(dto);
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in subject Student");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model search End");

		return list;

	}

}
