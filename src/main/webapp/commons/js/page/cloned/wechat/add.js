app.controller('wechatAdd', function($scope, $state, $stateParams, $http){
    //定义实体
    $scope.wechat = {
        wxno           : '',
        organizationId : $stateParams.organizationId
    };

    //定义方法
    $scope.reset = function(){
        $scope.wechat.wxno           = '';
        $scope.wechat.appletId       = '';
    };

    $scope.submit = function(){
        $http({
            url     : '/api/1.0/wechat',
            method  : 'POST',
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
});