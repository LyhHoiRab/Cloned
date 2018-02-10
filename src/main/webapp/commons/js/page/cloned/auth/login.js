var app = angular.module('app', ['ngSanitize', 'ui.bootstrap']);

app.controller('login', function($scope, $http){
    $scope.username       = '';
    $scope.password       = '';

    $scope.submit = function(){
        if($scope.username === null || $scope.username === undefined || $scope.username === ''){
            alert("请填写登录名");
            return;
        }else if($scope.password === null || $scope.password === undefined || $scope.password === ''){
            alert("请填写密码");
            return;
        }

        $http({
            url : '/api/1.0/login',
            method: 'POST',
            data: $.param({
                'username'       : $scope.username,
                'password'       : md5($scope.password)
            }),
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                location.href = '/page/cloned/index';
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.register = function(){
        location.href = '/page/cloned/register';
    };
});