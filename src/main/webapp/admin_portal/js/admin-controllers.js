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
                            
                            $scope.product.texts = [ {
                                language : 'EN',
                                name : "",
                                description : "",
                                warranty : "",
                                return_policy : "",
                                shipping_info : "",
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
                            }  ];                            
                            
                          

                            $scope.product.links = [ {
                                url : '',
                                name : "",
                                rating : "",
                                review_number : ""
                            }];
                            
                            
                            $scope.product.options = [];                            
                            
                            $scope.product.reviews = [];                            
                            $scope.product.likes = [];                            
                            
                            
                            
                            
                            
                            
                            
                            
                            // sample data
                            $scope.product.url = "http://shop.nordstrom.com/s/steve-madden-troopa-boot/3132609?origin=shoppingbag";
                            $scope.product.key = "33132609";
                            $scope.product.status = "AVAILABLE";
                            $scope.product.store = {
                                id : 2
                            };
                            $scope.product.prices = [ {
                                currency : 'CAD',
                                value : '106.39'
                            }];
                            
                            $scope.product.taxes = [ {
                                title : 'FEDERAL',
                                type : 'PERCENTAGE',
                            }, {
                                title : 'PROVINCE',
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
                            },

                            {
                                title : 'ECO',
                                type : 'AMOUNT',
                                value : ''
                            } ];

                            
                            $scope.product.images = [ {
                                url : 'http://g.nordstromimage.com/imagegallery/store/product/Large/6/_6787726.jpg',
                                alt : 'Steve Boot'
                            } ];
                            
                            $scope.product.texts = [ {
                                language : 'EN',
                                name : "Steve 'Troopa' Boot",
                                description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus cursus blandit leo, ac interdum erat fringilla at. Sed molestie nulla risus, ut volutpat sem lacinia eget. Suspendisse ut porttitor purus. Mauris tempus porta sapien, at aliquet ex elementum at. Praesent luctus feugiat dui, sit amet posuere elit tincidunt sit amet. Aenean ac justo tortor. Aenean maximus ipsum neque, non fermentum urna blandit ut. Praesent iaculis elit nec tortor dapibus pretium. Proin mollis odio nunc, et ultricies metus venenatis sit amet. Ut vel faucibus dolor. Mauris placerat porta nisi nec tempor. Ut convallis ac eros vel consequat. Nam a felis non ligula lobortis lacinia et laoreet sem. Nam rhoncus diam at lorem tristique vehicula a sed odio. In nec orci id ligula sagittis lacinia vel non magna. Maecenas pellentesque nibh risus, ac luctus nunc dapibus et.",
                                warranty : "n/a",
                                return_policy : "Nunc ac elit a ligula euismod efficitur vel ut urna. Morbi accumsan, nisi sit amet vestibulum consequat, lorem augue gravida enim, a feugiat diam dolor in odio. ",
                                shipping_info : "Usually arrives in 5-13 business days.",
                                meta_keyword : "Steve Madden , Boot",
                                meta_title : "Steve Madden Boot On sale",
                                meta_description : "Steve Madden Boot On sale"
                            }, {
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
                                value : "Steve Madden"
                            }, {
                            	language : 'FR',
                                value : "Boot"
                            }  ];                            
                            
                          

                            $scope.product.links = [ {
                                url : 'http://shop.nordstrom.com/s/steve-madden-troopa-boot/3132609',
                                name : "nordstrom.com",
                                rating : "4.6",
                                review_number : "1789"
                            }];
                            
                            
                            $scope.product.options = [];                            
                            

                            




                            // payment term calculation
                            $scope.Math = window.Math;
                            $scope.product.total = parseFloat($scope.product.prices[0].value);
                            //add taxes
                            for (var i=0; i<$scope.product.taxes.length; i++) {
                            	if ( $scope.product.taxes[i].type == "AMOUNT") {
                                  	 $scope.product.total = $scope.product.total + parseFloat($scope.product.taxes[i].value);                            		
                            	}
                            	if ( $scope.product.taxes[i].type == "PERCENTAGE") {
                                 	 $scope.product.total = $scope.product.total * (1+ parseFloat($scope.product.taxes[i].value));                            		
                           	    }                            	

                           }                            
                            //add fees
                            for (var i=0; i<$scope.product.fees.length; i++) {
                            	if ( $scope.product.fees[i].type == "AMOUNT") {
                                 	 $scope.product.total = $scope.product.total + parseFloat($scope.product.fees[i].value);                            		
                           	    }
                           	    if ( $scope.product.fees[i].type == "PERCENTAGE") {
                                	 $scope.product.total = $scope.product.total * (1+ parseFloat($scope.product.fees[i].value));                            		
                          	    }   
                            }
                            
                            if ($scope.product.prices[0].currency == 'USD') {
                            	$scope.exchange_rate = 1.2573;
                            }
                            if ($scope.product.prices[0].currency == 'CAD') {
                            	$scope.exchange_rate = 1;
                            }                            

                            // total
                            $scope.product.total_raw = $scope.product.total * $scope.exchange_rate;
                            $scope.product.total = Math.round($scope.product.total_raw * 100) / 100;

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