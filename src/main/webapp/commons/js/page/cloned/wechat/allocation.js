app.controller('allocation', function($scope, $http, $state, $stateParams){
    //查询列表
    $scope.wechatId        = $stateParams.wechatId;
    $scope.name            = '';
    $scope.step            = '';
    $scope.isOfflineAllots = {
        true  : '可分配',
        false : '不可分配'
    };

    //列表参数
    $scope.list          = [];
    $scope.total         = 0;

    $scope.setDefault = function(){
        $http({
            url    : '/api/1.0/allocation/default/probability/' + $scope.wechatId,
            method : 'PUT'
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                location.reload();
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.updateByNow = function(){
        $http({
            url    : '/api/1.0/allocation/update/' + $scope.wechatId,
            method : 'POST'
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                location.reload();
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.save = function(){
        if($scope.step === null || $scope.step === undefined || $scope.step < 1){
            alert("请正确填写步长");
            return false;
        }

        angular.forEach($scope.list, function(allocation){
            allocation.step = $scope.step;
        });

        $http({
            url     : '/api/1.0/allocation',
            method  : 'PUT',
            data    : JSON.stringify($scope.list),
            headers : {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                location.reload();
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.reset = function(){
        $scope.name  = '';
    };

    $scope.search = function(){
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.getData = function(){
        $http({
            url     : '/api/1.0/allocation/find',
            method  : 'GET',
            params  : {
                'wechatId'       : $scope.wechatId,
                'name'           : $scope.name
            },
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.list = res.result;
                $scope.total = res.result.size;
            }else{
                alert(res.msg);

                $scope.list  = [];
                $scope.total = 0;
            }
        }).error(function(response){
            console.error(response);

            $scope.list = [];
            $scope.total = 0;
        });
    };

    $scope.$watch('pagingOptions', function(newVal, oldVal){
        if(newVal !== oldVal && (newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize)){
            $scope.getData();
        }
    }, true);

    //列表属性
    $scope.gridOptions = {
        data                   : 'list',
        enablePaging           : false,
        enableSorting          : false,
        enableRowSelection     : false,
        showFooter             : true,
        showSelectionCheckbox  : false,
        selectWithCheckboxOnly : true,
        multiSelect            : false,
        keepLastSelected       : false,
        totalServerItems       : 'total',
        i18n                   : 'zh-cn',
        columnDefs: [{
            field   : 'id',
            visible : false
        },{
            field       : 'service.name',
            displayName : '销售名称'
        },{
            field          : 'probability',
            displayName    : '概率',
            enableCellEdit : true
        },{
            field          : 'defaultProbability',
            displayName    : '默认概率',
            enableCellEdit : true
        },{
            field                : 'isOfflineAllot',
            displayName          : '离线可分配',
            enableCellEdit       : true,
            cellTemplate         : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{isOfflineAllots[COL_FIELD]}}</span></div>'
        },{
            field       : 'step',
            displayName : '步长'
        },{
            field        : 'createTime',
            displayName  : '创建时间',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            field        : 'updateTime',
            displayName  : '更新时间',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
});