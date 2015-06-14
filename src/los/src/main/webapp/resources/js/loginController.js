/**
 * Created by Mathias Hölzl on 21.04.2015.
 */

app.controller('loginController', function($scope, pageData, $http, LxNotificationService) {
    $scope.pageClass = 'login';
    pageData.setTitle('Login');
    pageData.setheader('Login');
    pageData.sethideToolbarRight(true);

    $scope.username = "";
    $scope.password = "";
    $scope.showError = false;

    $scope.onConnect = function(user, password)
    {
    	$http.post('/los/authentication/login', { username: user, password: password }).
    	  success(function(data, status, headers, config) 
    	  {
    		  if(data == null)
    		  {
    			  LxNotificationService.error('Login failed');
    			  $scope.password = "";
    		  }
    		  
    		  if(data.affiliation == "staff")
    			  $scope.changeView("professor");
    		  else if(data.affiliation == "student")
    			  $scope.changeView("student");
    		  else
    		  {
    			  LxNotificationService.error('Login failed');
    			  $scope.password = "";
    		  }
    	  }).
    	  error(function(data, status, headers, config) 
          {
			  LxNotificationService.error('Login failed');
			  $scope.password = "";
		  });
    }
});