app.controller('serviceAdd', function($scope, $state, $stateParams, $http){
    //下拉
    $scope.wechats        = [];
    $scope.organizationId = $stateParams.organizationId;

    //定义实体
    $scope.service = {
        username   : '',
        password   : '',
        wechatId   : '',
        name       : '',
        headImgUrl : ''
    };

    //定义方法
    $scope.reset = function(){
        $scope.service.username   = '';
        $scope.service.password   = '';
        $scope.service.name       = '';
        $scope.service.headImgUrl = '';
        $scope.service.wechatId   = '';
    };

    $scope.getWechats = function(){
        $http({
            url     : '/api/1.0/wechat/find',
            method  : 'GET',
            params : {
                'organizationId' : $scope.organizationId,
                'wxno'           : $scope.wxno
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.wechats  = res.result;
            }else{
                alert(res.msg);

                $scope.wechats = [];
            }
        }).error(function(response){
            console.error(response);

            $scope.wechats = [];
        });
    };

    $scope.submit = function(){
        $scope.service.password = md5($scope.service.password);

        $http({
            url     : '/api/1.0/service',
            method  : 'POST',
            data    : JSON.stringify($scope.service),
            headers : {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('service');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    //初始化
    $scope.getWechats();
});