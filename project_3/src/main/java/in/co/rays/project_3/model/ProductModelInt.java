package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface ProductModelInt {
	
	public long add(ProductDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(ProductDTO dto)throws ApplicationException;
	public void update(ProductDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(ProductDTO dto)throws ApplicationException;
	public List search(ProductDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public ProductDTO findByPK(long pk)throws ApplicationException;
	public ProductDTO fingByName(String name)throws ApplicationException;

}
