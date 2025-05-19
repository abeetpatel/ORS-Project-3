package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.FacultyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.CollegeModelInt;
import in.co.rays.project_3.model.CourseModelInt;
import in.co.rays.project_3.model.FacultyModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * faculty list functionality ctl.To perform show,search and delete operation
 * @author Anshul Prajapati
 *
 */
@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })
public class FacultyListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(FacultyListCtl.class);
     
	protected void preload(HttpServletRequest request) {
		CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
		CourseModelInt model1=ModelFactory.getInstance().getCourseModel();
		try {
			List list=model.list();
			List list1=model1.list();
			request.setAttribute("collegeList", list);
			request.setAttribute("courseList", list1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("Faculty Ctl populateBean start");
		FacultyDTO dto = new FacultyDTO();
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		dto.setEmailId(DataUtility.getString(request.getParameter("login")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
		populateBean(dto,request);
       
		log.debug("Faculty Ctl populateBean end");
		return dto;

	}

	/**
	 * contain display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Faculty Ctl do get start");
		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		FacultyDTO bean = (FacultyDTO) populateDTO(request);
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("Faculty Ctl do get end");
	}

	/**
	 * Contains submit logic
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Faculty Ctl do post start");
		List list;
		List next;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		String op = DataUtility.getString(request.getParameter("operation"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		FacultyDTO dto = (FacultyDTO) populateDTO(request);
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
		String[] ids = request.getParameterValues("ids");
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if ("Next".equalsIgnoreCase(op)) {
					pageNo++;
				} else if ("Previous".equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				System.out.println("kiljjj");
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				System.out.println("helloooo"+ids);
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					FacultyDTO deleteBean = new FacultyDTO();
					for (String id : ids) {
						deleteBean.setId(DataUtility.getLong(id));
						model.delete(deleteBean);
						ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("select at least one record", request);
				}
			}
			dto = (FacultyDTO) populateDTO(request);
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setDto(dto, request);
			next = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("NO Record Found", request);

			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("Faculty Ctl do post end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_LIST_VIEW;
	}

}

