angular.module('ddbApp.controllers', [ 'angular-md5' ])

/*
 * NavBarCtrl definition
 */
.controller('NavBarCtrl', [ '$scope', '$location', '$modal', 'ProfileService', 'CookieService', 'LoginService', function($scope, $location, $modal, ProfileService, CookieService, LoginService) {

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
            $scope.$root.language.language = 'FR';
        }
        CookieService.setLanguage($scope.$root.language.language);
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
.controller('ProductCtrl', [ '$scope', '$location', 'ProductService', 'ActionService', function($scope, $location, ProductService, ActionService) {
    $scope.actions = ActionService;
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
.controller('ProductDetailsCtrl', [ '$scope', '$location', '$routeParams', 'ProductService', 'ActionService', function($scope, $location, $routeParams, ProductService, ActionService) {
    var id = $routeParams.id;
    $scope.item = {};
    $scope.actions = ActionService;

    // retrieve product details
    ProductService.get(id, function(response) {
        if (response.status == 'SUCCESS') {
            angular.extend($scope.item, response.data);
        }
    });

    // retrieve product reviews
    $scope.item.review = {
        productId : id,
        content : '',
        rating : 3,
        overStar : 3,
        showMsg : 'None',
        msg : ''
    };
    $scope.item.reviews = {
        page : 0,
        size : 20
    };
    ProductService.reviews(id, $scope.item.reviews.page, $scope.item.reviews.size, function(response) {
        if (response.status == 'SUCCESS') {
            $scope.item.reviews = response.data;
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
.controller('HomeCtrl', [ '$scope', '$location', 'ProductService', 'ActionService', function($scope, $location, ProductService, ActionService) {
    $scope.actions = ActionService;

    // init product list
    $scope.page = 0;
    $scope.size = 9;
    ProductService.list(function(response) {
        if (response.status == 'SUCCESS') {
            $scope.items = response.data.content;

            $scope.getTotal = function() {
                $scope.product.total = parseFloat($scope.product.prices[0].value);
                // add fees
                total_fee = 0;
                for (var i = 0; i < $scope.product.fees.length; i++) {
                    if ($scope.product.fees[i].type == "AMOUNT") {
                        total_fee = total_fee + parseFloat($scope.product.fees[i].value);
                    }
                    if ($scope.product.fees[i].type == "PERCENTAGE") {
                        total_fee = total_fee + $scope.product.prices[0].value * parseFloat($scope.product.fees[i].value / 100);
                    }
                }
                total = total_fee + $scope.product.total;

                return total;
            };

            angular.forEach($scope.items, function(item) {
                // item financial info
                item.total = parseFloat(item.prices[0].value);
                // get toal, fees
                total_fee = 0;
                for (var i = 0; i < item.fees.length; i++) {
                    if (item.fees[i].type == "AMOUNT") {
                        total_fee = total_fee + parseFloat(item.fees[i].value);
                    }
                    if (item.fees[i].type == "PERCENTAGE") {
                        total_fee = total_fee + item.prices[0].value * parseFloat(item.fees[i].value / 100);
                    }
                }

                item.total = total_fee + item.total;

                if (item.prices[0].currency == "CAD") {
                    item.exchange_rate = 1;
                }
                if (item.prices[0].currency == "USD") {
                    item.exchange_rate = 1.30;
                }

                item.total = item.total * item.exchange_rate;

                //
                $scope.yearly_interest_rate = 0.24;
                $scope.number_of_payments = 12;
                $scope.monthly_interest_rate = $scope.yearly_interest_rate / $scope.number_of_payments;

                item.principal = item.total * 0.618;
                item.down = item.total * 0.382;

                item.MonthlyPayment = $scope.monthly_interest_rate * item.principal / (1 - Math.pow((1 + $scope.monthly_interest_rate), -$scope.number_of_payments));
                item.MonthlyPayment = Math.round(item.MonthlyPayment * 100) / 100;

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
