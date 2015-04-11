<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html lang="en" ng-app="StarterApp">
  <head>
    <link rel="stylesheet" href="<c:url value="/resources/bower_components/angular-material/angular-material.css" />">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=RobotoDraft:300,400,500,700,400italic"> 
    <link rel="stylesheet" href="<c:url value="/resources/css/home.css" />">   
    <meta name="viewport" content="initial-scale=1" />
  </head>
  
  <body>
	<div ng-controller="AppCtrl">
	<section layout="row" flex>
	
	  	<md-sidenav class="md-sidenav-left md-whiteframe-z2" md-component-id="left">
	      <md-toolbar class="md-theme-indigo">
	        <h1 class="md-toolbar-tools">Navigation</h1>
	      </md-toolbar>
	      <md-content layout=column>
	        <md-button class="md-primary">
				<span style="float:left;">Lectures</span>
	        </md-button>
	       	<md-button class="md-primary">
				<span style="float:left;">Statistic</span>
	        </md-button>
	        <md-button class="md-primary">
				<span style="float:left;">Map</span>
	        </md-button>
	      </md-content>
	    </md-sidenav>
	    <md-toolbar>
	      <div class="md-toolbar-tools">
	        <md-button class="md-icon-button" aria-label="Settings" ng-click="toggleLeft()">
	          <ng-md-icon icon="menu"></ng-md-icon>
	        </md-button>
	        <h2>
	          <span>Student</span>
	        </h2>
	        <span flex></span>
	        <md-button class="md-icon-button" aria-label="More">
	          <ng-md-icon icon="more_vert"></ng-md-icon>
	        </md-button>
	      </div>
	    </md-toolbar>
	
		</section>
	</div>


    <!-- Angular Material Dependencies -->
    <script src="<c:url value="/resources/bower_components/angular/angular.js"/>"></script>
    <script src="<c:url value="/resources/bower_components/angular-animate/angular-animate.js"/>"></script>
    <script src="<c:url value="/resources/bower_components/angular-aria/angular-aria.js"/>"></script>
    <script src="<c:url value="/resources/bower_components/angular-material-icons/angular-material-icons.min.js" />"></script>
    <script src="<c:url value="/resources/bower_components/angular-material/angular-material.min.js" />"></script>
    
    <script src="<c:url value="/resources/js/student.js" />"></script>
  </body>
</html>


