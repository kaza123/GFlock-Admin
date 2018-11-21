/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
    angular.module("AppModule")
            .filter("fixedLength", function () {
                return function (n, l) {
                    return(1e4 + '' + n).slice(-l);
                };
            });
}());