app.controller('wechatEdit', function($scope, $state, $stateParams, $http){
    //定义实体
    $scope.wechat = {
        id             : $stateParams.id,
        wxno           : '',
        organizationId : ''
    };

    //定义方法
    $scope.reset = function(){
        $scope.wechat.wxno           = '';
    };

    $scope.submit = function(){
        $http({
            url     : '/api/1.0/wechat',
            method  : 'PUT',
            data    : JSON.stringify($scope.wechat),
            headers : {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('wechat');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getData = function(){
        $http({
            url     : '/api/1.0/wechat/' + $scope.wechat.id,
            method  : 'GET',
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.wechat);
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    //初始化
    $scope.getData();
});