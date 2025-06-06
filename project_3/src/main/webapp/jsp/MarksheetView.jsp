<%@page import="in.co.rays.project_3.controller.MarksheetCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
.log1 {
	padding-top: 5%;
	/* padding-left: 30%; */
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed; 
	background-size: cover;
	padding-top: 70px;
	
	/* background-size: 100%; */
}

.grad {
	background-color: linear-gradient(to bottom right, blue, white);
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}
</style>
</head>
<body class="hm">
	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.MarksheetDTO"
		scope="request"></jsp:useBean>

	<div class="header">
		<%@include file="Header.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">

			<div class="row pt-2 pb-5">
				<div class="col-md-4"></div>
				<!-- Grid column -->
				<div class="col-md-4">
					<div class="card input-group-addon grad">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getId() != null) {
							%>
							<h3 class="text-center default-text text-primary">Update
								Marksheet</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add
								Marksheet</h3>
							<%
								}
							%>

							<!--Body-->
							<div>
								<%
									List l = (List) request.getAttribute("studenList");
								%>

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
								<span class="pl-sm-5"><b>Roll No</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-list-ol grey-text"></i>
											</div>
										</div>
										<input type="text" name="roll" class="form-control"
											placeholder="Enter RollNo"
											value="<%=DataUtility.getStringData(dto.getRollNo())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("roll", request)%></font></br>


								<span class="pl-sm-5"><b>Student Name</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user grey-text"></i>
											</div>
										</div>
										<%=HTMLUtility.getList("studentId", String.valueOf(dto.getStudentId()), l)%>
									</div>

								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("studentId", request)%></font></br>


								<span class="pl-sm-5"><b>Physics</b><span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-equals grey-text"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="physics"
											id="defaultForm-email" placeholder="Enter Physics"
											value="<%=DataUtility.getStringData(dto.getPhysics()).equals("0") ? ""
					: DataUtility.getStringData(dto.getPhysics())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("physics", request)%></font></br>


								<span class="pl-sm-5"><b>Chemistry</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-equals grey-text"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="chemistry"
											id="defaultForm-email" placeholder="Enter chemistry"
											value="<%=DataUtility.getStringData(dto.getChemistry()).equals("0") ? ""
					: DataUtility.getStringData(dto.getChemistry())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font></br>

								<span class="pl-sm-5"><b>Maths</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-equals grey-text"></i>
											</div>
										</div>
										<input type="text" name="maths" class="form-control"
											placeholder="Enter Maths"
											value="<%=DataUtility.getStringData(dto.getMaths()).equals("0") ? ""
					: DataUtility.getStringData(dto.getMaths())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("maths", request)%></font></br>


							</div>
							</br>
							<%
								if (dto.getId()!=null) {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=MarksheetCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=MarksheetCtl.OP_CANCEL%>">

							</div>
							<%
								} else {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=MarksheetCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=MarksheetCtl.OP_RESET%>">
							</div>

						</div>
						<%
							}
						%>
					</div>
				</div>



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