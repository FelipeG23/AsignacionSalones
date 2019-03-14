var app = angular.module('angularApp');

app.controller("SalonesController", ['$scope', '$http', '$mdDialog', 'edificiosConsulta', 'salonesConsulta', function ($scope, $http, $mdDialog, edificiosConsulta, salonesConsulta) {
        $scope.edificios = edificiosConsulta.edificiosConsulta;
        $scope.todosSalones = salonesConsulta.salonesConsulta;

        $scope.selected = [];
        $scope.limitOptions = [5, 10, 15];


        $scope.insertar = function () {
            if ($scope.nombreSalon != undefined && $scope.nombreSalon != "") {

                if ($scope.capacidad != undefined && $scope.capacidad != null) {

                    if ($scope.selectEdificio != "-1") {

                        var param;
                        var envio = new Object();
                        envio.nombre = $scope.nombreSalon;
                        envio.edificio = $scope.selectEdificio;
                        envio.capacidad = $scope.capacidad;

                        param = JSON.stringify(envio);
                        var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/SalonesService/insertarSalones/" + param;
                        $http({
                            method: 'GET',
                            url: pUrl
                        }).then(function (response) {
                            if (response.data.objeto == 'OK') {
                                swal(
                                        'Correcto',
                                        'Se ha insertado un edificio correctamente',
                                        'success'
                                        );
                                $scope.nombreSalon = "";
                                $scope.selectEdificio = -1;
                                $scope.capacidad = null;
                            } else {
                                swal(
                                        'Atencion',
                                        response.data.objeto,
                                        'error'
                                        );

                            }
                        }).catch(function (err) {
                            alert(err);
                        });








                    } else {
                        swal(
                                'Advertencia',
                                'Seleccione un edificio',
                                'warning'
                                );
                    }


                } else {
                    swal(
                            'Advertencia',
                            'Digite un capacidad',
                            'warning'
                            );

                }


            } else {
                swal(
                        'Advertencia',
                        'Digite un nombre',
                        'warning'
                        );
            }

        };




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
            $http.get("/Proyecto/v1/EdificiosService/consultarEdificios/").then(function (data) {
                $scope.abrirModal(item, data.data.objeto);
            }).catch(function (error) {
                console.log(error);
            });

            $scope.abrirModal = function (item, edificiosConsulta) {
                console.log(edificiosConsulta)
                console.log(item)

                $mdDialog.show({
                    onComplete: afterShowAnimation,
                    locals: {dataToPass: item, listaEdificios: edificiosConsulta},
                    controller: ['$scope', 'dataToPass', 'listaEdificios', function ($scope, dataToPass, listaEdificios) {
                            $scope.edificiosConsulta = listaEdificios;
                            $scope.item = dataToPass;

                            $scope.eliminarEdificio = function () {
                                var param;
                                var envio = new Object();
                                envio.idSalon = $scope.item.codigo;
                                param = JSON.stringify(envio);
                                var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/SalonesService/eliminarSalon/" + param;
                                $http({
                                    method: 'GET',
                                    url: pUrl
                                }).then(function (response) {
                                    swal({
                                        title: "Accion Correcta",
                                        text: "El salon se ha eliminado correctamente!",
                                        type: "success",
                                        showCancelButton: false,
                                        confirmButtonColor: "#DD6B55",
                                        confirmButtonText: "OK",
                                        closeOnConfirm: false
                                    },
                                            function (isConfirm) {
                                                location.reload();

                                            });

                                }).catch(function (err) {
                                    alert(err);
                                });


                            };
                            $scope.actualizarSalon = function () {
                                var param;
                                var envio = new Object();
                                envio.codigo = $scope.item.codigo;
                                envio.nombre = $scope.item.nombre;
                                envio.capacidad = $scope.item.capacidad;
                                envio.edificio = $scope.item.codigoEdificio;
                                param = JSON.stringify(envio);
                                var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/SalonesService/actualizarSalon/" + param;
                                $http({
                                    method: 'GET',
                                    url: pUrl
                                }).then(function (response) {
                                    swal(
                                            'Exito',
                                            "Se agrego actualizo un salon correctamente",
                                            'success'
                                            );
                                }).catch(function (err) {
                                    alert(err);
                                });
                            };

                        }],

                    templateUrl: 'acciones/utiles/modal/modalActualizarSalon.html'
                }).then(function (answer) {
                    console.log(answer);
                }, function () {
                    $scope.selected = new Array();
                });
                function afterShowAnimation(scope, element, options) {
//                    scope.edificioActualiza = scope.item.codigoEdificio;
//                    console.log(scope.edificioActualiza);
                }
            };


        };




    }]);