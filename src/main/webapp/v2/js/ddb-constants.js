angular.module('ddbApp.constants', [ 'ngResource' ])

.run([ '$rootScope', '$resource', function($rootScope, $resource) {
    $rootScope.constantMapper = {};
    $resource('/api/constant', {}, {
        'query' : {
            method : 'GET',
            isArray : false
        }
    }).query(function(response) {
        if (response.status == 'SUCCESS') {
            $rootScope.constant = response.data;
            console.log($rootScope.constant);
        }
    });

    $resource('/data/language.json', {}, {
        'query' : {
            method : 'GET',
            isArray : false
        }
    }).query(function(response) {
        $rootScope.constantMapper.LANGUAGE = response;
    });

    $resource('/data/country.json', {}, {
        'query' : {
            method : 'GET',
            isArray : false
        }
    }).query(function(response) {
        $rootScope.constantMapper.COUNTRY = response;
    });

} ])

.factory('ConstantService', [ '$rootScope', function($rootScope) {
    return {
        init : function() {
            // var constant = $rootScope.constant;
            // console.log($rootScope.$root.constant);
            // $rootScope.constant.forEach(function(key, value, map) {
            // console.log(key);
            // console.log(value);
            // });
        }
    };
} ]);
