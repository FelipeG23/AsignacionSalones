/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var app = angular.module("angularApp", ['ui.router', 'ngMaterial', 'md.data.table']);



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
                .state('InsertarUsuario', {
                    url: "/InsertarUsuario",
                    views: {
                        "main": {
                            controller: "UsuarioController",
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
            swal({
                title: 'PROCESANDO INFORMACION...',
                imageUrl: './img/source.gif',
                imageWidth: 358,
                imageHeight: 285,
                imageAlt: 'Custom image'
            });
            var oData = new FormData(document.forms[0]);
            oData.append("anexos", document.forms[0].anexo.files[0]);
            var oReq = new XMLHttpRequest();
            oReq.timeout = 20000000000; // time in milliseconds
            oReq.open("POST", "/Proyecto/subirArchivo", true);
            oReq.onload = function (oEvent) {
                if (oReq.status == 200) {
                    var obj = JSON.parse(oReq.responseText);
                    if (obj.respuesta == 'OK') {
                        swal('Atención', 'Se han procesado :' + obj.objeto + ' registros.', 'success');
                        angular.element($scope.file).val(null);
                        $("#botonUpload").attr("disabled");
                        $("#botonUpload").css("background-color", "#71bfe9");
                        $("#botonUpload").css("cursor", "no-drop");
                        var nombre = "<h4></h4>";
                        $("#nombreArchivo").html(nombre);
                    } else {
                        swal('Atención', obj.objeto, 'error');

                    }
                } else {
                    alert("Error al subir un anexo ");
                }
            };

            oReq.send(oData);
        }
        ;
    }]);
/**
 * Controlador de usuarios
 */
app.controller("UsuarioController", ['$scope', '$http', function ($scope, $http) {
        $scope.registrarUsuario = function () {
            var valida = $scope.validarDatos();
            if (valida) {
                var param;
                var envio = new Object();
                envio.nombre = $scope.nombre;
                envio.apellido = $scope.apellido;
                envio.documento = $scope.documento;
                envio.clave = $scope.clave;
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
                    $scope.clave = "";
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
                swal('Atención', 'El campo documento es obligatorio', 'warning');
                valida = false;
            } else if ($scope.clave == undefined) {
                swal('Atención', 'El campo clave es obligatorio', 'warning');
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
        };
        $scope.logItem = function (item) {
            console.log(item);
//            /// MODAL CON TEMPLATE
            $mdDialog.show({
                locals: {dataToPass: item},
                controller: ['$scope', 'dataToPass', function ($scope, dataToPass) {
                        $scope.item = dataToPass;

                        $scope.closeDialog = function () {
                            $mdDialog.hide();
                        };

                        $scope.eliminarUsuario = function () {
                            console.log($scope.item.codigo);
                            swal(
                                    'Exito',
                                    "Se agrego eliminó un usuario",
                                    'success'
                                    );

                        };
                        $scope.actualizarUsuario = function () {
                            var param;
                            var envio = new Object();
                            envio.nombre = $scope.item.nombre;
                            envio.apellido = $scope.item.apellido;
                            envio.documento = $scope.item.documento;
                            envio.clave = $scope.item.clave;
                            envio.codigo = $scope.item.codigo;
                            param = JSON.stringify(envio);
                            var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/Usuario/actualizarUsuario/" + param;
                            $http({
                                method: 'GET',
                                url: pUrl
                            }).then(function (response) {
                                console.log(response);
                                swal(
                                        'Exito',
                                        "Se agrego actualizo un usuario",
                                        'success'
                                        );
                            }).catch(function (err) {
                                alert(err);
                            });
                        };


                    }],
                templateUrl: 'acciones/utiles/modal/modalInsertarUsuario.html'
            }).then(function (answer) {
                console.log(answer);
            }, function () {
                $scope.selected = new Array();
                console.log('modal cerrado');
            });

        };
        $scope.logOrder = function (order) {
        };
        $scope.logPagination = function (page, limit) {
        };
        $scope.mostrarModalCorreo = function () {
            if ($scope.selected.length == 0) {
                swal('Atención', 'Debe seleccionar al menos un usuario', 'warning');
            } else {
                $("#overlay").show();
                $("#modalCorreo").show();
            }
        };

        $scope.actualizar = function () {
            $scope.selected;
// if auto selection is enabled you will want to stop the event
            // from propagating and selecting the row
            event.stopPropagation();

            /* 
             * messages is commented out because there is a bug currently
             * with ngRepeat and ngMessages were the messages are always
             * displayed even if the error property on the ngModelController
             * is not set, I've included it anyway so you get the idea
             */

            var promise = $mdEditDialog.small({});

            promise.then(function (ctrl) {
                var input = ctrl.getInput();

                input.$viewChangeListeners.push(function () {
                    input.$setValidity('test', input.$modelValue !== 'test');
                });
            });

        };


        $scope.limpiarModalCorreo = function () {
            $('#modalCorreo').hide();
            $('#overlay').hide();
            $('#mensajeCorreo').empty();
            $('#mensajeCorreo').val('');
        };
        $(document).keyup(function (e) {
            if (e.keyCode == 27) {
                $('#modalCorreo').hide();
                $('#overlay').hide();
                $('#mensajeCorreo').empty();
                $('#mensajeCorreo').val('');
            }

        });
    }]);
