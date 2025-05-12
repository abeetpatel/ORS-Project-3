package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 * @author Abeet Patel
 *
 */
public abstract class BaseCtl extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String OP_SAVE = "Save";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_RESET = "Reset";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_UPDATE = "Update";
	public static final String OP_LOG_OUT = "Logout";
	public static final String OP_CHANGE_MY_PROFILE = "MyProfile";

    /**
     * Success message key constant
     */
    public static final String MSG_SUCCESS = "success";

    /**
     * Error message key constant
     */
    public static final String MSG_ERROR = "error";

    /**
     * Validates input data entered by User
     *
     * @param request
     * @return
     */
    protected boolean validate(HttpServletRequest request) {
        return true;
    }

    /**
     * Loads list and other data required to display at HTML form
     *
     * @param request
     */
    protected void preload(HttpServletRequest request) {
    }

    protected BaseDTO populateBean(BaseDTO dto,HttpServletRequest request){

    	System.out.println("populateDTO method in BaseCtl");

    	String createdBy=request.getParameter("createdBy");
    	String modifiedBy=null;

    	// UserDTO userDto=(UserDTO)request.getSession().getAttribute("user");

    	HttpSession session=request.getSession();

    	UserDTO userDto=(UserDTO) session.getAttribute("user");

    	if(userDto==null){
    	createdBy="root";
    	modifiedBy="root";

    	}else{
    	modifiedBy=userDto.getLogin();
    	if("null".equalsIgnoreCase(createdBy)||DataValidator.isNull(createdBy)){
    	createdBy=modifiedBy;
    	}
    	}
    	dto.setCreatedBy(createdBy);
    	dto.setModifiedBy(modifiedBy);

    	long cdt=DataUtility.getLong(request.getParameter("createdDateTime"));

    	if(cdt>0){
    	dto.setCreatedDatetime(DataUtility.getTimeStamp(cdt));
    	}else{
    	dto.setCreatedDatetime(DataUtility.getCurrentTimeStamp());
    	}
    	dto.setModifiedDatetime(DataUtility.getCurrentTimeStamp());
    	return dto;
    	}
    /**
     * Populates DTO object from request parameters
     *
     * @param request
     * @return
     */
    protected BaseDTO populateDTO(HttpServletRequest request) {
        return null;
    }

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // Load the preloaded data required to display at HTML form
        preload(request);

        String op = DataUtility.getString(request.getParameter("operation"));

        // Check if operation is not DELETE, VIEW, CANCEL, RESET and NULL then
        // perform input data validation

        if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op)&& !OP_RESET.equalsIgnoreCase(op)) {
            // Check validation, If fail then send back to page with error
            // messages

            if (!validate(request)) {
                BaseDTO dto = (BaseDTO) populateDTO(request);
                ServletUtility.setDto(dto, request);
                ServletUtility.forward(getView(), request, response);
                return;
            }
        }
        super.service(request, response);
        System.out.println("Service base");
    }

    /**
     * Returns the VIEW page of this Controller
     *
     * @return
     */
    protected abstract String getView();
}
