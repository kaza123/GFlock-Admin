(function () {
    //index module
    angular.module("AppModule", [
        "ngRoute",
        "ngAnimate",
        "ngCookies",
        "ui.bootstrap",
        "ui-notification",
        "homeModule",
        "chart.js"
    ]);

    //constants
    angular.module("AppModule")
            .constant("systemConfig", {
                apiUrl:
                        location.hostname === 'localhost'
                        ? "http://localhost:8085"
                        : location.protocol + "//" + location.hostname + (location.port ? ':' + location.port : '')
            });

    //route config
    angular.module("AppModule")
            .config(function ($routeProvider) {
                $routeProvider
                        //system
                        .when("/", {
                            templateUrl: "app/system/login.html",
                            controller: "LoginController"
                        })
                        .when("/sms_discount", {
                            templateUrl: "app/master/sms_discount/sms_discount.html",
                            controller: "SmsDiscountController"
                        })
                        .when("/loyalty_customer", {
                            templateUrl: "app/master/loyalty_customer/loyalty_customer.html",
                            controller: "LoyaltyCustomerController"
                        })
                        .when("/plant", {
                            templateUrl: "app/master/plant/plant.html",
                            controller: "PlantController"
                        })
                        .when("/home", {
                            templateUrl: "app/home/home.html",
                            controller: "homeController"
                        })
                        .otherwise({
                            redirectTo: "/"
                        });
            });
    angular.module("AppModule")
            .run(function ($rootScope, $location, $cookieStore, $http) {
                // keep user logged in after page refresh
                $rootScope.globals = $cookieStore.get('globals') || {};
                if ($rootScope.globals.currentUser) {
                    $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
                }

                $rootScope.$on('$locationChangeStart', function (event, next, current) {
                    // redirect to login page if not logged in
                    if ($location.path() !== '/' && !$rootScope.globals.currentUser) {
                        $location.path('/');
                    }
                });
            });

    angular.module("AppModule")
            .controller("IndexController", function ($scope, $rootScope, $timeout, Factory, $location) {
                $rootScope.model = {};
                $rootScope.level = {};
                $scope.ui = {};
                $scope.model.user = {};
                $rootScope.model.map = [];
                

                $scope.ui.logout = function () {
                    $rootScope.value = null;
                    $rootScope.globals={};
                    $location.path("/");
                };
                $scope.ui.CliclLevel1=function (name) {
                    $rootScope.level.one=name;
                    $rootScope.level.two='';
                    console.log(name);
                };
                $scope.ui.CliclLevel2=function (name) {
                    $rootScope.level.two=name;
                    console.log(name);
                };

                $scope.ui.init = function () {
//                    $rootScope.level.one='Home';
                };
                $scope.ui.init();
            });
}());