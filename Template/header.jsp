<!DOCTYPE html>
<%@page import="helpers.UserSession"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Social Network CS595 Project">
<meta name="author" content="Team22,Vishnu,Rohith,Meenakshi">
<title><%=request.getAttribute("title")%></title>

<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link href="assets/css/animate.min.css" rel="stylesheet" media="screen">
<link href="assets/css/smallworld.css" rel="stylesheet"	media="screen">
<% if(Boolean.parseBoolean((String)request.getAttribute("hidenav"))){%>
<link href="assets/css/gsdk-base.css" rel="stylesheet"	media="screen">
<%} %>

<link rel="shortcut icon" href="img/logo/logo_50.png">
</head>
<body onload="init()">
	<style>			
		.popupItem {
			color: black !important;
			font-size: 16px;
		}		
		.popupItem:hover td {
			border-color: blue;
			color: blue;
		}
		.popupItem td{
			border-bottom: 1px gray solid;
		}
	</style>
	<% if(!Boolean.parseBoolean((String)request.getAttribute("hidenav"))){%>
	<nav class="navbar navbar-fixed-top navbar-default navbar-principal"
		style="min-height: 55px;">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<img alt="" src="img/logo/logo_50.png" style="width:45px;height:45px;float: left;padding-top: 5px;margin-right: 10px ">
				<a class="navbar-brand" href="Home"><b>Small World</b></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<div class="col-md-5 col-sm-4">
					<div class="navbar-form">
						<div class="form-group" style="display: inline;">
							<div class="input-group" style="display: table;">
								<input class="form-control" name="search" id="autocompletefield"
									placeholder="Search..." autocomplete="off" onkeyup="doCompletion()"
									 type="text"> <span
									class="input-group-addon" style="width: 1%;"> <span
									class="glyphicon glyphicon-search"></span>
								</span>
							</div>
							
						<div id="auto-row">
							<table id="complete-table" class="table table-bordered" 
								style="position: absolute;  background-color: white; width:350px"></table>
						</div>
						</div>
					</div>
				</div>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="Home" class="nav-controller"><i class="fa fa-home"></i> Home</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="true"> <i
							class="fa fa-user"></i> <%=UserSession.getFirstname(session)%><span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="Timeline">Timeline</a></li>
							<li><a href="About">Edit Profile</a></li>
							<!-- <li><a href="#">Messages</a></li> -->
							<li><a href="Friends">Friends</a></li>
							<li class="divider"></li>
							<li><a href="ChangePassword">Change Password</a></li>
						</ul></li>
					<li><a href="Logout" class="nav-controller"><i class="fa fa-sign-out"></i> Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<% } %>