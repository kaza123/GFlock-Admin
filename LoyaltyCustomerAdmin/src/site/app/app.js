(function () {
    //index module
    angular.module("AppModule", [
        "ngRoute",
        "ngAnimate",
        "ngCookies",
        "ui.bootstrap",
        "ui-notification",
        "homeModule",
        "chart.js",
        "ngFileSaver",
        "FBAngular"
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
                        .when("/loyalty_customer_update", {
                            templateUrl: "app/master/loyalty_customer_update/loyalty_customer_update.html",
                            controller: "LoyaltyUpdateController"
                        })
                        .when("/plant", {
                            templateUrl: "app/master/plant/plant.html",
                            controller: "PlantController"
                        })
                        .when("/calender", {
                            templateUrl: "app/master/calender/calender.html",
                            controller: "CalenterController"
                        })
                        .when("/image_upload", {
                            templateUrl: "app/master/image_upload/image_upload.html",
                            controller: "imageUploadController"
                        })
                        .when("/home", {
                            templateUrl: "app/home/home.html",
                            controller: "homeController"
                        })
                        .when("/stock_balance", {
                            templateUrl: "app/reports/stock_balance/stock_balance.html",
                            controller: "stockBalanceController"
                        })
                        .when("/transaction_summary", {
                            templateUrl: "app/reports/transaction_summary/transaction_summary.html",
                            controller: "transactionSummaryController"
                        })
                        .when("/grn_summary", {
                            templateUrl: "app/reports/grn_summary/grn_summary.html",
                            controller: "grnSummaryController"
                        })
                        .when("/item_wise", {
                            templateUrl: "app/reports/item_wise_sale/item_wise_sale.html",
                            controller: "itemWiseController"
                        })
                        .when("/item_wise_grn", {
                            templateUrl: "app/reports/item_wise_grn/item_wise_grn.html",
                            controller: "itemWiseGrnController"
                        })
                        .when("/payment_summary", {
                            templateUrl: "app/reports/payment_summary/payment_summary.html",
                            controller: "paymentSummaryController"
                        })
                        .when("/invoice_wise", {
                            templateUrl: "app/reports/invoice_wise_sale/invoice_wise_sale.html",
                            controller: "invoiceWiseController"
                        })
                        .when("/category_wise_sale", {
                            templateUrl: "app/reports/category_wise_sale/category_wise_sale.html",
                            controller: "categoryWiseController"
                        })
                        .when("/category_wise_grn", {
                            templateUrl: "app/reports/category_wise_grn/category_wise_grn.html",
                            controller: "categoryWiseGrnController"
                        })
                        .when("/style_wise_sale", {
                            templateUrl: "app/reports/style_wise_sale/style_wise_sale.html",
                            controller: "styleWiseController"
                        })
                        //system sales report
                        .when("/system_sales_report", {
                            templateUrl: "app/reports/system_sales_report/system_sales_report.html",
                            controller: "systemSalesController"
                        })
                        .when("/kpi_style_report", {
                            templateUrl: "app/reports/sales_units_kpi_report/sales_units_kpi_report.html",
                            controller: "kpiStyleController"
                        })
                        .when("/sales_unit_branch_report", {
                            templateUrl: "app/reports/sales_unit_detailed_report_branch/sales_unit_detailed_report_branch.html",
                            controller: "salesUnitDetailedReportBranchController"
                        })
                        .when("/sales_unit_department_report", {
                            templateUrl: "app/reports/sales-unit-department-kpi-report/sales-unit-department-kpi-report.html",
                            controller: "salesUnitDepartmentController"
                        })
                        .when("/sales_unit_designer_report", {
                            templateUrl: "app/reports/sales-unit-designer-kpi-report/sales-unit-designer-kpi-report.html",
                            controller: "kpiDesignerController"
                        })
                        .when("/ipg_report", {
                            templateUrl: "app/reports/ipg_report/ipg_report.html",
                            controller: "ipgController"
                        })
                        .when("/hourly_report", {
                            templateUrl: "app/reports/hourly_report/hourly_report.html",
                            controller: "hourlyReportController"
                        })
                        // transaction
//                        .when("/price_change", {
//                            templateUrl: "app/transaction/price_change/price_change.html",
//                            controller: "PriceChangeController"
//                        })
                        .when("/profile", {
                            templateUrl: "app/master/user_profile/user_profile.html",
                            controller: "UserProfileController"
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
            .filter('startFrom', function () {
                return function (input, start) {
                    start = +start; //parse to int
                    return input.slice(start);
                };
            });


    angular.module("AppModule")
            .controller("IndexController", function ($scope, $rootScope, $timeout, Factory, $location) {
                $rootScope.model = {};
                $rootScope.level = {};
                $scope.ui = {};
                $scope.model.user = {};
                $rootScope.model.map = [];
                $scope.companyName = "ADMIN DASHBOARD";
                var getCompany = "/api/sv/master/company/find-company";


                $scope.ui.logout = function () {
                    $rootScope.value = null;
                    $rootScope.globals = {};
                    $location.path("/");
                };
                $scope.profile = function () {
                    $location.path("/profile");
                };
                $scope.ui.CliclLevel1 = function (name) {
                    $rootScope.level.one = name;
                    $rootScope.level.two = '';
                    console.log(name);
                };
                $scope.ui.CliclLevel2 = function (name) {
                    $rootScope.level.two = name;
                    console.log(name);
                };
                $scope.ui.company = function () {
                    Factory.findAll(getCompany,
                            function (data) {
                                if (data) {
                                    $scope.companyName =data[0].name;
                                } else {
                                    $scope.companyName = "ADMIN DASHBOARD";
                                }
                            },
                            function () {
                                $scope.companyName = "ADMIN DASHBOARD";
                            }
                    );
                };

                $scope.ui.init = function () {
//                    $rootScope.level.one='Home';
                    $scope.ui.company();
                };
                $scope.ui.init();
            });
}());