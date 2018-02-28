app.controller('appletAdd', function($scope, $state, $stateParams, $http){
    //定义实体
    $scope.applet = {
        appId          : '',
        privateKeyPath : '',
        publicKeyPath  : '',
        organizationId : $stateParams.organizationId
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
            method  : 'POST',
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
});