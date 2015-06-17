angular.module('adminApp.controllers', [ 'angular-md5' ])

.controller(
        'AdminHomeCtrl',
        [ '$scope', '$location', 'AdminLoginService',
                function($scope, $location, AdminLoginService) {
                    AdminLoginService.check();
                } ])

.controller(
        'AdminLoginCtrl',
        [ '$scope', '$location', 'AdminLoginService', 'md5',
                function($scope, $location, AdminLoginService, md5) {
                    $scope.login = function() {
                        $scope.member.password = md5.createHash($scope.member.pwd || '');
                        AdminLoginService.login($scope.member, function(response) {
                            console.log(response);
                            if (response.status == "SUCCESS" && response.data.role == "ADMIN") {
                                $location.path("/home");
                            }
                        });
                    };
                } ]);
