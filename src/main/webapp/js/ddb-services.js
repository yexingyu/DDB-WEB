angular.module('ddbApp.services', [ 'ngResource', 'ngCookies' ])

.factory('MenuService', [ '$rootScope', function($rootScope) {
    return {
        go : function(id) {
            // if (id == 1) {
            // $rootScope.menu_css_i0 = "";
            // $rootScope.menu_css_i1 = "current_page_item";
            // $rootScope.menu_css_i2 = "";
            // $rootScope.menu_css_i3 = "";
            // } else if (id == 2) {
            // $rootScope.menu_css_i0 = "";
            // $rootScope.menu_css_i1 = "";
            // $rootScope.menu_css_i2 = "current_page_item";
            // $rootScope.menu_css_i3 = "";
            // } else if (id == 3) {
            // $rootScope.menu_css_i0 = "";
            // $rootScope.menu_css_i1 = "";
            // $rootScope.menu_css_i2 = "";
            // $rootScope.menu_css_i3 = "current_page_item";
            // } else {
            // $rootScope.menu_css_i0 = "current_page_item";
            // $rootScope.menu_css_i1 = "";
            // $rootScope.menu_css_i2 = "";
            // $rootScope.menu_css_i3 = "";
            // }

        }
    };
} ])

.factory('CookieService', [ '$cookies', function($cookies) {
    return {
        logout : function() {
            console.log($cookies);
            $cookies.token = '';
        }
    };
} ])

.factory('LoginService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        login : function(account, success) {
            var LoginResource = $resource($rootScope.apiUrl + '/api/login', {}, {
                'login' : {
                    method : 'POST',
                    isArray : false
                }
            });
            new LoginResource(account).$login(success);
        }
    };
} ])

.factory('ProfileService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        profile : function(success) {
            return $resource($rootScope.apiUrl + '/api/profile', {}, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(success);
        }
    };
} ])

.factory('PayMonthlyService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        home : function() {
            return [ {
                id : 1,
                name : 'test_01',
                describe : 'for test 01'
            }, {
                id : 2,
                name : 'test_02',
                describe : 'for test 02'
            }, {
                id : 3,
                name : 'test_03',
                describe : 'for test 03'
            } ];
        },
        get : function(id) {
        }
    };
} ]);
