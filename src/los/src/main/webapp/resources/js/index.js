var app = angular.module('Application', [ 'googlechart', 'xeditable', 'ngRoute', 'ngAnimate', 'ngMaterial','ngMdIcons','lumx']);

app.factory('pageData', function() {
    var title = 'default';
    var header = 'default';
    var hideToolbarRight = true;

    return {
        title: function() { return title; },
        setTitle: function(value) { title = value },

        header: function() { return header; },
        setheader: function(value) { header = value },

        hideToolbarRight: function() { return hideToolbarRight; },
        sethideToolbarRight: function(value) { hideToolbarRight = value }
    };
});

app.run(function(editableOptions) {
    editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
});


// ROUTING ===============================================
// set our routing for this application
// each route will pull in a different controller
app.config(function($routeProvider) {

    $routeProvider

        // home page
        .when('/', {
            templateUrl: 'resources/static/login.html',
            controller: 'loginController'
        })

        .when('/login', {
            templateUrl: 'resources/static/login.html',
            controller: 'loginController'
        })

        // about page
        .when('/student', {
            templateUrl: 'resources/static/student.html',
            controller: 'studentController'
        })

        // contact page
        .when('/professor', {
            templateUrl: 'resources/static/professor.html',
            controller: 'professorController'
        });

});


// CONTROLLERS ============================================
app.controller('mainController', function($scope, pageData, $location) {
    $scope.pageData = pageData;

    $scope.changeView = function(view){
        $location.path(view); // path not hash
    }
    
    pageData.toggleDropdown = function()
	{
    	console.log("toggle");
    	$mdSidenav('right').open()
        .then(function () {
        });
	}
});



