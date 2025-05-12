package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.project_3.dto.CollegeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CollegeModelHibImp;
import in.co.rays.project_3.model.CollegeModelInt;
import in.co.rays.project_3.model.CollegeModelJDBCImpl;

public class CollegeModelTest {
public static CollegeModelInt model=new CollegeModelHibImp();
//public static CollegeModelInt model=new CollegeModelJDBCImpl();
public static void main(String[] args) throws Exception {
	addTest();
	//deleteTest();
	//updateTest();
	//findByPKTest();
	//findByNameTest();
	
	
}
public static void findByNameTest() throws ApplicationException {
	// TODO Auto-generated method stub
	CollegeDTO dto=model.fingByName("davv");
	System.out.println(dto.getId());
    System.out.println(dto.getName());
    System.out.println(dto.getAddress());
    System.out.println(dto.getState());
    System.out.println(dto.getCity());
    System.out.println(dto.getPhoneNo());
    System.out.println(dto.getCreatedBy());
    System.out.println(dto.getCreatedDatetime());
    System.out.println(dto.getModifiedBy());
    System.out.println(dto.getModifiedDatetime()); 
}
public static void findByPKTest() throws ApplicationException {
	// TODO Auto-generated method stub
	CollegeDTO dto=model.findByPK(1L);
	System.out.println(dto.getId());
    System.out.println(dto.getName());
    System.out.println(dto.getAddress());
    System.out.println(dto.getState());
    System.out.println(dto.getCity());
    System.out.println(dto.getPhoneNo());
    System.out.println(dto.getCreatedBy());
    System.out.println(dto.getCreatedDatetime());
    System.out.println(dto.getModifiedBy());
    System.out.println(dto.getModifiedDatetime()); 
}
public static void updateTest() throws ApplicationException, DuplicateRecordException {
	// TODO Auto-generated method stub
	 CollegeDTO dto = new CollegeDTO();
     dto.setId(1L);
     dto.setName("mjjjit");
     dto.setAddress("borawan");
     dto.setState("mp");
     dto.setCity("indore");
     dto.setPhoneNo("073124244");
     dto.setCreatedBy("Admin");
     dto.setModifiedBy("Admin");
     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
     model.update(dto);
}
public static void deleteTest() throws ApplicationException {
	// TODO Auto-generated method stub
	CollegeDTO dto = new CollegeDTO();
	dto.setId(1L);
	model.delete(dto);
}
public static void addTest() throws ApplicationException, DuplicateRecordException {
	// TODO Auto-generated method stub
	 CollegeDTO dto = new CollegeDTO();
     dto.setId(1L);
     dto.setName("Davv");
     dto.setAddress("Indore");
     dto.setState("M.P.");
     dto.setCity("Indore");
     dto.setPhoneNo("2532095");
     dto.setCreatedBy("Admin");
     dto.setModifiedBy("Admin");
     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
     long pk = model.add(dto); 
}
}
