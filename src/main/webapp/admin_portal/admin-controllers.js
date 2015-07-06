angular.module('adminApp.controllers', [ 'angular-md5' ])

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
.controller('PMCtrl',
        [ '$scope', '$location', 'ProductService', function($scope, $location, ProductService) {

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
        [ '$scope', '$location', '$routeParams', 'ProductService',
                function($scope, $location, $routeParams, ProductService, MenuService) {
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

                    $scope.stores = [ {
                        id : 1,
                        type : "Online",
                        name : "walmart.ca"
                    }, {
                        id : 2,
                        type : "Online",
                        name : "ebay.ca"
                    }, {
                        id : 3,
                        type : "Online",
                        name : "costco.ca"
                    }, {
                        id : 4,
                        type : "Local",
                        name : "Brick"
                    } ];

                    ProductService.get(id, function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.product = response.data;
                        }
                    });

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
        [ '$scope', '$location', '$routeParams', 'ProductService',
                function($scope, $location, $routeParams, ProductService, MenuService) {
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

                    $scope.stores = [ {
                        id : 1,
                        type : "Online",
                        name : "walmart.ca"
                    }, {
                        id : 2,
                        type : "Online",
                        name : "ebay.ca"
                    }, {
                        id : 3,
                        type : "Online",
                        name : "costco.ca"
                    }, {
                        id : 4,
                        type : "Local",
                        name : "Brick"
                    } ];

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

                    
                    //sample data
                    $scope.product.url ="http://www.ebay.com/itm/Samsung-Galaxy-S6-SM-G920F-32GB-Factory-Unlocked-LTE-Smartphone-GSM/301678036602";
                    $scope.product.key = "301678036602";
                    $scope.product.status = "ACTIVE";
                    $scope.product.storeId = 2;
                    
                    $scope.product.prices[0].currency = 'CAD';
                    $scope.product.prices[0].value = '599.99';                    
                    $scope.product.prices[1].currency = 'USD';
                    $scope.product.prices[1].value = '499.99'; 
                    
                    $scope.product.taxes[0].title = 'FEDERAL';
                    $scope.product.taxes[0].value = '0';
                    $scope.product.taxes[1].title = 'PROVINCE';
                    $scope.product.taxes[1].value = '0';
                  
                    $scope.product.fees[0].title = 'SHIPPING';
                    $scope.product.fees[0].value = '13.87';
                    $scope.product.fees[1].title = 'IMPORT';
                    $scope.product.fees[1].value = '32.4';                   
                    
                    $scope.product.names[0].value='Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit';
                    $scope.product.images[0].url ='http://i.ebayimg.com/00/s/ODAwWDgwMA==/z/BNMAAOSwqu9VHBpA/$_57.JPG';
                    
                    $scope.product.descriptions[0].value ="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent risus elit, tempor nec accumsan at, vestibulum et est. Donec tempus lectus sed dui semper, sed pharetra leo auctor. Vestibulum commodo elit quis dignissim suscipit. Vestibulum pharetra lacus ut aliquet molestie. Donec laoreet urna sit amet nisi sollicitudin placerat. Integer viverra magna eu enim placerat blandit. Cras et viverra risus. Proin in scelerisque magna, a viverra nibh. Proin in quam felis. Vivamus et felis aliquam purus hendrerit mollis in at lorem. Fusce euismod at mauris eget laoreet. Nulla erat quam, sollicitudin ut malesuada in, pellentesque at turpis. Duis tempor quis nulla ut interdum. Quisque facilisis at orci vitae feugiat.Phasellus ac odio libero. Curabitur ac feugiat lacus, nec placerat sem. Sed aliquam nunc quis metus eleifend faucibus. Maecenas pellentesque mi augue, vulputate sollicitudin ipsum venenatis ac. Proin a accumsan leo. Integer pretium cursus neque, sit amet hendrerit risus dapibus non. Nulla sodales quam eu tortor tristique venenatis. Praesent elementum magna risus, at suscipit nisl ullamcorper et. Morbi vehicula hendrerit ipsum vitae ullamcorper. Vivamus facilisis nisl non lorem ultrices, sit amet bibendum nibh consectetur.";

                    
                    
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
        [ '$scope', '$location', 'AdminLoginService', 'md5',
                function($scope, $location, AdminLoginService, md5) {
                    $scope.login = function() {
                        $scope.member.password = md5.createHash($scope.member.pwd || '');
                        AdminLoginService.login($scope.member, function(response) {
                            console.log(response);
                            if (response.status == "SUCCESS" && response.data.role == "ADMIN") {
                                $location.path("/home");
                            }
                        });
                    };
                } ]);
