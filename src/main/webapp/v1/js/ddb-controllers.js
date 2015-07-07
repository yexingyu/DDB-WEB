angular.module('ddbApp.controllers', [ 'angular-md5' ])

/*
 * NavBarCtrl definition
 */
.controller(
        'NavBarCtrl',
        [ '$scope', '$location', '$modal', 'ProfileService', 'CookieService',
                function($scope, $location, $modal, ProfileService, CookieService) {

                    // retreive profile
                    $scope.$root.profile = {};
                    ProfileService.profile(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.$root.profile = response.data;
                            console.log($scope.$root.profile);
                        }
                    });

                    // display login modal
                    $scope.login = function() {
                        var modalInstance = $modal.open({
                            animation : $scope.animationsEnabled,
                            templateUrl : 'tpl-login.html',
                            controller : 'LoginCtrl',
                            size : 'sm',
                            resolve : {
                                items : function() {
                                    return $scope.items;
                                }
                            }
                        });
                    };

                    // logout
                    $scope.logout = function() {
                        CookieService.logout();
                        $scope.$root.profile = {};
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
        [ '$scope', '$location', 'LoginService', 'md5', '$modalInstance',
                function($scope, $location, LoginService, md5, $modalInstance) {
                    $scope.login = function() {
                        $scope.isFail = false;
                        $scope.member.password = md5.createHash($scope.member.passwd || '');
                        LoginService.login($scope.member, function(response) {
                            if (response.status == "SUCCESS") {
                                $scope.$root.profile = response.data;
                                $modalInstance.close();
                                $location.path("/profile");
                                $scope.isFail = false;
                            } else {
                                $scope.isFail = true;
                            }
                        });
                    };
                } ])

/*
 * ProductCtrl definition
 */
.controller('ProductCtrl',
        [ '$scope', '$location', 'ProductService', function($scope, $location, ProductService) {
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
        [ '$scope', '$location', '$routeParams', 'ProductService',
                function($scope, $location, $routeParams, ProductService) {
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
        [ '$scope', '$location', '$routeParams', 'ProductService',
                function($scope, $location, $routeParams, ProductService) {
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
.controller('ProfileCtrl',
        [ '$scope', '$location', 'ProfileService', function($scope, $location, ProfileService) {
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
.controller('ProfileEditCtrl',
        [ '$scope', '$location', 'ProfileService', function($scope, $location, ProfileService) {
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
                    } else if (response.status == 'EMPTY_RESULT') {
                        $scope.page--;
                    }
                }, $scope.page, $scope.size);
            };
        } ])

/*
 * ContactCtrl definition
 */
.controller('ContactCtrl', [ '$scope', '$location', function($scope, $location) {

} ])

/*
 * AboutCtrl definition
 */
.controller('AboutCtrl', [ '$scope', '$location', function($scope, $location) {

} ]);
