package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.StaffMemberDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface StaffMemberModelInt {

	public long add(StaffMemberDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(StaffMemberDTO dto) throws ApplicationException;

	public void update(StaffMemberDTO dto) throws ApplicationException, DatabaseException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(StaffMemberDTO dto) throws ApplicationException;

	public List search(StaffMemberDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public StaffMemberDTO findByPK(long pk) throws ApplicationException;

}
