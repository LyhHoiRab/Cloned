app.controller('deviceEdit', function($scope, $state, $stateParams, $http){
    //定义实体
    $scope.device = {
        id             : $stateParams.id,
        type           : '',
        phone          : '',
        imei           : '',
        organizationId : ''
    };

    //定义方法
    $scope.reset = function(){
        $scope.device.type           = '';
        $scope.device.phone          = '';
        $scope.device.imei           = '';
    };

    $scope.submit = function(){
        $http({
            url     : '/api/1.0/device',
            method  : 'PUT',
            data    : JSON.stringify($scope.device),
            headers : {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('device');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getData = function(){
        $http({
            url     : '/api/1.0/device/' + $scope.device.id,
            method  : 'GET',
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.device);
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