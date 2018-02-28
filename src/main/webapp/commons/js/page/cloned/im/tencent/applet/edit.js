app.controller('appletEdit', function($scope, $state, $stateParams, $http){
    //定义实体
    $scope.applet = {
        id             : $stateParams.id,
        appId          : '',
        privateKeyPath : '',
        publicKeyPath  : '',
        organizationId : ''
    };

    //定义方法
    $scope.reset = function(){
        $scope.applet.appId           = '';
        $scope.applet.privateKeyPath  = '';
        $scope.applet.publicKeyPath   = '';
    };

    $scope.submit = function(){
        $http({
            url     : '/api/1.0/im/tencent/applet',
            method  : 'PUT',
            data    : JSON.stringify($scope.applet),
            headers : {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('applet');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getData = function(){
        $http({
            url     : '/api/1.0/im/tencent/applet/' + $scope.applet.id,
            method  : 'GET',
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.applet);
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