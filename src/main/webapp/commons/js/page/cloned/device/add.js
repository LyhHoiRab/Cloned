app.controller('deviceAdd', function($scope, $state, $stateParams, $http){
    //定义实体
    $scope.device = {
        type           : '',
        phone          : '',
        imei           : '',
        organizationId : $stateParams.organizationId
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
            method  : 'POST',
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
});