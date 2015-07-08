angular.module('ddbApp.controllers', [ 'angular-md5' ])

/*
 * NavBarCtrl definition
 */
.controller(
        'NavBarCtrl',
        [ '$scope', '$location', '$modal', 'ProfileService', 'CookieService', 'LoginService',
                function($scope, $location, $modal, ProfileService, CookieService, LoginService) {

                    // retreive profile
                    $scope.$root.profile = {};
                    ProfileService.profile(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.$root.profile = response.data;
                        }
                    });

                    // display login modal
                    $scope.login = function() {
                        LoginService.showLoginBox();
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
        [
                '$scope',
                '$location',
                '$routeParams',
                'ProductService',
                'ProfileService',
                'LoginService',
                function($scope, $location, $routeParams, ProductService, ProfileService,
                        LoginService) {
                    var id = $routeParams.id;
                    $scope.order = {};

                    // retrieve product details
                    ProductService.get(id, function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.product = ProductService.fix(response.data);
                            $scope.order.productId = $scope.product.id;
                        } else {
                            $location.path('/home');
                        }
                    });

                    // retrieve profile information
                    ProfileService.profile(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.$root.profile = response.data;
                            $scope.profile = response.data;
                            $scope.order.memberId = $scope.profile.id;

                            // set addresses for order
                            var haveBillingAddress = false;
                            var haveShippingAddress = false;
                            if (angular.isArray($scope.profile.addresses)
                                    && $scope.profile.addresses.length > 0) {
                                $scope.order.addresses = [];
                                // set billing address
                                for (var i = 0; i < $scope.profile.addresses.length; i++) {
                                    if ($scope.profile.addresses[i].type === 'BILLING') {
                                        var obj = {};
                                        angular.copy($scope.profile.addresses[i], obj);
                                        obj.id = 0;
                                        $scope.order.addresses.push(obj);
                                        haveBillingAddress = true;
                                        break;
                                    }
                                }
                                if (haveBillingAddress === false) {
                                    var obj = {};
                                    angular.copy($scope.profile.addresses[0], obj);
                                    obj.id = 0;
                                    obj.type = 'BILLING';
                                    $scope.order.addresses.push(obj);
                                }

                                // set shipping address
                                for (var i = 0; i < $scope.profile.addresses.length; i++) {
                                    if ($scope.profile.addresses[i].type === 'SHIPPING') {
                                        var obj = {};
                                        angular.copy($scope.profile.addresses[i], obj);
                                        obj.id = 0;
                                        $scope.order.addresses.push(obj);
                                        haveShippingAddress = true;
                                        break;
                                    }
                                }
                                if (haveShippingAddress === false) {
                                    var obj = {};
                                    angular.copy($scope.profile.addresses[0], obj);
                                    obj.id = 0;
                                    obj.type = 'SHIPPING';
                                    $scope.order.addresses.push(obj);
                                }
                            } else {
                                $scope.order.addresses.push({
                                    type : 'BILLING'
                                });
                                $scope.order.addresses.push({
                                    type : 'SHIPPING'
                                });
                            }
                        } else if (response.status == 'NEED_LOGIN') {
                            LoginService.showLoginBox();
                        } else {
                            $location.path('/home');
                        }
                    });
                } ])

/*
 * ProfileCtrl definition
 */
.controller(
        'ProfileCtrl',
        [ '$scope', '$location', 'ProfileService', 'LoginService',
                function($scope, $location, ProfileService, LoginService) {
                    ProfileService.profile(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.$root.profile = response.data;
                            $scope.profile = response.data;
                        } else {
                            LoginService.showLoginBox();
                        }
                    });
                } ])

/*
 * ProfileEditCtrl definition
 */
.controller(
        'ProfileEditCtrl',
        [ '$scope', '$location', 'ProfileService', 'LoginService',
                function($scope, $location, ProfileService) {
                    // retrieve profile information
                    ProfileService.profile(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.$root.profile = response.data;
                            $scope.profile = response.data;
                        } else {
                            LoginService.showLoginBox();
                        }
                    });

                    // submit profile modification
                    $scope.submit = function() {
                        ProfileService.edit($scope.profile, function(response) {
                            if (response.status == 'SUCCESS') {
                                $scope.$root.profile = response.data;
                            } else {
                                $location.path('/profile/edit');
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
