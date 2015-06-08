/**
 * Created by Mathias Hölzl on 21.04.2015.
 */
// contact page controller
app.controller('studentController', function($scope, pageData, $http )
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


    $http.get('/los/lecture').
	  success(function(data, status, headers, config) 
	  {
		  $scope.lectures = data;
	  }).
	  error(function(data, status, headers, config) 
	  {  
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

app.controller('studentGeneralController', function($scope, $http, LxNotificationService )
{
	$scope.code = "";
	
    $scope.verify = function()
    {
      
       $http.post('/los/verification/' + $scope.code).
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


app.controller('studentQuizController', function($scope )
{
	$scope.answers = []
	
   $scope.questions = 
	   [
           {
        	question: "Question1",
        	answers: ["asd","afg"]
           },
           {
           	question: "Question2",
           	answers: ["asd","afg"]
              },
              {
              	question: "Question3",
              	answers: ["asd","afg"]
                 },
                 {
                 	question: "Question4",
                 	answers: ["asd","afg"]
                    }     
       ];
	
   $scope.hasQuiz = true;

   $scope.submit = function()
   {
	   $scope.hasQuiz = false;
   }
});

app.controller('studentFeedbackController', function($scope, LxNotificationService, $http, pageData )
{
    $scope.submit = function()
    {
    	
        $http.post('/los/feedback/' + pageData.currentLecture, {message: $scope.message,title: $scope.title, rating: $scope.rating}).
    	  success(function(data, status, headers, config) 
    	  {
    		  LxNotificationService.success('Feedback send successfully');
    	  }).
    	  error(function(data, status, headers, config) 
    	  {  
    		  LxNotificationService.error('Sending feedback failed');
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
