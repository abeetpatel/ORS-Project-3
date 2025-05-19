<%@page import="in.co.rays.project_3.controller.MarksheetListCtl"%>
<%@page import="in.co.rays.project_3.dto.MarksheetDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet Merit List View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.p1 {
	padding-top: 200px;
	font-size: 20px;
	color: #b62f2f;
}

.text {
	text-align: center;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed; 
	background-size: cover;
	padding-top: 85px;
	
}
</style>
</head>
<body class="p4">
	<div>
		<%@include file="Header.jsp"%>
	</div>
	<div>
		<form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="post">



			<div align="center">
				<h1 class="text-light font-weight-bold pt-3"><font color="black">Marksheet Merit
					List</h1></font>
			</div>

			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>

				<div class="col-md-4"></div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.MarksheetDTO"
				scope="request"></jsp:useBean>



			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<MarksheetDTO> it = list.iterator();
				if (list.size() != 0) {
			%>



			</br>
			<div style="margin-left: 87%;" class="pb-2">

				<a href="/project_3/ctl/JasperCtl" class="btn btn-lg btn-warning "
					role="button" target="blank"> <span class="fa fa-print mr-1"></span>Print
				</a>
			</div>
			<div style="margin-bottom: 20px;" class="table-responsive">
				<table class="table table-dark  table-hover">
					<thead>
						<tr style="background-color: #8C8C8C;">

							<th class="text">S.NO</th>

							<th class="text">RollNo</th>
							<th class="text">Name</th>
							<th class="text">Physics</th>
							<th class="text">Chemistry</th>
							<th class="text">Maths</th>
							<th class="text">Total</th>
							<th class="text">Percentage(%)</th>
						</tr>
					</thead>
					<%
						while (it.hasNext()) {
								dto = it.next();
					%>

					<tbody>
						<tr>

							<td align="center"><%=index++%></td>
							<td align="center"><%=dto.getRollNo()%></td>
							<td align="center"><%=dto.getName()%></td>
							<td align="center"><%=dto.getPhysics()%></td>
							<td align="center"><%=dto.getChemistry()%></td>
							<td align="center"><%=dto.getMaths()%></td>
							<td align="center">
								<%
									int total = (dto.getChemistry() + dto.getPhysics() + dto.getMaths());
								%><%=total%></td>
							<td align="center">
								<%
									float percentage = ((total * 100) / 300);
								%> <%=percentage%></td>

						</tr>
					</tbody>
					<%
						}
					%>
				</table>
			</div>
			<div style="padding-left: 48%;">
				<input type="submit" name="operation" class="btn btn-dark btn-md"
					style="font-size: 17px" value="<%=MarksheetListCtl.OP_BACK%>">
			</div>



			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


		</form>
		<br> <br> <br>
	</div>

</body>
<%@include file="FooterView.jsp"%>


</html>