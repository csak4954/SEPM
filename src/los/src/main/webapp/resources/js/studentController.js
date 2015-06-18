/**
 * Created by Mathias Hölzl on 21.04.2015.
 */
// contact page controller
app.controller('studentController', function($scope, pageData, $http, LxNotificationService, $timeout )
{
    $scope.pageClass = 'student';
    $scope.showContent = true;

    pageData.setTitle('Student');
    pageData.setheader('Student');
    pageData.sethideToolbarRight(false);

    pageData.currentLecture = null;
    pageData.validLecture = false;
    
    $scope.lectures = [];
    $scope.lecturesRegistered = null;
    $scope.selected = { data:""}
    
    $scope.getLectures = function()
    {
        $http.get('/los/lecture/all').
  	  success(function(data, status, headers, config) 
  	  {
  		  $scope.lectures = data;
  	  }).
  	  error(function(data, status, headers, config) 
  	  {  
  		  if(status == 401)
  			  $scope.changeView("login");
  	  });
        
      return $scope.lectures;
    }
    
    $scope.getLectures();
    
    $scope.selectLecture = function(item)
    {
    	$scope.selected.data = item;
    }
    
    pageData.quizPoller = function()
	{
		pageData.quizTimer = $timeout(pageData.quizPoller,2000);
		
		if(!pageData.currentLecture)
			  return;
		
		$http.get('/los/quiz/active').
		  success(function(data, status, headers, config) 
		  {  
			  var found = false;
			  for (var i in data)
			  {
				  if(data[i].lecture.id == pageData.currentLecture)
				  {
					  if(!pageData.hasQuiz)
					  {
						  LxNotificationService.info('Quiz active');
						  pageData.activeQuiz = data[i];
						  pageData.hasQuiz = true;
						  found = true;
					  }
					  
					  return;
				  }
			  }	  
			  
			  if(!found && pageData.activeQuiz)
			  {
				  pageData.activeQuiz = null;
				  pageData.hasQuiz = false;
				  LxNotificationService.info('Quiz ended');
			  }	  
			  
		  }).
		  error(function(data, status, headers, config) 
		  {  
			  if(status == 401)
			  {
				  $scope.changeView("login");	  
				  $timeout.cancel(pageData.quizTimer);
			  }
		  });
	}
	
    pageData.quizPoller();
    
    
    //debuonce mobile click
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
    	return true;
    }
	
    
    
    $scope.logout = function()
    {
    	if(!$scope.isValid("logout"))
    		return;
    	
    	$http.post('/los/authentication/logout').
	  	  success(function(data, status, headers, config) 
	  	  {
	  		$scope.changeView("login");
	  	  }).
	  	  error(function(data, status, headers, config) 
	  	  {  
	  		  LxNotificationService.error('Logout failed');
	  	  });
    }
   

    $http.get('/los/lecture/all').
	  success(function(data, status, headers, config) 
	  {
		  $scope.lectures = data;
	  }).
	  error(function(data, status, headers, config) 
	  {  
		  if(status == 401)
			  $scope.changeView("login");
	  });
    
    $http.get('/los/lecture/my').
	  success(function(data, status, headers, config) 
	  {
		  $scope.lecturesRegistered = data;
	  }).
	  error(function(data, status, headers, config) 
	  {  
		  $scope.lecturesRegistered = null;
		  
		  
		  if(status == 401)
			  $scope.changeView("login");
	  });
    
    
    $http.get('/los/authentication').
	  success(function(data, status, headers, config) 
	  {
		  if(data.affiliation == "student")
			  return;
		  else if(data.affiliation == "staff")
			  $scope.changeView("professor");
		  else
			  $scope.changeView("login");
	  }).
	  error(function(data, status, headers, config) 
	  {  
		  $scope.changeView("login");	  
	  });
    
    
    $scope.$watchCollection('selected.data', function()
    { 
    	var found = false;
    	
    	$scope.lectures.map( function(item) 
    	{
    	     if(item.id == $scope.selected.data.id)
    	    	 {
    	    	 	pageData.validLecture = true;
    	    	 	pageData.currentLecture = $scope.selected.data.id;
    	    	 	found = true;
    	    	 }
    	})
    });
});

app.controller('studentGeneralController', function($scope, pageData, $http, LxNotificationService )
{
	$scope.code = "";
	
    //debuonce mobile click
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
    	return true;
    }
	
    $scope.verify = function()
    {
	   	if(!$scope.isValid("attent"))
			return;
	   
    	
       $http.post('/los/lecture/' + pageData.currentLecture + '/verification?key=' + $scope.code).
  	  success(function(data, status, headers, config) 
  	  {
  		  LxNotificationService.success('Successfully attented');
  	  }).
  	  error(function(data, status, headers, config) 
  	  {  
  		  LxNotificationService.error('Verification failed: ' + data.message);
  	  });
    }
});


app.controller('studentQuizController', function($scope, pageData, $http, LxNotificationService, $timeout )
{
	$scope.quiz = pageData.activeQuiz;
	
    //debuonce mobile click
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
    	return true;
    }
	
	$scope.answers = []
	
   $scope.submit = function()
   {
	   	if(!$scope.isValid("submit"))
			return;
	   
	   var error = null;
	   
	   for(var i in $scope.answers)
	   {	   
		   data = { questions: [] };
		   var found = false;
		   var answerId = $scope.answers[i].id;
		   	   
		   for(var j in $scope.quiz.questions)
		   {
			   var question = $scope.quiz.questions[j];			   
			   for(var k in question.answers)
			   {
				   if(question.answers[k].id == answerId)
				   {
					   data.questions.push({
						   questionId: question.id,
						   answers: [answerId]
					   });				   
					   found = true;
					   break;
				   }
			   }	   
			   if(found)
				   break;
		   }
		   
		   if(found)
		   {
			   $http.post('/los/lecture/' + pageData.currentLecture + '/quiz/' + $scope.quiz.quizId + '/answer', data).
				  success(function(data, status, headers, config) 
				  {
	
				  }).
				  error(function(data, status, headers, config) 
				  {  
					  error = data.message;
				  });
		   }
	   }
	   
	   if(error)
	   {
		   LxNotificationService.error('Submit failed: ' + error);
	   }
	   else
	   {
		   LxNotificationService.success('Submit successfully');
	   }
   }
});

app.controller('studentFeedbackController', function($scope, LxNotificationService, $http, pageData )
{
    //debuonce mobile click
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
    	return true;
    }
	
	$scope.message = "";
	
    $scope.submit = function()
    {
    	if(!$scope.isValid("submit"))
    		return;
    	
    	if(!$scope.message || $scope.message == "")
		{
    		LxNotificationService.error('Sending feedback failed: message empty');
    		return;
		}
    	
        $http.put('/los/lecture/' + pageData.currentLecture + "/feedback?message=" + $scope.message +"&rating=" + $scope.rating).
    	  success(function(data, status, headers, config) 
    	  {
    		  LxNotificationService.success('Feedback send successfully');
    	  }).
    	  error(function(data, status, headers, config) 
    	  {  
    		  if(status == 403)
			  {
    			  LxNotificationService.error('Sending feedback failed: you need to attent this lecture first');
			  }
    		  else
    		  {
        		  LxNotificationService.error('Sending feedback failed:' + data.message);
    		  }
    	  });
    }
});

app.controller('studentStatisticController', function($scope, LxNotificationService, $http, pageData )
{	
	$scope.quizResults = [];
	$scope.currentQuiz = null;
	
	$http.get('/los/lecture/' + pageData.currentLecture + '/results/my').
	  success(function(data, status, headers, config) 
	  {
		  $scope.quizResults = data;	  
	  }).
	  error(function(data, status, headers, config) 
	  {  
		  LxNotificationService.error("Query statistic failed: " + data.message);
	  });   
	
	$scope.$watchCollection('selectedQuiz', function()
		    { 
				for(var i in $scope.quizResults)
					{
					 	if($scope.quizResults[i].quiz.quizId == $scope.selectedQuiz)
						 {
					 		$scope.currentQuiz = $scope.quizResults[i];	 		
					 		return;
						 }
					
					}
		    });
});
