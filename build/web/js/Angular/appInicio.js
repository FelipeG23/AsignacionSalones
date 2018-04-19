/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var app = angular.module("loginApp", ['ui.router', 'ngMaterial']);

app.config([
    '$stateProvider',
    '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
        $stateProvider
                .state('Login', {
                    url: "/Login",
                    views: {
                        "main": {
                            templateUrl: "/Proyecto/index.html"
                        }}
                })
                .state('Inicio', {
                    url: "/Inicio",
                    views: {
                        "main": {
                            templateUrl: "/Proyecto/Principal.html#/Inicio"
                        }}
                });
        $urlRouterProvider.otherwise("/Login");
    }
]);

app.controller("LoginController", ['$scope', '$http', function ($scope, $http) {
        var correcto = true;
        $scope.validarCampos = function () {
            if ($scope.usuario == "") {
                swal(
                        'Error',
                        'Digite la cedula',
                        'error'
                        );
                correcto = false;
            } else if ($scope.clave == "") {
                swal(
                        'Error',
                        'Digite la clave',
                        'error'
                        );
                correcto = false;
            }

        };


        $scope.login = function () {
            $scope.validarCampos();
            if (correcto) {
                var param;
                var envio = new Object();
                envio.usuario = $scope.usuario;
                envio.clave = $scope.clave;
                param = JSON.stringify(envio);
                var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/Usuario/consultarLogin/" + param;
                $http({
                    method: 'GET',
                    url: pUrl
                }).then(function (response) {
                    if (response.data.objeto == true) {
                        var host = window.location.origin + window.location.pathname + "Principal.html#/Inicio";
                        location.replace(host);
                    } else {
                        swal(
                                'Error',
                                'Datos Incorrectos , intente  nuevamente',
                                'error'
                                );

                    }
                }).catch(function (err) {
                    alert(err);
                });
            }


        };
    }]);