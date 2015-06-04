angular.module('ddbApp', [ 'ngRoute', 'ddbApp.controllers', 'ddbApp.services' ]).config(
        [ '$routeProvider', function($routeProvider) {
            $routeProvider.when('/accounts', {
                templateUrl : 'templates/accounts-list.html',
                controller : 'AccountsListCtrl'

            }).when('/accounts/register', {
                templateUrl : 'templates/accounts-register.html',
                controller : 'AccountsRegisterCtrl'

            }).when('/accounts/:accountId', {
                templateUrl : 'templates/accounts-detail.html',
                controller : 'AccountsDetailCtrl'

            }).when('/login', {
                templateUrl : 'templates/login.html',
                controller : 'LoginCtrl'

            }).when('/home', {
                templateUrl : 'templates/home.html',
                controller : 'HomeCtrl'

            }).when('/pm', {
                templateUrl : 'templates/pm-index.html',
                controller : 'PMCtrl'

            }).when('/profile', {
                templateUrl : 'templates/profile.html',
                controller : 'ProfileCtrl'

            }).when('/about', {
                templateUrl : 'templates/about.html',
                controller : 'AboutCtrl'

            }).otherwise({
                redirectTo : '/home'
            });
        } ]);
