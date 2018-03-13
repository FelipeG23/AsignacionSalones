/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var app = angular.module("angularApp", ['ui.router']);

// REDIRECCIONES

app.config([
    '$stateProvider',
    '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
        $stateProvider
                .state('Inicio', {
                    url: "/Inicio",
                    views: {
                        "main": {
                            templateUrl: "/Proyecto/acciones/inicial.html"
                        }}
                })
                .state('UsuariosMasivos', {
                    url: "/UsuariosMasivos",
                    views: {
                        "main": {
                            controller: "MasivosController",
                            templateUrl: "/Proyecto/acciones/usuarios/insertarMasivo.html"
                        }}
                })
                .state('Usuarios', {
                    url: "/Usuarios",
                    views: {
                        "main": {
                            templateUrl: "/Proyecto/acciones/usuarios/InsertarUsuario.html"
                        }}
                });
        $urlRouterProvider.otherwise("/Inicio");
    }
]);


/**
 * Controlador de Masivos
 */
app.controller("MasivosController", ['$scope', function ($scope) {
        $scope.uploadFile = function (files) {
            $scope.file = files;
            if (files.size > 0) {
                var nombre = document.forms[0].anexo.files[0].name;
                var extension = nombre.substring(nombre.lastIndexOf(".") + 1, nombre.lenght).toUpperCase();
                if (extension == 'CSV') {
                    $("#subirImagen").show();
                    $("#botonUpload").removeAttr("disabled");
                    $("#botonUpload").css("background-color", "#0288D1");
                    $("#botonUpload").css("cursor", "pointer");
                    nombre = "<h4>" + nombre + "</h4>";
                    $("#nombreArchivo").html(nombre);
                } else {
                    swal('Error...', 'Debe subir un archivo de tipo .CSV', 'error');
                    angular.element($scope.file).val(null);
                }
            }

        };
        $scope.subirArchivo = function () {
            var oData = new FormData(document.forms[0]);
            oData.append("anexos", document.forms[0].anexo.files[0]);
            var oReq = new XMLHttpRequest();
            oReq.timeout = 2000; // time in milliseconds
            oReq.open("POST", "/Proyecto/subirArchivo", true);
            oReq.onload = function (oEvent) {
                if (oReq.status == 200) {

                } else {
                    alert("Error al subir un anexo ");
                }
            };
            oReq.send(oData);
        }
        ;

    }]);
