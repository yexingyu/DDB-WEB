angular.module('ddbApp', [ 'ngRoute', 'ddbApp.controllers', 'ddbApp.services' ])

.config([ '$routeProvider', function($routeProvider) {
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

    }).when('/profile/edit', {
        templateUrl : 'templates/profile-edit.html',
        controller : 'ProfileEditCtrl'

    }).when('/about', {
        templateUrl : 'templates/about.html',
        controller : 'AboutCtrl'

    }).otherwise({
        redirectTo : '/home'
    });
} ])

.run([ '$rootScope', '$window', function($rootScope, $window) {
    $rootScope.apiUrl = "http://localhost:8080";
    $rootScope.profile = {
        sw : ""
    };
    $rootScope.menuCss = [ "current_page_item", "", "", "" ];

    // Load the SDK asynchronously
    (function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s);
        js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));

    // Initial fb SDK
    $window.fbAsyncInit = function() {
        FB.init({
            appId : '1170711572955238',
            cookie : true,
            xfbml : true,
            version : 'v2.3'
        });
    };
} ]);