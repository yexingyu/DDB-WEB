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
.controller('LoginCtrl', [ '$scope', '$location', 'LoginService', 'md5', '$modalInstance', function($scope, $location, LoginService, md5, $modalInstance) {
    $scope.login = function() {
        $scope.isFail = false;
        $scope.member.password = md5.createHash($scope.member.passwd || '');
        delete $scope.member.passwd;
        LoginService.login($scope.member, function(response) {
            if (response.status == "SUCCESS") {
                $scope.$root.profile = response.data;
                $modalInstance.close(response.data);
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
.controller('ProductCtrl', [ '$scope', '$location', 'ProductService', function($scope, $location, ProductService) {
    $scope.page = 0;
    $scope.size = 9;
    ProductService.list(function(response) {
        if (response.status == 'SUCCESS') {
            $scope.items = response.data.content;
        }
    }, $scope.page, $scope.size);
} ])

/*
 * ProductDetailsCtrl definition
 */
.controller('ProductDetailsCtrl', [ '$scope', '$location', '$routeParams', 'ProductService', function($scope, $location, $routeParams, ProductService) {
    var id = $routeParams.id;

    // retrieve product details
    ProductService.get(id, function(response) {
        if (response.status == 'SUCCESS') {
            $scope.product = response.data;
        }
    });

    // retrieve product reviews
    $scope.reviews = {
        page : 0,
        size : 20
    };
    ProductService.reviews(id, $scope.reviews.page, $scope.reviews.size, function(response) {
        if (response.status == 'SUCCESS') {
            $scope.reviews = response.data;
        }
    });
} ])

/*
 * ProductOrderCtrl definition
 */
.controller(
        'ProductOrderCtrl',
        [ '$scope', '$location', '$window', '$routeParams', 'ProductService', 'ProfileService', 'OrderService', 'LoginService',
                function($scope, $location, $window, $routeParams, ProductService, ProfileService, OrderService, LoginService) {
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
                            OrderService.fix($scope.order, $scope.profile);
                        } else if (response.status == 'NEED_LOGIN') {
                            LoginService.showLoginBox(function(profile) {
                                $scope.$root.profile = profile;
                                $scope.profile = profile;
                                OrderService.fix($scope.order, $scope.profile);
                            }, function(reason) {
                                $window.history.back();
                            });
                        } else {
                            $location.path('/home');
                            return;
                        }

                    });

                    // submit order
                    $scope.submit = function() {
                        console.log($scope.order);
                        OrderService.add($scope.order, function(response) {
                            if (response.status === 'SUCCESS') {
                                $scope.order = response.data;
                                $location.path('/order/' + $scope.order.id + '/confirm');
                            } else {
                                $location.path('/home');
                                return;
                            }
                        });
                    };
                } ])

/*
 * OrderConfirmCtrl definition
 */
.controller(
        'OrderConfirmCtrl',
        [ '$scope', '$location', '$window', '$routeParams', 'ProductService', 'OrderService', 'ProfileService', 'LoginService',
                function($scope, $location, $window, $routeParams, ProductService, ProfileService, OrderService, LoginService) {
                    var orderId = $routeParams.id;
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
                            OrderService.fix($scope.order, $scope.profile);
                        } else if (response.status == 'NEED_LOGIN') {
                            LoginService.showLoginBox(function(profile) {
                                $scope.$root.profile = profile;
                                $scope.profile = profile;
                                OrderService.fix($scope.order, $scope.profile);
                            }, function(reason) {
                                $window.history.back();
                            });
                        } else {
                            $location.path('/home');
                            return;
                        }

                    });

                    // submit order
                    $scope.submit = function() {

                    };
                } ])

/*
 * ProfileCtrl definition
 */
.controller('ProfileCtrl', [ '$scope', '$location', 'ProfileService', 'LoginService', function($scope, $location, ProfileService, LoginService) {
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
.controller('ProfileEditCtrl', [ '$scope', '$location', 'ProfileService', 'LoginService', function($scope, $location, ProfileService) {
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
.controller('HomeCtrl', [ '$scope', '$location', 'ProductService', function($scope, $location, ProductService) {
    // init product list
    $scope.page = 0;
    $scope.size = 9;
    ProductService.list(function(response) {
        if (response.status == 'SUCCESS') {
            $scope.items = response.data.content;
            angular.forEach($scope.items, function(item) {
                item.review = {
                    productId : item.id,
                    content : '',
                    rating : 3,
                    overStar : 3,
                    showMsg : 'None',
                    msg : ''
                };
            });
        }
    }, $scope.page, $scope.size);

    // load more product
    $scope.loadMore = function() {
        $scope.page++;
        ProductService.list(function(response) {
            if (response.status == 'SUCCESS') {
                response.data.content.forEach(function(item) {
                    item.review = {
                        productId : item.id,
                        content : '',
                        rating : 3,
                        overStar : 3,
                        showMsg : 'None',
                        msg : ''
                    };
                    $scope.items.push(item);
                });
            } else if (response.status == 'EMPTY_RESULT') {
                $scope.page--;
            }
        }, $scope.page, $scope.size);
    };

    // product like
    $scope.like = function(product) {
        ProductService.like(product.id, function(response) {
            if (response.status === 'SUCCESS' && response.data === 'Success') {
                product.countLikes++;
            }
        });
    };

    // public review
    $scope.reviewPopover = {
        templateUrl : 'tpl-product-review.html'
    };
    $scope.hoveringOver = function(value, item) {
        item.review.overStar = value;
        console.log(item.review);
    };
    $scope.review = function(product) {
        ProductService.review(product.review, function(response) {
            if (response.status === 'SUCCESS' && response.data === 'Success') {
                product.countReviews++;
                product.review.showMsg = 'message';
                product.review.msg = 'Review success';
            } else {
                product.review.showMsg = 'error';
                product.review.msg = response.data;
            }
        });
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
