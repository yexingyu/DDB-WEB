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
        } ]);
