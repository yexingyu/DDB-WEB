angular.module('ddbApp.controllers', [ 'angular-md5' ])

.controller(
        'LoginCtrl',
        [ '$scope', '$location', 'LoginService', 'md5',
                function($scope, $location, LoginService, md5) {
                    $scope.login = function() {
                        $scope.result = "loggining in...";
                        $scope.member.password = md5.createHash($scope.member.passwd || '');
                        // alert(JSON.stringify($scope.member));
                        LoginService.login($scope.member, function(response) {
                            $scope.result = response.data;
                            if (response.status == "SUCCESS") {
                                $location.path("/profile");
                            }
                        });
                    };
                } ])

.controller(
        'HomeCtrl',
        [ '$scope', '$location', 'PayMonthlyService', 'MenuService',
                function($scope, $location, PayMonthlyService, MenuService) {
                    MenuService.go(0);
                    $scope.items = PayMonthlyService.home();
                } ])

.controller(
        'PMCtrl',
        [ '$scope', '$location', 'PayMonthlyService', 'MenuService',
                function($scope, $location, PayMonthlyService, MenuService) {
                    MenuService.go(1);
                } ])

.controller(
        'ProfileCtrl',
        [ '$scope', '$location', 'ProfileService', 'MenuService',
                function($scope, $location, ProfileService, MenuService) {
                    MenuService.go(2);
                    ProfileService.profile(function(response) {
                        // console.log(response);
                        if (response.status == 'SUCCESS') {
                            $scope.profile = response.data;
                        } else {
                            $location.path('/login');
                        }
                    });
                } ])

.controller(
        'AboutCtrl',
        [ '$scope', '$location', 'PayMonthlyService', 'MenuService',
                function($scope, $location, PayMonthlyService, MenuService) {
                    MenuService.go(3);
                } ]);
