angular.module('ddbApp', ['ngRoute', 'ddbApp.constants', 'ddbApp.controllers', 'ddbApp.services', 'ui.bootstrap', 'ezfb', 'frapontillo.bootstrap-switch'])

    .config(['$routeProvider', 'ezfbProvider', function ($routeProvider, ezfbProvider) {
        ezfbProvider.setInitParams({
            appId: '1170711572955238',
            version: 'v2.3'
        });

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

        }).when('/profile/verify_email/:hashCode', {
            templateUrl: 'tpl-profile-verify-email.html',
            controller: 'ProfileVerifyEmailCtrl'

        }).when('/store', {
            templateUrl: 'tpl-store.html',
            controller: 'StoreCtrl'

        }).otherwise({
            redirectTo: '/home'
        });
    }])

    .run(['$rootScope', '$window', '$location', 'CookieService', 'ConstantService', function ($rootScope, $window, $location, CookieService, ConstantService) {
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

        $rootScope.$on('$routeChangeSuccess', function (event) {
            if (!$window.ga) {
                return;
            }
            console.log($location.path());
            $window.ga('send', 'pageview', {
                page: $location.path()
            });
        });
    }]);
