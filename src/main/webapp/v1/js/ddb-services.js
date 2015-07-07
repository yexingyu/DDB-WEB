angular.module('ddbApp.services', [ 'ngResource', 'ngCookies' ])

/*
 * CookieService
 */
.factory('CookieService', [ '$rootScope', '$cookies', function($rootScope, $cookies) {
    return {
        logout : function() {
            $cookies.remove('token', {
                path : '/'
            });
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
            $cookies.put('lang', lang.toUpperCase(), {
                path : '/'
            });
            return lang;
        }
    };
} ])

/*
 * LoginService
 */
.factory('LoginService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        login : function(member, callback) {
            var LoginResource = $resource('/api/login', {
                'rememberMe' : member.rememberMe
            }, {
                'login' : {
                    method : 'POST',
                    isArray : false
                }
            });
            new LoginResource(member).$login(callback);
        }
    };
} ])

/*
 * ProfileService
 */
.factory('ProfileService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        profile : function(callback) {
            return $resource('/api/profile', {}, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(callback);
        },
        edit : function(profile, callback) {
            var profileResource = $resource('/api/profile', {}, {
                'login' : {
                    method : 'PUT',
                    isArray : false
                }
            });
            new profileResource(profile).$login(callback);
        }
    };
} ])

/*
 * StoreService
 */
.factory('StoreService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        list : function(callback) {
            return $resource('/api/store', {}, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(callback);
        },
        get : function(storeId, callback) {
            return $resource('/api/store/:storeId', {
                'storeId' : storeId
            }, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(callback);
        }
    };
} ])

/*
 * ProductService
 */
.factory('ProductService', [ '$rootScope', '$resource', function($rootScope, $resource) {
    return {
        list : function(callback, page, size) {
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
            }).query(callback);
        },
        get : function(id, callback) {
            return $resource('/api/product/:id', {
                'id' : id
            }, {
                'query' : {
                    method : 'GET',
                    isArray : false
                }
            }).query(callback);
        },
        edit : function(product, callback) {
            var productResource = $resource($rootScope.apiUrl + '/api/product', {}, {
                'login' : {
                    method : 'PUT',
                    isArray : false
                }
            });
            new productResource(product).$login(callback);
        },
        add : function(product, callback) {
            var productResource = $resource($rootScope.apiUrl + '/api/product', {}, {
                'login' : {
                    method : 'POST',
                    isArray : false
                }
            });
            new productResource(product).$login(callback);
        }

    };
} ]);
