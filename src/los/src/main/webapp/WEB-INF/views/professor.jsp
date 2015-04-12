<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html lang="en" ng-app="StarterApp">
<head>
<link rel="stylesheet"
	href="<c:url value="/resources/bower_components/angular-material/angular-material.css" />">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=RobotoDraft:300,400,500,700,400italic">
<link rel="stylesheet" href="<c:url value="/resources/css/home.css" />">
<meta name="viewport" content="initial-scale=1" />
</head>

<body>
	<div ng-controller="AppCtrl">
		<section layout="row" flex>
			<md-toolbar>
			<div class="md-toolbar-tools">
				<md-button class="md-icon-button" aria-label="Settings"
					ng-click="toggleLeft()"> <ng-md-icon icon="menu"></ng-md-icon>
				</md-button>
				<h2>
					<span>Professor</span>
				</h2>
				<span flex></span>
				<md-button class="md-icon-button" aria-label="More"> 
				<ng-md-icon icon="more_vert"></ng-md-icon> </md-button>
			</div>
			</md-toolbar>
		</section>




		<md-tabs class="md-primary"> 
		
		<md-tab id="tab1"> 
			<md-tab-label>General</md-tab-label>
			<md-tab-template> 
				<md-button class="md-raised md-primary" style="margin:20px" ng-click="">Start Lecture</md-button>
				
				<span class="centered" style="font-size:80px">1123</span>
									
									
			</md-tab-template> 
		</md-tab> 
		
		
		
		<md-tab id="tab2"
			ng-disabled="hasLecture"> <md-tab-label>Quiz</md-tab-label>
		<md-tab-template> View for Item #2 <br />
		data.selectedIndex = 1; </md-tab-template> </md-tab> <md-tab id="tab3" ng-disabled="hasLecture">
		<md-tab-label>Feedback</md-tab-label> 
		<md-tab-template>



		<div layout="column" flex>
			<md-content style="height: 600px;">
			<section>
				<md-list layout-padding> <md-list-item class="md-3-line"
					ng-repeat="message in feedback">
				<div layout="row" class="md-whiteframe-z2 ellipsis"
					style="margin: 16px;">
					<div>
						<img ng-src="<c:url value="/resources/img/feedback.jpeg" />"
							class="md-avatar face" alt="{{message.who}}">
					</div>
					<div>
						<div class="md-list-item-text" style="margin-left: 16px">
							<div layout="row">
								<div>
									<img ng-src="<c:url value="/resources/img/star.png" />"
										style="width: 12px; height: 12px">
								</div>
								<div>
									<img ng-src="<c:url value="/resources/img/star.png" />"
										style="width: 12px; height: 12px">
								</div>
								<div>
									<img ng-src="<c:url value="/resources/img/star.png" />"
										style="width: 12px; height: 12px">
								</div>
								<div>
									<img ng-src="<c:url value="/resources/img/star.png" />"
										style="width: 12px; height: 12px">
								</div>
								<div>
									<img ng-src="<c:url value="/resources/img/star.png" />"
										style="width: 12px; height: 12px">
								</div>
							</div>
							<h2>{{message.what}}</h2>
							<h3>{{message.when}}</h3>
							<h4>{{message.who}}</h4>
							<pre>{{message.notes}}</pre>
						</div>
					</div>
				</div>
				</md-list-item> </md-list>
			</section>
			</md-content>
		</div>

		</md-tab-template> </md-tab> <md-tab id="tab4" ng-disabled="hasLecture"> <md-tab-label>Statistic</md-tab-label>
		<md-tab-template> View for Item #3 <br />
		data.selectedIndex = 2; </md-tab-template> </md-tab> 
		
		<md-autocomplete
			md-selected-item="selectedItem"
			md-search-text-change="searchTextChange(searchText)"
			md-search-text="searchText"
			md-selected-item-change="selectedItemChange(item)"
			md-items="item in querySearch(searchText)"
			md-item-text="item.display" placeholder="Lecture"
			style="width:200px; margin-top:4px"> <span
			md-highlight-text="searchText" md-highlight-flags="^i">{{item.display}}</span>
		</md-autocomplete> 
		
		</md-tabs>



	</div>


	<!-- Angular Material Dependencies -->
	<script
		src="<c:url value="/resources/bower_components/angular/angular.js"/>"></script>
	<script
		src="<c:url value="/resources/bower_components/angular-animate/angular-animate.js"/>"></script>
	<script
		src="<c:url value="/resources/bower_components/angular-aria/angular-aria.js"/>"></script>
	<script
		src="<c:url value="/resources/bower_components/angular-material-icons/angular-material-icons.min.js" />"></script>
	<script
		src="<c:url value="/resources/bower_components/angular-material/angular-material.min.js" />"></script>

	<script src="<c:url value="/resources/js/professor.js" />"></script>
</body>
</html>


