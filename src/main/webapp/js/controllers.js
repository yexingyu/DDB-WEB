var accountControllers = angular.module('accountsControllers', []);

accountControllers.controller('AccountsListCtrl', [ '$scope', '$http', '$location',
        function($scope, $http, $location) {
            $http.get('/api/accounts').success(function(data) {
                $scope.accounts = data;
            });
            $scope.orderProp = 'account';

            $scope.goRegister = function() {
                $location.path('/accounts/register');
            };
        } ]);

accountControllers.controller('AccountsDetailCtrl', [ '$scope', '$http', '$routeParams',
        function($scope, $http, $routeParams) {
            $scope.accountId = $routeParams.accountId;
            $http.get('/api/accounts/' + $scope.accountId).success(function(data) {
                $scope.accountDetail = data;
            });
        } ]);

accountControllers.controller('AccountsRegisterCtrl', [
        '$scope',
        '$http',
        'md5',
        '$location',
        function($scope, $http, md5, $location) {
            $scope.register = function() {
                $scope.account.password = md5.createHash($scope.passwd || '');
                alert(JSON.stringify($scope.account));
                $http.post('/api/accounts', $scope.account).success(
                        function(data, status, headers, config) {
                            $location.path('/accounts');
                        }).error(function(data, status, headers, config) {
                    alert("register error.");
                    $location.path('/accounts');
                });
            };
        } ]);
