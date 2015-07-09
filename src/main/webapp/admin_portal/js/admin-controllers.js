angular
        .module('adminApp.controllers', [ 'angular-md5' ])

        .controller(
                'AdminHomeCtrl',
                [ '$scope', '$location', 'AdminLoginService',
                        function($scope, $location, AdminLoginService) {
                            AdminLoginService.check();
                        } ])

        /*
         * DashboardCtrl definition
         */
        .controller('DashboardCtrl', [ '$scope', '$location', function($scope, $location) {
        } ])

        /*
         * PMCtrl definition
         */
        .controller(
                'PMCtrl',
                [ '$scope', '$location', 'ProductService',
                        function($scope, $location, ProductService) {

                            ProductService.list(function(response) {
                                if (response.status == 'SUCCESS') {
                                    $scope.items = response.data;
                                }
                            });
                        } ])

        /*
         * PMProductEdit definition
         */
        .controller(
                'PMProductEdit',
                [ '$scope', '$location', '$routeParams', 'ProductService', 'StoreService',
                        function($scope, $location, $routeParams, ProductService, StoreService) {
                            var id = $routeParams.id;

                            $scope.status = [ {
                                id : 1,
                                name : "ACTIVE"
                            }, {
                                id : 2,
                                name : "INACTIVE"
                            }, {
                                id : 3,
                                name : "AVAILABLE"
                            } ];

                            // retrieve store list
                            $scope.stores = [];
                            StoreService.list(function(response) {
                                if (response.status === "SUCCESS") {
                                    $scope.stores = response.data;
                                }
                            });

                            // retrieve product details
                            ProductService.get(id, function(response) {
                                if (response.status == 'SUCCESS') {
                                    $scope.product = response.data;
                                }
                            });

                            // submit method
                            $scope.submit = function() {
                                console.log($scope.product);
                                ProductService.edit($scope.product, function(response) {
                                    if (response.status == 'SUCCESS') {
                                        $scope.product = response.data;
                                        console.log(response);
                                        
                                    }
                                });
                            };

                        } ])

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
                        function($scope, $location, $routeParams, ProductService, StoreService) {
                            var id = $routeParams.id;

                            $scope.status = [ {
                                id : 1,
                                name : "AVAILABLE"
                            } ];

                            // retrieve store list
                            $scope.stores = [];
                            StoreService.list(function(response) {
                                if (response.status === "SUCCESS") {
                                    $scope.stores = response.data;
                                }
                            });

                            $scope.product = {};
                            $scope.product.prices = [ {
                                currency : 'CAD',
                                value : ''
                            }, {
                                currency : 'USD',
                                value : ''
                            } ];
                            $scope.product.taxes = [ {
                                title : 'FEDERAL',
                                type : 'PERCENTAGE',
                                value : ''
                            }, {
                                title : 'PROVINCE',
                                type : 'PERCENTAGE',
                                value : ''
                            } ];
                            $scope.product.fees = [ {
                                title : 'SHIPPING',
                                type : 'AMOUNT',
                                value : ''
                            }, {
                                title : 'IMPORT',
                                type : 'AMOUNT',
                                value : ''
                            },

                            {
                                title : 'ECO',
                                type : 'AMOUNT',
                                value : ''
                            } ];

                            $scope.product.images = [ {
                                url : '',
                                alt : ''
                            } ];
                            $scope.product.names = [ {
                                language : 'EN',
                                value : ""
                            }, {
                                language : 'FR',
                                value : ""
                            } ];
                            $scope.product.descriptions = [ {
                                language : 'EN',
                                value : ""
                            }, {
                                language : 'FR',
                                value : ""
                            } ];
                            $scope.product.store = {
                                    id : 2
                                };
                            // sample data
                            /*
                            $scope.product.url = "http://www.ebay.com/itm/Samsung-Galaxy-S6-SM-G920F-32GB-Factory-Unlocked-LTE-Smartphone-GSM/301678036602";
                            $scope.product.key = "301678036602";
                            $scope.product.status = "AVAILABLE";


                            $scope.product.prices[0].currency = 'CAD';
                            $scope.product.prices[0].value = '599.99';
                            $scope.product.prices[1].currency = 'USD';
                            $scope.product.prices[1].value = '499.99';

                            // $scope.product.taxes[0].title = 'FEDERAL';
                            // $scope.product.taxes[0].value = '0';
                            // $scope.product.taxes[1].title = 'PROVINCE';
                            // $scope.product.taxes[1].value = '0';

                            $scope.product.fees[0].title = 'SHIPPING';
                            $scope.product.fees[0].value = '13.87';
                            $scope.product.fees[1].title = 'IMPORT';
                            $scope.product.fees[1].value = '73.40';
*/
                            // usd
                            $scope.Math = window.Math;
                            $scope.product.total = parseFloat($scope.product.prices[0].value);
                            $scope.exchange_rate = 1.2573;

                            // total
                            $scope.product.total_raw = $scope.product.total * $scope.exchange_rate;
                            $scope.product.total = $scope.Math.round($scope.product.total_raw * 100) / 100;

                            // down
                            $scope.product.down_raw = $scope.product.total_raw * 0.382;
                            $scope.product.down = Math.round($scope.product.down_raw * 100) / 100;

                            // interest
                            $scope.yearly_interest_rate = 0.24;
                            $scope.number_of_payments = 12;
                            $scope.monthly_interest_rate = $scope.yearly_interest_rate
                                    / $scope.number_of_payments;

                            // principal
                            $scope.product.principal_raw = $scope.product.total_raw * 0.618;
                            $scope.product.principal = Math
                                    .round($scope.product.principal_raw * 100) / 100;

                            $scope.monthly_payment_raw = $scope.monthly_interest_rate
                                    * $scope.product.principal_raw
                                    / (1 - Math.pow((1 + $scope.monthly_interest_rate),
                                            -$scope.number_of_payments));
                            $scope.monthly_payment = Math.round($scope.monthly_payment_raw * 100) / 100;

                            $scope.interest_raw = $scope.monthly_payment
                                    * $scope.number_of_payments - $scope.product.principal;
                            $scope.interest = Math.round($scope.interest_raw * 100) / 100;

                             $scope.add = function() {
                                console.log($scope.product);
                                ProductService.add($scope.product, function(response) {
                                	if (response.status == 'SUCCESS') {
                                		window.location.href="/admin_portal/index.html#/product";
                                	} 
                                	console.log(response);
                                });
                            };
                        } ])

        /*
         * FlotCtrl definition
         */
        .controller('FlotCtrl', [ '$scope', '$location', function($scope, $location) {
        } ])

        /*
         * MorrisCtrl definition
         */
        .controller('MorrisCtrl', [ '$scope', '$location', function($scope, $location) {
        } ]).controller(
                'AdminLoginCtrl',
                [
                        '$scope',
                        '$location',
                        'AdminLoginService',
                        'md5',
                        function($scope, $location, AdminLoginService, md5) {
                            $scope.login = function() {
                                $scope.member.password = md5.createHash($scope.member.pwd || '');
                                AdminLoginService.login($scope.member, function(response) {
                                    console.log(response);
                                    if (response.status == "SUCCESS"
                                            && response.data.role == "ADMIN") {
                                        $location.path("/home");
                                    }
                                });
                            };
                        } ]);
