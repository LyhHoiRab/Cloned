app.controller('wechatAdd', function($scope, $state, $stateParams, $http){
    //下拉
    $scope.devices = [];

    //定义实体
    $scope.wechat = {
        wxno           : '',
        deviceId       : '',
        organizationId : $stateParams.organizationId
    };

    //定义方法
    $scope.reset = function(){
        $scope.wechat.wxno           = '';
        $scope.wechat.deviceId       = '';
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

    $scope.getDevices = function(){
        $http({
            url     : '/api/1.0/device/find',
            method  : 'GET',
            params  : {
                'organizationId' : $scope.wechat.organizationId
            },
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.devices = res.result;
            }else{
                alert(res.msg);

                $scope.devices = [];
            }
        }).error(function(response){
            console.error(response);

            $scope.devices = [];
        });
    };

    //初始化
    $scope.getDevices();
});