package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.CollegeDTO;
import in.co.rays.project_3.dto.CourseDTO;
import in.co.rays.project_3.dto.FacultyDTO;
import in.co.rays.project_3.dto.SubjectDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of Faculty model
 * @author Abeet Patel
 *
 */
public class FacultyModelJDBCImpl implements FacultyModelInt {

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
			PreparedStatement ps = con.prepareStatement("select max(ID) from st_faculty");
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
	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		long pk = 0;

		/*
		 * CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		 * CollegeDTO dto1 = model.findByPK(dto.getCollegeId()); String
		 * CollegeName = dto1.getName();
		 * 
		 * 
		 * CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
		 * CourseDTO dto2 = model1.findByPK(dto.getCourseId()); String
		 * CourseName = dto2.getCourseName();
		 * 
		 * SubjectModelInt model2 =
		 * ModelFactory.getInstance().getSubjectModel(); SubjectDTO dto3 =
		 * model2.findByPK(dto.getSubjectId()); String SubjectName =
		 * dto3.getSubjectName();
		 * 
		 * FacultyDTO duplicataRole = findByEmailId(dto.getEmailId()); // Check
		 * if create Faculty already exist if (duplicataRole != null) { throw
		 * new DuplicateRecordException("Faculty already exists"); }
		 */

		java.util.Date d = dto.getDob();
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
			ps.setString(2, dto.getFirstName());
			ps.setString(3, dto.getLastName());

			ps.setString(4, dto.getQualification());
			//ps.setString(5, CollegeName);
			//ps.setString(6, CourseName);
			ps.setString(7, dto.getGender());
			ps.setDate(8, date);
			ps.setLong(9, dto.getCollegeId());
			ps.setString(10, dto.getEmailId());
			ps.setString(11, dto.getMobileNo());
            ps.setLong(12, dto.getCourseId());
            ps.setLong(13, dto.getSubjectId());
			//ps.setString(14, SubjectName);
			ps.setString(15, dto.getCreatedBy());
			ps.setString(16, dto.getModifiedBy());
			ps.setTimestamp(17, dto.getCreatedDatetime());
			ps.setTimestamp(18, dto.getModifiedDatetime());
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
	public FacultyDTO findByEmailId(String emailId) throws ApplicationException {
		FacultyDTO dto = null;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_faculty where EMAIL_ID=?");
			ps.setString(1, emailId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setQualification(rs.getString(4));
				dto.setCollegeName(rs.getString(5));
				dto.setCourseName(rs.getString(6));
				dto.setGender(rs.getString(7));
				dto.setDob(rs.getDate(8));
				dto.setCollegeId(rs.getLong(9));
				dto.setEmailId(rs.getString(10));
				dto.setMobileNo(rs.getString(11));
				dto.setCourseId(rs.getLong(12));
				dto.setSubjectId(rs.getLong(13));
				dto.setSubjectName(rs.getString(14));

			}
			ps.close();
			con.close();

		} catch (Exception e) {
			throw new ApplicationException("exception in faculty findByEmail  add..... " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	/**
	 * delete faculty
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(FacultyDTO dto) throws ApplicationException {
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
	public void update(FacultyDTO dto) throws DatabaseException, ApplicationException  {
		long pk = nextPK();
		java.util.Date d = dto.getDob();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);
		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(
					"update st_faculty set FIRST_NAME=?,LAST_NAME=?,GENDER=?,JOINING_DOB=?,QUALIFICATION=?,EMAIL_ID=?,MOBILE_NO=?,COLLEGE_ID=?,COLLEGE_NAME=?,SUBJECT_ID=?,SUBJECT_NAME=?,COURSE_ID=?,COURSE_NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=?");
			ps.setString(1, dto.getFirstName());
			ps.setString(2, dto.getLastName());
			ps.setString(3, dto.getGender());
			ps.setDate(4, date);
			ps.setString(5, dto.getQualification());
			ps.setString(6, dto.getEmailId());
			ps.setString(7, dto.getMobileNo());
			ps.setLong(8, dto.getCollegeId());
			ps.setString(9, dto.getCollegeName());
			ps.setLong(10, dto.getCourseId());
			ps.setString(11, dto.getCourseName());
			ps.setLong(12, dto.getSubjectId());
			ps.setString(13, dto.getSubjectName());
			ps.setString(14, dto.getCreatedBy());
			ps.setString(15, dto.getModifiedBy());
			ps.setTimestamp(16, dto.getCreatedDatetime());
			ps.setTimestamp(17, dto.getModifiedDatetime());
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
	public FacultyDTO findByPK(long pk) throws ApplicationException {

		FacultyDTO dto = null;
		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_faculty where ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setQualification(rs.getString(4));
				dto.setCollegeName(rs.getString(5));
				dto.setCourseName(rs.getString(6));
				dto.setGender(rs.getString(7));
				dto.setDob(rs.getDate(8));
				dto.setCollegeId(rs.getLong(9));
				dto.setEmailId(rs.getString(10));
				dto.setMobileNo(rs.getString(11));
				dto.setCourseId(rs.getLong(12));
				dto.setSubjectId(rs.getLong(13));
				dto.setSubjectName(rs.getString(14));
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
		FacultyDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setQualification(rs.getString(4));
				dto.setCollegeName(rs.getString(5));
				dto.setCourseName(rs.getString(6));
				dto.setGender(rs.getString(7));
				dto.setDob(rs.getDate(8));
				dto.setCollegeId(rs.getLong(9));
				dto.setEmailId(rs.getString(10));
				dto.setMobileNo(rs.getString(11));
				dto.setCourseId(rs.getLong(12));
				dto.setSubjectId(rs.getLong(13));
				dto.setSubjectName(rs.getString(14));
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

	public List search(FacultyDTO dto1) throws ApplicationException {
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
	public List search(FacultyDTO dto1, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");
		if (dto1 != null) {
			if (dto1.getId() > 0) {
				sql.append(" AND ID = " + dto1.getId());
			}
			if ((dto1.getFirstName() != null) && (dto1.getFirstName().length() > 0)) {
				sql.append(" AND FIRST_NAME like '" + dto1.getFirstName() + "%'");
			}
			if ((dto1.getLastName() != null) && (dto1.getLastName().length() > 0)) {
				sql.append(" AND LAST_NAME like '" + dto1.getLastName() + "%'");
			}
			if ((dto1.getGender() != null) && (dto1.getGender().length() > 0)) {
				sql.append(" AND GENDER like '" + dto1.getGender() + "%'");
			}
			if ((dto1.getDob() != null) && (dto1.getDob().getDate() > 0)) {
				sql.append(" AND DOB = " + dto1.getDob());
			}
			if ((dto1.getQualification() != null) && (dto1.getQualification().length() > 0)) {
				sql.append(" AND QUALIFICATION like '" + dto1.getQualification() + "%'");
			}
			if ((dto1.getEmailId() != null) && (dto1.getEmailId().length() > 0)) {
				sql.append(" AND EMAILID like '" + dto1.getEmailId() + "%'");
			}
			if ((dto1.getMobileNo() != null) && (dto1.getMobileNo().length() > 0)) {
				sql.append(" AND MOBILENO like '" + dto1.getMobileNo() + "%'");
			}
			if (dto1.getCollegeId() > 0) {
				sql.append(" AND COLLEGEID = " + dto1.getCollegeId());
			}

			if ((dto1.getCollegeName() != null) && (dto1.getCollegeName().length() > 0)) {
				sql.append(" AND COLLEGE_NAME like '" + dto1.getCollegeName() + "%'");
			}
			if (dto1.getCourseId() > 0) {
				sql.append(" AND COURSEID = " + dto1.getCourseId());
			}
			if ((dto1.getCourseName() != null) && (dto1.getCourseName().length() > 0)) {
				sql.append(" AND COURSE_NAME like '" + dto1.getCourseName() + "%'");

			}
			if (dto1.getSubjectId() > 0) {
				sql.append(" AND SUBJECTID = " + dto1.getSubjectId());
			}

			if ((dto1.getSubjectName() != null) && (dto1.getSubjectName().length() > 0)) {
				sql.append(" AND SUBJECTNAME like '" + dto1.getSubjectName() + "%'");
			}
		}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<FacultyDTO> ar = new ArrayList<FacultyDTO>();
		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FacultyDTO dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setQualification(rs.getString(4));
				dto.setCollegeName(rs.getString(5));
				dto.setCourseName(rs.getString(6));
				dto.setGender(rs.getString(7));
				dto.setDob(rs.getDate(8));
				dto.setCollegeId(rs.getLong(9));
				dto.setEmailId(rs.getString(10));
				dto.setMobileNo(rs.getString(11));
				dto.setCourseId(rs.getLong(12));
				dto.setSubjectId(rs.getLong(13));
				dto.setSubjectName(rs.getString(14));

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
