angular.module('adminApp',
        [ 'ngRoute', 'adminApp.controllers', 'adminApp.services', 'ddbApp.services' ]).config(
        [ '$routeProvider', function($routeProvider) {
            $routeProvider.when('/home', {
                templateUrl : 'tpl-admin-home.html',
                controller : 'AdminHomeCtrl'

            }).when('/login', {
                templateUrl : 'tpl-admin-login.html',
                controller : 'AdminLoginCtrl'
                	
            }).when('/dashboard', {
                templateUrl : 'tpl-admin-dashboard.html',
                controller : 'DashboardCtrl'
                	
            }).when('/product', {
                templateUrl : 'tpl-admin-product-index.html',
                controller : 'PMCtrl'
                	
            }).when('/pm-product/:id', {
                templateUrl : 'tpl-admin-product-detail.html',
                controller : 'PMProductCtrl'              	

            }).when('/flot', {
                templateUrl : 'tpl-admin-flot.html',
                controller : 'FlotCtrl'
                	
            }).when('/morris', {
                templateUrl : 'tpl-admin-morris.html',
                controller : 'MorrisCtrl'                   	
                	
            }).otherwise({
                redirectTo : '/dashboard'
            });
        } ])

.run([ '$rootScope', '$window', 'ConstantService', function($rootScope, $window, ConstantService) {
    $rootScope.apiUrl = "http://localhost:8080";
    ConstantService.init();
} ]);
