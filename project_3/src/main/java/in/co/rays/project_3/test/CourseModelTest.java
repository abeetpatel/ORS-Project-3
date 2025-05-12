package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.CourseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CourseModelHibImp;
import in.co.rays.project_3.model.CourseModelInt;
import in.co.rays.project_3.model.CourseModelJDBCImpl;

public class CourseModelTest {
public static CourseModelInt model=new CourseModelHibImp();
//public static CourseModelInt model=new CourseModelJDBCImpl();
public static void main(String[] args) throws Exception {
	//addTest();
	//deleteTest();
	//updateTest();
	//findByNameTest();
	findByPkTest();
	//listTest();
	searchTest();
}
public static void searchTest() throws ApplicationException {
	// TODO Auto-generated method stub
	CourseDTO cbean1 = new CourseDTO();
	cbean1.setId(2L);
	cbean1.setCourseName("pcm");
	System.out.println("hello");

	
	List<CourseDTO> a = (List<CourseDTO>) model.search(cbean1,0,0);
	for (CourseDTO dto : a) {
		System.out.println(dto.getId() + "\t" + dto.getCourseName() + "\t" + dto.getDescription() + "\t"
				+ dto.getDuration());
	}
}
public static void listTest() throws ApplicationException {
	// TODO Auto-generated method stub
	CourseDTO dto=new CourseDTO();
	List list=new ArrayList();
	 list=model.list(0,0);
	 if(list.size() < 0){
		 System.out.println("list fail");
	 }
	Iterator it=list.iterator();
	while(it.hasNext()){   
		dto = (CourseDTO) it.next();
		System.out.println(dto.getId() + "\t" + dto.getCourseName() + "\t" + dto.getDescription() + "\t"
				+ dto.getDuration());
	}
}
public static void findByPkTest() throws ApplicationException {
	// TODO Auto-generated method stub
	CourseDTO dto=model.findByPK(2L);
	System.out.println(dto.getId() + "\t" + dto.getCourseName() + "\t" + dto.getDescription() + "\t"
			+ dto.getDuration());
}
public static void findByNameTest() throws Exception {
	// TODO Auto-generated method stub
	CourseDTO dto=model.findByName("pcm");
	System.out.println(dto.getId() + "\t" + dto.getCourseName() + "\t" + dto.getDescription() + "\t"
			+ dto.getDuration());
	
}
public static void updateTest() throws Exception {
	// TODO Auto-generated method stub
	CourseDTO dto=new CourseDTO();
	dto.setId(2L);
	dto.setCourseName("pcm");
	dto.setDescription("hello");
	dto.setDuration("3year");
	dto.setCreatedBy("admin");
	dto.setModifiedBy("admin");
	dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	model.update(dto);
}

public static void deleteTest() throws ApplicationException {
	// TODO Auto-generated method stub
	CourseDTO dto=new CourseDTO();
	dto.setId(1L);
	model.delete(dto);
}
public static void addTest() throws Exception {
	// TODO Auto-generated method stub
	CourseDTO dto=new CourseDTO();

	dto.setCourseName("maths");
	dto.setDescription("hello");
	dto.setDuration("3year");
	dto.setCreatedBy("admin");
	dto.setModifiedBy("admin");
	dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	model.add(dto);
	
}
}
