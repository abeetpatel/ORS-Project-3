package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jdt.internal.compiler.env.ISourceMethod;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.MarksheetDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.MarksheetModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.StudentModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * marksheeet functionality controller.to perform add,delete and update
 * operation
 * 
 * @author Anshul Prajapati
 *
 */
@WebServlet(urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	protected void preload(HttpServletRequest request) {
		StudentModelInt model = ModelFactory.getInstance().getStudentModel();
		try {
			List li = model.list();
			request.setAttribute("studenList", li);
			System.out.println("add marksheet" + li);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("marksheet validate bean start");
		boolean pass = true;
		String id = request.getParameter("studentId");
		if (DataValidator.isNull(request.getParameter("roll"))) {
			request.setAttribute("roll", PropertyReader.getValue("error.require", "Roll No"));
			pass = false;
		} /*
			 * else if (!DataValidator.isRollNo(request.getParameter("roll"))) {
			 * request.setAttribute("roll", "Please Enter Valid Roll No"); pass = false; }
			 */
		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.require", "Physics marks"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.require", "Maths marks "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Chemistry marks"));
			pass = false;
		}

		if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Physics Marks"));
			pass = false;

		}
		if (DataUtility.getInt(request.getParameter("physics")) > 100
				|| DataUtility.getInt(request.getParameter("physics")) < 0) {

			request.setAttribute("physics", "marks must be less than 100 and greater than 0");
			pass = false;
		}
		if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "chemistry marks"));
		}
		if (DataUtility.getInt(request.getParameter("chemistry")) > 100
				|| DataUtility.getInt(request.getParameter("chemistry")) < 0) {

			request.setAttribute("chemistry", "marks less than less than 100 and greater than 0");
			pass = false;
		}
		if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "maths marks"));
			pass = false;
		}
		if (DataUtility.getInt(request.getParameter("maths")) > 100
				|| DataUtility.getInt(request.getParameter("maths")) < 0) {

			request.setAttribute("maths", "marks must be less than 100 and greater than 0");
			pass = false;
		}
		log.debug("marksheet validate bean end");
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("marksheet populate bean start");
		MarksheetDTO dto = new MarksheetDTO();
		String id = request.getParameter("studentId");
		String id1 = id.trim();
		dto.setRollNo(request.getParameter("roll"));

		dto.setStudentId(DataUtility.getLong(id1));

		dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		dto.setMaths(DataUtility.getInt(request.getParameter("maths")));

		populateBean(dto, request);
		log.debug("marksheet populate bean end");
		return dto;

	}

	/**
	 * Display Logics inside this method
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("marksheet ctl doget  start");
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		if (id > 0) {
			MarksheetDTO dto;
			try {
				dto = model.fingByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("marksheet ctl doget  end");
	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("marksheet ctl dopost  start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				System.out.println("in catch");
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("marksheet ctl dopost  end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_VIEW;
	}

}
