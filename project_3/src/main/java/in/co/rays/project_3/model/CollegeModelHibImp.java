package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CollegeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of college model
 * @author Abeet Patel
 *
 */
public class CollegeModelHibImp implements CollegeModelInt {

	public long add(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		CollegeDTO duplicateCollegeName = fingByName(dto.getName());
		if (duplicateCollegeName != null) {
			throw new DuplicateRecordException("college name already exist");
		}
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in college Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(CollegeDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in college Delete" + e.getMessage());
		} finally {
			session.close();
		}

	}

	public void update(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		CollegeDTO dtoExist = fingByName(dto.getName());

		// Check if updated College already exist
		/*
		 * if (dtoExist != null && dtoExist.getId() != dto.getId()) { throw new
		 * DuplicateRecordException("College is already exist"); }
		 */
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			System.out.println("before update");
			
			session.saveOrUpdate(dto);
			System.out.println("after update");
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in college update" + e.getMessage());
		} finally {
			session.close();
		}

	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  College list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(CollegeDTO dto) throws ApplicationException {
				return search(dto, 0, 0);
	}

	public List search(CollegeDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));

			}
			if(dto.getName()!=null&&dto.getName().length()>0){
				criteria.add(Restrictions.like("name", dto.getName()+"%"));
			}
			if(dto.getAddress()!=null&&dto.getAddress().length()>0){
				criteria.add(Restrictions.like("address", dto.getAddress()+"%"));
			}
			if(dto.getState()!=null&&dto.getState().length()>0){
				criteria.add(Restrictions.like("state", dto.getState()+"%"));
			}
			if(dto.getCity()!=null&&dto.getCity().length()>0){
				criteria.add(Restrictions.like("city", dto.getCity()+"%"));
			}
			if(pageSize>0){
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
				
			}
              list=criteria.list();
		}catch (HibernateException e) {
           e.printStackTrace();
            throw new ApplicationException("Exception in college search");
        } finally {
            session.close();
        }
		return list;
	}

	public CollegeDTO findByPK(long pk) throws ApplicationException {
		System.out.println("======"+pk+"----------------------------------");
		Session session = null;
		CollegeDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (CollegeDTO) session.get(CollegeDTO.class, pk);
			System.out.println(dto);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting course by pk");
		} finally {
			session.close();
		}
		System.out.println("++++"+dto);
		return dto;
	}

	public CollegeDTO fingByName(String name) throws ApplicationException {
		Session session=null;
		CollegeDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(CollegeDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list=criteria.list();
			if(list.size()==1){
				dto=(CollegeDTO) list.get(0);
			}
		} catch (HibernateException e) {
            
            throw new ApplicationException(
                    "Exception in getting User by Login " + e.getMessage());

        } finally {
            session.close();
        }
		return dto;
	}

}
