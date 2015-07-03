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

                    // logout
                    $scope.logout = function() {
                        CookieService.logout();
                        $scope.$root.profile = {
                            sw : ""
                        };
                        $location.path('/home');
                    };

                    // switch language
                    $scope.language = $scope.$root.language;
                    $scope.switchLanguage = function() {
                        if ($scope.$root.language.language == 'FR') {
                            $scope.$root.language.language = 'EN';
                        } else {
                            $scope.$root.language.language = 'FR'
                        }
                        CookieService.setLanguage($scope.$root.language.language);
                        console.log($scope.$root.language);
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
 * ProductCtrl definition
 */
.controller(
        'ProductCtrl',
        [ '$scope', '$location', 'ProductService', 'MenuService',
                function($scope, $location, ProductService, MenuService) {
                    MenuService.setCurrent(1);
                    ProductService.list(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.items = response.data;
                        }
                    }, 0, 2);
                } ])

/*
 * ProductDetailsCtrl definition
 */
.controller(
        'ProductDetailsCtrl',
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
 * OrderCtrl definition
 */
.controller(
        'OrderCtrl',
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
                            if (response.status == 'SUCCESS') {
                                $scope.profile = response.data;
                            } else {
                                $location.path('/login');
                            }
                        });
                    };
                } ])

/*
 * HomeCtrl definition
 */
.controller('HomeCtrl',
        [ '$scope', '$location', 'ProductService', function($scope, $location, ProductService) {
            // init product list
            $scope.page = 0;
            $scope.size = 3;
            ProductService.list(function(response) {
                if (response.status == 'SUCCESS') {
                    $scope.items = response.data;
                }
            }, $scope.page, $scope.size);

            // load more product
            $scope.loadMore = function() {
                $scope.page++;
                console.log("page=" + $scope.page);
                ProductService.list(function(response) {
                    if (response.status == 'SUCCESS') {
                        response.data.forEach(function(item) {
                            $scope.items.push(item);
                        });
                    }
                }, $scope.page, $scope.size);
            };
        } ])

/*
 * ContactCtrl definition
 */
.controller('ContactCtrl',
        [ '$scope', '$location', 'MenuService', function($scope, $location, MenuService) {
            // MenuService.setCurrent(3);
        } ])

/*
 * AboutCtrl definition
 */
.controller('AboutCtrl',
        [ '$scope', '$location', 'MenuService', function($scope, $location, MenuService) {
            // MenuService.setCurrent(3);
        } ]);
