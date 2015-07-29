angular.module('ddbApp', ['ngRoute', 'ddbApp.constants', 'ddbApp.controllers', 'ddbApp.services', 'ui.bootstrap'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/home', {
            templateUrl: 'tpl-home.html',
            controller: 'HomeCtrl'

        }).when('/contact', {
            templateUrl: 'tpl-contact.html',
            controller: 'ContactCtrl'

        }).when('/about', {
            templateUrl: 'tpl-about.html',
            controller: 'AboutCtrl'

        }).when('/product', {
            templateUrl: 'tpl-product.html',
            controller: 'ProductCtrl'

        }).when('/product/:id', {
            templateUrl: 'tpl-product-details.html',
            controller: 'ProductDetailsCtrl'

        }).when('/product/:id/order', {
            templateUrl: 'tpl-product-order.html',
            controller: 'ProductOrderCtrl'

        }).when('/order/me', {
            templateUrl: 'tpl-order-me.html',
            controller: 'OrderMeCtrl'

        }).when('/order/id/:id', {
            templateUrl: 'tpl-order-details.html',
            controller: 'OrderDetailsCtrl'

        }).when('/profile', {
            templateUrl: 'tpl-profile.html',
            controller: 'ProfileCtrl'

        }).when('/profile/edit', {
            templateUrl: 'tpl-profile-edit.html',
            controller: 'ProfileEditCtrl'

        }).otherwise({
            redirectTo: '/home'
        });
    }])

    .run(['$rootScope', '$window', 'CookieService', 'ConstantService', function ($rootScope, $window, CookieService, ConstantService) {
        ConstantService.init();

        // set language
        $rootScope['language'] = {
            'language': CookieService.getLanguage()
        };

        // set fingerprint
        $rootScope['fingerprint'] = new Fingerprint({
            canvas: true,
            ie_activex: true
        }).get();
        CookieService.setFingerprint($rootScope['fingerprint']);

        // set templates
        $rootScope.popoverTemplate = {
            reviewTemplateUrl: 'tpl-product-review.html'
        };

        // Load the SDK asynchronously
        /*
         * (function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if
         * (d.getElementById(id)) return; js = d.createElement(s); js.id = id;
         * js.src = "//connect.facebook.net/en_US/sdk.js";
         * fjs.parentNode.insertBefore(js, fjs); }(document, 'script',
         * 'facebook-jssdk')); // Initial fb SDK $window.fbAsyncInit = function() {
         * FB.init({ appId : '1170711572955238', cookie : true, xfbml : true,
         * version : 'v2.3' }); };
         */
    }]);
