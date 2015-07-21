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
                                    $scope.items = response.data.content;
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
                            // add input field
                            $scope.product = {};

                            $scope.product.prices = [ {
                                currency : 'CAD',
                                value : ''
                            } ];

                            $scope.product.taxes = [ {
                                title : 'CAFEDERAL',
                                type : 'PERCENTAGE',
                                value : ''
                            }, {
                                title : 'CAPROVINCE',
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
                            } ];

                            $scope.product.images = [ {
                                url : '',
                                alt : ''
                            } ];

                            $scope.product.texts = [ {
                                language : 'EN',
                                name : "",
                                description : "",
                                warranty : "",
                                return_policy : "",
                                shipping_info : "",
                                coupon : "",
                                meta_keyword : "",
                                meta_title : "",
                                meta_description : ""
                            }, {
                                language : 'FR',
                                name : "",
                                description : "",
                                warranty : "",
                                return_policy : "",
                                shipping_info : "",
                                coupon : "",
                                meta_keyword : "",
                                meta_title : "",
                                meta_description : ""
                            } ];

                            $scope.product.tags = [ {
                                language : 'EN',
                                value : ""
                            }, {
                                language : 'EN',
                                value : ""
                            }, {
                                language : 'FR',
                                value : ""
                            }, {
                                language : 'FR',
                                value : ""
                            } ];

                            $scope.product.links = [ {
                                url : '',
                                name : "",
                                rating : "",
                                review_number : ""
                            } ];

                            $scope.product.options = [ {
                                type : "COLOR",
                                value : ""
                            }, {
                                type : "COLOR",
                                value : ""
                            } ];

                            $scope.product.reviews = [];
                            $scope.product.likes = [];

                            // sample data
                            $scope.product.url = "http://shop.nordstrom.com/s/steve-madden-troopa-boot/3132609";
                            $scope.product.key = "33132609";
                            $scope.product.status = "AVAILABLE";
                            $scope.product.store = {
                                id : 2
                            };
                            $scope.product.prices = [ {
                                currency : 'CAD',
                                value : '106.39'
                            } ];

                            $scope.product.taxes = [ {
                                title : 'CAFEDERAL',
                                type : 'PERCENTAGE',
                            }, {
                                title : 'CAPROVINCE',
                                type : 'PERCENTAGE',

                            } ];

                            $scope.product.fees = [ {
                                title : 'SHIPPING',
                                type : 'AMOUNT',
                                value : '9.75'
                            }, {
                                title : 'IMPORT',
                                type : 'AMOUNT',
                                value : '33.11'
                            } ];

                            $scope.product.images = [ {
                                url : 'http://g.nordstromimage.com/imagegallery/store/product/Large/6/_6787726.jpg',
                                alt : 'Steve Boot'
                            } ];

                            $scope.product.texts = [
                                    {
                                        language : 'EN',
                                        name : "Steve 'Troopa' Boot",
                                        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus cursus blandit leo, ac interdum erat fringilla at. Sed molestie nulla risus, ut volutpat sem lacinia eget. Suspendisse ut porttitor purus. Mauris tempus porta sapien, at aliquet ex elementum at. Praesent luctus feugiat dui, sit amet posuere elit tincidunt sit amet. Aenean ac justo tortor. Aenean maximus ipsum neque, non fermentum urna blandit ut. Praesent iaculis elit nec tortor dapibus pretium. Proin mollis odio nunc, et ultricies metus venenatis sit amet. Ut vel faucibus dolor. Mauris placerat porta nisi nec tempor. Ut convallis ac eros vel consequat. Nam a felis non ligula lobortis lacinia et laoreet sem. Nam rhoncus diam at lorem tristique vehicula a sed odio. In nec orci id ligula sagittis lacinia vel non magna. Maecenas pellentesque nibh risus, ac luctus nunc dapibus et.",
                                        warranty : "n/a",
                                        return_policy : "Nunc ac elit a ligula euismod efficitur vel ut urna. Morbi accumsan, nisi sit amet vestibulum consequat, lorem augue gravida enim, a feugiat diam dolor in odio. ",
                                        shipping_info : "Usually arrives in 5-13 business days.",
                                        meta_keyword : "Steve Madden , Boot",
                                        meta_title : "Steve Madden Boot On sale",
                                        meta_description : "Steve Madden Boot On sale"
                                    },
                                    {
                                        language : 'FR',
                                        name : "Steve 'Troopa' Boot",
                                        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus cursus blandit leo, ac interdum erat fringilla at. Sed molestie nulla risus, ut volutpat sem lacinia eget. Suspendisse ut porttitor purus. Mauris tempus porta sapien, at aliquet ex elementum at. Praesent luctus feugiat dui, sit amet posuere elit tincidunt sit amet. Aenean ac justo tortor. Aenean maximus ipsum neque, non fermentum urna blandit ut. Praesent iaculis elit nec tortor dapibus pretium. Proin mollis odio nunc, et ultricies metus venenatis sit amet. Ut vel faucibus dolor. Mauris placerat porta nisi nec tempor. Ut convallis ac eros vel consequat. Nam a felis non ligula lobortis lacinia et laoreet sem. Nam rhoncus diam at lorem tristique vehicula a sed odio. In nec orci id ligula sagittis lacinia vel non magna. Maecenas pellentesque nibh risus, ac luctus nunc dapibus et.",
                                        warranty : "n/a",
                                        return_policy : "Nunc ac elit a ligula euismod efficitur vel ut urna. Morbi accumsan, nisi sit amet vestibulum consequat, lorem augue gravida enim, a feugiat diam dolor in odio. ",
                                        shipping_info : "Usually arrives in 5-13 business days.",
                                        meta_keyword : "Steve Madden , Boot",
                                        meta_title : "Steve Madden Boot On sale",
                                        meta_description : "Steve Madden Boot On sale"
                                    } ];

                            $scope.product.tags = [ {
                                language : 'EN',
                                value : "Steve Madden"
                            }, {
                                language : 'EN',
                                value : "Boot"
                            }, {
                                language : 'FR',
                                value : "Steve Madden f"
                            }, {
                                language : 'FR',
                                value : "Boot f"
                            } ];

                            $scope.product.links = [ {
                                url : 'http://shop.nordstrom.com/s/steve-madden-troopa-boot/3132609',
                                name : "nordstrom.com",
                                rating : "4.6",
                                review_number : "1789"
                            } ];

                            $scope.product.options = [ {
                                type : "COLOR",
                                value : ""
                            }, {
                                type : "COLOR",
                                value : ""
                            } ];

                            // payment term calculation
                            $scope.Math = window.Math;

                            $scope.getExchangeRate = function() {
                                if ($scope.product.prices[0].currency == "CAD") {
                                    exchange_rate = 1;
                                }
                                if ($scope.product.prices[0].currency == "USD") {
                                    exchange_rate = 1.25;
                                }
                                return exchange_rate

                            }

                            $scope.getTotal = function() {
                                $scope.product.total = parseFloat($scope.product.prices[0].value);
                                // add fees
                                total_fee = 0;
                                for (var i = 0; i < $scope.product.fees.length; i++) {
                                    if ($scope.product.fees[i].type == "AMOUNT") {
                                        total_fee = total_fee
                                                + parseFloat($scope.product.fees[i].value);
                                    }
                                    if ($scope.product.fees[i].type == "PERCENTAGE") {
                                        total_fee = total_fee + $scope.product.prices[0].value
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

                            $scope.getMonthlyPayment = function() {
                                $scope.product.total = parseFloat($scope.product.prices[0].value);
                                // add fees
                                total_fee = 0;
                                for (var i = 0; i < $scope.product.fees.length; i++) {
                                    if ($scope.product.fees[i].type == "AMOUNT") {
                                        total_fee = total_fee
                                                + parseFloat($scope.product.fees[i].value);
                                    }
                                    if ($scope.product.fees[i].type == "PERCENTAGE") {
                                        total_fee = total_fee + $scope.product.prices[0].value
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

                            $scope.add = function() {
                                console.log($scope.product);
                                ProductService.add($scope.product, function(response) {
                                    console.log(response);
                                });
                            };
                        } ])

        /*
         * PMStoreAdd definition
         */
        .controller(
                'PMStoreAdd',
                [ '$scope', '$location', '$routeParams', 'StoreService',
                        function($scope, $location, $routeParams, StoreService) {
                            // add input field
                            $scope.store = {};

                            $scope.store.name = "NordStorm";
                            $scope.store.website = "nordstorm.com";
                            $scope.store.dealPage = "nordstorm.com";
                            $scope.store.logo = "nordstore.jpg";
                            $scope.store.country = "US";
                            $scope.store.province = "";
                            $scope.store.type = "ONLINE";
                            $scope.store.status = "AVAILABLE";

                            $scope.add = function() {
                                console.log($scope.store);
                                StoreService.add($scope.store, function(response) {
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
