angular.module(
    'adminApp',
    ['ngRoute', 'ddbApp.constants', 'adminApp.controllers', 'adminApp.services',
        'ddbApp.services']).config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/home', {
            templateUrl: 'tpl/tpl-admin-home.html',
            controller: 'AdminHomeCtrl'

        }).when('/dashboard', {
            templateUrl: 'tpl/tpl-admin-dashboard.html',
            controller: 'DashboardCtrl'

        }).when('/product', {
            templateUrl: 'tpl/tpl-admin-product-index.html',
            controller: 'PMCtrl'

        }).when('/product/add', {
            templateUrl: 'tpl/tpl-admin-product-add.html',
            controller: 'PMProductAdd'

        }).when('/product/:id', {
            templateUrl: 'tpl/tpl-admin-product-edit.html',
            controller: 'PMProductEdit'

        }).when('/store/add', {
            templateUrl: 'tpl/tpl-admin-store-add.html',
            controller: 'PMStoreAdd'

        }).when('/flot', {
            templateUrl: 'tpl/tpl-admin-flot.html',
            controller: 'FlotCtrl'

        }).when('/morris', {
            templateUrl: 'tpl/tpl-admin-morris.html',
            controller: 'MorrisCtrl'

        }).otherwise({
            redirectTo: '/dashboard'
        });
    }])

    .run(['$rootScope', '$window', 'ConstantService', function ($rootScope, $window, ConstantService) {
        ConstantService.init();
    }]);
