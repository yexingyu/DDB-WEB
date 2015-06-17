angular.module('adminApp',
        [ 'ngRoute', 'adminApp.controllers', 'adminApp.services', 'ddbApp.services' ]).config(
        [ '$routeProvider', function($routeProvider) {
            $routeProvider.when('/home', {
                templateUrl : 'tpl-admin-home.html',
                controller : 'AdminHomeCtrl'

            }).when('/login', {
                templateUrl : 'tpl-admin-login.html',
                controller : 'AdminLoginCtrl'

            }).otherwise({
                redirectTo : '/home'
            });
        } ])

.run([ '$rootScope', '$window', 'ConstantService', function($rootScope, $window, ConstantService) {
    $rootScope.apiUrl = "http://localhost:8080";
    ConstantService.init();
} ]);
