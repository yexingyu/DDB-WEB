angular.module('ddbApp.controllers', [ 'angular-md5' ])

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
