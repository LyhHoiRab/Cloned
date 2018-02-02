app.controller('device', function($scope, $http, $state){
    //定义实体
    $scope.organizations = [];

    //切换tab
    $scope.toggle = function(organizationId){
        $state.go('device.list', {'organizationId' : organizationId});
    };

    //查询企业
    $scope.getOrganizations = function(){
        $http({
            url     : '/api/1.0/organization/find',
            method  : 'GET',
            params  : {
                'state' : 0
            },
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.organizations = res.result;
            }else{
                alert(res.msg);

                $scope.organizations = [];
            }
        }).error(function(response){
            console.error(response);

            $scope.organizations = [];
        });
    };

    //初始化数据
    $scope.getOrganizations();
});