app.controller('wechatList', function($scope, $http, $state, $stateParams){
    //查询列表
    $scope.organizationId = $stateParams.organizationId;
    $scope.wxno           = '';

    //列表参数
    $scope.list          = [];
    $scope.total         = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    //定义方法
    $scope.login = function(id){
        $http({
            url: '/api/1.0/wechat/login/' + id,
            method: 'POST'
        }).success(function(res, status, headers, config){
            if(!res.success){
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.service = function(id){
        $state.go('service', {'wechatId' : id});
    };

    $scope.im = function(id){
        $state.go('im', {'wechatId' : id});
    };

    $scope.edit = function(id){
        $state.go('wechatEdit', {'id' : id});
    };

    $scope.add = function(){
        $state.go('wechatAdd', {'organizationId' : $scope.organizationId});
    };

    $scope.reset = function(){
        $scope.wxno  = '';
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
            url: '/api/1.0/wechat/page',
            method: 'GET',
            params: {
                'pageNum'        : $scope.pagingOptions.currentPage,
                'pageSize'       : $scope.pagingOptions.pageSize,
                'organizationId' : $scope.organizationId,
                'wxno'           : $scope.wxno
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
            field       : 'wxno',
            displayName : '微信号'
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
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="edit(row.getProperty(\'id\'))">[修改]</a><a ng-click="login(row.getProperty(\'id\'))">[登录]</a><a ng-click="service(row.getProperty(\'id\'))">[客服]</a><a ng-click="im(row.getProperty(\'id\'))">[绑定IM]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
});