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
	    <md-toolbar>
	      <div class="md-toolbar-tools">
	        <md-button class="md-icon-button" aria-label="Settings">
	          <ng-md-icon icon="menu"></ng-md-icon>
	        </md-button>
	        <h2>
	          <span>Login</span>
	        </h2>
	        <span flex></span>
	        <md-button class="md-icon-button" aria-label="More">
	          <ng-md-icon icon="more_vert"></ng-md-icon>
	        </md-button>
	      </div>
	    </md-toolbar>
	    

		<div layout layout-align="center center">   
			<md-whiteframe class="md-whiteframe-z1" layout layout-align="center center">
				<div layout="column">
				
				  <div>
				  	<span style="font-size:26px">Connect now</span>	
				  </div>
				  
				  <div style="margin-top:10px">
				  	<md-input-container flex>
	          			<label>Username</label>
	          			<input ng-model="username">
	        		</md-input-container>
				  </div>
				  
				  <div>
				  	<md-input-container flex>
	          			<label>Password</label>
	          			<input type="password" ng-model="password">
	        		</md-input-container>
				  </div>
				  
				  <div>
				 	 <md-button class="md-raised md-primary" ng-click="navStudent()">Connect</md-button>
				  </div>	
				 <div>
				 	 <md-button class="md-raised md-primary" ng-click="navProfessor()">Connect-Professor</md-button>
				  </div>
				</div>
					
		  	</md-whiteframe>
		</div>
	</div>


    <!-- Angular Material Dependencies -->
    <script src="<c:url value="/resources/bower_components/angular/angular.js"/>"></script>
    <script src="<c:url value="/resources/bower_components/angular-animate/angular-animate.js"/>"></script>
    <script src="<c:url value="/resources/bower_components/angular-aria/angular-aria.js"/>"></script>
    <script src="<c:url value="/resources/bower_components/angular-material-icons/angular-material-icons.min.js" />"></script>
    <script src="<c:url value="/resources/bower_components/angular-material/angular-material.min.js" />"></script>
    
    <script src="<c:url value="/resources/js/home.js" />"></script>
  </body>
</html>


