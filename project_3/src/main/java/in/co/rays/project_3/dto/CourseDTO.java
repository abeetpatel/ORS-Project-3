package in.co.rays.project_3.dto;

/**
 * course JavaDto encapsulates course attributes
 * @author Abeet Patel
 *
 */

public class CourseDTO extends BaseDTO {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String courseName;
	private String description;
	private String duration;
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return courseName;
	}

}
