  
var app = angular.module('StarterApp', ['ngMaterial','ngMdIcons']);

app.controller('AppCtrl', ['$scope', '$mdSidenav', function($scope, $mdSidenav){
	
	$mdSidenav('left').close()
    .then(function(){
      $log.debug("close LEFT is done");
    });
	
	
	
	
	$scope.feedback = [
	                {
	                  face : '/img/feedback.jpeg',
	                  what: 'Title',
	                  who: 'Anomynous',
	                  when: '3:08PM',
	                  notes: "Feedback description",
	                  stars: "1"
	                },
	                {
		                  face : '/img/feedback.jpeg',
		                  what: 'Title',
		                  who: 'Anomynous',
		                  when: '3:08PM',
		                  notes: "Feedback description",
		                	  stars: "2"
	                },
	                {
		                  face : '/img/feedback.jpeg',
		                  what: 'Title',
		                  who: 'Anomynous',
		                  when: '3:08PM',
		                  notes: "Feedback description",
		                	  stars: "3"
	                },
	                {
		                  face : '/img/feedback.jpeg',
		                  what: 'Title',
		                  who: 'Anomynous',
		                  when: '3:08PM',
		                  notes: "Feedback description",
		                	  stars: "4"
	                },
	                {
		                  face : '/img/feedback.jpeg',
		                  what: 'Title',
		                  who: 'Anomynous',
		                  when: '3:08PM',
		                  notes: "Feedback description",
		                  stars: "5"
	                },
	              ];
	
	
	
	$scope.getNumber = function(num) {
		
		console.log(num);
	    return new Array(num);   
	}
	
	
	
	
	
	
	
	
	
	
	
	$scope.toggleLeft = function() {
	    $mdSidenav('left').toggle()
	                      .then(function(){
	                          console.log("toggle left is done");
	                      });
	  };
	  
	  

	  	$scope.hasLecture = true;
	  
	    var self = $scope;
	    // list of `state` value/display objects
	    self.states        = loadAll();
	    self.selectedItem  = null;
	    self.searchText    = null;
	    self.querySearch   = querySearch;
	    self.simulateQuery = false;
	    self.isDisabled    = false;
	    self.selectedItemChange = selectedItemChange;
	    self.searchTextChange   = searchTextChange;
	    
	    console.log(self.states);
	    // ******************************
	    // Internal methods
	    // ******************************
	    /**
	     * Search for states... use $timeout to simulate
	     * remote dataservice call.
	     */
	    function querySearch (query) {
	      var results = query ? self.states.filter( createFilterFor(query) ) : [],
	          deferred;
	      if (self.simulateQuery) {
	        deferred = $q.defer();
	        $timeout(function () { deferred.resolve( results ); }, Math.random() * 1000, false);
	        return deferred.promise;
	      } else {
	        return results;
	      }
	    }
	    function searchTextChange(text)
	    {
	    	self.hasLecture = false;
	    }
	    function selectedItemChange(item) {
	      console.log('Item changed to ' + item);
	    }
	    /**
	     * Build `states` list of key/value pairs
	     */
	    function loadAll() {
	      var allStates = 'Alabama, Alaska, Arizona, Arkansas, California, Colorado, Connecticut, Delaware,\
	              Florida, Georgia, Hawaii, Idaho, Illinois, Indiana, Iowa, Kansas, Kentucky, Louisiana,\
	              Maine, Maryland, Massachusetts, Michigan, Minnesota, Mississippi, Missouri, Montana,\
	              Nebraska, Nevada, New Hampshire, New Jersey, New Mexico, New York, North Carolina,\
	              North Dakota, Ohio, Oklahoma, Oregon, Pennsylvania, Rhode Island, South Carolina,\
	              South Dakota, Tennessee, Texas, Utah, Vermont, Virginia, Washington, West Virginia,\
	              Wisconsin, Wyoming';
	      return allStates.split(/, +/g).map( function (state) {
	        return {
	          value: state.toLowerCase(),
	          display: state
	        };
	      });
	    }
	    /**
	     * Create filter function for a query string
	     */
	    function createFilterFor(query) {
	      var lowercaseQuery = angular.lowercase(query);
	      return function filterFn(state) {
	        return (state.value.indexOf(lowercaseQuery) === 0);
	      };
	    }
	  
	  
	  
}]);
