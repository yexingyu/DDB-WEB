angular.module('ddbApp.services', [ 'ngResource' ])

.factory('LoginService', [ '$resource', '$location', function($resource) {
    return {
        login : function(account, success) {
            var LoginResource = $resource('/api/login', {}, {
                'login' : {
                    method : 'POST',
                    isArray : false
                }
            });
            new LoginResource(account).$login(success);
        }
    };
} ])

.factory('AccountsService', [ '$resource', function($resource) {
    return {
        all : function() {
            return $resource('/api/accounts', {}, {
                query : {
                    method : 'GET',
                    isArray : true
                }
            }).query();
        },
        save : function(account) {
            var AccountResource = $resource('/api/accounts', {}, {
                'save' : {
                    method : 'POST',
                }
            });
            new AccountResource(account).$save();
        },
        get : function(id) {
            return $resource('/api/accounts/:id', {
                'id' : id
            }, {
                query : {
                    method : 'GET',
                    isArray : false
                }
            }).query();
        }
    };
} ])

.factory('PayMonthlyService', [ '$resource', function($resource) {
    return {
        index : function() {
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
