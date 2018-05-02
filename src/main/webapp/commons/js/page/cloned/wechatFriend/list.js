app.controller('wechatFriendList', function($scope, $http, $state, $stateParams){
    //下拉
    $scope.sexs = {};

    //查询列表
    $scope.organizationId = $stateParams.organizationId;
    $scope.wxno           = '';
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
    $scope.message = function(remarkname){
        $state.go('message', {'remarkname' : remarkname});
    };

    $scope.edit = function(id){
        $state.go('wechatFriendEdit', {'id' : id});
    };

    $scope.reset = function(){
        $scope.wxno        = '';
        $scope.serviceName = '';
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
            url: '/api/1.0/wechatFriend/page',
            method: 'GET',
            params: {
                'pageNum'        : $scope.pagingOptions.currentPage,
                'pageSize'       : $scope.pagingOptions.pageSize,
                'organizationId' : $scope.organizationId,
                'wxno'           : $scope.wxno,
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

    $scope.getSex = function(){
        $http({
            url    : '/api/1.0/consts/sex',
            method : 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.sexs = res.result;
            }else{
                alert(res.msg);

                $scope.sexs = {};
            }
        }).error(function(response){
            console.error(response);

            $scope.sexs = {};
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
        rowHeight              : 60,
        columnDefs: [{
            field   : 'id',
            visible : false
        },{
            field       : 'headImgUrl',
            displayName : '头像',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><img src="{{COL_FIELD}}" width="50" height="50" style="vertical-align:middle;"></div>'
        },{
            field       : 'nickname',
            displayName : '昵称'
        },{
            field       : 'wxno',
            displayName : '微信号'
        },{
            field       : 'wechat.wxno',
            displayName : '所属微信'
        },{
            field        : 'sex',
            displayName  : '性别',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{sexs[COL_FIELD]}}</span></div>'
        },{
            field       : 'remarkname',
            displayName : '备注名'
        },{
            displayName  : '操作',
            cellTemplate : '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="edit(row.getProperty(\'id\'))">[修改]</a><a ng-click="message(row.getProperty(\'remarkname\'))">[聊天记录]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getSex();
    $scope.getData();
});