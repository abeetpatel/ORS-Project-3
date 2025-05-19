<%@page import="in.co.rays.project_3.controller.ChangePasswordCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Change View</title>
<link href="<%=ORSView.APP_CONTEXT%>/css/main.css" rel="stylesheet"
	id="bootstrap-css">
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.4.1/js/mdb.min.js"></script>
<style type="text/css">
.log1 {
	padding-top: 5%;
	/* padding-left: 30%; */
}


i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	 padding-bottom: 11px; 
	 background-color: #ebebe0;
}
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/password.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed; 
	background-size: cover;
	padding-top: 70px;
	
	
}
</style>
</head>
<body class="hm">
<body style="background-color: pink;">
	<div class="header">
		<%@include file="Header.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">

			<div class="row log1">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card">
						<div class="card-body">

							<h3 class="text-center text-primary">
							 Change Password
							</h3>
							<!--Body-->
							<div>

								<jsp:useBean id="dto" class="in.co.rays.project_3.dto.UserDTO"
									scope="request"></jsp:useBean>
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
								<span class="pl-sm-5"><b>Old Password</b><span style="color:red;">*</span></span></br>
	<div class="col-sm-12">
      <div class="input-group">
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-key grey-text" style="font-size: 1rem;"></i> </div>
        </div>
		<input class="form-control" type="password" name="oldpassword" placeholder="Enter password" 
		value=<%=DataUtility.getString(request.getParameter("oldpassword") == null ? "": DataUtility.getString(request.getParameter("oldpassword")))%>>
		 </div>
    </div>					
	<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("oldpassword", request)%></font></br>												
								 
                                 
     <span class="pl-sm-5"><b>New Password</b><span style="color:red;">*</span></span> </br>
     <div class="col-sm-12">
      <div class="input-group">
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-lock grey-text" style="font-size: 1rem;"></i> </div>
        </div>
		<input class="form-control" type="password" name="newpassword" placeholder="Enter password" 
	value=<%=DataUtility.getString(request.getParameter("newpassword") == null ? ""
							: DataUtility.getString(request.getParameter("newpassword")))%>>
		 </div>
    </div>
     <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("newpassword", request)%></font></br>
                                
     <span class="pl-sm-5"><b>Confirm Password</b><span style="color:red;">*</span></span> </br>
     <div class="col-sm-12">
      <div class="input-group">
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-lock grey-text" style="font-size: 1rem;"></i> </div>
        </div>
		<input class="form-control" type="password" name="confirmpassword" placeholder="Enter password" value=<%=DataUtility.getString(request.getParameter("confirmpassword") == null ? ""
							: DataUtility.getString(request.getParameter("confirmpassword")))%>>
		 </div>
    </div>	                            
    <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("confirmpassword", request)%></font></br>                             
                                 
							</div>
							</br>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=ChangePasswordCtl.OP_SAVE%>">
									<input type="submit" name="operation"
									class="btn btn-warning btn-md" style="font-size: 17px"
									value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>">
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