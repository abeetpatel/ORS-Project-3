package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CollegeDTO;
import in.co.rays.project_3.dto.StudentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Student model
 * @author Abeet Patel
 *
 */
public class StudentModelHibImp implements StudentModelInt {

	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = HibDataSource.getSession();
		Transaction tx = null;

		CollegeModelInt collegemod = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegedto = collegemod.findByPK(dto.getCollegeId());
		dto.setCollegeName(collegedto.getName());
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
			throw new ApplicationException("Exception in Student Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	public void delete(StudentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
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
			throw new ApplicationException("Exception in Student Delete" + e.getMessage());
		} finally {
			session.close();
		}

	}

	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		/*
		 * StudentDTO dtoExist = findByEmailId(dto.getEmailId());
		 * 
		 * // Check if updated Email already exist if (dtoExist != null &&
		 * dtoExist.getId() != dto.getId()) { throw new
		 * DuplicateRecordException("Email is already exist"); }
		 */
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
				throw new ApplicationException("Exception in Student Update" + e.getMessage());
			}
		} finally {
			session.close();
		}
	}

	public StudentDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		System.out.println(pk+"-==================----------");
		Session session = HibDataSource.getSession();
		StudentDTO dto = null;
		try {
			dto = (StudentDTO) session.get(StudentDTO.class, pk);
			System.out.println(dto);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting Student by pk");
		} finally {
			session.close();
		}
		return dto;
	}

	public StudentDTO findByEmailId(String emailId) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=HibDataSource.getSession();
		StudentDTO dto=null;
		try {
			Criteria criteria=session.createCriteria(StudentDTO.class);
			criteria.add(Restrictions.eq("emailId", emailId));
			List list=criteria.list();
			if(list.size()==1){
				dto=(StudentDTO) list.get(0);
			}
		} catch (HibernateException e) {
           
            throw new ApplicationException(
                    "Exception in getting Student by email " + e.getMessage());

        } finally {
            session.close();
        }
		return dto;
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
	            Criteria criteria = session.createCriteria(StudentDTO.class);

	            // if page size is greater than zero then apply pagination
	            if (pageSize > 0) {
	                pageNo = ((pageNo - 1) * pageSize) + 1;
	                criteria.setFirstResult(pageNo);
	                criteria.setMaxResults(pageSize);
	            }

	            list = criteria.list();
	        } catch (HibernateException e) {
	           
	            throw new ApplicationException(
	                    "Exception : Exception in  Student list");
	        } finally {
	            session.close();
	        }

	       
	        return list;
	}

	public List search(StudentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		System.out.println("llllllll"+dto.getCollegeId()+"....."+dto.getFirstName()+"''"+dto.getEmailId());
		Session session = null;
        List list = null;
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(StudentDTO.class);
           if(dto!=null){
            if (dto.getId() != null ) {
                criteria.add(Restrictions.eq("id", dto.getId()));
            }
            if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
                criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
            }
            if (dto.getEmailId() != null && dto.getEmailId().length() > 0) {
                criteria.add(Restrictions.like("emailId", dto.getEmailId()
                        + "%"));
            }
            if (dto.getLastName() != null && dto.getLastName().length() > 0) {
                criteria.add(Restrictions.like("lastName", dto.getLastName()
                        + "%"));
            }
            if (dto.getDob() != null && dto.getDob().getDate() > 0) {
                criteria.add(Restrictions.eq("dob", dto.getDob()));
            }
            if (dto.getCollegeId() >0 ) {
                criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
            }
            
            if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
                criteria.add(Restrictions.like("mobileNo", dto.getMobileNo()
                        + "%"));
            }
           }
            // if page size is greater than zero the apply pagination
            if (pageSize > 0) {
                criteria.setFirstResult(((pageNo - 1) * pageSize));
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (HibernateException e) {
           
            throw new ApplicationException("Exception in Student search");
        } finally {
            session.close();
        }

       return list;
	}

}
