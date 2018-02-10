app.controller('allocation', function($scope, $http, $state, $stateParams){
    //查询列表
    $scope.wechatId       = $stateParams.wechatId;
    $scope.name           = '';
    $scope.step           = 1;

    //列表参数
    $scope.list          = [];
    $scope.total         = 0;

    $scope.setDefault = function(){

    };

    $scope.save = function(){

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
            url: '/api/1.0/allocation/find',
            method: 'GET',
            params: {
                'wechatId'       : $scope.wechatId,
                'name'           : $scope.name
            },
            headers: {
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
        //pagingOptions          : $scope.pagingOptions,
        totalServerItems       : 'total',
        i18n                   : 'zh-cn',
        columnDefs: [{
            field   : 'id',
            visible : false
        },{
            field       : 'service.name',
            displayName : '销售名称'
        },{
            field       : 'probability',
            displayName : '概率'
        },{
            field       : 'defaultProbability',
            displayName : '默认概率',
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