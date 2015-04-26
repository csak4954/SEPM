/**
 * Created by Mathias Hölzl on 21.04.2015.
 */


app.controller('loginController', function($scope, pageData) {
    $scope.pageClass = 'login';
    pageData.setTitle('Login');
    pageData.setheader('Login');
    pageData.sethideToolbarRight(true);

    $scope.username = "";
    $scope.password = "";

    $scope.onConnect = function(user, password)
    {
        if(user == "a")
        {
            $scope.changeView('student');
        }
        else
        {
            $scope.changeView('professor');
        }
    }
});