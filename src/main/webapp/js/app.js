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

            }).when('/pm', {
                templateUrl : 'templates/pm-index.html',
                controller : 'PayMonthlyIndexCtrl'

            }).when('/login', {
                templateUrl : 'templates/login.html',
                controller : 'LoginCtrl'

            }).otherwise({
                redirectTo : '/pm'
            });
        } ]);
