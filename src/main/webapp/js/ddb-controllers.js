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
            $location.path('/all');
        };

        // search
        $scope.keyword = $location.search()['keyword'];
        $scope.search = function () {
            $location.path('/search').search({keyword: $scope.keyword});
        };
        $scope.searchKeypress = function (event) {
            if (event.which === 13) {
                $scope.search();
            }
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
    .controller('LoginCtrl', ['$scope', '$location', 'LoginService', 'md5', '$modalInstance', 'ezfb', '$resource', function ($scope, $location, LoginService, md5, $modalInstance, ezfb, $resource) {

        // facebook login
        $scope.fb_login = function () {
            ezfb.login(null, {scope: 'public_profile,email'}).then(function (res) {
                return ezfb.getLoginStatus();
            }).then(function (res) {
                LoginService.facebookLogin(res.authResponse.accessToken, function (response) {
                    if (response.status === 'SUCCESS') {
                        $scope.$root.profile = response.data;
                        console.log($scope.$root.profile);
                        $modalInstance.close(response.data);
                    } else {
                        $modalInstance.dismiss('Facebook login fail.');
                    }
                });
            });
        };

        // member login
        $scope.loginAccount = {};
        $scope.isLoginFail = false;
        $scope.login = function () {
            $scope.isLoginFail = false;
            $scope.loginAccount.password = md5.createHash($scope.loginAccount.passwd || '');
            delete $scope.loginAccount.passwd;
            LoginService.login($scope.loginAccount, function (response) {
                if (response.status === 'SUCCESS') {
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
                if (response.status === 'SUCCESS') {
                    // register success
                    console.log('register success');
                    $scope.isRegisterFail = false;
                    $modalInstance.close(response.data);
                    $location.path('/welcome');
                } else {
                    // register fail
                    console.log('register fail');
                    $scope.isRegisterFail = true;
                }
            });
        };
    }])

    /*
     * SearchCtrl defination
     */
    .controller('SearchCtrl', ['$scope', '$location', 'ProductService', 'StoreService', 'ProfileService', 'ProductModel', 'StoreModel', function ($scope, $location, ProductService, StoreService, ProfileService, ProductModel, StoreModel) {
        $scope.requestSearch = $location.search();
        $scope.keyword = $scope.requestSearch['keyword'];
        $scope.items = [];
        $scope.actions = {
            'like': ProductModel.like, 'unlike': ProductModel.unlike, 'review': ProductModel.review, 'reviewHoveringOver': ProductModel.reviewHoveringOver
        };
        $scope.pagination = {'page': 1, 'size': 36, 'sort': 'createdAt,desc'};
        $scope.pagination.change = function () {
            $scope.loading();
        };

        // sort
        $scope.select = function (sort) {
            $scope.pagination.page = 1;
            $scope.pagination.sort = sort;
            $scope.loading();
        };

        // loading products
        var callback = function (response) {
            if (response.status == 'SUCCESS') {
                angular.forEach(response.data.content, function (item) {
                    ProductModel.fixLikeAndReviewOnProduct(item);
                    StoreModel.fixFollowed(item.store, $scope.me.stores);
                });
                $scope.items = response.data.content;
                $scope.pagination.total = response.data.totalElements;
            }
        };

        $scope.me = {'loaded': false};
        $scope.loading = function () {
            if ($scope.me.loaded) {
                ProductService.search(callback, $scope.pagination.page - 1, $scope.pagination.size, $scope.pagination.sort, {'keyword': $scope.keyword});
            } else {
                ProfileService.profile(function (response) {
                    if (response.status === 'SUCCESS') {
                        angular.extend($scope.me, response.data);
                    }
                    $scope.me.loaded = true;
                    ProductService.search(callback, $scope.pagination.page - 1, $scope.pagination.size, $scope.pagination.sort, {'keyword': $scope.keyword});
                });
            }
        };

        // follow and unfollow
        $scope.follow = StoreModel.follow;
        $scope.unfollow = StoreModel.unfollow;
    }])

    /*
     * AllCtrl definition
     */
    .controller('AllCtrl', ['$scope', '$location', 'ProductService', 'StoreService', 'ProfileService', 'ProductModel', 'StoreModel', function ($scope, $location, ProductService, StoreService, ProfileService, ProductModel, StoreModel) {
        $scope.requestSearch = $location.search();
        $scope.currentDate = new Date();
        $scope.items = [];
        $scope.actions = {
            'like': ProductModel.like, 'unlike': ProductModel.unlike, 'review': ProductModel.review, 'reviewHoveringOver': ProductModel.reviewHoveringOver
        };
        $scope.pagination = {'page': 1, 'size': 36, 'sort': 'createdAt,desc'};
        $scope.pagination.change = function () {
            $scope.loading();
        };

        // sort
        $scope.select = function (sort) {
            $scope.pagination.page = 1;
            $scope.pagination.sort = sort;
            $scope.loading();
        };

        // tags selecting
        $scope.tags = {'result': $scope.requestSearch['tags'], 'all': [], 'checklist': {}};
        $scope.loadTags = function () {
            if ($scope.tags.all.length === 0) {
                ProductService.listAllTags(function (response) {
                    if (response.status === 'SUCCESS') {
                        $scope.tags.all = response.data;
                    }
                });
            }
        };
        //$scope.$watchCollection('tags.checklist', function () {
        //    $scope.tags.result = [];
        //    angular.forEach($scope.tags.checklist, function (value, key) {
        //        if (value) {
        //            $scope.tags.result.push(key);
        //        }
        //    });
        //});

        // stores selecting
        $scope.stores = {'result': $scope.requestSearch['store_id'] ? $scope.requestSearch['store_id'] : '0', 'all': [], 'checklist': {}};
        $scope.loadStores = function () {
            if ($scope.stores.all.length === 0) {
                StoreService.listAll(function (response) {
                    if (response.status === 'SUCCESS') {
                        $scope.stores.all = response.data;
                    }
                });
            }
        };
        $scope.loadStores();
        //$scope.$watchCollection('stores.checklist', function () {
        //    $scope.stores.result = [];
        //    angular.forEach($scope.stores.checklist, function (value, key) {
        //       if (value) {
        //            $scope.stores.result.push(key);
        //        }
        //    });
        //});

        // loading products
        var callback = function (response) {
            if (response.status == 'SUCCESS') {
                angular.forEach(response.data.content, function (item) {
                    ProductModel.fixLikeAndReviewOnProduct(item);
                    StoreModel.fixFollowed(item.store, $scope.me.stores);
                });
                $scope.items = response.data.content;
                $scope.pagination.total = response.data.totalElements;
            }
        };
        $scope.me = {'loaded': false};
        $scope.loading = function () {
            console.log($scope.tags);
            if ($scope.me.loaded) {
                ProductService.list(callback, $scope.pagination.page - 1, $scope.pagination.size, $scope.pagination.sort, {'tags': $scope.tags.result, 'store_ids': $scope.stores.result});
            } else {
                ProfileService.profile(function (response) {
                    if (response.status === 'SUCCESS') {
                        angular.extend($scope.me, response.data);
                    }
                    $scope.me.loaded = true;
                    ProductService.list(callback, $scope.pagination.page - 1, $scope.pagination.size, $scope.pagination.sort, {'tags': $scope.tags.result, 'store_ids': $scope.stores.result});
                });
            }
        };

        // follow and unfollow
        $scope.follow = StoreModel.follow;
        $scope.unfollow = StoreModel.unfollow;
    }])

    /*
     * ProductDetailsCtrl definition
     */
    .controller('ProductDetailsCtrl', ['$scope', '$location', '$routeParams', 'ProductService','ProfileService', 'ProductModel', 'TagModel', function ($scope, $location, $routeParams, ProductService,ProfileService, ProductModel,TagModel) {
        var id = $routeParams.id;
        $scope.item = {};
        $scope.actions = {
            'like': ProductModel.like, 'unlike': ProductModel.unlike, 'review': ProductModel.review, 'reviewHoveringOver': ProductModel.reviewHoveringOver
        };

        $scope.me = {};
        ProfileService.profile(function (response) {
            if (response.status === 'SUCCESS') {
                $scope.me = response.data;
                console.log($scope.me.tags);
            }
        });
        
        $scope.loading = function () {
            ProfileService.profile(function (response) {
                if (response.status === 'SUCCESS') {
                    $scope.me = response.data;
                } else {
                    $scope.me = {};
                }
            });
        };        
        
        $scope.loading();
        // retrieve product details
        ProductService.get(id, function (response) {
            if (response.status == 'SUCCESS') {
                angular.extend($scope.item, response.data);
                ProductModel.fixLikeAndReviewOnProduct($scope.item);
                console.log(response.data);
                
                angular.forEach(response.data.tags, function (tag) {
	                TagModel.fixFollowed(tag, $scope.me.tags);
                });
            }
        });

        // retrieve product reviews
        $scope.item.reviews = {page: 0, size: 20};
        ProductService.reviews(id, $scope.item.reviews.page, $scope.item.reviews.size, function (response) {
            if (response.status == 'SUCCESS') {
                $scope.item.reviews = response.data;
            }
        });
        
        $scope.followTag = TagModel.followTag;
        $scope.unfollowTag = TagModel.unfollowTag;
    }])

    /*
     * ProductOrderCtrl definition
     */
    .controller(
    'ProductOrderCtrl',
    ['$scope', '$location', '$window', '$modal', '$routeParams', 'ProductService', 'ProfileService', 'OrderService', 'LoginService', 'ProductModel', 'OrderModel',
        function ($scope, $location, $window, $modal, $routeParams, ProductService, ProfileService, OrderService, LoginService, ProductModel, OrderModel) {
            var id = $routeParams.id;
            var date = new Date();
            var day = date.getUTCDate();
            if (day > 9 && day < 23) {
                $scope.payDay = 16;
            } else {
                $scope.payDay = 1;
            }
            ;
            $scope.month_1 = date.setMonth(date.getMonth() + 1);
            $scope.month_2 = date.setMonth(date.getMonth() + 1);
            $scope.month_3 = date.setMonth(date.getMonth() + 1);
            $scope.month_4 = date.setMonth(date.getMonth() + 1);
            $scope.month_5 = date.setMonth(date.getMonth() + 1);
            $scope.month_6 = date.setMonth(date.getMonth() + 1);
            $scope.month_7 = date.setMonth(date.getMonth() + 1);
            $scope.month_8 = date.setMonth(date.getMonth() + 1);
            $scope.month_9 = date.setMonth(date.getMonth() + 1);
            $scope.month_10 = date.setMonth(date.getMonth() + 1);
            $scope.month_11 = date.setMonth(date.getMonth() + 1);
            $scope.month_12 = date.setMonth(date.getMonth() + 1);


            $scope.tax_rate = {
                AB: {
                    province: "AB",
                    tax_type: "GST",
                    provincial_rate: 0.00,
                    canada_rate: 0.05,
                    total_rate: 0.05
                },
                BC: {
                    province: "BC",
                    tax_type: "GST+PST",
                    provincial_rate: 0.07,
                    canada_rate: 0.05,
                    total_rate: 0.12
                },
                MB: {
                    province: "MB",
                    tax_type: "GST+PST",
                    provincial_rate: 0.08,
                    canada_rate: 0.05,
                    total_rate: 0.13
                },
                NB: {
                    province: "NB",
                    tax_type: "HST",
                    provincial_rate: 0.08,
                    canada_rate: 0.05,
                    total_rate: 0.13
                },
                NL: {
                    province: "NL",
                    tax_type: "GST",
                    provincial_rate: 0.08,
                    canada_rate: 0.05,
                    total_rate: 0.13
                },
                NT: {
                    province: "NT",
                    tax_type: "GST",
                    provincial_rate: 0.00,
                    canada_rate: 0.05,
                    total_rate: 0.05
                },
                NS: {
                    province: "NS",
                    tax_type: "GST",
                    provincial_rate: 0.00,
                    canada_rate: 0.05,
                    total_rate: 0.05
                },
                NU: {
                    province: "NU",
                    tax_type: "GST",
                    provincial_rate: 0.00,
                    canada_rate: 0.05,
                    total_rate: 0.05
                },
                ON: {
                    province: "ON",
                    tax_type: "HST",
                    provincial_rate: 0.08,
                    canada_rate: 0.05,
                    total_rate: 0.13
                },
                PE: {
                    province: "PE",
                    tax_type: "HST",
                    provincial_rate: 0.09,
                    canada_rate: 0.05,
                    total_rate: 0.14
                },
                QC: {
                    province: "QC",
                    tax_type: "GST+QST",
                    provincial_rate: 0.9975,
                    canada_rate: 0.05,
                    total_rate: 0.14975
                },
                SK: {
                    province: "SK",
                    tax_type: "GST",
                    provincial_rate: 0.05,
                    canada_rate: 0.05,
                    total_rate: 0.10
                },
                YK: {
                    province: "YK",
                    tax_type: "GST",
                    provincial_rate: 0.00,
                    canada_rate: 0.05,
                    total_rate: 0.05
                }
            };
            $scope.order = {};

            // retrieve product details
            ProductService.get(id, function (response) {
                if (response.status === 'SUCCESS') {
                    $scope.product = ProductModel.fix(response.data);
                    $scope.order.productId = $scope.product.id;
                } else {
                    $location.path('/all');
                }
            });

            // retrieve profile information
            ProfileService.profile(function (response) {
                if (response.status === 'SUCCESS') {
                    $scope.$root.profile = response.data;
                    $scope.profile = response.data;
                    OrderModel.fix($scope.order, $scope.profile);
                } else if (response.status === 'NEED_LOGIN') {
                    LoginService.showLoginBox(function (profile) {
                        $scope.$root.profile = profile;
                        $scope.profile = profile;
                        OrderModel.fix($scope.order, $scope.profile);
                    }, function (reason) {
                        $window.history.back();
                    });
                } else {
                    $location.path('/all');
                }
            });

            // next button
            $scope.next = function () {
                $modal.open({
                    animation: true,
                    templateUrl: 'tpl/tpl-product-order-confirm.html',
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
                console.log($scope.order);
                console.log(response);
                if (response.status === 'SUCCESS') {
                    // submit success
                    $scope.order = response.data;
                    $location.path('/order/me');
                } else {
                    // fail
                    $location.path('/order/me');
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
    .controller('ProfileEditCtrl', ['$scope', '$location', 'ProfileService', 'LoginService', function ($scope, $location, ProfileService, LoginService) {
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
     * FollowingCtrl definition
     */
    .controller('FollowingCtrl', ['$scope', '$location', 'ProductService', 'LoginService', 'ProfileService', 'ProductModel', 'StoreModel', function ($scope, $location, ProductService, LoginService, ProfileService, ProductModel, StoreModel) {
        $scope.items = [];
        $scope.actions = {
            'like': ProductModel.like, 'unlike': ProductModel.unlike, 'review': ProductModel.review, 'reviewHoveringOver': ProductModel.reviewHoveringOver
        };

        // callback function for ProductService
        var callback = function (response) {
            if (response.status == 'SUCCESS') {
                angular.forEach(response.data.content, function (item) {
                    ProductModel.fixLikeAndReviewOnProduct(item);
                    StoreModel.fixFollowed(item.store, $scope.me.stores);
                    $scope.items.push(item);
                });
            } else if (response.status == 'EMPTY_RESULT') {
                $scope.pagination.page--;
            }
        };

        // loading products
        $scope.loading = function () {
            ProfileService.profile(function (response) {
                if (response.status === 'SUCCESS') {
                    $scope.me = response.data;
                    ProductService.listFollowed(callback, $scope.pagination.page - 1, $scope.pagination.size, $scope.pagination.sort);
                } else {
                    $scope.me = {};
                    ProductService.list(callback, $scope.pagination.page - 1, $scope.pagination.size, $scope.pagination.sort);
                }
            });
        };
        $scope.pagination = {'page': 1, 'size': 16, 'sort': 'createdAt,desc'};
        $scope.loading();

        // load more product
        $scope.loadMore = function () {
            $scope.pagination.page++;
            $scope.loading();
        };

        // follow/unfollow
        $scope.follow = StoreModel.follow;
        $scope.unfollow = StoreModel.unfollow;
    }])

    /*
     * StoreCtrl definition
     */
    .
    controller('StoreCtrl', ['$scope', '$location', '$route', 'StoreService', 'ProfileService', 'LoginService', 'StoreModel', function ($scope, $location, $route, StoreService, ProfileService, LoginService, StoreModel) {
        $scope.stores = {};
        $scope.me = {};
        ProfileService.profile(function (response) {
            if (response.status === 'SUCCESS') {
                $scope.me = response.data;
            }
        }).$promise.then(function (response) {
                StoreService.list(function (response) {
                    if (response.status === 'SUCCESS') {
                        $scope.stores = response.data.content;
                        angular.forEach($scope.stores, function (store) {
                            StoreModel.fixFollowed(store, $scope.me.stores);
                        });
                    }
                });
            });

        // follow/unfollow
        $scope.follow = StoreModel.follow;
        $scope.unfollow = StoreModel.unfollow;

    }])

    /*
     * ContactCtrl definition
     */
    .controller('ContactCtrl', ['$scope', '$location', 'ProductService', function ($scope, $location, ProductService) {
    }])

    /*
     * LocalCtrl definition
     */
    .controller('LocalCtrl', ['$scope', '$location', 'ProductService', function ($scope, $location, ProductService) {
    }])

    /*
     * WelcomeCtrl definition
     */
    .controller('WelcomeCtrl', ['$scope', '$location', 'ProductService', 'LoginService', function ($scope, $location, ProductService, LoginService) {
    }])

    /*
     * DiscussCtrl definition
     */
    .controller('DiscussCtrl', ['$scope', '$location', 'ProductService', 'LoginService', function ($scope, $location, ProductService, LoginService) {
    }])

    /*
     * MemberDetailsCtrl definition
     */
    .controller('MemberDetailsCtrl', ['$scope', '$location', '$routeParams','MemberService', 'LoginService', function ($scope, $location,$routeParams, MemberService, LoginService) {
    	
        var id = $routeParams.id;



        // retrieve product details
        MemberService.get(id, function (response) {
            if (response.status == 'SUCCESS') {
                $scope.member = response.data;
                console.log(response.data);
            }
        });
    }]) 
    
  /*
     * PMProductAdd definition
     */
    .controller(
    'ShareCtrl',
    [
        '$scope',
        '$location',
        '$routeParams',
        'ProductService',
        'StoreService',
        function ($scope, $location, $routeParams, ProductService, StoreService) {
            console.log("share control");
        	
        	var id = $routeParams.id;

            $scope.status = [{
                id: true,
                name: "true"
            }, {
                id: false,
                name: "false"
            }
            ];

            $scope.tag_hot_options = [{
                value: "phone"
            }, {
                value: "laptop"
            }, {
                value: "tablet"
            }, {
                value: "electronic"
            }, {
                value: "appliance"
            }, {
                value: "furniture"
            }, {
                value: "sport"
            }, {
                value: "outdoor"
            }, {
                value: "clothing"
            }, {
                value: "shoes"
            }, {
                value: "handbag"
            }, {
                value: "cosmetics"
            }, {
                value: "other"
            }];


            // retrieve store list
            $scope.stores = [];
            StoreService.list(function (response) {
                if (response.status === "SUCCESS") {
                    $scope.stores = response.data.content;
                }
            });
            // add input field
            $scope.product = {};
            $scope.product.currency = "CAD";
            $scope.product.currentPrice = "0.00";

            $scope.product.prices = [{
                currency: 'CAD',
                value: ''
            }];


            $scope.product.images = [{
                url: '',
                alt: ''
            }];

            $scope.product.texts = [{
                language: 'EN',
                name: "",
                description: "",
                warranty: "",
                return_policy: "",
                shipping_info: "",
                coupon: "",
                meta_keyword: "",
                meta_title: "",
                meta_description: ""
            }, {
                language: 'FR',
                name: "",
                description: "",
                warranty: "",
                return_policy: "",
                shipping_info: "",
                coupon: "",
                meta_keyword: "",
                meta_title: "",
                meta_description: ""
            }];


            // sample data
            $scope.product.url = "";
            $scope.product.key = "";
            $scope.product.status = "AVAILABLE";
            $scope.product.store = {
                id: 2
            };
            $scope.product.prices = [{
                currency: 'CAD',
                value: ''
            }];

            $scope.product.taxes = [{
                title: 'CAFEDERAL',
                type: 'PERCENTAGE',
            }, {
                title: 'CAPROVINCE',
                type: 'PERCENTAGE',

            }];

            $scope.product.fees = [{
                title: 'SHIPPING',
                type: 'AMOUNT',
                value: ''
            }, {
                title: 'IMPORT',
                type: 'AMOUNT',
                value: ''
            }];

            $scope.product.images = [{
                url: '',
                alt: ''
            }];

            $scope.product.texts = [
                {
                    language: 'EN',
                    name: "",
                    description: "",
                    warranty: "n/a",
                    return_policy: "n/a",
                    shipping_info: "n/a",
                    meta_keyword: "",
                    meta_title: "",
                    meta_description: ""
                },
                {
                    language: 'FR',
                    name: "",
                    description: "",
                    warranty: "n/a",
                    return_policy: "n/a",
                    shipping_info: "n/a",
                    meta_keyword: "",
                    meta_title: "",
                    meta_description: ""
                }];





            // payment term calculation
            $scope.Math = window.Math;

            $scope.getExchangeRate = function () {
                if ($scope.product.currency == "CAD") {
                    exchange_rate = 1;
                }
                if ($scope.product.currency == "USD") {
                    exchange_rate = 1.3588;
                }
                return exchange_rate

            }

            $scope.getTotal = function () {
                $scope.product.total = parseFloat($scope.product.currentPrice);
                // add fees
                total_fee = 0;
                for (var i = 0; i < $scope.product.fees.length; i++) {
                    if ($scope.product.fees[i].type == "AMOUNT") {
                        total_fee = total_fee
                            + parseFloat($scope.product.fees[i].value);
                    }
                    if ($scope.product.fees[i].type == "PERCENTAGE") {
                        total_fee = total_fee + $scope.product.currentPrice
                            * parseFloat($scope.product.fees[i].value / 100);
                    }
                }
                total = total_fee + $scope.product.total;

                return total;
            }

            $scope.yearly_interest_rate = 0.24;
            $scope.number_of_payments = 12;
            $scope.monthly_interest_rate = $scope.yearly_interest_rate
                / $scope.number_of_payments;

            $scope.getMonthlyPayment = function () {
                $scope.product.total = parseFloat($scope.product.currentPrice);
                // add fees
                total_fee = 0;
                for (var i = 0; i < $scope.product.fees.length; i++) {
                    if ($scope.product.fees[i].type == "AMOUNT") {
                        total_fee = total_fee
                            + parseFloat($scope.product.fees[i].value);
                    }
                    if ($scope.product.fees[i].type == "PERCENTAGE") {
                        total_fee = total_fee + $scope.product.currentPrice
                            * parseFloat($scope.product.fees[i].value / 100);
                    }
                }
                principal = total * 0.618;
                MonthlyPayment = $scope.monthly_interest_rate
                    * principal
                    / (1 - Math.pow((1 + $scope.monthly_interest_rate),
                        -$scope.number_of_payments));
                MonthlyPayment = Math.round(MonthlyPayment * 100) / 100;
                return MonthlyPayment;
            }

            $scope.parse = function () {
                ProductService.spider($scope.product.url, function (response) {
                    if (response.status === 'SUCCESS') {
                        $scope.product = response.data;


                        $scope.product.options = [];

                        $scope.product.reviews = [];
                        $scope.product.likes = [];

                        console.log(response.data);
                    }
                })
            }


            $scope.add = function () {
                //add currentPrice to prices
                $scope.product.prices = [{
                    currency: $scope.product.currency,
                    value: $scope.product.currentPrice
                }];
                //$scope.product.tags.concat($scope.tag_hot);

                console.log(JSON.stringify($scope.product));
                ProductService.add($scope.product, function (response) {
                    console.log(response);
                    $location.path("/home");
                });
            };
        }])    
    
    /*
     * AboutCtrl definition
     */
    .controller('AboutCtrl', ['$scope', '$location', function ($scope, $location) {
    }]);
