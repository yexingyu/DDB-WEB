angular.module('ddbApp.controllers', [ 'angular-md5' ])

.controller('PayMonthlyIndexCtrl',
        [ '$scope', '$location', 'PayMonthly', function($scope, $location, PayMonthly) {
            $scope.items = PayMonthly.index();
        } ])

.controller('AccountsListCtrl',
        [ '$scope', '$location', 'Accounts', function($scope, $location, Accounts) {
            $scope.accounts = Accounts.all();
            $scope.orderProp = 'account';
            $scope.goRegister = function() {
                $location.path('/accounts/register');
            };
        } ])

.controller('AccountsDetailCtrl',
        [ '$scope', '$routeParams', 'Accounts', function($scope, $routeParams, Accounts) {
            $scope.account = null;
            $scope.account = Accounts.get($routeParams.accountId);
        } ])

.controller('AccountsRegisterCtrl',
        [ '$scope', '$location', 'md5', 'Accounts', function($scope, $location, md5, Accounts) {
            $scope.register = function() {
                $scope.account.password = md5.createHash($scope.passwd || '');
                Accounts.save($scope.account);
                $location.path('/accounts');
            };
        } ]);
