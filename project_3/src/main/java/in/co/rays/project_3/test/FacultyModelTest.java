package in.co.rays.project_3.test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.FacultyDTO;
import in.co.rays.project_3.dto.StudentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.FacultyModelHibImp;
import in.co.rays.project_3.model.FacultyModelInt;

public class FacultyModelTest {
	//public static FacultyModelInt model=new FacultyModelJDBCImpl();
	public static FacultyModelInt model=new FacultyModelHibImp();
	public static void main(String[] args) throws Exception {
		addTest();
	//	deleteTest();
		//updateTest();
		//findByPKTest();
		//findByEmailidTest();
		//searchTest();
		//listTest();
		
	}
	public static void listTest() throws ApplicationException {
		// TODO Auto-generated method stub
		FacultyDTO dto=new FacultyDTO();
		 List list = new ArrayList();
	        list = model.list(0,0);
	        if (list.size() < 0) {
	            System.out.println("Test list fail");
	        }
	        Iterator it = list.iterator();
	        while (it.hasNext()) {
	            dto = (FacultyDTO) it.next();
	            System.out.println(dto.getId());
	            System.out.println(dto.getFirstName());
	            System.out.println(dto.getLastName());
	            System.out.println(dto.getDob());
	            System.out.println(dto.getMobileNo());
	            System.out.println(dto.getEmailId());
	            System.out.println(dto.getCollegeId());
	            System.out.println(dto.getCreatedBy());
	            System.out.println(dto.getCreatedDatetime());
	            System.out.println(dto.getModifiedBy());
	            System.out.println(dto.getModifiedDatetime());}
		
	}
	public static void searchTest() throws ApplicationException {
		// TODO Auto-generated method stub
		FacultyDTO dto1=new FacultyDTO();
		
		dto1.setId(1L);
		dto1.setFirstName("ayushii");
		// dto1.setLastName("agrawals");
		

		
		ArrayList<FacultyDTO> a = (ArrayList<FacultyDTO>) model.search(dto1);
		for (FacultyDTO dto : a) {
			System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t"
					+ dto.getGender() + "\t" + dto.getDob() + "\t" + dto.getQualification() + "\t"
					+ dto.getMobileNo() + "\t" + dto.getEmailId() + "\t" + dto.getCollegeId() + "\t"
					+ dto.getCollegeName() + "\t" + dto.getSubjectId() + "\t" + dto.getSubjectName() + "\t"
					+ dto.getCourseId() + "\t" + dto.getCourseName());

			}
	}
	public static void updateTest() throws ParseException, ApplicationException, DatabaseException, DuplicateRecordException {
		// TODO Auto-generated method stub
		FacultyDTO dto=new FacultyDTO();
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		dto.setId(1L);
		dto.setFirstName("ayushiii");
		dto.setLastName("agrawals");
		dto.setGender("female");
		dto.setDob(sdf.parse("02/12/2015"));
		dto.setQualification("be");
		dto.setEmailId("a@");
		dto.setMobileNo("98987887778");
		dto.setCollegeId(101);
		dto.setCollegeName("svce");
		dto.setCourseId(102);
		dto.setCourseName("maths");
		dto.setSubjectId(103);
		dto.setSubjectName("maths");
		model.update(dto);
	}
	public static void findByPKTest() throws ApplicationException {
		// TODO Auto-generated method stub
		FacultyDTO dto=model.findByPK(1L);
		System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t"
				+ dto.getGender() + "\t" + dto.getDob() + "\t" + dto.getQualification() + "\t"
				+ dto.getMobileNo() + "\t" + dto.getEmailId() + "\t" + dto.getCollegeId() + "\t"
				+ dto.getCollegeName() + "\t" + dto.getSubjectId() + "\t" + dto.getSubjectName() + "\t"
				+ dto.getCourseId() + "\t" + dto.getCourseName());
	}
	public static void findByEmailidTest() throws ApplicationException {
		// TODO Auto-generated method stub
		FacultyDTO dto=model.findByEmailId("a@");
		System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t"
				+ dto.getGender() + "\t" + dto.getDob() + "\t" + dto.getQualification() + "\t"
				+ dto.getMobileNo() + "\t" + dto.getEmailId() + "\t" + dto.getCollegeId() + "\t"
				+ dto.getCollegeName() + "\t" + dto.getSubjectId() + "\t" + dto.getSubjectName() + "\t"
				+ dto.getCourseId() + "\t" + dto.getCourseName());
		
	}
	public static void deleteTest() throws ApplicationException {
		// TODO Auto-generated method stub
		FacultyDTO dto=new FacultyDTO();
		dto.setId(2L);
		model.delete(dto);
	}
	public static void addTest() throws Exception {
		// TODO Auto-generated method stub
		FacultyDTO dto=new FacultyDTO();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		dto.setFirstName("ayushi");
		dto.setLastName("agrawal");
		dto.setGender("male");
		dto.setDob(sdf.parse("01/12/2015"));
		dto.setQualification("be");
		dto.setEmailId("hmmk@gmail.com");
		dto.setMobileNo("98987887778");
		dto.setCollegeId(1);
		dto.setCollegeName("svce");
		dto.setCourseId(1);
		dto.setCourseName("pcm");
		dto.setSubjectId(1);
		dto.setSubjectName("maths");
		model.add(dto);
	}

}
