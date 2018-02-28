app.controller('im', function($scope, $http, $stateParams){
    //下拉
    $scope.applets = [];

    //定义实体
    $scope.wechat = {
        id             : $stateParams.wechatId,
        organizationId : ''
    };

    $scope.appletId = '';

    //定义方法
    $scope.submit = function(){
        if($scope.appletId === '' || $scope.appletId === null || $scope.appletId === undefined){
            alert("请选择IM应用");
            return false;
        }

        $http({
            url     : '/api/1.0/im/tencent/applet/binding',
            method  : 'POST',
            data    : $.param({
                'wechatId' : $scope.wechat.id,
                'appletId' : $scope.appletId
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

    $scope.getApplets = function(){
        $http({
            url    : '/api/1.0/im/tencent/applet/find',
            method : 'GET',
            data   : $.param({
                'organizationId' : $scope.wechat.organizationId
            }),
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.applets = res.result;
            }else{
                alert(res.msg);

                $scope.applets = [];
            }
        }).error(function(response){
            console.error(response);

            $scope.applets = [];
        });
    };

    $scope.$watch('wechat', function(newVal, oldVal){
        if(newVal !== oldVal && newVal.organizationId !== oldVal.organizationId){
            $scope.getApplets();
        }
    }, true);

    //初始化
    $scope.getData();
});