package in.co.rays.project_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.StaffMemberDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.StaffMemberModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * staffMember functionality ctl.To perform add,delete and update operation
 * @author Abeet Patel
 *
 */

@WebServlet(urlPatterns = { "/ctl/StaffMemberCtl" })
public class StaffMemberCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(FacultyCtl.class);

	protected boolean validate(HttpServletRequest request) {
		log.debug("StaffMember ctl validate start");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("fullName"))) {
			request.setAttribute("fullName", PropertyReader.getValue("error.require", "Full Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("fullName"))) {
			request.setAttribute("fullName", PropertyReader.getValue("error.name", "Full Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("joiningDate"))) {
			request.setAttribute("joiningDate", PropertyReader.getValue("error.require", "Joining Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("joiningDate"))) {
			request.setAttribute("joiningDate", PropertyReader.getValue("error.date", " Joining Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("division"))) {
			request.setAttribute("division", PropertyReader.getValue("error.require", "Division"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("previousEmployer"))) {
			request.setAttribute("previousEmployer", PropertyReader.getValue("error.require", "Previous Employer"));
			pass = false;
		}
		log.debug("staffMember ctl validate end");
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("staffMember ctl populate bean start");
		System.out.println("staffMember bean populate start");
		StaffMemberDTO dto = new StaffMemberDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setFullName(DataUtility.getString(request.getParameter("fullName")));
		dto.setDivision(DataUtility.getString(request.getParameter("division")));
		dto.setPreviousEmployer(DataUtility.getString(request.getParameter("previousEmployer")));
		dto.setJoiningDate(DataUtility.getDate(request.getParameter("joiningDate")));
		populateBean(dto, request);
		log.debug("staffMember ctl populate bean end");
		return dto;

	}

	/**
	 * Display Logics inside this method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("staffMember ctl do get start");
		System.out.println("============");

		StaffMemberModelInt model = ModelFactory.getInstance().getStaffMemberModel();
		StaffMemberDTO dto = new StaffMemberDTO();
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("staffMember ctl do get end");
	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("staffMember do post start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		StaffMemberModelInt model = ModelFactory.getInstance().getStaffMemberModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			StaffMemberDTO dto = (StaffMemberDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("StaffMember id already exists", request);
					}

				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("StaffMember id already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println("alteast");
			StaffMemberDTO dto = (StaffMemberDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.STAFF_MEMBER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.debug(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STAFF_MEMBER_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STAFF_MEMBER_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("staffMember do post end");
	}

	@Override
	protected String getView() {
		return ORSView.STAFF_MEMBER_VIEW;
	}

}
