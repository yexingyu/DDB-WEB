angular.module('ddbApp.controllers', [ 'angular-md5' ])

.controller(
        'LoginCtrl',
        [ '$scope', '$location', 'LoginService', 'md5',
                function($scope, $location, LoginService, md5) {
                    $scope.login = function() {
                        $scope.result = "loggining in...";
                        $scope.account.password = md5.createHash($scope.account.passwd || '');
                        // alert(JSON.stringify($scope.account));
                        LoginService.login($scope.account, function(response) {
                            $scope.result = response.data;
                            if (response.data == "GO") {
                                $location.path("/accounts");
                            }
                        });
                        // do login action
                        // alert(JSON.stringify($scope.account));
                    };
                } ])

.controller(
        'PayMonthlyIndexCtrl',
        [ '$scope', '$location', 'PayMonthlyService',
                function($scope, $location, PayMonthlyService) {
                    $scope.items = PayMonthlyService.index();
                } ])

.controller('AccountsListCtrl',
        [ '$scope', '$location', 'AccountsService', function($scope, $location, AccountsService) {
            $scope.accounts = AccountsService.all();
            $scope.orderProp = 'account';
            $scope.goRegister = function() {
                $location.path('/accounts/register');
            };
        } ])

.controller(
        'AccountsDetailCtrl',
        [ '$scope', '$routeParams', 'AccountsService',
                function($scope, $routeParams, AccountsService) {
                    $scope.account = null;
                    $scope.account = Accounts.get($routeParams.accountId);
                } ])

.controller(
        'AccountsRegisterCtrl',
        [ '$scope', '$location', 'md5', 'AccountsService',
                function($scope, $location, md5, AccountsService) {
                    $scope.register = function() {
                        $scope.account.password = md5.createHash($scope.passwd || '');
                        AccountsService.save($scope.account);
                        $location.path('/accounts');
                    };
                } ]);
