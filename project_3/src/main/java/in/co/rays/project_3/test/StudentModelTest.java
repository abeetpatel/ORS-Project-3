package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.StudentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.StudentModelHibImp;
import in.co.rays.project_3.model.StudentModelInt;
import in.co.rays.project_3.model.StudentModelJDBCImpl;

public class StudentModelTest {
	/* public static StudentModelInt model = new StudentModelJDBCImpl(); */
	public static StudentModelInt model = new StudentModelHibImp();
	public static void main(String[] args) throws Exception {
		/* addTest(); */
		/* deleteTest(); */
		/* updateTest(); */ 
		/* findByPkTest(); */
		/* findByEmailIdTest(); */
		/* listTest(); */
		/* searchTest(); */
	}

	public static void searchTest() throws ApplicationException {
		// TODO Auto-generated method stub
		StudentDTO dto=new StudentDTO();
		dto.setId(1L);
		dto.setFirstName("ram");
		// dto.setLastName("agrawals");
		// dto.setMobileNO("989");
		// dto.setRoleId(101);
		// dto.setUnSuccessfullLogin(1);

		
		ArrayList<StudentDTO> a = (ArrayList<StudentDTO>) model.search(dto);
		for (StudentDTO uu : a) {

			System.out.println(uu.getId() + "\t" + uu.getFirstName() + "\t" + uu.getLastName() + "\t" + uu.getDob()
					+ "\t" + uu.getEmailId() + "\t" + uu.getMobileNo() + "\t" + uu.getCollegeId() + "\t"
					+ uu.getCollegeName());}
	}

	public static void listTest() throws ApplicationException {
		// TODO Auto-generated method stub
		StudentDTO dto=new StudentDTO();
		
       
        List list = new ArrayList();
        list = model.list(0,0);
        if (list.size() < 0) {
            System.out.println("Test list fail");
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            dto = (StudentDTO) it.next();
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

	public static void findByEmailIdTest() throws ApplicationException {
		// TODO Auto-generated method stub
		StudentDTO dto=model.findByEmailId("ghht@hg.com");
		System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t" + dto.getDob() + "\t"
				+ dto.getEmailId() + "\t" + dto.getMobileNo() + "\t" + dto.getCollegeId() + "\t" + dto.getCollegeName());
		
	}

	public static void findByPkTest() throws ApplicationException {
		// TODO Auto-generated method stub
		StudentDTO dto=model.findByPK(2L);
		System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t" + dto.getDob() + "\t"
				+ dto.getEmailId() + "\t" + dto.getMobileNo() + "\t" + dto.getCollegeId() + "\t" + dto.getCollegeName());
	}

	public static void updateTest() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Test Update scc111");
            StudentDTO dto = new StudentDTO();
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
//        System.out.println("Test Update scc222"+dto.getCollegeName());
        dto.setId(2L);
        dto.setFirstName("ram");
        dto.setLastName("sharma");
        dto.setDob(sdf.parse("15/01/1990"));
        dto.setMobileNo("9165254357");
        dto.setEmailId("ghht@hg.com");
        dto.setCollegeId(3L);
        dto.setCollegeName("LNCT");
        dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
        model.update(dto); 
	}

	public static void deleteTest() throws ApplicationException {
		// TODO Auto-generated method stub
		  StudentDTO dto = new StudentDTO();
          long pk = 1;
          dto.setId(pk);
          model.delete(dto);
          System.out.println("Test Delete succ");
          
	}

	public static void addTest() throws ParseException, ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		StudentDTO dto = new StudentDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		dto.setId(1L);
		dto.setFirstName("sonu");
		dto.setLastName("sharma");
		dto.setDob(sdf.parse("15/01/1990"));
		dto.setMobileNo("94066553787");
		dto.setEmailId("aaa@gmail.com");
		dto.setCollegeId(2L);
		dto.setCollegeName("davv");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		 model.add(dto);
		System.out.println("Test add succ");
		

	}
}
