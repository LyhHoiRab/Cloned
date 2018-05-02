app.controller('messageList', function($scope, $http, $state, $stateParams){
    //下拉
    $scope.types = {};

    //查询列表
    $scope.remarkname     = $stateParams.remarkname;
    $scope.serviceName    = '';

    //列表参数
    $scope.list          = [];
    $scope.total         = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    //定义方法
    $scope.reset = function(){
        $scope.serviceName    = '';
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
            url: '/api/1.0/message/page',
            method: 'GET',
            params: {
                'pageNum'        : $scope.pagingOptions.currentPage,
                'pageSize'       : $scope.pagingOptions.pageSize,
                'remarkname'     : $scope.remarkname,
                'serviceName'    : $scope.serviceName
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

    $scope.getType = function(){
        $http({
            url    : '/api/1.0/consts/messageType',
            method : 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.types = res.result;
            }else{
                alert(res.msg);

                $scope.types = {};
            }
        }).error(function(response){
            console.error(response);

            $scope.types = {};
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
            field        : 'createTime',
            displayName  : '发送时间',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            displayName  : '发送情况',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{row.entity.friend.nickname}} {{row.entity.sendByService == true ? "←" : "→"}} {{row.entity.service.name}}</span></div>'
        },{
            field       : 'text',
            displayName : '消息内容'
        },{
            field       : 'type',
            displayName : '消息类型',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{types[COL_FIELD]}}</span></div>'
        }]
    };

    //初始化数据
    $scope.getType();
    $scope.getData();
});