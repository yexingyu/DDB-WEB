var accountControllers = angular.module('accountsControllers', []);

accountControllers.controller('AccountsListCtrl', [ '$scope', '$http', function($scope, $http) {
    $http.get('/api/accounts').success(function(data) {
        $scope.accounts = data;
    });
    $scope.orderProp = 'account';
} ]);

accountControllers.controller('AccountsDetailCtrl', [ '$scope', '$http', '$routeParams',
        function($scope, $http, $routeParams) {
            $scope.accountId = $routeParams.accountId;
            $http.get('/api/accounts/' + $scope.accountId).success(function(data) {
                $scope.accountDetail = data;
            });
        } ]);

accountControllers.controller('AccountsRegisterCtrl', [ '$scope', '$http', function($scope, $http) {
    this.register = new function($http) {
        alert('Account: ' + account);
    };
} ]);
