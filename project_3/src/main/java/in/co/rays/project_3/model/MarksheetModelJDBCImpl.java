package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.MarksheetDTO;
import in.co.rays.project_3.dto.StudentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of Marksheet model
 * @author Abeet Patel
 *
 */
public class MarksheetModelJDBCImpl implements MarksheetModelInt {

	private static Logger log = Logger.getLogger(MarksheetModelJDBCImpl.class);

	/**
	 * add new id
	 * @return pk
	 * @throws DatabaseException
	 */
	public static long nextPK() throws DatabaseException {
		long nextPK = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID)FROM st_marksheet");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				nextPK = rs.getLong(1);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting in pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return nextPK + 1;
	}

	/**
	 * add new marksheet
	 * @param dto
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		long pk = 0;
		Connection conn = null;
		System.out.println("----kkkkk" + dto);
		
	  /*StudentModelInt sModel = ModelFactory.getInstance().getStudentModel();
		StudentDTO studentdto = sModel.findByPK(dto.getStudentId());
		dto.setName(studentdto.getFirstName() + " " + studentdto.getLastName());
		MarksheetDTO duplicateMarksheet = findByRollNo(dto.getRollNo());

		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("Roll Number already exists");
		}*/

		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, dto.getRollNo());
			ps.setLong(3, dto.getStudentId());
			ps.setInt(4, dto.getChemistry());
			ps.setInt(5, dto.getMaths());
			ps.setInt(6, dto.getPhysics());
			ps.setString(7, dto.getName());
			ps.setString(8, dto.getCreatedBy());
			ps.setString(9, dto.getModifiedBy());
			ps.setTimestamp(10, dto.getCreatedDatetime());
			ps.setTimestamp(11, dto.getModifiedDatetime());
			ps.execute();
			System.out.println("hhlllll");
			conn.commit();
			ps.close();
			
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student"+e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * update marksheet information
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		MarksheetDTO dtoExist = findByRollNo(dto.getRollNo());

	/*	if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Roll No is already exist");
		}
		StudentModelInt sModel = ModelFactory.getInstance().getStudentModel();
		
		StudentDTO studentdto = sModel.findByPK(dto.getStudentId());
		dto.setName(studentdto.getFirstName() + " " + studentdto.getLastName());*/
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update st_marksheet set ROLL_NO=?, STUDENT_ID=?, NAME=?, PHYSICS=?, CHEMISTRY=?, MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=? ");
			ps.setString(1, dto.getRollNo());
			ps.setLong(2, dto.getStudentId());
			ps.setString(3, dto.getName());
			ps.setInt(4, dto.getPhysics());
			ps.setInt(5, dto.getChemistry());
			ps.setInt(6, dto.getMaths());
			ps.setString(7, dto.getCreatedBy());
			ps.setString(8, dto.getModifiedBy());
			ps.setTimestamp(9, dto.getCreatedDatetime());
			ps.setTimestamp(10, dto.getModifiedDatetime());
			ps.setLong(11, dto.getId());
			ps.execute();
			ps.close();
			conn.commit();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating marksheet ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	
	/**
	 * delete marksheet information
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(MarksheetDTO dto) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_marksheet where ID=?");
			ps.setLong(1, dto.getId());
			ps.execute();
			ps.close();
			conn.commit();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	
	/**
	 * find information with the help of pk
	 * @param pk
	 * @return dto
	 * @throws ApplicationException
	 */
	public MarksheetDTO fingByPK(long pk) throws ApplicationException {
		MarksheetDTO dto = null;
		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_marksheet where ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new MarksheetDTO();

				dto.setId(rs.getLong(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setChemistry(rs.getInt(4));
				dto.setMaths(rs.getInt(5));
				dto.setPhysics(rs.getInt(6));
				dto.setName(rs.getString(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting marksheet by pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("model findBy pk end");

		return dto;

	}

	/**
	 * find information with the help of rollno
	 * @param rollNO
	 * @return dto
	 * @throws ApplicationException
	 */
	public MarksheetDTO findByRollNo(String rollNO) throws ApplicationException {
		MarksheetDTO dto = null;
		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_marksheet where ROLL_NO=?");
			ps.setString(1, rollNO);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new MarksheetDTO();
				dto.setId(rs.getLong(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setChemistry(rs.getInt(4));
				dto.setMaths(rs.getInt(5));
				dto.setPhysics(rs.getInt(6));
				dto.setName(rs.getString(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));

			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by marksheet");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model findByRollNo End");
		return dto;

	}

	public List search(MarksheetDTO dto) throws ApplicationException  {
		return search(dto, 0, 0);
	}

	/**
	 * get merit list
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		log.debug("marksheet model get merit list start");
		
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"select ID,ROLL_NO,NAME,PHYSICS,CHEMISTRY,MATHS (PHYSICS+CHEMISTRY+MATHS)as Total from st_marksheet order by Total desc ");
		if(pageSize>0){
			pageNo=(pageNo-1)*pageSize;
			sql.append(" limit "+pageNo+","+pageSize);
		}
		
		Connection con = null;
		MarksheetDTO dto =null;
		try {
			

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("heiuiujiou");
				dto = new MarksheetDTO();
				dto.setId(rs.getLong(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setChemistry(rs.getInt(4));
				dto.setMaths(rs.getInt(5));
				dto.setPhysics(rs.getInt(6));
				dto.setName(rs.getString(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));
				System.out.println("heiuiujiou"+dto.getId()+"jj"+dto.getChemistry()+"..."+dto.getPhysics()+"df"+dto.getName()+"jj"+dto.getRollNo());
				list.add(dto);
			}
		}catch (Exception e) {
            log.error(e);
            throw new ApplicationException(
                    "Exception in getting merit list of Marksheet");
        } finally {
            JDBCDataSource.closeConnection(con);
        }
        log.debug("Model  MeritList End");
        return list;
    } 
		
		
		
		

	/**
	 * search marksheet
	 * @param marksheet
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApplicationException
	 */
	public List search(MarksheetDTO marksheet, int pageNo, int pageSize) throws ApplicationException {
		Connection con = null;
         System.out.println("<<>>>>>>>>>>>>>"+marksheet.getRollNo());
		StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");
		if (marksheet != null) {
			if (marksheet.getId() > 0) {
				sql.append(" AND ID = " + marksheet.getId());
			}
			if ((marksheet.getRollNo() != null) && (marksheet.getRollNo().length() > 0)) {
				
				sql.append(" AND ROLL_NO like '" + marksheet.getRollNo() + "%'");
			}
			if (marksheet.getStudentId() > 0) {
				sql.append(" AND STUDENT_ID = " + marksheet.getStudentId());
			}
			if (marksheet.getName() != null && marksheet.getName().length() > 0) {
				sql.append(" AND NAME like '" + marksheet.getName() + "%'");
			}
			if (marksheet.getPhysics() > 0) {
				sql.append(" AND PHYSICS = " + marksheet.getPhysics());
			}
			if (marksheet.getChemistry() > 0) {
				sql.append(" AND CHEMISTRY = " + marksheet.getChemistry());
			}
			if (marksheet.getMaths() > 0) {
				sql.append(" AND MATHS = " + marksheet.getMaths());
			}
		}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		List list = new ArrayList();
		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MarksheetDTO dto = new MarksheetDTO();
				dto.setId(rs.getLong(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setChemistry(rs.getInt(4));
				dto.setMaths(rs.getInt(5));
				dto.setPhysics(rs.getInt(6));
				dto.setName(rs.getString(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			
			//throw new ApplicationException("Exception : Exception in search time table");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model search End");

		return list;

	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model  list Started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_marksheet");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MarksheetDTO dto = new MarksheetDTO();
				dto.setId(rs.getLong(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setChemistry(rs.getInt(4));
				dto.setMaths(rs.getInt(5));
				dto.setPhysics(rs.getInt(6));
				dto.setName(rs.getString(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception in getting list of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model  list End");
		return list;

	}

	
}
