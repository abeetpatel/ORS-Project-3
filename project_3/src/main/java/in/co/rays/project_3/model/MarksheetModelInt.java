package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.MarksheetDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Marksheet model
 * @author Abeet Patel
 *
 */
public interface MarksheetModelInt {
public long add(MarksheetDTO dto)throws ApplicationException,DuplicateRecordException;
public void delete(MarksheetDTO dto)throws ApplicationException;
public void update(MarksheetDTO dto)throws ApplicationException,DuplicateRecordException;
public List list()throws ApplicationException;
public List list(int pageNo,int pageSize)throws ApplicationException;
public List search(MarksheetDTO dto)throws ApplicationException;
public List search(MarksheetDTO dto,int pageNo,int pageSize)throws ApplicationException;
public MarksheetDTO fingByPK(long pk)throws ApplicationException;
public MarksheetDTO findByRollNo(String rollNo)throws ApplicationException;
public List getMeritList(int pageNo,int pageSize)throws ApplicationException;
}
