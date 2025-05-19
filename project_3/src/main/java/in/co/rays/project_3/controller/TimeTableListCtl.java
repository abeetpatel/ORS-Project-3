package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.TimetableDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.CourseModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SubjectModelInt;
import in.co.rays.project_3.model.TimetableModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * subject functionality controller.to show list
 * @author Anshul Prajapati
 *
 */
@WebServlet(name = "TimeTableListCtl", urlPatterns = { "/ctl/TimeTableListCtl" })
public class TimeTableListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(TimeTableListCtl.class);
      
	protected void preload(HttpServletRequest request){
		CourseModelInt model=ModelFactory.getInstance().getCourseModel();
		SubjectModelInt smodel=ModelFactory.getInstance().getSubjectModel();
		try{
			List list=model.list();
			List list1=smodel.list();
			request.setAttribute("subjectList", list1);
			request.setAttribute("courseList", list);
		}catch(Exception e){
			log.error(e);
		}
	}
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		TimetableDTO dto = new TimetableDTO();

		//dto.setCourseName(DataUtility.getString(request.getParameter("courseId")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setExamDate(DataUtility.getDate(request.getParameter("Exdate")));
		System.out.println("--------->"+dto.getExamDate());
		//dto.setSubName(DataUtility.getString(request.getParameter("subjectId")));
		dto.setSubId(DataUtility.getInt(request.getParameter("subjectId")));
		dto.setSemester(DataUtility.getString(request.getParameter("semester")));
		populateBean(dto,request);
		return dto;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Time table ctl doGet Start");
		List list;
		List next = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		TimetableDTO dto = (TimetableDTO) populateDTO(request);
		TimetableModelInt model = ModelFactory.getInstance().getTimetableModel();
		try {
			System.out.println("in ctllllllllll search");
			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Time table ctl doPOst End");
	}

	/**
	 * Contains Submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Time table ctl doPost Start");
		System.out.println("Hello inside post");
		List list = null;
		List next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		TimetableDTO dto = (TimetableDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("jmmmmm"+op);
		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		TimetableModelInt model = ModelFactory.getInstance().getTimetableModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					TimetableDTO deletebean = new TimetableDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
				 
			}
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				return;
			} 
			dto = (TimetableDTO)populateDTO(request);
			list = model.search(dto, pageNo, pageSize);
			
			ServletUtility.setDto(dto, request);

			next = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Time table ctl doGet End");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_LIST_VIEW;
	}

}

