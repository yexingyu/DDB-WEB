var ddbApp = angular.module('ddbApp', [ 'ngRoute', 'accountsControllers', 'angular-md5' ]);

ddbApp.config([ '$routeProvider', function($routeProvider) {
    $routeProvider.when('/accounts', {
        templateUrl : 'partials/accounts-list.html',
        controller : 'AccountsListCtrl'
    }).when('/accounts/register', {
        templateUrl : 'partials/accounts-register.html',
        controller : 'AccountsRegisterCtrl'
    }).when('/accounts/:accountId', {
        templateUrl : 'partials/accounts-detail.html',
        controller : 'AccountsDetailCtrl'
    }).otherwise({
        redirectTo : '/accounts'
    });
} ]);
