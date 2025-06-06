<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.ForgetPasswordCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ForgetPassword View</title>
<style type="text/css">
/* .header {
	width: 103%;
} */
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
	background-repeat: no-repeat;
	background-size: 100%;
	padding-top: 120px;
	.
	input-group-addon
	{
	box-shadow
	:
	9px
	8px
	7px
	#001a33;
}
}
</style>
</head>
<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">

			<div class="row pt-5">
				<!-- Grid column -->
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div class="card input-group-addon">
						<div class="card-body">
							<h3 class="text-center default-text text-primary">Forget
								Your Password?</h3>
							<p class="text-center default-text text-info"
								style="font-size: 11px;">Submit your Email Address and we
								will Send You password</p>

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
							<!--Body-->
							<div>

								<jsp:useBean id="bean" class="in.co.rays.project_3.dto.UserDTO"
									scope="request"></jsp:useBean>

								<input type="hidden" name="id" value="<%=bean.getId()%>">
								<input type="hidden" name="createdBy"
									value="<%=bean.getCreatedBy()%>"> <input type="hidden"
									name="modifiedBy" value="<%=bean.getModifiedBy()%>"><input
									type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
							</div>
							<div class="md-form input-group-addon">
								<span class="pl-sm-5"><b>Email Id</b><span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-envelope grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="login"
											id="defaultForm-pass" placeholder="Enter email"
											value="<%=ServletUtility.getParameter("login", request)%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("login", request)%></font></br>



								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 16px"
										value="<%=ForgetPasswordCtl.OP_GO%>">
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
