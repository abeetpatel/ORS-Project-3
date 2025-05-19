package in.co.rays.project_3.dto;

/**
 * subject JavaDto encapsulates subject attributes
 * @author Anshul Prajapati
 *
 */

public class SubjectDTO extends BaseDTO {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long SubjectId;

	public long getSubjectId() {
		return SubjectId;
	}

	public void setSubjectId(long subjectId) {
		SubjectId = subjectId;
	}

	private long courseId;
  private String subjectName;
  private String courseName;
  private String description;

  

public long getCourseId() {
	return courseId;
}

public void setCourseId(long courseId) {
	this.courseId = courseId;
}

public String getSubjectName() {
	return subjectName;
}

public void setSubjectName(String subjectName) {
	this.subjectName = subjectName;
}

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

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return subjectName;
	}
 
}
