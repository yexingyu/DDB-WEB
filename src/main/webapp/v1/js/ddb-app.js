angular.module('ddbApp', ['ngRoute', 'ddbApp.constants', 'ddbApp.controllers', 'ddbApp.services', 'ddbApp.models', 'ui.bootstrap', 'ezfb', 'frapontillo.bootstrap-switch'])

    .config(['$routeProvider', 'ezfbProvider', function ($routeProvider, ezfbProvider) {
        ezfbProvider.setInitParams({
            appId: '1170711572955238',
            version: 'v2.3'
        });

        $routeProvider.when('/following', {
            templateUrl: 'tpl-following.html',
            controller: 'FollowingCtrl'
        
        }).when('/local', {
            templateUrl: 'tpl-local.html',
            controller: 'LocalCtrl'
            	
        }).when('/contact', {
            templateUrl: 'tpl-contact.html',
            controller: 'ContactCtrl'

        }).when('/about', {
            templateUrl: 'tpl-about.html',
            controller: 'AboutCtrl'

        }).when('/all', {
            templateUrl: 'tpl-all.html',
            controller: 'AllCtrl'

        }).when('/product/:id', {
            templateUrl: 'tpl-product-details.html',
            controller: 'ProductDetailsCtrl'

        }).when('/m/product/:id', {
            templateUrl: 'tpl-product-details-m.html',
            controller: 'ProductDetailsCtrl'
            	
        }).when('/product/:id/order', {
            templateUrl: 'tpl-product-order.html',
            controller: 'ProductOrderCtrl'

        }).when('/search', {
            templateUrl: 'tpl-search.html',
            controller: 'SearchCtrl'

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

        }).when('/share', {
            templateUrl: 'tpl-share.html',
            controller: 'ShareCtrl'
            	
        }).when('/discuss', {
            templateUrl: 'tpl-discuss.html',
            controller: 'DiscussCtrl'            	

        }).when('/category', {
            templateUrl: 'tpl-category.html'

        }).when('/welcome', {
            templateUrl: 'tpl-welcome.html',
            controller: 'WelcomeCtrl' 
            	
        }).otherwise({
            redirectTo: '/all'
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
    }])

    .filter('RowSwitchFilter', function () {
        return function (arrayLength) {
            if (arrayLength) {
                arrayLength = Math.ceil(arrayLength);
                var arr = new Array(arrayLength), i = 0;
                for (; i < arrayLength; i++) {
                    arr[i] = i;
                }
                return arr;
            }
        };
    });

