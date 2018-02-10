app.controller('serviceAdd', function($scope, $state, $stateParams, $http){
    //下拉
    $scope.users = [];

    //定义实体
    $scope.service = {
        accountId      : '',
        wechatId       : $stateParams.wechatId,
        name           : ''
    };

    //定义方法
    $scope.change = function(accountId){
        var user = utils.getById(accountId, 'accountId', $scope.users);

        if(user === undefined || user === null){
            $scope.service.name = '';
        }else{
            $scope.service.name = user.name;
        }
    };

    $scope.reset = function(){
        $scope.service.accountId       = '';
        $scope.service.name            = '';
    };

    $scope.submit = function(){
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
                $state.go('service', {'wechatId' : $scope.service.wechatId});
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getUsers = function(){
        $http({
            url     : '/api/1.0/user/notService/' + $scope.service.wechatId,
            method  : 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.users = res.result;
            }else{
                alert(res.msg);

                $scope.users = [];
            }
        }).error(function(response){
            console.error(response);

            $scope.users = [];
        });
    };

    //初始化
    $scope.getUsers();
});