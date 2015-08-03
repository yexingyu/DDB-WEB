angular.module('ddbApp.controllers', ['angular-md5'])

    /*
     * NavBarCtrl definition
     */
    .controller('NavBarCtrl', ['$scope', '$location', '$modal', 'ProfileService', 'CookieService', 'LoginService', function ($scope, $location, $modal, ProfileService, CookieService, LoginService) {

        // retreive profile
        $scope.$root.profile = {};
        ProfileService.profile(function (response) {
            if (response.status == 'SUCCESS') {
                $scope.$root.profile = response.data;
            }
        });

        // display login modal
        $scope.login = function () {
            LoginService.showLoginBox();
        };

        // logout
        $scope.logout = function () {
            CookieService.logout();
            $scope.$root.profile = {};
            $location.path('/home');
        };

        // switch language
        $scope.language = $scope.$root.language;
        $scope.switchLanguage = function () {
            if ($scope.$root.language.language == 'FR') {
                $scope.$root.language.language = 'EN';
            } else {
                $scope.$root.language.language = 'FR';
            }
            CookieService.setLanguage($scope.$root.language.language);
        };
    }])
    /*
     * LoginCtrl definition
     */
    .controller('LoginCtrl', ['$scope', '$location', 'LoginService', 'md5', '$modalInstance', function ($scope, $location, LoginService, md5, $modalInstance) {
        // member login
        $scope.loginAccount = {};
        $scope.isLoginFail = false;
        $scope.login = function () {
            $scope.isLoginFail = false;
            $scope.loginAccount.password = md5.createHash($scope.loginAccount.passwd || '');
            delete $scope.loginAccount.passwd;
            LoginService.login($scope.loginAccount, function (response) {
                if (response.status == "SUCCESS") {
                    $scope.$root.profile = response.data;
                    $modalInstance.close(response.data);
                    $scope.isLoginFail = false;
                } else {
                    $scope.isLoginFail = true;
                }
            });
        };

        // member register
        $scope.registerInfo = {};
        $scope.isRegisterFail = false;
        $scope.register = function () {
            $scope.isRegisterFail = false;
            $scope.registerInfo.password = md5.createHash($scope.registerInfo.firstPassword || '');
            delete $scope.registerInfo.firstPassword;
            delete $scope.registerInfo.rePassword;
            $scope.registerInfo.emails = [{email: $scope.registerInfo.account}];
            LoginService.register($scope.registerInfo, function (response) {
                if (response.status === "SUCCESS") {
                    // register success
                    console.log('register success');
                    $scope.isRegisterFail = false;
                } else {
                    // register fail
                    console.log('register fail');
                    $scope.isRegisterFail = true;
                }
            });
        };
    }])

    /*
     * ProductCtrl definition
     */
    .controller('ProductCtrl', ['$scope', '$location', 'ProductService', 'ActionService', function ($scope, $location, ProductService, ActionService) {
        $scope.actions = ActionService;
        $scope.page = 0;
        $scope.size = 9;
        ProductService.list(function (response) {
            if (response.status == 'SUCCESS') {
                $scope.items = response.data.content;
            }
        }, $scope.page, $scope.size);
    }])

    /*
     * ProductDetailsCtrl definition
     */
    .controller('ProductDetailsCtrl', ['$scope', '$location', '$routeParams', 'ProductService', 'ActionService', function ($scope, $location, $routeParams, ProductService, ActionService) {
        var id = $routeParams.id;
        $scope.item = {};
        $scope.actions = ActionService;

        // retrieve product details
        ProductService.get(id, function (response) {
            if (response.status == 'SUCCESS') {
                angular.extend($scope.item, response.data);
            }
        });

        // retrieve product reviews
        $scope.item.review = {
            productId: id,
            content: '',
            rating: 3,
            overStar: 3,
            showMsg: 'None',
            msg: ''
        };
        $scope.item.reviews = {
            page: 0,
            size: 20
        };
        ProductService.reviews(id, $scope.item.reviews.page, $scope.item.reviews.size, function (response) {
            if (response.status == 'SUCCESS') {
                $scope.item.reviews = response.data;
            }
        });
    }])

    /*
     * ProductOrderCtrl definition
     */
    .controller(
    'ProductOrderCtrl',
    ['$scope', '$location', '$window', '$modal', '$routeParams', 'ProductService', 'ProfileService', 'OrderService', 'LoginService',
        function ($scope, $location, $window, $modal, $routeParams, ProductService, ProfileService, OrderService, LoginService) {
            var id = $routeParams.id;
            $scope.order = {};

            // retrieve product details
            ProductService.get(id, function (response) {
                if (response.status === 'SUCCESS') {
                    $scope.product = ProductService.fix(response.data);
                    $scope.order.productId = $scope.product.id;
                } else {
                    $location.path('/home');
                }
            });

            // retrieve profile information
            ProfileService.profile(function (response) {
                if (response.status === 'SUCCESS') {
                    $scope.$root.profile = response.data;
                    $scope.profile = response.data;
                    OrderService.fix($scope.order, $scope.profile);
                } else if (response.status === 'NEED_LOGIN') {
                    LoginService.showLoginBox(function (profile) {
                        $scope.$root.profile = profile;
                        $scope.profile = profile;
                        OrderService.fix($scope.order, $scope.profile);
                    }, function (reason) {
                        $window.history.back();
                    });
                } else {
                    $location.path('/home');
                    return;
                }

            });

            // next button
            $scope.next = function () {
                $modal.open({
                    animation: true,
                    templateUrl: 'tpl-product-order-confirm.html',
                    controller: 'ProductOrderConfirmCtrl',
                    size: 'lg',
                    backdrop: true,
                    resolve: {
                        product: function () {
                            return $scope.product;
                        },
                        order: function () {
                            return $scope.order;
                        }
                    }
                }).result.then(function (profile) {
                        // on success
                        console.log('success');
                    }, function (reason) {
                        // on cancel
                        console.log('dismiss');
                    });
            };

        }])

    /*
     * ProductOrderConfirmCtrl
     */
    .controller('ProductOrderConfirmCtrl', ['$scope', '$location', '$modalInstance', 'product', 'order', 'ProductService', 'OrderService', function ($scope, $location, $modalInstance, product, order, ProductService, OrderService) {
        $scope.item = product;
        $scope.order = order;

        // cancel button
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        // submit button
        $scope.submit = function () {
            OrderService.add($scope.order, function (response) {
                if (response.status === 'SUCCESS') {
                    // submit success
                    $scope.order = response.data;
                    $location.path('/order/' + $scope.order.id + '/confirm');
                } else {
                    // fail
                    $location.path('/home');
                }
            });
        };
    }])

    /*
     * OrderDetailsCtrl definition
     */
    .controller('OrderDetailsCtrl', ['$scope', '$location', '$routeParams', 'OrderService', 'ProductService', 'LoginService', function ($scope, $location, $routeParams, OrderService, ProductService, LoginService) {
        var orderId = $routeParams.id;
        $scope.order = {};
        OrderService.get(orderId, function (response) {
            if (response.status === 'SUCCESS') {
                $scope.order = response.data;
                ProductService.get($scope.order.productId, function (response) {
                    if (response.status === 'SUCCESS') {
                        $scope.order.product = response.data;
                    }
                });
            } else {
                LoginService.showLoginBox();
            }
        });
    }])

    /*
     * OrderMeCtrl definition
     */
    .controller('OrderMeCtrl', ['$scope', '$location', 'OrderService', 'ProductService', 'LoginService', function ($scope, $location, OrderService, ProductService, LoginService) {
        $scope.orders = {};
        $scope.page = 0;
        $scope.size = 9;
        $scope.sort = 'createdAt,desc';
        OrderService.list(function (response) {
            if (response.status === 'SUCCESS') {
                $scope.orders = response.data;
                angular.forEach($scope.orders.content, function (item) {
                    item.product = {};
                    console.log(item);
                    ProductService.get(item.productId, function (response) {
                        if (response.status === 'SUCCESS') {
                            item.product = response.data;
                        }
                    })
                });
            } else {
                LoginService.showLoginBox();
            }
        }, $scope.page, $scope.size, $scope.sort);
    }])

    /*
     * ProfileCtrl definition
     */
    .controller('ProfileCtrl', ['$scope', '$location', 'ProfileService', 'LoginService', function ($scope, $location, ProfileService, LoginService) {
        ProfileService.profile(function (response) {
            if (response.status == 'SUCCESS') {
                $scope.$root.profile = response.data;
                $scope.profile = response.data;
            } else {
                LoginService.showLoginBox();
            }
        });

        // sendEmailVerification
        $scope.sendEmailVerification = function (email) {
            ProfileService.sendEmailVerification(email, function (response) {
                if (response.status === 'SUCCESS') {
                    //success send email verification
                    email.sent = 1;
                } else {
                    email.sent = 2;
                }
            });
        };
    }])

    /*
     * ProfileVerifyEmailCtrl definition
     */
    .controller('ProfileVerifyEmailCtrl', ['$scope', '$location', '$routeParams', 'ProfileService', function ($scope, $location, $routeParams, ProfileService) {
        $scope.rst = 0;
        $scope.email = {};
        var hashCode = $routeParams.hashCode;
        ProfileService.verifyEmail(hashCode, function (response) {
            if (response.status === 'SUCCESS') {
                // success
                $scope.rst = 1;
                $scope.email = response.data;
            } else {
                $scope.rst = 2;
            }
        });
    }])

    /*
     * ProfileEditCtrl definition
     */
    .controller('ProfileEditCtrl', ['$scope', '$location', 'ProfileService', 'LoginService', function ($scope, $location, ProfileService) {
        // retrieve profile information
        ProfileService.profile(function (response) {
            if (response.status == 'SUCCESS') {
                $scope.$root.profile = response.data;
                $scope.profile = response.data;
            } else {
                LoginService.showLoginBox();
            }
        });

        // submit profile modification
        $scope.submit = function () {
            ProfileService.edit($scope.profile, function (response) {
                if (response.status == 'SUCCESS') {
                    $scope.$root.profile = response.data;
                } else {
                    $location.path('/profile/edit');
                }
            });
        };
    }])

    /*
     * HomeCtrl definition
     */
    .controller('HomeCtrl', ['$scope', '$location', 'ProductService', 'ActionService', function ($scope, $location, ProductService, ActionService) {
        $scope.actions = ActionService;

        // init product list
        $scope.page = 0;
        $scope.size = 9;
        ProductService.list(function (response) {
            if (response.status == 'SUCCESS') {
                $scope.items = response.data.content;

                $scope.getTotal = function () {
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

                angular.forEach($scope.items, function (item) {
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
                        productId: item.id,
                        content: '',
                        rating: 3,
                        overStar: 3,
                        showMsg: 'None',
                        msg: ''
                    };
                });
            }
        }, $scope.page, $scope.size);

        // load more product
        $scope.loadMore = function () {
            $scope.page++;
            ProductService.list(function (response) {
                if (response.status == 'SUCCESS') {
                    response.data.content.forEach(function (item) {
                        item.review = {
                            productId: item.id,
                            content: '',
                            rating: 3,
                            overStar: 3,
                            showMsg: 'None',
                            msg: ''
                        };
                        $scope.items.push(item);
                    });
                } else if (response.status == 'EMPTY_RESULT') {
                    $scope.page--;
                }
            }, $scope.page, $scope.size);
        };
    }])

    /*
     * ContactCtrl definition
     */
    .controller('ContactCtrl', ['$scope', '$location', function ($scope, $location) {

    }])

    /*
     * AboutCtrl definition
     */
    .controller('AboutCtrl', ['$scope', '$location', function ($scope, $location) {

    }]);
