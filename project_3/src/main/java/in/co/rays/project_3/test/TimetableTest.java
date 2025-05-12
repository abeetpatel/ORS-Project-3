package in.co.rays.project_3.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.RoleDTO;
import in.co.rays.project_3.dto.TimetableDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.TimetableModelHibImp;
import in.co.rays.project_3.model.TimetableModelInt;
import in.co.rays.project_3.model.TimetableModelJDBCImpl;

public class TimetableTest {
	public static TimetableModelInt model=new TimetableModelHibImp();
	//public static TimetableModelInt model=new TimetableModelJDBCImpl();
	public static void main(String[] args) throws Exception {
		addTest();
		//deleteTest();
		//updateTest();
		//findByPkTest();
		//findByNameTest();
	//searchTest();
		//listTest();
		//checkByCourseName();
		//checkBySubjectName();
		/* checkBySemester(); */
	}
	public static void checkBySemester() throws ApplicationException, DuplicateRecordException, ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		TimetableDTO dto=model.checkBysemester(102, 6,"third", sdf.parse("01/02/2012"));
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubId());
		System.out.print("\t" + dto.getSubName());
		System.out.print("\t" + dto.getCourseId());
		System.out.print("\t" + dto.getCourseName());
		System.out.print("\t" + dto.getSemester());
		System.out.print("\t" + dto.getExamDate());
		System.out.print("\t" + dto.getExamTime());
	}
	public static void checkBySubjectName() throws ApplicationException, DuplicateRecordException, ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		TimetableDTO dto=model.checkBySubjectName(102, 1, sdf.parse("01/02/2012"));
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubId());
		System.out.print("\t" + dto.getSubName());
		System.out.print("\t" + dto.getCourseId());
		System.out.print("\t" + dto.getCourseName());
		System.out.print("\t" + dto.getSemester());
		System.out.print("\t" + dto.getExamDate());
		System.out.print("\t" + dto.getExamTime());
	}
	public static void checkByCourseName() throws ApplicationException, DuplicateRecordException, ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		TimetableDTO dto=model.checkByCourseName(102,sdf.parse("01/02/2012") );
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubId());
		System.out.print("\t" + dto.getSubName());
		System.out.print("\t" + dto.getCourseId());
		System.out.print("\t" + dto.getCourseName());
		System.out.print("\t" + dto.getSemester());
		System.out.print("\t" + dto.getExamDate());
		System.out.print("\t" + dto.getExamTime());
	}
	public static void listTest() throws ApplicationException {
		// TODO Auto-generated method stub
		TimetableDTO dto=new TimetableDTO();
		List list=model.list(0, 0);
		Iterator it=list.iterator();
		while(it.hasNext()){
			 dto=(TimetableDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubId());
			System.out.print("\t" + dto.getSubName());
			System.out.print("\t" + dto.getCourseId());
			System.out.print("\t" + dto.getCourseName());
			System.out.print("\t" + dto.getSemester());
			System.out.print("\t" + dto.getExamDate());
			System.out.print("\t" + dto.getExamTime());
		}
	}
	public static void searchTest() throws ApplicationException {
		// TODO Auto-generated method stub
		TimetableDTO dto1=new TimetableDTO();
		dto1.setId(1L);
		//dto1.setCourseName("bsc");
		List<TimetableDTO> a=(List<TimetableDTO>) model.search(dto1, 0, 0);
		for(TimetableDTO dto: a){
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubId());
			System.out.print("\t" + dto.getSubName());
			System.out.print("\t" + dto.getCourseId());
			System.out.print("\t" + dto.getCourseName());
			System.out.print("\t" + dto.getSemester());
			System.out.print("\t" + dto.getExamDate());
			System.out.print("\t" + dto.getExamTime());
			
		}
	}
	public static void findByNameTest() {
		// TODO Auto-generated method stub
		TimetableDTO dto=new TimetableDTO();
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubId());
		System.out.print("\t" + dto.getSubName());
		System.out.print("\t" + dto.getCourseId());
		System.out.print("\t" + dto.getCourseName());
		System.out.print("\t" + dto.getSemester());
		System.out.print("\t" + dto.getExamDate());
		System.out.print("\t" + dto.getExamTime());
	}
	public static void findByPkTest() throws ApplicationException {
		// TODO Auto-generated method stub
		TimetableDTO dto=model.findByPK(1L);
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubId());
		System.out.print("\t" + dto.getSubName());
		System.out.print("\t" + dto.getCourseId());
		System.out.print("\t" + dto.getCourseName());
		System.out.print("\t" + dto.getSemester());
		System.out.print("\t" + dto.getExamDate());
		System.out.print("\t" + dto.getExamTime());
	}
	public static void updateTest() throws ApplicationException, DuplicateRecordException, DatabaseException {
		// TODO Auto-generated method stub
		TimetableDTO dto=new TimetableDTO();
		dto.setId(1L);
		dto.setSubId(1);
		dto.setSubName("physics");
		dto.setCourseId(102);
		dto.setCourseName("bee");
		dto.setSemester("fourth");
		
		dto.setExamTime("4hr");
		model.update(dto);
	}
	public static void deleteTest() throws ApplicationException {
		// TODO Auto-generated method stub
		TimetableDTO dto=new TimetableDTO();
		dto.setId(2L);
		model.delete(dto);
	}
	public static void addTest() throws ParseException, ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		TimetableDTO dto=new TimetableDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		
		dto.setSubId(1);
		dto.setSubName("physics");
		dto.setCourseId(102);
		dto.setCourseName("bee");
		dto.setSemester("fourth");
		dto.setExamDate(sdf.parse("01/02/2012"));
		dto.setExamTime("4hr");
		model.add(dto);
	}
	
}
