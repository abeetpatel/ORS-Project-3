package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.MarksheetDTO;
import in.co.rays.project_3.dto.StudentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of marksheet model
 * 
 * @author Abeet Patel
 *
 */
public class MarksheetModelHibImp implements MarksheetModelInt {

	public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = HibDataSource.getSession();
		Transaction tx = null;

		// get Student Name
		StudentModelInt sModel = ModelFactory.getInstance().getStudentModel();
		StudentDTO studentDTO = sModel.findByPK(dto.getStudentId());
		dto.setName(studentDTO.getFirstName() + " " + studentDTO.getLastName());

		MarksheetDTO duplicateMarksheet = findByRollNo(dto.getRollNo());

		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("Roll Number already exists!");
		}

		long pk = 0;
		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in marksheet Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	public void delete(MarksheetDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		MarksheetDTO dtoExist = fingByPK(dto.getId());
		if (dtoExist == null) {
			throw new ApplicationException("Marksheet does not exist!");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Marksheet Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;

		// MarksheetDTO dtoExist = findByRollNo(dto.getRollNo());

		// Check if updated Roll no already exist

		/*
		 * if (dtoExist != null && dtoExist.getId() == dto.getId()) { throw new
		 * DuplicateRecordException("Roll No is already exist"); }
		 */

		// get Student Name
		StudentModelInt sModel = ModelFactory.getInstance().getStudentModel();
		StudentDTO studentDTO = sModel.findByPK(dto.getStudentId());
		dto.setName(studentDTO.getFirstName() + " " + studentDTO.getLastName());

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			// session.merge(dto);
			// session.saveOrUpdate(dto);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
				throw new ApplicationException("Exception in Marksheet Update" + e.getMessage());
			}
		} finally {
			session.close();
		}
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			if (pageSize > 0) {

				pageNo = ((pageNo - 1) * pageSize) + 1;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();

		} catch (Exception e) {

			throw new ApplicationException("Exception in  Marksheet List" + e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}

	public List search(MarksheetDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(MarksheetDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));

			}
			if (dto.getRollNo() != null && dto.getRollNo().length() > 0) {
				criteria.add(Restrictions.like("rollNo", dto.getRollNo() + "%"));
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
			}
			if (dto.getPhysics() != null && dto.getPhysics() > 0) {
				criteria.add(Restrictions.eq("physics", dto.getPhysics()));
			}
			if (dto.getChemistry() != null && dto.getChemistry() > 0) {
				criteria.add(Restrictions.eq("chemistry", dto.getChemistry()));
			}
			if (dto.getMaths() != null && dto.getMaths() > 0) {
				criteria.add(Restrictions.eq("maths", dto.getMaths()));
			}
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Marksheet Search " + e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}

	public MarksheetDTO fingByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		MarksheetDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (MarksheetDTO) session.get(MarksheetDTO.class, pk);

		} catch (Exception e) {

			throw new ApplicationException("Exception in getting Marksheet by pk" + e.getMessage());

		} finally {
			session.close();
		}
		return dto;
	}

	public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		MarksheetDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			criteria.add(Restrictions.eq("rollNo", rollNo));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (MarksheetDTO) list.get(0);

			}
		} catch (Exception e) {

			throw new ApplicationException("Exception in getting Marksheet by pk" + e.getMessage());

		} finally {
			session.close();
		}
		return dto;
	}

	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			StringBuffer hql = new StringBuffer(
					"from MarksheetDTO where physics >=33 and chemistry >=33 and maths >=33 order by (physics+chemistry+maths) desc");
			/*
			 * if (pageSize > 0) { pageNo = (pageNo - 1) * pageSize; hql.append(" limit " +
			 * pageNo + "," + pageSize); }
			 */
			Query query = session.createQuery(hql.toString()).setMaxResults(pageSize);
			list = query.list();

		} catch (Exception e) {

			throw new ApplicationException("Exception in  marksheet list" + e.getMessage());
		} finally {
			session.close();
		}

		return list;
	}

}
