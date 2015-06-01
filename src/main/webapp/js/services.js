angular.module('ddbApp.services', [ 'ngResource' ])

.factory('Accounts', [ '$resource', function($resource) {
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

.factory('PayMonthly', [ '$resource', function($resource) {
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
