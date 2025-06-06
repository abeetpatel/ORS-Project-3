package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.CollegeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
/**
 * Interface of College model
 * @author Abeet Patel
 *
 */
public interface CollegeModelInt {
public long add(CollegeDTO dto)throws ApplicationException,DuplicateRecordException;
public void delete(CollegeDTO dto)throws ApplicationException;
public void update(CollegeDTO dto)throws ApplicationException,DuplicateRecordException;
public List list()throws ApplicationException;
public List list(int pageNo,int pageSize)throws ApplicationException;
public List search(CollegeDTO dto)throws ApplicationException;
public List search(CollegeDTO dto,int pageNo,int pageSize)throws ApplicationException;
public CollegeDTO findByPK(long pk)throws ApplicationException;
public CollegeDTO fingByName(String name)throws ApplicationException;
}
