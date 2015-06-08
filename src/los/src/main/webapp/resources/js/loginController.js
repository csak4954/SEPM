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
    	$http.get('/los/authentication/'+user+'/'+password).
    	  success(function(data, status, headers, config) 
    	  {
    		  if(data.affiliation != null)
    			  $scope.changeView(data.affiliation);
    		  else
    			  LxNotificationService.error('Login failed'); 
    	  }).
    	  error(function(data, status, headers, config) 
          {
    		  LxNotificationService.error('Login failed'); 
    	  });
    }
});