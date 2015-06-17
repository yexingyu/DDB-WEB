angular.module('ddbApp.controllers', [ 'angular-md5' ])

/*
 * BannerCtrl definition
 */
.controller(
        'BannerCtrl',
        [ '$scope', '$location', 'ProfileService', 'CookieService',
                function($scope, $location, ProfileService, CookieService) {
                    $scope.menuCss = $scope.$root.menuCss;
                    ProfileService.profile(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.$root.profile = response.data;
                            $scope.$root.profile.sw = "membership";
                        }
                    });

                    $scope.logout = function() {
                        CookieService.logout();
                        $scope.$root.profile = {
                            sw : ""
                        };
                        $location.path('/home');
                    };
                } ])
/*
 * LoginCtrl definition
 */
.controller(
        'LoginCtrl',
        [ '$scope', '$location', 'LoginService', 'md5',
                function($scope, $location, LoginService, md5) {
                    $scope.login = function() {
                        $scope.result = "loggining in...";
                        $scope.member.password = md5.createHash($scope.member.passwd || '');
                        LoginService.login($scope.member, function(response) {
                            if (response.status == "SUCCESS") {
                                $scope.result = 'Welcome ' + response.data.firstName;
                                $scope.$root.profile = response.data;
                                $scope.$root.profile.sw = 'membership';
                                $location.path("/profile");
                            } else {
                                $scope.result = 'Fail to login!';
                            }
                        });
                    };
                } ])

/*
 * HomeCtrl definition
 */
.controller(
        'HomeCtrl',
        [ '$scope', '$location', 'ProductService', 'MenuService',
                function($scope, $location, ProductService, MenuService) {
                    MenuService.setCurrent(0);
                    ProductService.home(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.items = response.data;
                        }
                    });
                } ])
/*
 * PMCtrl definition
 */
.controller(
        'PMCtrl',
        [ '$scope', '$location', 'ProductService', 'MenuService',
                function($scope, $location, ProductService, MenuService) {
                    MenuService.setCurrent(1);
                    ProductService.home(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.items = response.data;
                        }
                    });
                } ])

/*
 * PMProductCtrl definition
 */
.controller(
        'PMProductCtrl',
        [ '$scope', '$location', '$routeParams', 'ProductService', 'MenuService',
                function($scope, $location, $routeParams, ProductService, MenuService) {
                    MenuService.setCurrent(1);
                    var id = $routeParams.id;
                    ProductService.get(id, function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.product = response.data;
                        }
                    });
                } ])

/*
 * PMOrderCtrl definition
 */
.controller(
        'PMOrderCtrl',
        [ '$scope', '$location', '$routeParams', 'ProductService', 'MenuService',
                function($scope, $location, $routeParams, ProductService, MenuService) {
                    MenuService.setCurrent(1);
                    var id = $routeParams.id;
                    ProductService.get(id, function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.product = response.data;
                        }
                    });
                } ])

/*
 * ProfileCtrl definition
 */
.controller(
        'ProfileCtrl',
        [ '$scope', '$location', 'ProfileService', 'MenuService',
                function($scope, $location, ProfileService, MenuService) {
                    MenuService.setCurrent(2);
                    ProfileService.profile(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.profile = response.data;
                        } else {
                            $location.path('/login');
                        }
                    });
                } ])

/*
 * ProfileEditCtrl definition
 */
.controller(
        'ProfileEditCtrl',
        [ '$scope', '$location', 'ProfileService', 'MenuService',
                function($scope, $location, ProfileService, MenuService) {
                    MenuService.setCurrent(2);
                    ProfileService.profile(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.profile = response.data;
                        } else {
                            $location.path('/login');
                        }
                    });
                    $scope.submit = function() {
                        console.log($scope.profile);
                        ProfileService.edit($scope.profile, function(response) {
                            console.log(response);
                        });
                    };
                } ])

/*
 * AboutCtrl definition
 */
.controller(
        'AboutCtrl',
        [ '$scope', '$location', 'PayMonthlyService', 'MenuService',
                function($scope, $location, PayMonthlyService, MenuService) {
                    MenuService.setCurrent(3);
                } ]);
