<%@page import="in.co.rays.project_3.controller.TimeTableCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Time Table View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<style type="text/css">
.log1 {
	padding-top: 5%;
	/* padding-left: 30%; */
}

i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 7px;
	background-color: #ebebe0;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed; 
	background-size: cover;
	padding-top: 75px;
	
/* 	background-size: 100%; */
}
</style>
</head>
<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@ include file="calendar.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">

			<div class="row pt-3 pb-3">
				<jsp:useBean id="dto" class="in.co.rays.project_3.dto.TimetableDTO"
					scope="request"></jsp:useBean>
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getId() != null) {
							%>
							<h3 class="text-center text-primary">Update Time Table</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add Time Table</h3>
							<%
								}
							%>
							<!--Body-->
							<div>


								<H4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</H4>

								<H4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>

								</H4>
								<%
									List l = (List) request.getAttribute("courseList");
									List li = (List) request.getAttribute("subjectList");
								%>
								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy"
									value="<%=dto.getCreatedBy()%>"> <input type="hidden"
									name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
									type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
							</div>
							<div class="md-form">
								<span class="pl-sm-5"><b>Course </b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-envelope grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<%=HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), l)%>
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></br>

								<span class="pl-sm-5"><b>Subject</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-book grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<%=HTMLUtility.getList("subjectId", String.valueOf(dto.getSubId()), li)%>
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("subjectId", request)%></font></br>


								<span class="pl-sm-5"><b>Semester</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-sort grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<%
											HashMap map = new HashMap();
											map.put("1 semester", "1 semester");
											map.put("2 semester", "2 semester");
											map.put("3 semester", "3 semester");
											map.put("4 semester", "4 semester");
											map.put("5 semester", "5 semester");
											map.put("6 semester", "6 semester");
											map.put("7 semester", "7 semester");
											map.put("8 semester", "8 semester");
											map.put("9 semester", "9 semester");
											map.put("10 semester", "10 semester");

											String htmlList = HTMLUtility.getList("semesterId", dto.getSemester(), map);
										%>
										<%=htmlList%>
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("semesterId", request)%></font></br>

								<span class="pl-sm-5"><b>Exam Date</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="examDate"
											placeholder="Enter Exam Date" id="udate5" readonly="readonly"
											value="<%=DataUtility.getDateString(dto.getExamDate())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("examDate", request)%></font></br>

								<span class="pl-sm-5"><b>Exam Time</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-clock grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<%
											HashMap map1 = new HashMap();
											map1.put("08:00 AM to 11:00 AM", "08:00 AM to 11:00 AM");
											map1.put("12:00PM to 3:00PM", "12:00PM to 3:00PM");
											map1.put("3:00PM to 6:00PM", "3:00PM to 6:00PM");
											String htmlList1 = HTMLUtility.getList("examId", dto.getExamTime(), map1);
										%>
										<%=htmlList1%>
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("examId", request)%></font></br>



								</br>
								<%
									if (dto.getCourseName()!= null) {
								%>
								<div class="text-center">
									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=TimeTableCtl.OP_UPDATE%>"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=TimeTableCtl.OP_CANCEL%>">
								</div>
								<%
									} else {
								%>
								<div class="text-center">
									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=TimeTableCtl.OP_SAVE%>"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=TimeTableCtl.OP_RESET%>">
								</div>
								<%
									}
								%>

							</div>
						</div>
					</div>
					<div class="col-md-4 mb-4"></div>
				</div>
		</form>
		</main>


	</div>

</body>
<%@include file="FooterView.jsp"%>
</html>