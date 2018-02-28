app.controller('appletList', function($scope, $http, $state, $stateParams){
    //查询列表
    $scope.organizationId = $stateParams.organizationId;
    $scope.appId          = '';

    //列表参数
    $scope.list          = [];
    $scope.total         = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    //定义方法
    $scope.admin = function(id){
        $state.go('admin', {'appletId' : id});
    };

    $scope.edit = function(id){
        $state.go('appletEdit', {'id' : id});
    };

    $scope.add = function(){
        $state.go('appletAdd', {'organizationId' : $scope.organizationId});
    };

    $scope.reset = function(){
        $scope.appId          = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.getData = function(){
        $http({
            url: '/api/1.0/im/tencent/applet/page',
            method: 'GET',
            params: {
                'pageNum'        : $scope.pagingOptions.currentPage,
                'pageSize'       : $scope.pagingOptions.pageSize,
                'organizationId' : $scope.organizationId,
                'appId'          : $scope.appId
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.list = res.result.content;
                $scope.total = res.result.total;
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
        enablePaging           : true,
        enableSorting          : false,
        enableRowSelection     : false,
        showFooter             : true,
        showSelectionCheckbox  : false,
        selectWithCheckboxOnly : true,
        multiSelect            : false,
        keepLastSelected       : false,
        pagingOptions          : $scope.pagingOptions,
        totalServerItems       : 'total',
        i18n                   : 'zh-cn',
        columnDefs: [{
            field   : 'id',
            visible : false
        },{
            field       : 'appId',
            displayName : 'AppId'
        },{
            field       : 'privateKeyPath',
            displayName : '密钥路径'
        },{
            field       : 'publicKeyPath',
            displayName : '公钥路径'
        },{
            field        : 'createTime',
            displayName  : '创建时间',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            field        : 'updateTime',
            displayName  : '更新时间',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            displayName  : '操作',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="edit(row.getProperty(\'id\'))">[修改]</a><a ng-click="admin(row.getProperty(\'id\'))">[添加管理员]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
});