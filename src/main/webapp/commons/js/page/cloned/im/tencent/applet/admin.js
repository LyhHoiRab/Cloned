app.controller('admin', function($scope, $http, $state, $stateParams){
    //参数
    $scope.identifier = '';
    $scope.appletId   = $stateParams.appletId;

    //自定义方法
    $scope.reset = function(){
        $scope.identifier = '';
    };

    $scope.submit = function(){
        $http({
            url     : '/api/1.0/im/user/admin',
            method  : 'POST',
            data    : $.param({
                'identifier' : $scope.identifier,
                'appletId'   : $scope.appletId
            }),
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };
});