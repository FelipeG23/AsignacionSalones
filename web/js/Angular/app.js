/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var app = angular.module("angularApp", ['ui.router']);


/**
 * 
 */
app.factory("empresas", ["$http", function ($http) {
        var em = {
            empresas: []
        };

        em.getEmpresas = function () {
            return $http.get("/Proyecto/v1/empresas/consultarEmpresas/").then(function (data) {
                angular.copy(data.data.objeto, em.empresas);
            }).catch(function (error) {
                console.log(error);
            });
        };

        return em;
    }]);



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
                            controller: "UsuarioController",
                            templateUrl: "/Proyecto/acciones/usuarios/InsertarUsuario.html"
                        }},
                    resolve: {
                        postPromise: ['empresas', function (empresas) {
                                return empresas.getEmpresas();
                            }]
                    }
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
                    var obj = JSON.parse(oReq.responseText);
                    if (obj.respuesta == 'OK') {
                        swal('Atención', 'Se han procesado :' + obj.objeto + ' registros.', 'success');
                        angular.element($scope.file).val(null);
                    } else {
                        swal('Atención', obj.objeto, 'error');

                    }
                } else {
                    alert("Error al subir un anexo ");
                }
            };
            oReq.onreadystatechange = function (oEvent) {
                if (oReq.readyState === 4) {
                    var obj = JSON.parse(oReq.responseText);
                    if (obj.respuesta === 200) {
                        console.log(obj.objeto);
                    } else {
                        console.log(obj.objeto);
                    }
                }
            };
            oReq.send(oData);
        }
        ;
    }]);
/**
 * Controlador de usuarios
 */
app.controller("UsuarioController", ['$scope', 'empresas','$http', function ($scope, empresas,$http) {
        $scope.empresas = empresas.empresas;

        $scope.registrarUsuario = function () {
            var valida = $scope.validarDatos();
            if (valida) {
                var param;
                var envio = new Object();
                envio.nombre = $scope.nombre;
                envio.apellido = $scope.apellido;
                envio.documento = $scope.documento;
                envio.sexo = document.forms[0].sexo.value;
                envio.email = $scope.email;
                envio.empresa = $scope.empresa;
                param = JSON.stringify(envio);
                var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/Usuario/insertarUsuario/" + param;
                $http({
                    method: 'GET',
                    url: pUrl
                }).then(function (response) {
                    console.log(response);
                    swal(
                            'Exito',
                            "Se agrego correctamente un usuario",
                            'success'
                            );
                    $scope.nombre = "";
                    $scope.apellido = "";
                    $scope.documento = "";
                    $scope.email = "";
                    $scope.empresa = "-1";
                }).catch(function (err) {
                    alert(err);
                });
            }
        };
        $scope.validarDatos = function () {
            var valida = true;
            if ($scope.nombre == undefined) {
                swal('Atención', 'El campo nombre es obligatorio', 'warning');
                valida = false;
            } else if ($scope.apellido == undefined) {
                swal('Atención', 'El campo apellido es obligatorio', 'warning');
                valida = false;
            } else if ($scope.documento == undefined) {
                swal('Atención', 'El campo apellido es obligatorio', 'warning');
                valida = false;
            } else if ($scope.email == undefined) {
                swal('Atención', 'El campo email es obligatorio', 'warning');
                valida = false;
            } else if ($scope.email == '-1') {
                swal('Atención', 'Debe seleccionar una empresa', 'warning');
                valida = false;
            }
            return valida;
        };
    }]);
