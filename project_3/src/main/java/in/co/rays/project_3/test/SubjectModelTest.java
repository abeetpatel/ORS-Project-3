package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.StudentDTO;
import in.co.rays.project_3.dto.SubjectDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.SubjectModelHibImp;
import in.co.rays.project_3.model.SubjectModelInt;
import in.co.rays.project_3.model.SubjectModelJDBCImpl;

public class SubjectModelTest {
	public static SubjectModelInt model = new SubjectModelHibImp();

	// public static SubjectModelInt model=new SubjectModelJDBCImpl();
	public static void main(String[] args) throws Exception {
		// addTest();
		// deleteTest();
		// updateTest();
		// findByPKTest();
		// findByNameTest();
		// searchTest();
		listTest();

	}

	public static void listTest() throws ApplicationException {
		// TODO Auto-generated method stub
		SubjectDTO dto = new SubjectDTO();
		List list = new ArrayList();
		list = model.list(0, 0);

		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (SubjectDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubjectName());
			System.out.print("\t" + dto.getCourseId());
			System.out.print("\t" + dto.getCourseName());
			System.out.print("\t" + dto.getSubjectId());
			System.out.print("\t" + dto.getDescription());
			System.out.println(dto.getCreatedDatetime());
			System.out.println(dto.getModifiedBy());
			System.out.println(dto.getModifiedDatetime());
		}
	}

	public static void searchTest() throws ApplicationException {
		// TODO Auto-generated method stub
		SubjectDTO dto1 = new SubjectDTO();
		dto1.setId(1L);
		dto1.setSubjectName("maths");
		// dto1.setCourseId(1);
		// dto1.setCourseName("be");

		ArrayList<SubjectDTO> a = (ArrayList<SubjectDTO>) model.search(dto1, 0, 0);
		for (SubjectDTO dto : a) {

			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubjectName());
			System.out.print("\t" + dto.getCourseId());
			System.out.print("\t" + dto.getCourseName());
			System.out.print("\t" + dto.getSubjectId());
			System.out.print("\t" + dto.getDescription());
		}
	}

	public static void findByNameTest() throws ApplicationException {
		// TODO Auto-generated method stub
		SubjectDTO dto = model.findByName("maths");
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectName());
		System.out.print("\t" + dto.getCourseId());
		System.out.print("\t" + dto.getCourseName());
		System.out.print("\t" + dto.getSubjectId());
		System.out.print("\t" + dto.getDescription());
	}

	public static void findByPKTest() throws ApplicationException {
		// TODO Auto-generated method stub
		SubjectDTO dto = model.findByPK(1L);
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectName());
		System.out.print("\t" + dto.getCourseId());
		System.out.print("\t" + dto.getCourseName());
		System.out.print("\t" + dto.getSubjectId());
		System.out.print("\t" + dto.getDescription());
	}

	public static void updateTest() throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		SubjectDTO dto = new SubjectDTO();
		dto.setId(1L);
		dto.setSubjectName("maths");
		dto.setCourseId(1);
		dto.setCourseName("bsc");
		dto.setDescription("hello");
		dto.setCreatedBy("dsfsd");
		dto.setModifiedBy("dsf");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
	}

	public static void deleteTest() throws ApplicationException {
		// TODO Auto-generated method stub
		SubjectDTO dto = new SubjectDTO();
		dto.setId(2L);
		model.delete(dto);
	}

	public static void addTest() throws Exception {
		// TODO Auto-generated method stub
		SubjectDTO dto = new SubjectDTO();
		dto.setSubjectId(1);
		dto.setSubjectName("science");
		dto.setCourseId(1);
		dto.setCourseName("be");
		dto.setDescription("hello");
		dto.setCreatedBy("dsfsd");
		dto.setModifiedBy("dsf");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = model.add(dto);
	}
}
