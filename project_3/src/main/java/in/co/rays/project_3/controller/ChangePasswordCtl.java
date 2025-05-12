package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.UserModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * change password operation functionality perform
 * 
 * @author Abeet Patel
 *
 */
@WebServlet(urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(ChangePasswordCtl.class);

	protected boolean validate(HttpServletRequest request) {
		System.out.println("validate.......");
		log.debug("change password validate method start");
		boolean pass = true;
		String op = request.getParameter("operation");
		if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("oldpassword"))) {
			request.setAttribute("oldpassword", PropertyReader.getValue("error.require", "Old password"));
			pass = false;
		} /*
			 * else if (!DataValidator.isPassword(request.getParameter("oldpassword"))) {
			 * request.setAttribute("oldpassword", "Please Enter valid Password"); pass =
			 * false; }
			 */

		if (DataValidator.isNull(request.getParameter("newpassword"))) {
			request.setAttribute("newpassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		} /*
			 * else if (!DataValidator.isPassword(request.getParameter("newpassword"))) {
			 * request.setAttribute("newpassword", "Please Enter vaild Password"); pass =
			 * false; }
			 */

		if (DataValidator.isNull(request.getParameter("confirmpassword"))) {
			request.setAttribute("confirmpassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}
		if (!request.getParameter("newpassword").equals(request.getParameter("confirmpassword"))
				&& !"".equals(request.getParameter("confirmpassword"))) {
			ServletUtility.setErrorMessage("New and confirm passwords not matched", request);
			pass = false;

		}
		log.debug("validate method end");
		return pass;
	}

	/**
	 * Display Logics inside this method
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("do get ............");
		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		log.debug("change password do post start");
		String op = DataUtility.getString(request.getParameter("operation"));
		UserModelInt model = ModelFactory.getInstance().getUserModel();

		UserDTO UserBean = (UserDTO) session.getAttribute("user");
		String newPassword = request.getParameter("newpassword");
		String oldPassword = request.getParameter("oldpassword");
		long id = UserBean.getId();
		System.out.println("do post id..." + id + "...." + UserBean.getPassword() + ";;;;;;;;;" + UserBean.getId()
				+ "....." + newPassword + "...." + oldPassword);
		if (OP_SAVE.equalsIgnoreCase(op)) {
			try {
				boolean flag = model.changePassword(id, newPassword, oldPassword);
				if (flag == true) {
					model.findByLogin(UserBean.getLogin());
					ServletUtility.setSuccessMessage("Password has been change successfully", request);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				e.printStackTrace();
				return;

			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage("Old PassWord is Invalid", request);
				e.printStackTrace();
			}

		} else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView() ,request, response);
		log.debug("ChangePasswordCtl Method doGet Ended");

	}

	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}
