angular.module('ddbApp', [ 'ngRoute', 'ddbApp.controllers', 'ddbApp.services' ]).config(
        [ '$routeProvider', function($routeProvider) {
            $routeProvider.when('/login', {
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
