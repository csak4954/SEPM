  
var app = angular.module('StarterApp', ['ngMaterial','ngMdIcons']);

app.controller('AppCtrl', ['$scope', '$mdSidenav', function($scope, $mdSidenav){
	
	$mdSidenav('left').close()
    .then(function(){
      $log.debug("close LEFT is done");
    });
	
	$scope.toggleLeft = function() {
	    $mdSidenav('left').toggle()
	                      .then(function(){
	                          console.log("toggle left is done");
	                      });
	  };
}]);
