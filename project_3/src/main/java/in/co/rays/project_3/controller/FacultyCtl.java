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
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CollegeModelInt;
import in.co.rays.project_3.model.CourseModelInt;
import in.co.rays.project_3.model.FacultyModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SubjectModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * faculty functionality ctl.To perform add,delete and update operation
 * @author Anshul Prajapati
 *
 */
@WebServlet(urlPatterns={"/ctl/FacultyCtl"})
public class FacultyCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(FacultyCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		try {
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
		SubjectModelInt model2 = ModelFactory.getInstance().getSubjectModel();
		
			List l = model.list();
			List li = model1.list();
			List list = model2.list();
			request.setAttribute("collegeList", l);
			request.setAttribute("courseList", li);
			request.setAttribute("subjectList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		
		protected boolean validate(HttpServletRequest request) {
			log.debug("Faculty ctl validate start");
			boolean pass = true;
			String login = request.getParameter("emailId");
			if (DataValidator.isNull(request.getParameter("firstName"))) {
				request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
				pass = false;
			}else if (!DataValidator.isName(request.getParameter("firstName"))) {
				request.setAttribute("firstName", PropertyReader.getValue("error.name", "First Name"));
				pass = false;

			}
			if (DataValidator.isNull(request.getParameter("lastName"))) {
				request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
				pass = false;
			}else if (!DataValidator.isName(request.getParameter("lastName"))) {
				request.setAttribute("lastName", PropertyReader.getValue("error.name", "Last Name"));
				pass = false;

			}
			if (DataValidator.isNull(request.getParameter("dob"))) {
				request.setAttribute("dob", PropertyReader.getValue("error.require", " Date of Birth"));
				pass = false;
			}else if (!DataValidator.isDate(request.getParameter("dob"))) {
				request.setAttribute("dob", PropertyReader.getValue("error.date", " Date of Birth"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("qualification"))) {
				request.setAttribute("qualification", PropertyReader.getValue("error.require", "Qualification"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("collegeId"))) {
				request.setAttribute("collegeId", PropertyReader.getValue("error.require", "college Name"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("courseId"))) {
				request.setAttribute("courseId", PropertyReader.getValue("error.require", "course Name"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("subjectId"))) {
				request.setAttribute("subjectId", PropertyReader.getValue("error.require", "subject Name"));
				pass = false;
			}
			if (DataValidator.isNull(login)) {
				request.setAttribute("emailId", PropertyReader.getValue("error.require", "Email Id"));
				pass = false;
			} else if (!DataValidator.isEmail(login)) {
				request.setAttribute("emailId", PropertyReader.getValue("error.email", "Please enter vaild email id"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("gender"))) {
				request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("mobileNo"))) {
				request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
				pass = false;
			}else if(!DataValidator.isPhoneNo(request.getParameter("mobileNo")))
			{
				  request.setAttribute("mobileNo", "Please Enter Valid Mobile Number");
				  pass=false;	
			    }


			if (DataValidator.isNull(request.getParameter("collegeId"))) {
				request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("courseId"))) {
				request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("subjectId"))) {
				request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
				pass = false;
			}
			log.debug("faculty ctl validate end");
			return pass;

		}

		protected BaseDTO populateDTO(HttpServletRequest request) {
			log.debug("faculty ctl populate bean start");
			System.out.println("faculty bean populate start");
			FacultyDTO dto = new FacultyDTO();
			dto.setId(DataUtility.getLong(request.getParameter("id")));
			dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
			dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
			dto.setEmailId(DataUtility.getString(request.getParameter("emailId")));
			dto.setQualification(DataUtility.getString(request.getParameter("qualification")));
			dto.setDob(DataUtility.getDate(request.getParameter("dob")));
			dto.setGender(request.getParameter("gender"));
			dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
			dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
			dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
			dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
			populateBean(dto,request);
			log.debug("faculty ctl populate bean end");
			return dto;

		}
		 /**
	     * Display Logics inside this method
	     */
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {

			
			log.debug("faculty ctl do get start");
			System.out.println("============");
			
			FacultyModelInt model =ModelFactory.getInstance().getFacultyModel() ;
			FacultyDTO dto = new FacultyDTO();
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
			log.debug("faculty ctl do get end");
		}
		 /**
	     * Submit logic inside it
	     */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
			log.debug("faculty do post start");
			String op = DataUtility.getString(request.getParameter("operation"));
			long id = DataUtility.getLong(request.getParameter("id"));
			FacultyModelInt model =ModelFactory.getInstance().getFacultyModel() ;

			if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
				FacultyDTO dto = (FacultyDTO) populateDTO(request);
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
							ServletUtility.setErrorMessage("Faculty id already exists", request);
						} 

					}
					ServletUtility.setDto(dto, request);
					
				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				} catch (Exception e) {
					ServletUtility.setDto(dto, request);
					ServletUtility.setErrorMessage("Faculty id already exists", request);
				} 
			
			}else if(OP_DELETE.equalsIgnoreCase(op)){
				System.out.println("alteast");
				FacultyDTO dto=(FacultyDTO) populateDTO(request);
				try{
					model.delete(dto);
					ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
					return;
				}catch(ApplicationException e){
					log.debug(e);
					ServletUtility.handleException(e, request, response);
					return;
				}
			}else if(OP_CANCEL.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			}
			else if(OP_RESET.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;
			}
			ServletUtility.forward(getView(), request, response);
			log.debug("faculty do post end");
		}

		@Override
		protected String getView() {
			// TODO Auto-generated method stub
			return ORSView.FACULTY_VIEW;
		}

		


}
