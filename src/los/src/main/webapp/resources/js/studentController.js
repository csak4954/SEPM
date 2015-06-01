/**
 * Created by Mathias Hölzl on 21.04.2015.
 */


app.controller('studentController', function($scope, pageData) {
    $scope.pageClass = 'student';
    pageData.setTitle('Student');
    pageData.setheader('Student');
    pageData.sethideToolbarRight(false);
});