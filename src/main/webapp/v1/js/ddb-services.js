angular.module('ddbApp.services', ['ngResource', 'ngCookies'])

    /*
     * CookieService
     */
    .factory('CookieService', ['$rootScope', '$cookies', function ($rootScope, $cookies) {
        return {
            logout: function () {
                $cookies.remove('token', {path: '/'});
            },
            setLanguage: function (lang) {
                $cookies.put('lang', lang.toUpperCase(), {path: '/'});
            },
            getLanguage: function () {
                var lang = $cookies.get('lang');
                if (lang == 'FR') {
                    lang = 'FR';
                } else {
                    lang = 'EN';
                }
                $cookies.put('lang', lang.toUpperCase(), {path: '/'});
                return lang;
            },
            setFingerprint: function (fingerprint) {
                $cookies.put('fingerprint', fingerprint, {path: '/'});
            }
        };
    }])

    /*
     * LoginService
     */
    .factory('LoginService', ['$rootScope', '$resource', '$modal', '$location', '$window', function ($rootScope, $resource, $modal, $location, $window) {
        return {
            login: function (member, callback) {
                return $resource('/api/credential', {'rememberMe': member.rememberMe}, {}).save(member, callback);
            },
            check: function (callback) {
                return $resource('/api/credential', {}, {}).get(callback);
            },
            register: function (member, callback) {
                return $resource('/api/credential/register', {}, {}).save(member, callback);
            },
            facebookLogin: function (accessToken, callback) {
                return $resource('/api/credential/facebook', {}, {}).save(accessToken, callback);
            },
            showLoginBox: function (success, dismiss) {
                return $modal.open({animation: true, templateUrl: 'tpl-login.html', controller: 'LoginCtrl', size: 'md'}).result.then(success, dismiss);
            }
        };
    }])

    /*
     * OrderService
     */
    .factory('OrderService', ['$rootScope', '$resource', function ($rootScope, $resource) {
        return {
            get: function (orderId, callback) {
                return $resource('/api/order/id/:id', {'id': orderId}, {}).get(callback);
            },
            list: function (callback, page, size, sort) {
                page = typeof page !== 'undefined' ? page : 0;
                size = typeof size !== 'undefined' ? size : 20;
                sort = typeof sort !== 'undefined' ? sort : "createdAt,desc";
                return $resource('/api/order/me', {'page': page, 'size': size, 'sort': sort}, {}).get(callback);
            },
            listAll: function (callback, page, size, sort) {
                page = typeof page !== 'undefined' ? page : 0;
                size = typeof size !== 'undefined' ? size : 20;
                sort = typeof sort !== 'undefined' ? sort : "createdAt,desc";
                return $resource('/api/order', {'page': page, 'size': size, 'sort': sort}, {}).get(callback);
            },
            add: function (order, callback) {
                return $resource('/api/order', {}, {}).save(order, callback);
            }
        };
    }])

    /*
     * ProfileService
     */
    .factory('ProfileService', ['$rootScope', '$resource', function ($rootScope, $resource) {
        return {
            profile: function (callback) {
                return $resource('/api/profile', {}, {}).get(callback);
            },
            edit: function (profile, callback) {
                return $resource('/api/profile', {}, {'update': {method: 'PUT'}}).update(prfile, callback);
            },
            sendEmailVerification: function (email, callback) {
                return $resource('/api/credential/verify_email', {'email': email.email}, {}).get(callback);
            },
            verifyEmail: function (hashCode, callback) {
                return $resource('/api/credential/verify_email', {}, {}).save(hashCode, callback);
            }
        };
    }])

    /*
     * StoreService
     */
    .factory('StoreService', ['$rootScope', '$resource', function ($rootScope, $resource) {
        return {
            list: function (callback) {
                return $resource('/api/store', {}, {}).get(callback);
            },
            listAll: function (callback) {
                return $resource('/api/store/all', {}, {}).get(callback);
            },
            get: function (storeId, callback) {
                return $resource('/api/store/id/:storeId', {'storeId': storeId}, {}).get(callback);
            },
            add: function (store, callback) {
                return $resource('/api/store', {}, {}).save(store, callback);
            },
            follow: function (storeId, callback) {
                return $resource('/api/store/id/:storeId/follow', {'storeId': storeId}, {}).save(callback);
            },
            unfollow: function (storeId, callback) {
                return $resource('/api/store/id/:storeId/follow', {'storeId': storeId}, {}).delete(callback);
            }
        };
    }])

    /*
     * ProductService
     */
    .factory('ProductService', ['$rootScope', '$resource', function ($rootScope, $resource) {
        return {
            list: function (callback, page, size, sort, filter) {
                page = typeof page !== 'undefined' ? page : 0;
                size = typeof size !== 'undefined' ? size : 20;
                sort = typeof sort !== 'undefined' ? sort : "createdAt,desc";
                filter = typeof filter !== 'undefined' ? filter : {};
                return $resource('/api/product', angular.extend({'page': page, 'size': size, 'sort': sort}, filter), {}).get(callback);
            },
            listFollowed: function (callback, page, size, sort) {
                page = typeof page !== 'undefined' ? page : 0;
                size = typeof size !== 'undefined' ? size : 20;
                sort = typeof sort !== 'undefined' ? sort : "createdAt,desc";
                return $resource('/api/product/followed', {'page': page, 'size': size, 'sort': sort}, {}).get(callback);
            },
            get: function (id, callback) {
                return $resource('/api/product/id/:id', {'id': id}, {}).get(callback);
            },
            edit: function (product, callback) {
                return $resource('/api/product/id/:id', {'id': product.id}, {'update': {method: 'PUT'}}).update(product, callback);
            },
            add: function (product, callback) {
                return $resource('/api/product', {}, {}).save(product, callback);
            },
            like: function (productId, callback) {
                return $resource('/api/product/id/:id/like', {'id': productId}, {}).save(callback);
            },
            review: function (review, callback) {
                return $resource('/api/product/id/:id/review', {'id': review.productId}, {}).save(review, callback);
            },
            reviews: function (productId, page, size, callback) {
                return $resource('/api/product/id/:id/review', {'id': productId, 'page': page, 'size': size}, {}).get(callback);
            },
            listAllTags: function (callback) {
                return $resource('/api/product/tags', {}, {}).get(callback);
            },
            spider: function (url, callback) {
                return $resource('/api/spider', {'url': url}, {}).get(callback);
            }
        };
    }]);
