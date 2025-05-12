package in.co.rays.project_3.dto;
/**
 * Role JavaDto encapsulates role attributes
 * @author Abeet Patel
 *
 */

public class RoleDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ADMIN = 1;
	public static final int STUDENT = 2;
	public static final int COLLEGE_SCHOOL = 3;
	public static final int KIOSK = 4;
	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {

		return id + "";
	}

	public String getValue() {

		return name + "";
	}
}
