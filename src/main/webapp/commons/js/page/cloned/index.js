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
        url         : '/list/:organizationId',
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
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/cloned/device/add.js'
                ]);
            }]
        }
    }).state('deviceEdit', {
        url         : '/deviceEdit/:id',
        templateUrl : function($stateParams){
            return '/page/cloned/device/' + $stateParams.id;
        },
        controller  : 'deviceEdit',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/cloned/device/edit.js'
                ]);
            }]
        }
    }).state('wechat', {
        url         : '/wechat',
        templateUrl : '/page/cloned/wechat',
        controller  : 'wechat',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/wechat/index.js'
                ]);
            }]
        }
    }).state('wechat.list', {
        url         : '/list/:organizationId',
        templateUrl : function($stateParams){
            return '/page/cloned/wechat/list/' + $stateParams.organizationId;
        },
        controller  : 'wechatList',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/angular-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/angular-grid/theme.css',
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/wechat/list.js'
                ]);
            }]
        }
    }).state('wechatAdd', {
        url         : '/wechatAdd/:organizationId',
        templateUrl : function($stateParams){
            return '/page/cloned/wechat/add/' + $stateParams.organizationId;
        },
        controller  : 'wechatAdd',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/cloned/wechat/add.js'
                ]);
            }]
        }
    }).state('wechatEdit', {
        url         : '/wechatEdit/:id',
        templateUrl : function($stateParams){
            return '/page/cloned/wechat/' + $stateParams.id;
        },
        controller  : 'wechatEdit',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/cloned/wechat/edit.js'
                ]);
            }]
        }
    }).state('allocation', {
        url         : '/wechat/allocation/:wechatId',
        templateUrl : function($stateParams){
            return '/page/cloned/wechat/allocation/' + $stateParams.wechatId;
        },
        controller  : 'allocation',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/angular-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/angular-grid/theme.css',
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/cloned/wechat/allocation.js'
                ]);
            }]
        }
    }).state('im', {
        url         : '/wechat/im/:wechatId',
        templateUrl : function($stateParams){
            return '/page/cloned/wechat/im/' + $stateParams.wechatId;
        },
        controller  : 'im',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/page/cloned/wechat/im.js'
                ]);
            }]
        }
    }).state('applet', {
        url         : '/applet',
        templateUrl : '/page/cloned/im/tencent/applet',
        controller  : 'applet',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/im/tencent/applet/index.js'
                ]);
            }]
        }
    }).state('applet.list', {
        url         : '/list/:organizationId',
        templateUrl : function($stateParams){
            return '/page/cloned/im/tencent/applet/list/' + $stateParams.organizationId;
        },
        controller  : 'appletList',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/angular-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/angular-grid/theme.css',
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/im/tencent/applet/list.js'
                ]);
            }]
        }
    }).state('appletAdd', {
        url         : '/appletAdd/:organizationId',
        templateUrl : function($stateParams){
            return '/page/cloned/im/tencent/applet/add/' + $stateParams.organizationId;
        },
        controller  : 'appletAdd',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/cloned/im/tencent/applet/add.js'
                ]);
            }]
        }
    }).state('appletEdit', {
        url         : '/appletEdit/:id',
        templateUrl : function($stateParams){
            return '/page/cloned/im/tencent/applet/' + $stateParams.id;
        },
        controller  : 'appletEdit',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/cloned/im/tencent/applet/edit.js'
                ]);
            }]
        }
    }).state('admin', {
        url         : '/admin/:appletId',
        templateUrl : function($stateParams){
            return '/page/cloned/im/tencent/applet/admin/' + $stateParams.appletId;
        },
        controller  : 'admin',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/im/tencent/applet/admin.js'
                ]);
            }]
        }
    }).state('service', {
        url         : '/service',
        templateUrl : '/page/cloned/service',
        controller  : 'service',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/service/index.js'
                ]);
            }]
        }
    }).state('service.list', {
        url         : '/list/:organizationId',
        templateUrl : function($stateParams){
            return '/page/cloned/service/list/' + $stateParams.organizationId;
        },
        controller  : 'serviceList',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/angular-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/angular-grid/theme.css',
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/page/cloned/service/list.js'
                ]);
            }]
        }
    }).state('serviceAdd', {
        url         : '/serviceAdd/:organizationId',
        templateUrl : function($stateParams){
            return '/page/cloned/service/add/' + $stateParams.organizationId;
        },
        controller  : 'serviceAdd',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/plugin/md5/md5.js',
                    basePath + '/commons/js/page/cloned/service/add.js'
                ]);
            }]
        }
    }).state('serviceEdit', {
        url         : '/serviceEdit/:id',
        templateUrl : function($stateParams){
            return '/page/cloned/service/' + $stateParams.id;
        },
        controller  : 'serviceEdit',
        resolve     : {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/cloned/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/plugin/md5/md5.js',
                    basePath + '/commons/js/page/cloned/service/edit.js'
                ]);
            }]
        }
    });
}]);