var app = angular.module('app', ['ui.router', 'oc.lazyLoad', 'ngGrid', 'ngSanitize', 'ui.bootstrap']);

app.config(['$controllerProvider', '$compileProvider', '$filterProvider', '$provide', function($controllerProvider, $compileProvider, $filterProvider, $provide){
    app.controller = $controllerProvider.register;
    app.directive  = $compileProvider.directive;
    app.filter     = $filterProvider.register;
    app.factory    = $provide.factory;
    app.service    = $provide.service;
    app.constant   = $provide.constant;
    app.value      = $provide.value;

}]).run(['$rootScope', '$state', '$stateParams', function($rootScope, $state, $stateParams){
    $rootScope.$state       = $state;
    $rootScope.$stateParams = $stateParams;

}]).config(['$stateProvider', function($stateProvider){
	$stateProvider.state('organization', {
		url   : '/organization',
        templateUrl : '/page/cloned/organization',
        controller  : 'organization',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/angular-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/angular-grid/theme.css',
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/organization/index.js'
                ]);
            }]
        }
	}).state('organizationAdd', {
        url         : '/organization/add',
        templateUrl : '/page/cloned/organization/add',
        controller  : 'organizationAdd',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/cloned/organization/add.js'
                ]);
            }]
        }
    }).state('organizationEdit', {
        url         : '/organization/:id',
        templateUrl : function($stateParams){
            return '/page/cloned/organization/' + $stateParams.id;
        },
        controller  : 'organizationEdit',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/cloned/organization/edit.js'
                ]);
            }]
        }
    }).state('device', {
        url         : '/device',
        templateUrl : '/page/cloned/device',
        controller  : 'device',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/device/index.js'
                ]);
            }]
        }
    }).state('device.list', {
        url         : '/list',
        templateUrl : function($stateParams){
            return '/page/cloned/device/list/' + $stateParams.organizationId;
        },
        controller  : 'deviceList',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/angular-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/angular-grid/theme.css',
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/device/list.js'
                ]);
            }]
        }
    }).state('deviceAdd', {
        url         : '/deviceAdd/:organizationId',
        templateUrl : function($stateParams){
            return '/page/cloned/device/add/' + $stateParams.organizationId;
        },
        controller  : 'deviceAdd',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/device/add.js'
                ]);
            }]
        }
    });
}]);