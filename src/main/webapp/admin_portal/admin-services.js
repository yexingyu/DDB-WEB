angular.module('adminApp.services', [ 'ngResource' ])

.factory('AdminLoginService',
        [ '$rootScope', '$resource', '$location', function($rootScope, $resource, $location) {
            return {
                login : function(member, success) {
                    var LoginResource = $resource('/api/login', {}, {
                        'login' : {
                            method : 'POST',
                            isArray : false
                        }
                    });
                    new LoginResource(member).$login(success);
                },
                check : function() {
                    $resource($rootScope.apiUrl + '/api/login', {}, {
                        'query' : {
                            method : 'GET',
                            isArray : false
                        }
                    }).query(function(response) {
                        if (response.status != "SUCCESS" || response.data.role != "ADMIN") {
                            $location.path("/login");
                        }
                    });
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
