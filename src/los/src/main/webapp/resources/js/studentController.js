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
    $scope.selected = { data:""}

    
    
	$scope.quizPoller = function()
	{
		pageData.timer = $timeout($scope.quizPoller,1000);
		
		if(pageData.activeQuiz || !pageData.currentLecture)
			  return;
		
		$http.get('/los/quiz/active').
		  success(function(data, status, headers, config) 
		  {
			  for (var i in data)
			  {
				  if(data[i].lecture.id == pageData.currentLecture)
				  {
					  LxNotificationService.success('Quiz active');
					  pageData.activeQuiz = data[i];
					  return;
				  }
			  
			  }	  
		  }).
		  error(function(data, status, headers, config) 
		  {  
		  });
	}
	
	$scope.quizPoller();
    
    
    
    
    
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

app.controller('studentGeneralController', function($scope, pageData, $http, LxNotificationService )
{
	$scope.code = "";
	
    $scope.verify = function()
    {
      
       $http.post('/los/lecture/' + pageData.currentLecture + '/verification?key=' + $scope.code).
  	  success(function(data, status, headers, config) 
  	  {
  		  LxNotificationService.success('Successfully attented');
  	  }).
  	  error(function(data, status, headers, config) 
  	  {  
  		  LxNotificationService.error('Verification failed');
  	  });
    }
});


app.controller('studentQuizController', function($scope, pageData, $http, LxNotificationService, $timeout )
{
	$scope.answers = []
	
	$scope.$watchCollection('pageData.activeQuiz', function()
    { 
		if(pageData.activeQuiz)
		{
			$scope.quiz = pageData.activeQuiz;
			$scope.hasQuiz = true;
		}
		else
		{
			if($scope.hasQuiz)
				LxNotificationService.info('Quiz ended');
			
			$scope.hasQuiz = false;
		}
    });
	

	
	
   $scope.submit = function()
   {
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
				  });
		   }
	   }
   }
});

app.controller('studentFeedbackController', function($scope, LxNotificationService, $http, pageData )
{
	$scope.message = "";
	
    $scope.submit = function()
    {
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
        		  LxNotificationService.error('Sending feedback failed');
    		  }
    	  });
    }
});

app.controller('studentStatisticController', function($scope )
{
    $scope.feedbackChart = {};

    $scope.feedbackChart.data =
    {
    "cols":
        [
            {id: "t", label: "Topping", type: "string"},
            {id: "s", label: "Stars", type: "number"}
        ],

    "rows": [
        {c: [
            {v: "5 stars"},
            {v: 10}
        ]},
        {c: [
            {v: "4 stars"},
            {v: 0}
        ]},
        {c: [
            {v: "3 stars"},
            {v: 2}
        ]},
        {c: [
            {v: "2 stars"},
            {v: 1}
        ]},
        {c: [
            {v: "1 stars"},
            {v: 2}
        ]},
        {c: [
            {v: "0 stars"},
            {v: 10}
        ]}
    ]};

    // $routeParams.chartType == BarChart or PieChart or ColumnChart...
    $scope.feedbackChart.type = "BarChart";
    $scope.feedbackChart.options = {
        'title': '',
         'width': 400,
        'legend':'none',
        'is3D': true,
        colors: ['#7986CB']
    }


    $scope.attendanceChart = {};

    $scope.attendanceChart.data =
    {
        "cols":
            [
                {id: "t", label: "Topping", type: "string"},
                {id: "s", label: "Stars", type: "number"}
            ],

        "rows": [
            {c: [
                {v: "5 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "4 stars"},
                {v: 0}
            ]},
            {c: [
                {v: "3 stars"},
                {v: 2}
            ]},
            {c: [
                {v: "2 stars"},
                {v: 1}
            ]},
            {c: [
                {v: "1 stars"},
                {v: 2}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]},
            {c: [
                {v: "0 stars"},
                {v: 10}
            ]}
        ]};

    // $routeParams.chartType == BarChart or PieChart or ColumnChart...
    $scope.attendanceChart.type = "ColumnChart";
    $scope.attendanceChart.options = {
        'title': '',
        'legend':'none',
        'is3D': true,
        colors: ['#7986CB']
    }
});
