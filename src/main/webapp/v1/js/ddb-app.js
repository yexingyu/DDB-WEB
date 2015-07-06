angular.module('ddbApp',
        [ 'ngRoute', 'ddbApp.constants', 'ddbApp.controllers', 'ddbApp.services', 'ui.bootstrap' ])

.config([ '$routeProvider', function($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl : 'tpl-login.html',
        controller : 'LoginCtrl'

    }).when('/home', {
        templateUrl : 'tpl-home.html',
        controller : 'HomeCtrl'

    }).when('/contact', {
        templateUrl : 'tpl-contact.html',
        controller : 'ContactCtrl'

    }).when('/about', {
        templateUrl : 'tpl-about.html',
        controller : 'AboutCtrl'

    }).when('/product', {
        templateUrl : 'tpl-product.html',
        controller : 'ProductCtrl'

    }).when('/product/:id', {
        templateUrl : 'tpl-product-details.html',
        controller : 'ProductDetailsCtrl'

    }).when('/order/:id', {
        templateUrl : 'tpl-order.html',
        controller : 'OrderCtrl'

    }).when('/profile', {
        templateUrl : 'tpl-profile.html',
        controller : 'ProfileCtrl'

    }).when('/profile/edit', {
        templateUrl : 'tpl-profile-edit.html',
        controller : 'ProfileEditCtrl'

    }).otherwise({
        redirectTo : '/home'
    });
} ])

.run(
        [ '$rootScope', '$window', 'CookieService', 'ConstantService',
                function($rootScope, $window, CookieService, ConstantService) {
                    ConstantService.init();
                    $rootScope.language = {
                        'language' : CookieService.getLanguage()
                    };
                    console.log($rootScope);

                    $rootScope.profile = {
                        sw : ""
                    };
                    $rootScope.menuCss = [ "current_page_item", "", "", "" ];

                    // Load the SDK asynchronously
                    /*
                     * (function(d, s, id) { var js, fjs =
                     * d.getElementsByTagName(s)[0]; if (d.getElementById(id))
                     * return; js = d.createElement(s); js.id = id; js.src =
                     * "//connect.facebook.net/en_US/sdk.js";
                     * fjs.parentNode.insertBefore(js, fjs); }(document,
                     * 'script', 'facebook-jssdk')); // Initial fb SDK
                     * $window.fbAsyncInit = function() { FB.init({ appId :
                     * '1170711572955238', cookie : true, xfbml : true, version :
                     * 'v2.3' }); };
                     */
                } ]);
