package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.RoleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of Role model
 * @author Abeet Patel
 *
 */
public class RoleModelJDBCImpl implements RoleModelInt{

	private static Logger log = Logger.getLogger(RoleModelJDBCImpl.class);

	/**
	 * create id 
	 * @return pk
	 * @throws DatabaseException
	 */
	public long nextPK() throws DatabaseException {
		Connection con = null;
		long pk = 0;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(ID) from st_role");
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
	 * add new role 
	 * @param rdto
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(RoleDTO rdto) throws  ApplicationException, DuplicateRecordException {
		Connection con = null;
		long pk = 0;
		RoleDTO duplicataRole = findByName(rdto.getName());
		if (duplicataRole != null) {
			throw new DuplicateRecordException("Role already exists");
		}
		try {
			pk=nextPK();
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, rdto.getName());
			ps.setString(3, rdto.getDescription());
			ps.setString(4, rdto.getCreatedBy());
			ps.setString(5, rdto.getModifiedBy());
			ps.setTimestamp(6, rdto.getCreatedDatetime());
			ps.setTimestamp(7, rdto.getModifiedDatetime());
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
	 * delete role
	 * @param rdto
	 * @throws ApplicationException
	 */
	public void delete(RoleDTO rdto) throws ApplicationException {
		Connection con = null;
		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("delete from st_role where ID=?");
			ps.setLong(1, rdto.getId());
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
			throw new ApplicationException("Exception : Exception in delete roleO");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model delete Started");

	}

	/**
	 * update role 
	 * @param rdto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(RoleDTO rdto) throws ApplicationException, DuplicateRecordException {
		Connection con = null;
		RoleDTO duplicataRole = findByName(rdto.getName());
		if (duplicataRole != null && duplicataRole.getId() != rdto.getId()) {
			throw new DuplicateRecordException("Role already exists");
		}
		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(
					"update st_role set NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=?");
			ps.setString(1, rdto.getName());
			ps.setString(2, rdto.getDescription());
			ps.setString(3, rdto.getCreatedBy());
			ps.setString(4, rdto.getModifiedBy());
			ps.setTimestamp(5, rdto.getCreatedDatetime());
			ps.setTimestamp(6, rdto.getModifiedDatetime());
			ps.setLong(7, rdto.getId());

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
			throw new ApplicationException("Exception in updating role ");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	public List list() throws ApplicationException {

		return list(0, 0);
	}

	
	/**
	 *list of role
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_role");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		RoleDTO dto=null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				 dto = new RoleDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
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

	/**
	 * find by role with the help of role
	 * @param pk
	 * @return dto
	 * @throws ApplicationException
	 */
	public RoleDTO findByPK(long pk) throws ApplicationException {
		Connection con = null;
		RoleDTO rdto = null;
		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_role where ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rdto = new RoleDTO();
				rdto.setId(rs.getLong(1));
				rdto.setName(rs.getString(2));
				rdto.setDescription(rs.getString(3));

			}
			ps.close();
			con.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting role by pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("model findBy pk end");

		return rdto;

	}

	/**
	 * find role with the help of name
	 * @param name
	 * @return dto
	 * @throws ApplicationException
	 */
	public RoleDTO findByName(String name) throws ApplicationException {
		Connection con = null;
		RoleDTO rdto = null;
		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_role where NAME=?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rdto = new RoleDTO();
				rdto.setId(rs.getLong(1));
				rdto.setName(rs.getString(2));
				rdto.setDescription(rs.getString(3));

			}
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by emailId");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model findBy EmailId End");

		return rdto;

	}

	public List search(RoleDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * search role
	 * @param rdto1
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public ArrayList<RoleDTO> search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException{
	    //log.debug("Model search Started");
	    StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

	    if (dto != null) {
	        if (dto.getId() > 0) {
	            sql.append(" AND ID = " + dto.getId());
	        }
	        if (dto.getName() != null && dto.getName().length() > 0) {
	            sql.append(" AND NAME like '" + dto.getName() + "%'");
	        }
	       
	    }

	    // if page size is greater than zero then apply pagination
	    if (pageSize > 0) {
	        // Calculate start record index
	        pageNo = (pageNo - 1) * pageSize;

	        sql.append(" Limit " + pageNo + ", " + pageSize);
	        // sql.append(" limit " + pageNo + "," + pageSize);
	    }

	    System.out.println(sql);
	    ArrayList<RoleDTO> list = new ArrayList<RoleDTO>();
	    Connection conn = null;
	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) 
	        {
	        	
	            dto = new RoleDTO();
	            dto.setId(rs.getLong(1));
	            dto.setName(rs.getString(2));
	            dto.setDescription(rs.getString(3));
         

	            list.add(dto);
	        }
	        rs.close();
	    } catch (Exception e) {
	    	throw new ApplicationException("exception in role model  search"+e.getMessage());
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }

	    //log.debug("Model search End");
	    return list;
	}

	
	
	
}