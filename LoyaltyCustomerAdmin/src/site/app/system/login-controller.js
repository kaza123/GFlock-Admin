(function () {
    'use strict';

    //-----------http controller---------
    angular.module("AppModule")
            .controller("LoginController", function ($http, systemConfig, Factory, $scope, $rootScope, $location, AuthenticationService, $cookieStore) {
                //ui models
                $scope.ui = {};

                // reset login status
                AuthenticationService.ClearCredentials();

                $scope.login = function () {
                    AuthenticationService.Login($scope.username, $scope.password, function (response) {
                        if (response) {

                            AuthenticationService.SetCredentials($scope.username, $scope.password, response.type, response.indexNo, response.branch);
                            $rootScope.user = response;

                            $cookieStore.put('globals', $rootScope.globals);

                            if ($rootScope.globals.currentUser.type === 'shop') {
                                $location.path('/plant');
                                $rootScope.level.one = 'Plant';
                                $rootScope.level.two = 'Plant Issue';
                            } else {
                                $location.path('/home');
                                $rootScope.level.one = 'Home';
                            }
                        } else {
                            console.log('login authentication respond fail;');
                        }
                    });
                };

            });
}());
