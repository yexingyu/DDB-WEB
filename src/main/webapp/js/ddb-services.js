angular.module('ddbApp.services', [ 'ngResource', 'ngCookies' ])

.factory('ConstantService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        init : function() {
            $resource($rootScope.apiUrl + '/api/constant', {}, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(function(response) {
                if (response.status == 'SUCCESS') {
                    $rootScope.constant = response.data;
                }
            });
        }
    };
} ])

.factory('MenuService', [ '$rootScope', function($rootScope) {
    return {
        setCurrent : function(id) {
            for (var i = 0; i < $rootScope.menuCss.length; i++) {
                if (i == id) {
                    $rootScope.menuCss[i] = "current_page_item";
                } else {
                    $rootScope.menuCss[i] = "";
                }
            }
        }
    };
} ])

.factory('CookieService', [ '$cookies', function($cookies) {
    return {
        logout : function() {
            $cookies.token = '';
        }
    };
} ])

.factory('LoginService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        login : function(member, success) {
            var LoginResource = $resource($rootScope.apiUrl + '/api/login', {}, {
                'login' : {
                    method : 'POST',
                    isArray : false
                }
            });
            new LoginResource(member).$login(success);
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
        },
        edit : function(profile, success) {
            var profileResource = $resource($rootScope.apiUrl + '/api/profile', {}, {
                'login' : {
                    method : 'PUT',
                    isArray : false
                }
            });
            new profileResource(profile).$login(success);
        }
    };
} ])

.factory('ProductService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        home : function(success) {
            return $resource($rootScope.apiUrl + '/api/product', {}, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(success);
        },
        get : function(id, success) {
            return $resource($rootScope.apiUrl + '/api/product/:id', {
                'id' : id
            }, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(success);
        }
    };
} ]);
