<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html lang="en"  ng-app="Application" ng-controller="mainController">
<head>
    <link rel="stylesheet" href="<c:url value="resources/bower_components/lumx/dist/lumx.css" />">
    <link rel="stylesheet" href="<c:url value="resources/bower_components/mdi/css/materialdesignicons.css" />">
    <link rel="stylesheet" href="<c:url value="resources/bower_components/angular-material/angular-material.css" />">
    <link rel="stylesheet" href="<c:url value="resources/bower_components/angular-xeditable/dist/css/xeditable.css" />">
    <link rel="stylesheet" href="<c:url value="resources/css/index.css" />">
    
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=RobotoDraft:300,400,500,700,400italic">


	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	
    <meta charset="utf-8">

    <title>{{pageData.title()}}</title>
    
    <style>
		
		html, body {
		
		width: 100%;
		margin: 0px;
		padding: 0px;
		}
		
	</style>
    
</head>

<body>
<div>
    <div class="page {{ pageClass }} viewContainer" ng-view></div>
</div>



<!-- Angular Material Dependencies -->
<script src="<c:url value="resources/bower_components/jquery/dist/jquery.js" />"></script>
<script src="<c:url value="resources/bower_components/velocity/velocity.js" />"></script>
<script src="<c:url value="resources/bower_components/moment/min/moment-with-locales.js" />"></script>

<script src="<c:url value="resources/bower_components/angular/angular.js" />"></script>
<script src="<c:url value="resources/bower_components/angular-route/angular-route.js" />"></script>
<script src="<c:url value="resources/bower_components/angular-animate/angular-animate.js" />"></script>
<script src="<c:url value="resources/bower_components/angular-aria/angular-aria.js" />"></script>
<script src="<c:url value="resources/bower_components/angular-material-icons/angular-material-icons.min.js" />"></script>
<script src="<c:url value="resources/bower_components/angular-material/angular-material.min.js" />"></script>


<script src="<c:url value="resources/bower_components/chart/chart.js" />"></script>

<script src="<c:url value="resources/bower_components/angular-xeditable/dist/js/xeditable.js" />"></script>

<script src="<c:url value="resources/bower_components/lumx/dist/lumx.js" />"></script>
<script src="<c:url value="resources/js/index.js" />"></script>

<script src="<c:url value="resources/js/loginController.js" />"></script>
<script src="<c:url value="resources/js/studentController.js" />"></script>
<script src="<c:url value="resources/js/professorController.js" />"></script>

</body>
</html>


