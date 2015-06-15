/**
 * Created by Mathias Hölzl on 21.04.2015.
 */
// contact page controller
app.controller('professorController', function($scope, pageData, $http, LxNotificationService)
{
    $scope.pageClass = 'professor';
    $scope.showContent = true;


    pageData.setTitle('Professor');
    pageData.setheader('Professor');
    pageData.sethideToolbarRight(false);

    pageData.currentLecture = null;
    pageData.validLecture = false;
    pageData.showLectureForm = false;
    
    
    $scope.lectures = [];
    $scope.selected = { data:""}
    
	$scope.openLecturForm = function()
	{
		pageData.showLectureForm = true;
	}
	

    $scope.hideForm = function()
    {
    	$scope.title = "";
    	$scope.desc = "";
    	pageData.showLectureForm = false;
    }
    
    $scope.logout = function()
    {
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
    
    $scope.addLecture = function(title, desc)
    {
    	$http.put('/los/lecture?title=' + title + "&description=" + desc).
	  	  success(function(data, status, headers, config) 
	  	  {
	  		LxNotificationService.success('Lecture added');
	  	  }).
	  	  error(function(data, status, headers, config) 
	  	  {  
	  		 if(status == 401)
			{
	  			 $scope.changeView("login");
	  			 return;
			}
	  			 
	  			 
	  		  
	  		  LxNotificationService.error('Add lecture failed');
	  	  });
    }
    

    $http.get('/los/lecture/all').
	  success(function(data, status, headers, config) 
	  {
		  $scope.lectures = data;
		  
		  if($scope.lectures.length == 0)
			  pageData.showLectureForm = true;
		  
	  }).
	  error(function(data, status, headers, config) 
	  {  
		  if(status == 401)
			  $scope.changeView("login");	  
	  });
    
    $http.get('/los/authentication').
	  success(function(data, status, headers, config) 
	  {
		  if(data.affiliation == "staff")
			  return;
		  else if(data.affiliation == "student")
			  $scope.changeView("student");
		  else
			  $scope.changeView("login");
	  }).
	  error(function(data, status, headers, config) 
	  {  
		  $scope.changeView("login");	  
	  });
    
    $scope.$watchCollection('selected.data', function()
    { 
    	$scope.lectures.map( function(item) 
    	{
    	     if(item.id == $scope.selected.data.id)
    	    	 {
    	    	 	pageData.validLecture = true;
    	    	 	pageData.currentLecture = $scope.selected.data.id;
    	    	 }
    	})
    });
});

app.controller('professorGeneralController', function($scope, pageData, LxDialogService, $http, $timeout )
{
    $scope.verificationCode = "0000";
	$scope.verify = false;
    
	

	
	$scope.cancleVerification = function()
	{
    	$http.delete('/los/lecture/' + pageData.currentLecture + '/verification').
	  	  success(function(data, status, headers, config) 
	  	  {
	  	  }).
	  	  error(function(data, status, headers, config) 
	  	  {  
	  	  });
		
		$scope.verify = false;
	}
	
    function updateVerificationCode() 
    {
    	$http.put('/los/lecture/' + pageData.currentLecture + '/verification').
	  	  success(function(data, status, headers, config) 
	  	  {
	  		  $scope.verificationCode = "" + data;
	  	  }).
	  	  error(function(data, status, headers, config) 
	  	  {  
	  	  });

    	if($scope.verify)
    		$timeout(updateVerificationCode, 5000);
    }
    
    $scope.startVerification = function()
    {
    	$scope.verify = true;
    	updateVerificationCode();
        LxDialogService.open('verificationDialog');
    }
});


app.controller('professorQuizEditorController', function($scope, pageData, LxNotificationService, $http, $timeout )
{
	$scope.quizes = [];
	
	 
	 String.prototype.toHHMMSS = function () {
		    var sec_num = parseInt(this, 10); // don't forget the second param
		    var hours   = Math.floor(sec_num / 3600);
		    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
		    var seconds = sec_num - (hours * 3600) - (minutes * 60);

		    if (hours   < 10) {hours   = "0"+hours;}
		    if (minutes < 10) {minutes = "0"+minutes;}
		    if (seconds < 10) {seconds = "0"+seconds;}
		    var time    = hours+':'+minutes+':'+seconds;
		    return time;
		}
	
	$scope.reloadQuiz = function()
	{
		var tmp = pageData.selectedQuiz;
		
		$http.get('/los/quiz/my').
		  success(function(data, status, headers, config) 
		  {
			  $scope.quizes = [];
			  
			  for (var i in data)
	          {
				  if(data[i].lecture.id == pageData.currentLecture)
				  {
					  $scope.quizes.push(data[i]);
				  }        
	          }
			  
			  pageData.selectedQuiz = tmp;
		  }).
		  error(function(data, status, headers, config) 
		  {  
			  
		  });
	}

	
    $scope.onTimeout = function()
    {
    	if(pageData.quizActive)
    	{
    		pageData.counter++;    	
    		pageData.timerVal = (""+pageData.counter).toHHMMSS();  	
    		pageData.timer = $timeout($scope.onTimeout,1000);
    	}
    }
    
	$scope.startQuiz = function()
	{
		if(!pageData.selectedQuiz || pageData.selectedQuiz == "Quiz")
			return;
		
		$http.post('/los/lecture/' + pageData.currentLecture + '/quiz/' + pageData.selectedQuiz + '?active=true').
		  success(function(data, status, headers, config) 
		  {
		  	  LxNotificationService.success('Quiz started');		  	  
		  	  
		  	  pageData.counter = 0;
		  	  pageData.timer = $timeout($scope.onTimeout,1000);
		  	  pageData.quizActive = true;
		  }).
		  error(function(data, status, headers, config) 
		  {  
		  	  LxNotificationService.error('Starting quiz failed');
		  });
	}
	
	$scope.stopQuiz = function()
	{
		$http.post('/los/lecture/' + pageData.currentLecture + '/quiz/' + pageData.selectedQuiz + '?active=false').
		  success(function(data, status, headers, config) 
		  {
		  	  LxNotificationService.success('Quiz stopped');
		  	  
		  	    pageData.timer = 0;
				pageData.timerVal = "";
				$timeout.cancel(pageData.timer);
			  	pageData.quizActive = false;
		  }).
		  error(function(data, status, headers, config) 
		  {  
		  	  LxNotificationService.error('Stopping quiz failed');
			  $timeout.cancel(pageData.timer);
			  pageData.quizActive = false;
		  });
	}
	
	
	$scope.reloadQuiz();
	
	$scope.remove = function()
	{
		if(!pageData.selectedQuiz || pageData.selectedQuiz == "Quiz")
			return;
		
		$http.delete('/los/lecture/' + pageData.currentLecture + '/quiz/' + pageData.selectedQuiz).
		  success(function(data, status, headers, config) 
		  {
			  $scope.reloadQuiz();
			  $scope.selectedQuiz = null;
			  LxNotificationService.success('Quiz successfully removed');
			  pageData.quizActive = false;
		  }).
		  error(function(data, status, headers, config) 
		  {  
			  LxNotificationService.error('Romve quiz failed');
		  });
	}
	

});

app.controller('professorQuizController', function($scope, pageData, LxNotificationService, $http )
{
    $scope.questionsAnswerText = [];
    $scope.questionsAnswerTextEnable = [];

    $scope.question = "";
    $scope.questions = [];

    $scope.addQuestionDisabled = true;
    $scope.addAnswerDisabled = true;

    $scope.questionChanged = function(text)
    {
        if(text == "")
        {
            $scope.addQuestionDisabled = true;
            return;
        }
       
        for (var i in $scope.questions)
        {
            if($scope.questions[i].text.localeCompare(text) == 0)
            {
                $scope.addQuestionDisabled = true;
                return;
            }
        }

        $scope.addQuestionDisabled = false;
    }

    $scope.addQuesition = function(text)
    {
        $scope.questions.push(
            {
            	id: null,
            	text: text,
                answers: []
            });

        
        
        $scope.question = "";
        $scope.addQuestionDisabled = true;
    }

    $scope.removeQuestion = function(i)
    {
        $scope.questions.splice(i, 1);
    }

    $scope.answerChanged = function(i)
    {
        if($.inArray($scope.questionsAnswerText[i], $scope.questions[i].answers) != -1)
            $scope.questionsAnswerTextEnable[i] = false;
        else
            $scope.questionsAnswerTextEnable[i] = ($scope.questionsAnswerText[i] != "");
    }

    $scope.setSolution = function(qIndex, aIndex)
    {	
    	for (var i in $scope.questions[qIndex].answers)
        {
    		$scope.questions[qIndex].answers[i].solution = false;        
        }
    	
    	 $scope.questions[qIndex].answers[aIndex].solution = true;
    }
    
    $scope.addAnswer = function(i, text)
    {
    	data = 
    	{
    	   id: null,
    	   text: $scope.questionsAnswerText[i],
    	   solution: false	
    	};
    	
    	if($scope.questions[i].answers.length == 0)
    		data.solution = true;
    	
        $scope.questions[i].answers.push(data);
        $scope.questionsAnswerText[i] = "";
        $scope.questionsAnswerTextEnable[i] = false;
    }

    $scope.removeAnswer = function(qIndex, aIndex)
    {
        $scope.questions[qIndex].answers.splice(aIndex, 1);
    }
    
    $scope.submit = function()
    {
       	if($scope.questions.length == 0)
   		{
       		LxNotificationService.error('Submit quiz failed: no questions');
       		return;
   		}
    	
       	if(!$scope.title)
   		{
       		LxNotificationService.error('Submit quiz failed: no title');
       		return;
   		}
       		
       	
       	for (var i in $scope.questions)
        {
       		var quest = $scope.questions[i];
       		
       		if(quest.answers.length == 0)
       		{
           		LxNotificationService.error('Submit quiz failed: question ' + (i + 1) + ' has no answers');
           		return;
       		}
        }
       	
    	data = 
    	{
    		  "id": null,
       		  "lecture": null,   
       		  "active": false,
       		  "title": $scope.title,
       		  "questions": $scope.questions
    	};
    	
    	$http.put('/los/lecture/' + pageData.currentLecture + '/quiz', data).
	  	  success(function(data, status, headers, config) 
	  	  {
		  	    $scope.questionsAnswerText = [];
		  	    $scope.questionsAnswerTextEnable = [];
	
		  	    $scope.question = "";
		  	    $scope.questions = [];
	
		  	    $scope.title = "";
		  	    
		  	    $scope.addQuestionDisabled = true;
		  	    $scope.addAnswerDisabled = true;
		  	    
		  	  LxNotificationService.success('Quiz created');
	  	  }).
	  	  error(function(data, status, headers, config) 
	  	  {  
	  		  LxNotificationService.error('Submit quiz failed');
	  	  });
    }
});

app.controller('professorFeedbackController', function($scope, LxDialogService, pageData, $http, $timeout )
{
    $scope.feedback = []


    $scope.getEmptyArray = function(size)
    {
    	if(size < 0)
    		size = size*-1;
    	
        return new Array(size);
    }
    
    
    function updateFeedback() 
    {
    	if(pageData.validLecture)
    	{	
        	$http.get('/los/lecture/' + pageData.currentLecture + '/feedback').
    	  	  success(function(data, status, headers, config) 
    	  	  {
    	  		$scope.feedback = data;
    	  		console.log(data);
    	  	  }).
    	  	  error(function(data, status, headers, config) 
    	  	  {  
    	  	  });    		
    	}
    	
    	$timeout(updateFeedback, 5000);
    }
    
    updateFeedback();
});

app.controller('professorStatisticController', function($scope, LxDialogService, pageData, $http, $timeout )
{
	$scope.users = [];
	$scope.userResults = [];
	$scope.statistic = [];
	
    $scope.attendanceChart = {};
    $scope.attendanceChart.data = {    
      "cols":
        [
          {id: "d", label: "Day", type: "string"},
          {id: "a", label: "Attendances", type: "number"}
        ], 
      "rows": [] };
    
    $scope.quizChart = {};
    $scope.quizChart.data = {    
      "cols":
        [
          {id: "d", label: "Quiz name", type: "string"},
          {id: "a", label: "Result", type: "number"}
        ], 
      "rows": [] };
    
    
    $scope.attendanceChart.type = "ColumnChart";
    $scope.quizChart.vAxis =  {
            viewWindow: {
                min: 0
            }
        };
    $scope.attendanceChart.options = {
        'title': '',
        'legend':  'none',
        'is3D': true,
        'vAxis': { viewWindow: { min: 0} }
    };
    

    $scope.quizChart.type = "ColumnChart";   
    $scope.quizChart.options = {
        'title': '',
        'legend':  'none',
        'is3D': true,
        'vAxis': {
	        viewWindow: {
	            min: 0,
	            max: 100
	        },
	        ticks: [0, 25, 50, 75, 100] // display labels every 25
	    }
    }
    
	
	$http.get('/los/lecture/' + pageData.currentLecture + '/statistics').
	  success(function(data, status, headers, config) 
	  {
		  $scope.statistic = data;
		  console.log(data);
		  
		  for(var i in $scope.statistic.attendancesPerDay)
		  {
			  var value = $scope.statistic.attendancesPerDay[i];
			  $scope.attendanceChart.data.rows.push({c: [
			                                             {v: value.key},
			                                             {v: value.value}
			                                         ]});
		  }
		  
		  
		  for(var i in $scope.statistic.quizAverageScore)
		  {
			  var value = $scope.statistic.quizAverageScore[i];
			  $scope.quizChart.data.rows.push({c: [
			                                             {v: value.key.title},
			                                             {v: value.value}
			                                         ]});
		  }
		  
	  }).
	  error(function(data, status, headers, config) 
	  {  
	  });    	
	
	$http.get('/los/lecture/' + pageData.currentLecture + '/user').
	  success(function(data, status, headers, config) 
	  {
		  $scope.users = [];
		  for(var i in data)
		  {
	         if(data[i].affiliation == "student")
	        	 $scope.users.push(data[i]);
		  }
	  }).
	  error(function(data, status, headers, config) 
	  {  
	  });    	
	
	$scope.$watchCollection('selectedUser', function()
    { 
		for(var i in $scope.users)
		{
			if($scope.users[i].id == $scope.selectedUser)
			{
				$http.get('/los/lecture/'+pageData.currentLecture+'/user/' + $scope.users[i].id + '/results').
				  success(function(data, status, headers, config) 
				  {
					  $scope.userResults = data;
				  }).
				  error(function(data, status, headers, config) 
				  {  
				  }); 
				
				return;
			}
		}
    });
});
