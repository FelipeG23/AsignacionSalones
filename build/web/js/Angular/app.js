/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var app = angular.module("angularApp", ['ui.router', 'ngMaterial', 'md.data.table']);


/**
 * Factoria de empresas
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
/**
 * Factoria de usuarios
 */
app.factory("usuariosConsulta", ["$http", function ($http) {
        var us = {
            usuariosConsulta: []
        };

        us.getUsuarios = function () {
            return $http.get("/Proyecto/v1/Usuario/consultarTodos/").then(function (data) {
                angular.copy(data.data.objeto, us.usuariosConsulta);
            }).catch(function (error) {
                console.log(error);
            });
        };

        return us;
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
                .state('ConsultaUsuarios', {
                    url: "/ConsultaUsuarios",
                    views: {
                        "main": {
                            controller: "UsuarioConsultaController",
                            templateUrl: "/Proyecto/acciones/usuarios/ConsultaUsuarios.html"
                        }},
                    resolve: {
                        postPromise: ['usuariosConsulta', function (usuariosConsulta) {
                                return usuariosConsulta.getUsuarios();
                            }]
                    }
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
app.controller("UsuarioController", ['$scope', 'empresas', '$http', function ($scope, empresas, $http) {
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
/**
 * Controlador de la consulta de usuarios
 */
app.controller("UsuarioConsultaController", ['$scope', 'usuariosConsulta', '$http', '$mdDialog', function ($scope, usuariosConsulta, $http, $mdDialog) {
        $scope.selected = [];
        $scope.limitOptions = [5, 10, 15];
        $scope.usuariosGeneral = usuariosConsulta.usuariosConsulta;
        /***
         * FUNCIONES NECESARIAS PARA EL FUNCIONAMIENTO 
         * DEL DATA TABLE
         */
        $scope.options = {
            rowSelection: true,
            multiSelect: true,
            autoSelect: true,
            decapitate: false,
            largeEditDialog: false,
            boundaryLinks: false,
            limitSelect: true,
            pageSelect: true
        };

        $scope.query = {
            order: 'nombre',
            limit: 5,
            page: 1
        };


        $scope.toggleLimitOptions = function () {
            $scope.limitOptions = $scope.limitOptions ? undefined : [5, 10, 15];
        };


        $scope.loadStuff = function () {
            $scope.promise = $timeout(function () {
                // loading
            }, 2000);
        }

        $scope.logItem = function (item) {
        };

        $scope.logOrder = function (order) {
        };

        $scope.logPagination = function (page, limit) {
        };

        $scope.enviarCorreo = function () {
            if ($scope.selected.length == 0) {
                swal('Atención', 'Debe seleccionar al menos un usuario', 'warning');
            } else {
                swal({
                    title: "Digite el mensaje que se enviara",
                    html: true,
                    showCancelButton: true,
                    showCloseButton: true,
                    type: 'info',
                    cancelButtonText: 'Cancelar',
                    confirmButtonText: 'Enviar',
                    confirmButtonClass: 'btn btn-success',
                    cancelButtonClass: 'btn btn-danger',
                    text: "<textarea class='form-control' style='resize:none;'>"
                });
            }
        };



    }]);
