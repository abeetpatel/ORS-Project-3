<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.MarksheetDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.MarksheetListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MarksheetListView</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.p1 {
	padding: 8px;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;

	/* background-size: 100%; */
}
</style>
</head>
<body class="hm">
	<div>
		<%@include file="Header.jsp"%>
	</div>
	<div>
		<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.MarksheetDTO"
				scope="request"></jsp:useBean>
			<%
				List list1 = (List) request.getAttribute("RollNo");
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				List list = ServletUtility.getList(request);
				Iterator<MarksheetDTO> it = list.iterator();
				if (list.size() != 0) {
			%>
			<center>
				<h1 class="text-dark font-weight-bold pt-3">
					<font color="black">Marksheet List 
				</h1>
				</font>
			</center>

			</br>
			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>
				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">
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
			<div class="row">

				<div class="col-sm-2"></div>
				<span><b>Name :</b></span>
				<div class="col-sm-2">
					<input class="form-control" type="text" name="name"
						placeholder="Enter name" class="p1"
						value="<%=ServletUtility.getParameter("name", request)%>">
				</div>
				<span><b>Roll No:</b></span>
				<div class="col-sm-3"><%=HTMLUtility.getList("rollId", String.valueOf(dto.getId()), list1)%></div>
				<div class="col-sm-2">
					<input type="submit" class="btn btn-primary btn-md"
						style="font-size: 17px" name="operation"
						value="<%=MarksheetListCtl.OP_SEARCH%>">&emsp; <input
						type="submit" class="btn btn-dark btn-md" style="font-size: 17px"
						name="operation" value="<%=MarksheetListCtl.OP_RESET%>">
				</div>

				<div class="col-sm-3"></div>
			</div>


			</br>
			<div style="margin-bottom: 20px;" class="table-responsive">
				<table class="table  table-dark table-bordered table-hover">
					<thead>
						<tr align="center" style="background-color: #8C8C8C;">

							<th width="10%"><input type="checkbox" id="select_all"
								name="Select" class="text"> Select All</th>
							<th class="text">S.NO</th>
							<th class="text">RollNo</th>
							<th class="text">Name</th>
							<th class="text">Physics</th>
							<th class="text">Chemistry</th>
							<th class="text">Maths</th>
							<th class="text">Edit</th>
						</tr>
					</thead>
					<%
						while (it.hasNext()) {
								dto = it.next();
					%>

					<tbody>
						<tr align="center">
							<td><input type="checkbox" class="checkbox" name="ids"
								value="<%=dto.getId()%>"></td>
							<td><%=index++%></td>
							<td><%=dto.getRollNo()%></td>
							<td><%=dto.getName()%></td>
							<td><%=dto.getPhysics()%></td>
							<td><%=dto.getChemistry()%></td>
							<td><%=dto.getMaths()%></td>

							<td align="center"><a
								href="MarksheetCtl?id=<%=dto.getId()%>">Edit</a></td>
						</tr>
					</tbody>
					<%
						}
					%>
				</table>
			</div>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						class="btn btn-secondary btn-md" style="font-size: 17px"
						value="<%=MarksheetListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=MarksheetListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						class="btn btn-danger btn-md" style="font-size: 17px"
						value="<%=MarksheetListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-secondary btn-md" style="font-size: 17px"
						style="padding: 5px;" value="<%=MarksheetListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
				<tr></tr>
			</table>

			<%
				}
				if (list.size() == 0) {
					System.out.println("user list view list.size==0");
			%>
			<center>
				<h1 class="text-primary font-weight-bold pt-3">
					<font color="black">Marksheet List 
				</h1>
				</font>
			</center>
			</br>
			<div class="row">
				<div class="col-md-4"></div>
				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>


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
			</br>
			<div style="padding-left: 48%;">
				<input type="submit" name="operation" class="btn btn-primary btn-md"
					style="font-size: 17px" value="<%=MarksheetListCtl.OP_BACK%>">
			</div>
			<%
				}
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


		</form>

	</div>
	</br>
	</br>
</body>
<%@include file="FooterView.jsp"%>

</html>