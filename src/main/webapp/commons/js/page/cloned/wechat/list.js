app.controller('wechatList', function($scope, $http, $state, $stateParams){
    //下拉
    $scope.status    = {};
    $scope.appStatis = {};
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

        var socket = new WebSocket('ws://' + location.host + '/socket/1.0/bot?wechatId=' + id);
        socket.onopen = function(event){

        };
        socket.onmessage = function(event){
            console.log(event.data);
            window.open(event.data);
        };
        socket.onclose = function(event){
            console.log(id + " is closed");
        };
    };

    $scope.allocation = function(id){
        $state.go('allocation', {'wechatId' : id});
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

    $scope.getStatus = function(){
        $http({
            url    : '/api/1.0/consts/wechatStatus',
            method : 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.status = res.result;
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getAppStatus = function(){
        $http({
            url    : '/api/1.0/consts/appStatus',
            method : 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.appStatus = res.result;
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
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
            field       : 'status',
            displayName : '状态',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{status[COL_FIELD]}}</span></div>'
        },{
            field       : 'phone',
            displayName : '手机号码'
        },{
            field       : 'imei',
            displayName : 'IMEI'
        },{
            field       : 'appStatus',
            displayName : 'App状态',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{appStatus[COL_FIELD]}}</span></div>'
        },{
            field        : 'lastCheckTime',
            displayName  : '最近一次检测时间',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            displayName  : '操作',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="edit(row.getProperty(\'id\'))">[修改]</a><a ng-click="login(row.getProperty(\'id\'))">[登录]</a><a ng-click="allocation(row.getProperty(\'id\'))">[客服]</a><a ng-click="im(row.getProperty(\'id\'))">[绑定IM]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
    $scope.getStatus();
    $scope.getAppStatus();
});