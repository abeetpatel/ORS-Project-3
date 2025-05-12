package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.StudentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Student model
 * @author Abeet Patel
 *
 */
public interface StudentModelInt {
public long add(StudentDTO dto)throws ApplicationException,DuplicateRecordException;
public void delete(StudentDTO dto)throws ApplicationException;
public void update(StudentDTO dto)throws ApplicationException,DuplicateRecordException;
public List list()throws ApplicationException;
public List list(int pageNo,int pageSize)throws ApplicationException;
public List search(StudentDTO dto)throws ApplicationException;
public List search(StudentDTO dto,int pageNo,int pageSize)throws ApplicationException;
public StudentDTO findByPK(long pk)throws ApplicationException;
public StudentDTO findByEmailId(String emailId)throws ApplicationException;
}
