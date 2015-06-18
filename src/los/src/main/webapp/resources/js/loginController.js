/**
 * Created by Mathias Hölzl on 21.04.2015.
 */

app.controller('loginController', function($scope, pageData, $http, LxNotificationService, LxDialogService, $timeout) {
    $scope.pageClass = 'login';
    pageData.setTitle('Login');
    pageData.setheader('Login');
    pageData.sethideToolbarRight(true);

    
    $scope.username = "";
    $scope.password = "";
    $scope.showError = false;
    

    $scope.showRegistration = false;
   
    $scope.prevID = "";
    
    //deobunce mobile click
    $scope.isValid = function(id)
    {
    	var check = false;
    	  (function(a){if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4)))check = true})(navigator.userAgent||navigator.vendor||window.opera);
    	if(check)
		{
    		if($scope.prevID !== id)
			{
    			$scope.prevID = id;
    			return true;
			}
    		else
			{
    			$scope.prevID = "";
    			return false;
			}
		}
    	else
		{
    		return true;
		}
    }
    
    
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
    	if(!$scope.isValid("loginAdmin"))
    		return;
    	
    	$http.post('/los/authentication/login', { username: usernameAdmin, password: ""+CryptoJS.SHA512(passwordAdmin) }).
  	  success(function(data, status, headers, config) 
  	  {
  		  if(data == null)
  		  {
  			  LxNotificationService.error('Login failed');
  			  $scope.password = "";
  		  }
  		  
  		  if(data.affiliation == "admin")
  		  {
  			$http.put('/los/authentication/register/professor', { name: $scope.name, surename: $scope.surename, email: $scope.email, id: $scope.username, password: ""+CryptoJS.SHA512($scope.password) }).
	      	  success(function(data, status, headers, config) 
	      	  {
	      		  if(data == null)
	      		  {
	      			  LxNotificationService.error('Login failed');
	      			  $scope.password = "";
	      			  $scope.password2 = "";
	      			  $scope.passwordAdmin = "";
	      		  }
	      		  
	      		  LxNotificationService.success('Registration successfully');
	      		  LxDialogService.close('adminDialog');
	      		  $scope.showRegistration = false;
	      		  $scope.password = "";
	      		  $scope.password2 = "";
	      		  $scope.passwordAdmin = "";
	      	  }).
	      	  error(function(data, status, headers, config) 
	            {
				  LxNotificationService.error('Login failed: ' + data.message);
	  			  $scope.passwordAdmin = "";
	  			  $scope.password = "";
	  			  $scope.password2 = "";
	  		  });		
  		  }
  	  }).
  	  error(function(data, status, headers, config) 
        {
  		      LxNotificationService.error('Login failed: ' + data.message);
  			  $scope.password = "";
  			  $scope.password2 = "";
  			  $scope.passwordAdmin = "";
		  });
    }
    
    $scope.onRegister = function(name, surename, email, username, password, password2)
    {	
    	if(!$scope.isValid("onRegister"))
    		return;
    	
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
        	$http.put('/los/authentication/register/student', { name: name, surename: surename, email: email, id: username, password: ""+CryptoJS.SHA512(password) }).
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
				  LxNotificationService.error('Login failed: ' + data.message);
	  			  $scope.password = "";
	  			  $scope.password2 = "";
	  		  });		
		}
    }
    
    $scope.onConnect = function(user, password)
    {
    	if(!$scope.isValid("onConnect"))
    		return;
    	
    	if(user == "")
		{
    		  LxNotificationService.error('Login failed: username empty');
			  $scope.password = "";
			  return;
		}
    	
    	if(user.length < 5)
		{
			  LxNotificationService.error('Login failed: username to short,more then 6 symbol');
			  $scope.password = "";
			  return;
		}
    	
    	if(password == "")
		{
			  LxNotificationService.error('Login failed: password empty');
			  $scope.password = "";
			  return;
		}
    	
    	if(password.length < 5)
		{
			  LxNotificationService.error('Login failed: password to short, more then 6 symbols');
			  $scope.password = "";
			  return;
		}
    	
    	$http.post('/los/authentication/login', { username: user, password: ""+CryptoJS.SHA512(password) }).
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