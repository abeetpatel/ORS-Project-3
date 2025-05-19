package in.co.rays.project_3.dto;

import java.util.Date;

/**
 * TimeTable JavaBean encapsulates TimeTable attributes
 * @author Anshul Prajapati
 *
 */

public class TimetableDTO extends BaseDTO
{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long subId;
	private long courseId;
	private String subName;
	private String courseName;
	private Date examDate;
	private String semester;
	private String examTime;
	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return subName;
	}

}
