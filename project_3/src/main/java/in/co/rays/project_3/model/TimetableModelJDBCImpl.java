package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.CourseDTO;
import in.co.rays.project_3.dto.SubjectDTO;
import in.co.rays.project_3.dto.TimetableDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of TimeTable model
 * @author Anshul Prajapati
 *
 */
public class TimetableModelJDBCImpl implements TimetableModelInt {
	private static Logger log = Logger.getLogger(TimetableModelJDBCImpl.class);
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
			PreparedStatement ps = con.prepareStatement("select max(ID) from ST_TIMETABLE");
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
	 * add timetable
	 * 
	 * @param tdto
	 * @return
	 * @throws ApplicationException
	 */
	public long add(TimetableDTO tdto) throws ApplicationException {
		long pk = 0;
		java.util.Date d = tdto.getExamDate();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);

		/*// get course Name and Subject Name by id
		       CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
				
				CourseDTO cdto = null;
				cdto = model1.findByPK(tdto.getCourseId());
				tdto.setCourseName(cdto.getCourseName());

				SubjectModelInt model2 = ModelFactory.getInstance().getSubjectModel();
				
				SubjectDTO sdto = model2.findByPK(tdto.getSubId());
				tdto.setSubName(sdto.getSubjectName());*/

		try {
			pk = nextPK();
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("insert into ST_TIMETABLE values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setLong(2, tdto.getSubId());
			ps.setString(3, tdto.getCourseName());
			ps.setDate(4, date);
			ps.setString(5, tdto.getSubName());
			ps.setString(7, tdto.getSemester());
			ps.setString(8, tdto.getExamTime());
			ps.setLong(9, tdto.getCourseId());
			ps.setString(10, tdto.getCreatedBy());
			ps.setString(11, tdto.getModifiedBy());
			ps.setTimestamp(12, tdto.getCreatedDatetime());
			ps.setTimestamp(13, tdto.getModifiedDatetime());
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
	 * delete timetable
	 * 
	 * @param tdto
	 * @throws ApplicationException
	 */
	public void delete(TimetableDTO tdto) throws ApplicationException {
		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("delete from ST_TIMETABLE where ID=?");
			ps.setLong(1, tdto.getId());
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
			throw new ApplicationException("Exception : Exception in delete timetable");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model delete Started");
	}

	/**
	 * update timetable
	 * 
	 * @param tdto
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public void update(TimetableDTO tdto) throws DatabaseException, ApplicationException {
		long pk = nextPK();
		System.out.println("update start" + tdto.getSemester());
		java.util.Date d = tdto.getExamDate();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);
		System.out.println(date);
	/*	// get course Name and Subject Name by id
	       CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
			
			CourseDTO cdto = null;
			cdto = model1.findByPK(tdto.getCourseId());
			tdto.setCourseName(cdto.getCourseName());

			SubjectModelInt model2 = ModelFactory.getInstance().getSubjectModel();
			
			SubjectDTO sdto = model2.findByPK(tdto.getSubId());
			tdto.setSubName(sdto.getSubjectName());
*/
		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(
					"update ST_TIMETABLE set SUB_ID=?,SUB_NAME=?,COURSE_ID=?,COURSE_NAME=?,SEMESTER=?,EXAM_DATE=?,EXAM_TIME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=?");

			ps.setLong(1, tdto.getSubId());
			ps.setString(2, tdto.getSubName());
			ps.setLong(3, tdto.getCourseId());
			ps.setString(4, tdto.getCourseName());
			ps.setString(5, tdto.getSemester());
			ps.setDate(6, date);
			ps.setString(7, tdto.getExamTime());
			ps.setString(9, tdto.getCreatedBy());
			ps.setString(10, tdto.getModifiedBy());
			ps.setTimestamp(11, tdto.getCreatedDatetime());
			ps.setTimestamp(12, tdto.getModifiedDatetime());
			ps.setLong(13, tdto.getId());
			ps.executeUpdate();
			ps.close();
			con.commit();
			System.out.println(" time table update data successfully");

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating timetable ");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	/**
	 * find time table by pk
	 * 
	 * @param pk
	 * @return dto
	 * @throws ApplicationException
	 */
	public TimetableDTO findByPK(long pk) throws ApplicationException {

		TimetableDTO dto = null;
		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ST_TIMETABLE where ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new TimetableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setExamDate(rs.getDate(4));
				dto.setSubName(rs.getString(5));
				dto.setSemester(rs.getString(7));
				dto.setExamTime(rs.getString(8));
				dto.setCourseId(rs.getLong(9));

			}
			ps.close();
			con.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Timetable by pk");
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
	 * list of time table
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_timetable");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		TimetableDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimetableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setExamDate(rs.getDate(4));
				dto.setSubName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamTime(rs.getString(7));
				dto.setCourseId(rs.getLong(8));
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

	public List search(TimetableDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * search time table
	 * 
	 * @param tdto1
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApplicationException
	 */
	public List search(TimetableDTO tdto1, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from ST_TIMETABLE where 1=1");
		if (tdto1 != null) {
			if (tdto1.getId() > 0) {
				sql.append(" AND ID = " + tdto1.getId());
			}
			if (tdto1.getSubId() > 0) {
				sql.append(" AND SUB_ID = " + tdto1.getSubId());
			}
			if ((tdto1.getSubName() != null) && (tdto1.getSubName().length() > 0)) {
				sql.append(" AND SUB_NAME like '" + tdto1.getSubName() + "%'");
			}
			if (tdto1.getCourseId() > 0) {
				sql.append(" AND COURSE_ID = " + tdto1.getCourseId());
			}
			if ((tdto1.getCourseName() != null) && (tdto1.getCourseName().length() > 0)) {
				sql.append(" AND COURSE_NAME like '" + tdto1.getCourseName() + "%'");

			}
			if ((tdto1.getSemester() != null) && (tdto1.getSemester().length() > 0)) {
				sql.append(" AND SEMESTER like '" + tdto1.getSemester() + "%'");

			}
			if ((tdto1.getExamDate() != null) && (tdto1.getExamDate().getDate() > 0)) {
				Date date = new Date(tdto1.getExamDate().getTime());
				System.out.println(">>>>" + date);
				sql.append(" AND EXAM_DATE = '" + date + "'");
			}

			if ((tdto1.getExamTime() != null) && (tdto1.getExamTime().length() > 0)) {
				sql.append(" AND EXAM_TIME like '" + tdto1.getExamTime() + "%'");

			}
					}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList<TimetableDTO> a = new ArrayList<TimetableDTO>();

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TimetableDTO dto = new TimetableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setExamDate(rs.getDate(4));
				dto.setSubName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamTime(rs.getString(7));
				dto.setCourseId(rs.getLong(8));
				a.add(dto);
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search time table");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model search End");

		return a;

	}

	/**
	 * @param CourseId
	 * @param ExamDate
	 * @return
	 * @throws ApplicationException
	 */
	public  TimetableDTO checkByCourseName(long CourseId, java.util.Date ExamDate) throws ApplicationException {
		long l = ExamDate.getTime();
		java.sql.Date date = new java.sql.Date(l);

		PreparedStatement ps = null;
		ResultSet rs = null;
		TimetableDTO dto = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? " + "AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setDate(2, date);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new TimetableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setExamDate(rs.getDate(4));
				dto.setSubName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamTime(rs.getString(7));
				dto.setCourseId(rs.getLong(8));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkByCourseName..." + e.getMessage());
		}
		return dto;
	}

	/**
	 * @param CourseId
	 * @param SubjectId
	 * @param ExamDAte
	 * @return
	 * @throws ApplicationException
	 */
	public TimetableDTO checkBySubjectName(long CourseId, long SubjectId, java.util.Date examDate)
			throws ApplicationException {
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);

		PreparedStatement ps = null;
		ResultSet rs = null;
		TimetableDTO dto = null;
		
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUB_ID=? AND" + " EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setDate(3, date);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new TimetableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setExamDate(rs.getDate(4));
				dto.setSubName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamTime(rs.getString(7));
				dto.setCourseId(rs.getLong(8));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkBySubjectName..." + e.getMessage());
		}
		return dto;
	}

	/**
	 * @param CourseId
	 * @param SubjectId
	 * @param semester
	 * @param ExamDAte
	 * @throws ApplicationException 
	 * 
	 * 
	 */
	public  TimetableDTO checkBysemester(long CourseId, long SubjectId, String semester, java.util.Date ExamDAte) throws ApplicationException
			 {
		long l = ExamDAte.getTime();
		java.sql.Date date = new java.sql.Date(l);

		PreparedStatement ps = null;
		ResultSet rs = null;
		TimetableDTO dto = null;
		System.out.println("jjkkk" + CourseId + "jjj" + SubjectId + "kk" + semester + "kk" + ExamDAte);
		Date ExDate = new Date(ExamDAte.getTime());

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUB_ID=? AND" + " SEMESTER=? AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, date);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new TimetableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setExamDate(rs.getDate(4));
				dto.setSubName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamTime(rs.getString(7));
				dto.setCourseId(rs.getLong(8));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkBySubjectName..." + e.getMessage());
		}
		return dto;
	}

	/**
	 * @param ExamTime
	 * @param CourseId
	 * @param SubjectId
	 * @param semester
	 * @param ExamDAte
	 * @return
	 * @throws ApplicationException
	 */
	public  TimetableDTO checkByExamTime(long CourseId, long SubjectId, String semester, java.util.Date ExamDAte,
			String ExamTime) throws ApplicationException {

		long l = ExamDAte.getTime();
		java.sql.Date date = new java.sql.Date(l);

		PreparedStatement ps = null;
		ResultSet rs = null;
		TimetableDTO dto = null;
		Date ExDate = new Date(ExamDAte.getTime());
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUB_ID=? AND"
				+ " SEMESTER=? AND EXAM_DATE=? AND EXAM_TIME=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, date);
			ps.setString(5, ExamTime);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new TimetableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setExamDate(rs.getDate(4));
				dto.setSubName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamTime(rs.getString(7));
				dto.setCourseId(rs.getLong(8));

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkByexamTime..." + e.getMessage());
		}
		return dto;
	}

	

}
