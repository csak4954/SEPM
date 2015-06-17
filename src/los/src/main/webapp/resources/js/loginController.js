/**
 * Created by Mathias Hölzl on 21.04.2015.
 */

app.controller('loginController', function($scope, pageData, $http, LxNotificationService, LxDialogService) {
    $scope.pageClass = 'login';
    pageData.setTitle('Login');
    pageData.setheader('Login');
    pageData.sethideToolbarRight(true);

    $scope.username = "";
    $scope.password = "";
    $scope.showError = false;

    $scope.showRegistration = false;
    
    
    $scope.onkey = function(event)
    {
    	if(event.keyCode == 13)
    		$scope.onConnect($scope.username, $scope.password);
    }
    
    $scope.cancleAdminLogin = function()
    {
    	$scope.password = "";
    }

    $scope.loginAdmin = function(usernameAdmin, passwordAdmin)
    {
    	$http.post('/los/authentication/login', { username: usernameAdmin, password: passwordAdmin }).
  	  success(function(data, status, headers, config) 
  	  {
  		  if(data == null)
  		  {
  			  LxNotificationService.error('Login failed');
  			  $scope.password = "";
  		  }
  		  
  		  if(data.affiliation == "admin")
  		  {
  			$http.put('/los/authentication/register/professor', { name: $scope.name, surename: $scope.surename, email: $scope.email, id: $scope.username, password: $scope.password }).
	      	  success(function(data, status, headers, config) 
	      	  {
	      		  if(data == null)
	      		  {
	      			  LxNotificationService.error('Login failed');
	      			  $scope.password = "";
	      			  $scope.password2 = "";
	      		  }
	      		  
	      		  LxNotificationService.success('Registration successfully');
	      		  LxDialogService.close('adminDialog');
	      		  $scope.showRegistration = false;
	      		  $scope.password = "";
	      	  }).
	      	  error(function(data, status, headers, config) 
	            {
	  			  LxNotificationService.error('Login failed');
	  			  $scope.password = "";
	  			  $scope.password2 = "";
	  		  });		
  		  }
  	  }).
  	  error(function(data, status, headers, config) 
        {
  		  console.log("asdasd");
			  LxNotificationService.error('Login failed');
			  $scope.passwordAdmin = "";
		  });
    }
    
    $scope.onRegister = function(name, surename, email, username, password, password2)
    {	
    	if(name == "")
		{
    		LxNotificationService.error('Name cannot be empty');
    		$scope.password = "";
    		$scope.password2 = "";
    		return;
		}
    	
    	if(surename == "")
		{
    		LxNotificationService.error('Surename cannot be empty');
    		$scope.password = "";
    		$scope.password2 = "";
    		return;
		}
    	
    	if(email == "")
		{
    		LxNotificationService.error('Email cannot be empty');
    		$scope.password = "";
    		$scope.password2 = "";
    		return;
		}
    	
    	if(username == "")
		{
    		LxNotificationService.error('Username cannot be empty');
    		$scope.password = "";
    		$scope.password2 = "";
    		return;
		}
    	
    	if(password == "" || password2 == "")
		{
    		LxNotificationService.error('Password cannot be empty');
    		$scope.password = "";
    		$scope.password2 = "";
    		return;
		}
    	
    	
    	if(password != password2)
		{
    		LxNotificationService.error('Passwords to not match');
    		$scope.password = "";
    		$scope.password2 = "";
    		return;
		}
    	
    	if($scope.asProfesssor)
		{
            LxDialogService.open('adminDialog');
		}
    	else
		{
        	$http.put('/los/authentication/register/student', { name: name, surename: surename, email: email, id: username, password: password }).
	      	  success(function(data, status, headers, config) 
	      	  {
	      		  if(data == null)
	      		  {
	      			  LxNotificationService.error('Registration failed');
	      			  $scope.password = "";
	      			  $scope.password2 = "";
	      		  }
	      		  
	      		  LxNotificationService.success('Registration successfully');
	      		  $scope.showRegistration = false;
	      		  $scope.password = "";
	      	  }).
	      	  error(function(data, status, headers, config) 
	            {
	  			  LxNotificationService.error('Registration failed: ' + data);
	  			  $scope.password = "";
	  			  $scope.password2 = "";
	  		  });		
		}
    }
    
    $scope.onConnect = function(user, password)
    {
    	failed = false;
    	
    	if(user == "")
		{
			  LxNotificationService.error('Login failed: username empty');
			  $scope.password = "";
			  failed = true;
		}
    	
    	if(user.length < 6)
		{
			  LxNotificationService.error('Login failed: username to short,more then 6 symbol');
			  $scope.password = "";
			  failed = true;
		}
    	
    	if(password == "")
		{
			  LxNotificationService.error('Login failed: password empty');
			  $scope.password = "";
			  failed = true;
		}
    	
    	if(password.length < 6)
		{
			  LxNotificationService.error('Login failed: password to short, more then 6 symbols');
			  $scope.password = "";
			  failed = true;
		}
    	
    	if(failed)
		{
    		return;
		}
    	
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
    		  else if(data.affiliation == "admin")
    			  $scope.changeView("admin");
    		  else
    		  {
    			  LxNotificationService.error('Login failed');
    			  $scope.password = "";
    		  }
    	  }).
    	  error(function(data, status, headers, config) 
          {
			  LxNotificationService.error('Login failed: ' + data.message);
			  $scope.password = "";
		  });
    }
});