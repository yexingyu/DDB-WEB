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
.factory('LoginService',
        [ '$rootScope', '$resource', '$modal', function($rootScope, $resource, $modal) {
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
                },
                showLoginBox : function() {
                    $modal.open({
                        animation : true,
                        templateUrl : 'tpl-login.html',
                        controller : 'LoginCtrl',
                        size : 'sm'
                    });
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
        },
        add : function(store, callback) {
            var storeResource = $resource('/api/store', {}, {
                'login' : {
                    method : 'POST',
                    isArray : false
                }
            });
            new storeResource(store).$login(callback);
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
            var productResource = $resource('/api/product', {}, {
                'login' : {
                    method : 'PUT',
                    isArray : false
                }
            });
            new productResource(product).$login(callback);
        },
        add : function(product, callback) {
            var productResource = $resource('/api/product', {}, {
                'login' : {
                    method : 'POST',
                    isArray : false
                }
            });
            new productResource(product).$login(callback);
        },
        fix : function(product) {
            // fix names
            var names = product.names;
            product.names = {};
            if (angular.isArray(names) && names.length > 0) {
                product.names = {};
                names.forEach(function(item) {
                    product.names[item.language] = item;
                });
                angular.forEach($rootScope.constant.LANGUAGE, function(key, value) {
                    if (!product.names[value]) {
                        product.names[value] = names[0];
                    }
                });
            }
            names = [];

            // fix description
            var descriptions = product.descriptions;
            product.descriptions = {};
            if (angular.isArray(descriptions) && descriptions.length > 0) {
                descriptions.forEach(function(item) {
                    product.descriptions[item.language] = item;
                });
                angular.forEach($rootScope.constant.LANGUAGE, function(key, value) {
                    if (!product.descriptions[value]) {
                        product.descriptions[value] = descriptions[0];
                    }
                });
            }
            descriptions = [];

            return product;
        }

    };
} ]);
