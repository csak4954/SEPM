/**
 * Created by Mathias Hölzl on 21.04.2015.
 */
// contact page controller
app.controller('professorController', function($scope, pageData, $http)
{
    $scope.pageClass = 'professor';
    $scope.showContent = true;


    pageData.setTitle('Professor');
    pageData.setheader('Professor');
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

app.controller('professorGeneralController', function($scope, pageData, LxDialogService, $http, $timeout )
{
    $scope.verificationCode = "0000";
	$scope.verify = false;
    
	
	$scope.cancleVerification = function()
	{
		$scope.verify = false;
	}
	
    function updateVerificationCode() 
    {
    	$http.get('/los/verification/' + pageData.currentLecture).
	  	  success(function(data, status, headers, config) 
	  	  {
	  		  $scope.verificationCode = data.verificationCode;
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


app.controller('professorQuizController', function($scope )
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

        for (var item in $scope.questions)
        {
            if(item.question.localeCompare(text) == 0)
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
                question: text,
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

    $scope.addAnswer = function(i, text)
    {
        $scope.questions[i].answers.push($scope.questionsAnswerText[i]);
        $scope.questionsAnswerText[i] = "";
        $scope.questionsAnswerTextEnable[i] = false;
    }

    $scope.removeAnswer = function(qIndex, aIndex)
    {
        $scope.questions[qIndex].answers.splice(aIndex, 1);
    }
});

app.controller('professorFeedbackController', function($scope, LxDialogService, pageData, $http, $timeout )
{
    $scope.feedback =
    [
        {
            user: "anonym",
            date: "12.01.2120",
            stars: 0,
            title: "msg title",
            message: "aaaaaaaaaaaa\n asdasd\a"
        },
        {
            user: "anonym",
            date: "12.01.2120",
            stars: 0,
            title: "msg title",
            message: "aaaaaaaaaaaa\n asdasd\a"
        },
        {
            user: "anonym",
            date: "12.01.2120",
            stars: 0,
            title: "msg title",
            message: "aaaaaaaaaaaa\n asdasd\a"
        },
        {
            user: "anonym",
            date: "12.01.2120",
            stars: 0,
            title: "msg title",
            message: "aaaaaaaaaaaa\n asdasd\a"
        },
        {
            user: "anonym",
            date: "12.01.2120",
            stars: 0,
            title: "msg title",
            message: "aaaaaaaaaaaa\n asdasd\a"
        },
        {
            user: "anonym",
            date: "12.01.2120",
            stars: 0,
            title: "msg title",
            message: "aaaaaaaaaaaa\n asdasd\a"
        },
        {
            user: "anonym",
            date: "12.01.2120",
            stars: 3,
            title: "msg title",
            message: "aaaaaaaaaaaa\n asdasd\a"
        },
        {
            user: "anonym",
            date: "12.01.2120",
            stars: 5,
            title: "msg title",
            message: "aaaaaaaaaaaa\n asdasd\a"
        }
    ];

    $scope.getEmptyArray = function(size)
    {
        return new Array(size);
    }
    
    
    function updateFeedback() 
    {
    	if(pageData.validLecture)
    	{	
        	$http.get('/los/feedback/' + pageData.currentLecture).
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

app.controller('professorStatisticController', function($scope )
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
