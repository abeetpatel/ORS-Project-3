package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.MarksheetDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.MarksheetModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 *  marksheetmerit list functionlity controller to show merit list student
 * @author Anshul Prajapati
 *
 */
@WebServlet(name = "MarksheetMeritListCtl", urlPatterns = { "/ctl/MarksheetMeritListCtl" })
public class MarksheetMeritListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(MarksheetMeritListCtl.class);

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("Marksheet merit list populate bean start");
		MarksheetDTO dto = new MarksheetDTO();
		return dto;

	} 
	/**
     * Contains Display logics
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Marksheet merit list do get  start");
		System.out.println("Inside merit list get");
		List list;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
		MarksheetModelInt model =ModelFactory.getInstance().getMarksheetModel();
		try {
			list = model.getMeritList(pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) 
		{
			log.error(e);
			ServletUtility.handleException(e, request, response);
			e.printStackTrace();
		}
		log.debug("Marksheet merit list do get  end");

	}
	/**
     * Contains Submit logics
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Marksheet merit list dopost  start");
		List list;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
		try {
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
				return;
			}
			list = model.getMeritList(pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(ORSView.MARKSHEET_MERIT_LIST_VIEW, request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("Marksheet merit list dopost end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}

}

