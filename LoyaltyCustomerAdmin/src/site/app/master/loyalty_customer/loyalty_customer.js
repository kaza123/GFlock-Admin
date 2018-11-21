(function () {
    angular.module("AppModule")
            .controller("LoyaltyCustomerController", function ($scope, $rootScope, $location, Notification) {
                $scope.ui.init = function () {
//                    if ($rootScope.globals.currentUser.type !== "admin" || $rootScope.globals.currentUser.type !== "shop") {
//                        Notification.error("No Authentication for your login type");
//                        $location.path('/home');
//                    }
                };
                $scope.ui.init();
            });
}());