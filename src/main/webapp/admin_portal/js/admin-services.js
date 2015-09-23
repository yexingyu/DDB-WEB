angular.module('adminApp.services', ['ngResource'])

    .factory('AdminLoginService',
    ['$rootScope', '$resource', '$location', function ($rootScope, $resource, $location) {
        return {};
    }])
    /*
     * ProfileService
     */
    .
    factory('ProfileService', ['$rootScope', '$resource', function ($rootScope, $resource) {
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

;
