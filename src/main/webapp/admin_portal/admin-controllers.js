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
.controller(
        'DashboardCtrl',
        [ '$scope', '$location', 
                function($scope, $location) {
                } ])                       
                

/*
 * PMCtrl definition
 */
.controller(
        'PMCtrl',
        [ '$scope', '$location', 'ProductService',
                function($scope, $location, ProductService) {
        	
                    
        	        ProductService.home(function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.items = response.data;
                        }
                    });
                } ])         
 
/*
 * PMProductCtrl definition
 */
.controller(
        'PMProductCtrl',
        [ '$scope', '$location', '$routeParams', 'ProductService',
                function($scope, $location, $routeParams, ProductService, MenuService) {
                    var id = $routeParams.id;
        	        
                    $scope.status =
        	        	[
        	        	 { id: 1, name: "ACTIVE" },
        	        	 { id: 2, name: "INACTIVE" },
        	        	 { id: 3, name: "AVAILABLE" }
        	            ];                     
        	        
                    $scope.stores =
        	        	[
        	        	 { id: 1, type: "Online", name: "walmart.ca" },
        	        	 { id: 2, type: "Online", name: "ebay.ca" },
        	        	 { id: 3, type: "Online", name: "costco.ca" },
        	        	 { id: 4, type: "Local", name: "Brick" }
        	            ];                      
                    
                    ProductService.get(id, function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.product = response.data;
                        }
                    });
                    
                    $scope.submit = function() {
                        console.log($scope.product);
                        ProductService.edit($scope.product, function(response) {
                            console.log(response);
                        });
                    };                    

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
.controller(
        'FlotCtrl',
        [ '$scope', '$location', 
                function($scope, $location) {
                } ])                       
                                
 /*
 * MorrisCtrl definition
 */
.controller(
        'MorrisCtrl',
        [ '$scope', '$location', 
                function($scope, $location) {
                } ])                   
.controller(
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
