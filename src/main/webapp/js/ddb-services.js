angular.module('ddbApp.services', [ 'ngResource', 'ngCookies' ])

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

.factory('CookieService', [ '$rootScope', '$cookies', function($rootScope, $cookies) {
    return {
        logout : function() {
            $cookies.remove('token');
        },
        setLanguage : function(lang) {
            $cookies.put('lang', lang.toUpperCase());
        },
        getLanguage : function() {
            var lang = $cookies.get('lang');
            if (lang == 'FR') {
                lang = 'FR';
            } else {
                lang = 'EN';
            }
            $cookies.put('lang', lang.toUpperCase());
            return lang;
        }
    };
} ])

.factory('LoginService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        login : function(member, success) {
            var LoginResource = $resource('/api/login', {}, {
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
            return $resource('/api/profile', {}, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(success);
        },
        edit : function(profile, success) {
            var profileResource = $resource('/api/profile', {}, {
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
        list : function(success, page, size) {
            page = typeof page !== 'undefined' ? page : 0;
            size = typeof size !== 'undefined' ? size : 20;
            return $resource('/api/product', {
                'page' : page,
                'size' : size
            }, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(success);
        },
        get : function(id, success) {
            return $resource('/api/product/:id', {
                'id' : id
            }, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(success);
        },
        edit : function(product, success) {
            var productResource = $resource($rootScope.apiUrl + '/api/product', {}, {
                'login' : {
                    method : 'PUT',
                    isArray : false
                }
            });
            new productResource(product).$login(success);
        },
        add : function(product, success) {
            var productResource = $resource($rootScope.apiUrl + '/api/product', {}, {
                'login' : {
                    method : 'POST',
                    isArray : false
                }
            });
            new productResource(product).$login(success);
        }

    };
} ]);
