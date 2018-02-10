var app = angular.module('app', ['ngSanitize', 'ui.bootstrap']);

app.controller('register', function($scope, $http){
    $scope.organizations  = [];
    $scope.organizationId = '';
    $scope.username       = '';
    $scope.password       = '';
    $scope.passwordAgain  = '';

    //查询企业
    $scope.getOrganizations = function(){
        $http({
            url     : '/api/1.0/organization/find',
            method  : 'GET',
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.organizations = res.result;
            }else{
                alert(res.msg);

                $scope.organizations = [];
            }
        }).error(function(response){
            console.error(response);

            $scope.organizations = [];
        });
    };

    $scope.submit = function(){
        if($scope.organizationId === null || $scope.organizationId === undefined || $scope.organizationId === ''){
            alert("请选择企业机构");
            return;
        }else if($scope.username === null || $scope.username === undefined || $scope.username === ''){
            alert("请填写登录名");
            return;
        }else if($scope.password === null || $scope.password === undefined || $scope.password === ''){
            alert("请填写密码");
            return;
        }else if($scope.passwordAgain !== $scope.password){
            alert("2次密码不相同，请重新填写");
            return;
        }

        $http({
            url : '/api/1.0/register',
            method: 'POST',
            data: $.param({
                'username'       : $scope.username,
                'password'       : md5($scope.password),
                'organizationId' : $scope.organizationId
            }),
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                location.href = '/page/cloned/login';
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    //初始化数据
    $scope.getOrganizations();
});