var app = angular.module('StarterApp', ['ngMaterial','ngMdIcons']);

app.controller('AppCtrl', ['$scope', '$mdSidenav', function($scope, $mdSidenav){
	
	
    $scope.navStudent = function() 
    {
    	window.location='/los/student';
    }
    $scope.navProfessor = function() 
    {
    	window.location='/los/professor';
    }
}]);