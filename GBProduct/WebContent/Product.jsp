<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.Product" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>GadgetBadgets</title>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script type="text/javascript" src="Components/item.js"></script>
		<link rel="stylesheet" href="Views/bootstrap.min.css">
	</head>
	<body>
	<div class="row container">
		<div class="col-10">
		<!-- start the card  -->
		<div class="card">
			<!-- Headline of the card -->
			<h3 class="text-center ">
				<strong>Add Products</strong>
			</h3>
			<!-- start the card body -->
			<div class="card-body container border border-info">
				<!-- start the form -->
				<form id="formProduct" name="formProduct" method="post" >
					Product Code:
					<input type="text" name="code" id="code" class="form-control border border-primary form-control-sm"><br>
					Product Name:
					<input type="text" name="name" id="name" class="form-control border border-primary form-control-sm"><br>
					Product Price:
					<input type="text" name="price" id="price" class="form-control border border-primary form-control-sm"><br>
					Product Description:
					<input type="text" name="description" id="description" class="form-control border border-primary form-control-sm"><br>
					
					<div id="alertSuccess" class="alert alert-success"></div><br>
					<div id="alertDanger" class="alert alert-danger"></div><br>
					
					<input type="button" id="bntSave" name="bntSave" value="Save" class="btn btn-outline-success"><br>
					
					<input type="hidden" id="hidID" name="hidID" value="">
				</form><br>
				<!-- end the form -->
				<!--  Display the HTML table -->
				<div class="row container">
					<div class="col-12" id="colProduct">
						<%
							Product productObj  = new Product();
							out.print(productObj.readProducts());
						%>
					</div>
				</div>
				<!-- End display the HTML  -->
			</div>
			<!-- end the card body -->
			
		</div>
		<!-- end the card  -->
		</div>
	</div>
	</body>
</html>