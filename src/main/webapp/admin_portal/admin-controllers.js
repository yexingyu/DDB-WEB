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
                    ProductService.get(id, function(response) {
                        if (response.status == 'SUCCESS') {
                            $scope.product = response.data;
                        }
                    });
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
