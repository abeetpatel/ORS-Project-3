package in.co.rays.project_3.model;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CourseDTO;
import in.co.rays.project_3.dto.SubjectDTO;
import in.co.rays.project_3.dto.TimetableDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of TimeTable model
 * 
 * @author Anshul Prajapati
 *
 */
public class TimetableModelHibImp implements TimetableModelInt {

	public long add(TimetableDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		CourseModelInt Cmodel = ModelFactory.getInstance().getCourseModel();
		CourseDTO Cbean = null;
		Cbean = Cmodel.findByPK(dto.getCourseId());
		dto.setCourseName(Cbean.getCourseName());

		SubjectModelInt Smodel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO Sbean = Smodel.findByPK(dto.getSubId());
		dto.setSubName(Sbean.getSubjectName());

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in timetable Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	public void delete(TimetableDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Timetable delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(TimetableDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		CourseModelInt Cmodel = ModelFactory.getInstance().getCourseModel();
		CourseDTO Cbean = null;
		Cbean = Cmodel.findByPK(dto.getCourseId());
		dto.setCourseName(Cbean.getCourseName());

		SubjectModelInt Smodel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO Sbean = Smodel.findByPK(dto.getSubId());
		dto.setSubName(Sbean.getSubjectName());

		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in timetable update " + e.getMessage());
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
			Criteria criteria = session.createCriteria(TimetableDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  TimetableDTO list");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(TimetableDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(TimetableDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		System.out.println("kkkkkkkk" + dto.getCourseId() + "....." + dto.getSubId() + ";;;;;" + dto.getExamDate());

		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			if (dto != null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
					criteria.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
				}
				if (dto.getSubName() != null && dto.getSubName().length() > 0) {
					criteria.add(Restrictions.like("subName", dto.getSubName() + "%"));
				}
				if (dto.getSemester() != null && dto.getSemester().length() > 0) {
					criteria.add(Restrictions.like("semester", dto.getSemester() + "%"));
				}
				if (dto.getExamDate() != null && dto.getExamDate().getDate() > 0) {
					criteria.add(Restrictions.eq("examDate", dto.getExamDate()));
				}
				if (dto.getSubId() > 0) {
					criteria.add(Restrictions.eq("subId", dto.getSubId()));
				}
				if (dto.getCourseId() > 0) {
					criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
				}
			}
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in course search");
		} finally {
			session.close();
		}
		return list;
	}

	public TimetableDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		TimetableDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (TimetableDTO) session.get(TimetableDTO.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting TimetableDTO by pk");
		} finally {
			session.close();
		}
		return dto;
	}

	public TimetableDTO checkByCourseName(long courseId, java.util.Date examDate)
			throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		Session session = null;
		TimetableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			criteria.add(Restrictions.eq("courseId", courseId));
			criteria.add(Restrictions.eq("examdate", date));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimetableDTO) list.get(0);
			}
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting TimetableDTO by pk");
		} finally {
			session.close();
		}
		return dto;
	}

	public TimetableDTO checkBySubjectName(long courseId, long subjectId, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		Session session = null;
		TimetableDTO dto = null;
		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.eq("courseId", courseId));
			dis.add(Restrictions.eq("subId", subjectId));
			dis.add(Restrictions.eq("examDate", date));
			criteria.add(dis);
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimetableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in getting TimetableDTO by pk");
		} finally {
			session.close();
		}
		return dto;
	}

	public TimetableDTO checkBysemester(long courseId, long subjectId, String semester, java.util.Date examDate)
			throws ApplicationException, DuplicateRecordException {

		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		Session session = null;
		TimetableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.eq("courseId", courseId));
			dis.add(Restrictions.eq("subId", subjectId));
			dis.add(Restrictions.eq("semester", semester));
			dis.add(Restrictions.eq("examDate", date));
			criteria.add(dis);
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimetableDTO) list.get(0);
			}

		} catch (HibernateException e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in getting TimetableDTO by pk");
		} finally {
			session.close();
		}
		return dto;
	}

}
