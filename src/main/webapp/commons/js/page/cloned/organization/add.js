app.controller('organizationAdd', function($scope, $state, $http){
    //定义实体
    $scope.organization = {
        name            : '',
        token           : '',
        companyName     : '',
        licenseNumber   : '',
        legalPerson     : '',
        companyAddress  : '',
        companyPhone    : '',
        companyEmail    : '',
        companyWebsite  : ''
    };

    //定义方法
    $scope.reset = function(){
        $scope.organization.name            = '';
        $scope.organization.token           = '';
        $scope.organization.companyName     = '';
        $scope.organization.licenseNumber   = '';
        $scope.organization.legalPerson     = '';
        $scope.organization.companyAddress  = '';
        $scope.organization.companyPhone    = '';
        $scope.organization.companyEmail    = '';
        $scope.organization.companyWebsite  = '';
    };

    $scope.submit = function(){
        $http({
            url     : '/api/1.0/organization',
            method  : 'POST',
            data    : JSON.stringify($scope.organization),
            headers : {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('organization');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };
});