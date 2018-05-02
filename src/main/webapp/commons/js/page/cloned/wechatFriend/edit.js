app.controller('wechatFriendEdit', function($scope, $state, $stateParams, $http){
    //下拉
    $scope.services        = [];

    //定义实体
    $scope.wechatFriend = {
        id         : '',
        nickname   : '',
        serviceId  : '',
        wechatId   : ''
    };

    //定义方法
    $scope.reset = function(){
        $scope.wechatFriend.nickname   = '';
        $scope.wechatFriend.serviceId  = '';
    };

    $scope.getWechat = function(id){
        $http({
            url    : '/api/1.0/wechat/' + id,
            method : 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.getIMUser(res.result.id);
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getIMUser = function(name){
        $http({
            url    : '/api/1.0/im/user/name/' + name,
            method : 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.getServices(res.result.appletId);
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getServices = function(imAppletId){
        $http({
            url    : '/api/1.0/service/find/applet/' + imAppletId,
            method : 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.services = res.result;
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);

            $scope.services = [];
        });
    };

    $scope.getData = function(){
        $http({
            url    : '/api/1.0/wechatFriend/' + $stateParams.id,
            method : 'GET',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.wechatFriend);
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.submit = function(){
        $http({
            url     : '/api/1.0/wechatFriend',
            method  : 'PUT',
            data    : JSON.stringify($scope.wechatFriend),
            headers : {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('wechatFriend');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.$watch('wechatFriend', function(newVal, oldVal){
        if(newVal !== oldVal && newVal.id !== oldVal.id){
            $scope.getWechat($scope.wechatFriend.wechatId);
        }
    }, true);

    //初始化
    $scope.getData();
});