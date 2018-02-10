var app = angular.module('app', ['ngSanitize', 'ui.bootstrap', 'angularFileUpload']);

app.controller('userInfo', function($scope, $http, FileUploader){
    var uploader = $scope.uploader = new FileUploader({
        url               : '/api/1.0/userInfo/upload',
        queueLimit        : 1,
        method            : 'POST',
        removeAfterUpload : true
    });

    uploader.filters.push({
        name : 'imageFilter',
        fn   : function(item){
            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
            return 'jpg|jpeg|png|bmp|gif|'.indexOf(type) !== -1;
        }
    });

    uploader.onSuccessItem = function(item, response, status, headers){
        $scope.user.headImgUrl = response.result;
    };

    $scope.user = {
        nickname   : '',
        name       : '',
        headImgUrl : '/commons/img/service_default_head.jpg'
    };

    $scope.submit = function(){
        if($scope.user.nickname === null || $scope.user.nickname === undefined || $scope.user.nickname === ''){
            alert('请填写用户昵称');
            return;
        }else if($scope.user.headImgUrl === null || $scope.user.headImgUrl === undefined || $scope.user.headImgUrl === ''){
            alert('请上传用户头像');
            return;
        }else if($scope.user.name === null || $scope.user.name === undefined || $scope.user.name === ''){
            alert('请填写用户姓名');
            return;
        }

        $http({
            url     : '/api/1.0/userInfo',
            method  : 'POST',
            data    : JSON.stringify($scope.user),
            headers : {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                location.href = '/page/cloned/index';
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };
});