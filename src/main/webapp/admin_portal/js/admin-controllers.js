angular
    .module('adminApp.controllers', ['angular-md5'])

    .controller('AdminHomeCtrl', ['$scope', '$location', 'AdminLoginService', function ($scope, $location, AdminLoginService) {
    }])

    /*
     * DashboardCtrl definition
     */
    .controller('DashboardCtrl', ['$scope', '$location', 'ProfileService', function ($scope, $location, ProfileService) {
        // retreive profile
        $scope.$root.profile = {};
        ProfileService.profile(function (response) {
            if (response.status == 'SUCCESS') {
                $scope.$root.profile = response.data;
                console.log($scope.$root.profile);
            }
        });

    }])

    /*
     * PMCtrl definition
     */
    .controller(
    'PMCtrl',
    ['$scope', '$location', 'ProductService',
        function ($scope, $location, ProductService) {

            ProductService.list(function (response) {
                if (response.status == 'SUCCESS') {
                    $scope.items = response.data.content;
                }
            });
        }])

    /*
     * PMProductEdit definition
     */
    .controller(
    'PMProductEdit',
    ['$scope', '$location', '$routeParams', 'ProductService', 'StoreService',
        function ($scope, $location, $routeParams, ProductService, StoreService) {
            var id = $routeParams.id;

            $scope.status = [{
                id: 1,
                name: "ACTIVE"
            }, {
                id: 2,
                name: "INACTIVE"
            }, {
                id: 3,
                name: "AVAILABLE"
            }];

            // retrieve store list
            $scope.stores = [];
            StoreService.list(function (response) {
                if (response.status === "SUCCESS") {
                    $scope.stores = response.data;
                }
            });

            // retrieve product details
            ProductService.get(id, function (response) {
                if (response.status == 'SUCCESS') {
                    $scope.product = response.data;
                }
            });

            // submit method
            $scope.submit = function () {
                console.log($scope.product);
                console.log(JSON.stringify($scope.product));
                ProductService.edit($scope.product, function (response) {
                    if (response.status == 'SUCCESS') {
                        $scope.product = response.data;
                    }
                });
            };

        }])

    /*
     * PMProductAdd definition
     */
    .controller(
    'PMProductAdd',
    [
        '$scope',
        '$location',
        '$routeParams',
        'ProductService',
        'StoreService',
        function ($scope, $location, $routeParams, ProductService, StoreService) {
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

            $scope.product.tags = [{
                value: ""
            }, {
                value: ""
            }, {
                value: ""
            }, {
                value: ""
            }, {
                value: ""
            }, {
                value: ""
            }];

            $scope.tag_hot = [{
                value: ""
            }];
            $scope.product.links = [{
                url: '',
                name: "",
                rating: "",
                review_number: ""
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


                        $scope.product.tags = [{
                            value: ""
                        }, {
                            value: ""
                        }, {
                            value: ""
                        }, {
                            value: ""
                        }, {
                            value: ""
                        }, {
                            value: ""
                        }];

                        $scope.product.links = [{
                            url: "",
                            name: "",
                            rating: "",
                            review_number: 0
                        }];

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
     * PMStoreAdd definition
     */
    .controller(
    'PMStoreAdd',
    ['$scope', '$location', '$routeParams', 'StoreService',
        function ($scope, $location, $routeParams, StoreService) {
            // add input field
            $scope.store = {};

            $scope.store.name = "";
            $scope.store.category = "";
            $scope.store.description = "";
            $scope.store.count_followings = 0;
            $scope.store.count_likes = 0;
            $scope.store.website = "";
            $scope.store.dealPage = "";
            $scope.store.logo = "";
            $scope.store.favicon = "";
            $scope.store.country = "CA";
            $scope.store.province = "";
            $scope.store.type = "ONLINE";
            $scope.store.status = "AVAILABLE";
            $scope.verified = false;

            $scope.add = function () {
                console.log($scope.store);
                StoreService.add($scope.store, function (response) {
                    console.log(response);
                    $location.path('/home');
                });
            };

        }])
    /*
     * FlotCtrl definition
     */
    .controller('FlotCtrl', ['$scope', '$location', function ($scope, $location) {
    }])

    /*
     * MorrisCtrl definition
     */
    .controller('MorrisCtrl', ['$scope', '$location', function ($scope, $location) {
    }]).controller(
    'AdminLoginCtrl',
    [
        '$scope',
        '$location',
        'AdminLoginService',
        'md5',
        function ($scope, $location, AdminLoginService, md5) {
            $scope.login = function () {
                $scope.member.password = md5.createHash($scope.member.pwd || '');
                AdminLoginService.login($scope.member, function (response) {
                    console.log(response);
                    if (response.status == "SUCCESS"
                        && response.data.role == "ADMIN") {
                        $location.path("/home");
                    }
                });
            };
        }]);
