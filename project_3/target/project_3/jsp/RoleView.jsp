<%@page import="in.co.rays.project_3.controller.RoleCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
.log1 {
	padding-top: 3%;
	/* padding-left: 30%; */
}

i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}
.input-group-addon{
	box-shadow: 9px 8px 7px #001a33;

}
.p4{
background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
background-repeat: no-repeat;
	background-attachment: fixed; 
	background-size: cover;
	padding-top: 85px;
	
	/* background-size: 100%; */
}

</style>
</head>
<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
	</div>
	<div>
		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.RoleDTO"
			scope="request"></jsp:useBean>
		<main>
		<form action="<%=ORSView.ROLE_CTL%>" method="post">

			<div class="row pt-3">
				<div class="col-md-4"></div>
				<!-- Grid column -->
				<div class="col-md-4 ">
					<div class="card input-group-addon">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getId()!=null) {
							%>

							<h3 class="text-center text-primary font-weight-bold">Update
								Role</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary font-weight-bold">Add
								Role</h3>
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

								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy"
									value="<%=dto.getCreatedBy()%>"> <input type="hidden"
									name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
									type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
							</div>
							<div class="md-form input-group-addon">
								<span class="pl-sm-5"><b>Name</b><span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" name="name" class="form-control"
											style="border: 2px solid #8080803b;" placeholder="Enter Name"
											value="<%=DataUtility.getStringData(dto.getName())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("name", request)%></font></br>


								<span class="pl-sm-5"><b>Description</b><span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-list grey-text"></i>
											</div>
										</div>
										<textarea name="description" class="form-control"
											placeholder="Enter description" rows="2" cols="2"><%=DataUtility.getStringData(dto.getDescription())%></textarea>
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("description", request)%></font></br>




							</div>
							</br> </br>
							<%
								if (id > 0) {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=RoleCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=RoleCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=RoleCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=RoleCtl.OP_RESET%>">
							</div>
							<%
								}
							%>

						</div>
					</div>
				</div>
				<div class="col-md-4 mb-4"></div>
		</form>
		</main>


	</div>

</body>
<%@include file="FooterView.jsp"%>
</html>