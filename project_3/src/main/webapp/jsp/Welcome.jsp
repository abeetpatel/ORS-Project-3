<%@page import="in.co.rays.project_3.controller.ORSView"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome Page</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="/resources/demos/style.css">
<style>
.p4 {
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/img/WallpaperDog-988052.jpg');
	background-size: 100%;
	background-repeat: no-repeat;
}

.cl {
	font-family: Lucida Calligraphy;
	font-family: Monotype Corsiva;
	font-family: Footlight MT Light;
}
</style>

</head>
<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
	</div>
	<div class="text-cs1">
		<center>
			<h1 style="padding-top: 20%; color: white">
				<b class="cl">" Welcome to online result system "</b>
			</h1>
		</center>
	</div>
	<div class="footer">
		<%@include file="FooterView.jsp"%>
	</div>
</body>

</html>