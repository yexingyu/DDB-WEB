angular.module('ddbApp.constants', ['ngResource'])

    .run(['$rootScope', '$resource', function ($rootScope, $resource) {
        $rootScope.constant = {};
    }])

    .factory('ConstantService', ['$rootScope', '$resource', function ($rootScope, $resource) {
        return {
            init: function () {
                $resource('/api/constant', {}, {
                    'query': {
                        method: 'GET',
                        isArray: false
                    }
                }).query(function (response) {
                    if (response.status == 'SUCCESS') {
                        $rootScope.constant = response.data;
                    }
                });
            }
        };
    }]);
