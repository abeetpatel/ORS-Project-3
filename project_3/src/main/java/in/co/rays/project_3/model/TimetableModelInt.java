package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.TimetableDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Timetable model
 * @author Abeet Patel
 *
 */
public interface TimetableModelInt {
public long add(TimetableDTO dto)throws ApplicationException,DuplicateRecordException;
public void delete(TimetableDTO dto)throws ApplicationException;
public void update(TimetableDTO dto)throws ApplicationException,DuplicateRecordException,DatabaseException;
public List list()throws ApplicationException;
public List list(int pageNo,int pageSize)throws ApplicationException;
public List search(TimetableDTO dto)throws ApplicationException;
public List search(TimetableDTO dto,int pageNo,int pageSize)throws ApplicationException;
public TimetableDTO findByPK(long pk)throws ApplicationException;
public TimetableDTO checkByCourseName(long courseId,java.util.Date examDate)throws ApplicationException,DuplicateRecordException;
public TimetableDTO checkBySubjectName(long courseId,long subjectId,java.util.Date examDate)throws ApplicationException,DuplicateRecordException;
public TimetableDTO checkBysemester(long courseId,long subjectId,String semester,java.util.Date examDate)throws ApplicationException,DuplicateRecordException;

}
