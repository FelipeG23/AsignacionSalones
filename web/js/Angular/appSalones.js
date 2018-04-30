var app = angular.module('angularApp');

app.controller("SalonesController", ['$scope', '$http', '$mdDialog', 'edificiosConsulta', function ($scope, $http, $mdDialog, edificiosConsulta) {
        $scope.edificios = edificiosConsulta.edificiosConsulta;


        $scope.insertar = function () {
            if ($scope.nombreSalon != undefined && $scope.nombreSalon != "") {

                if ($scope.capacidad != undefined && $scope.capacidad != null) {

                    if ($scope.selectEdificio != "-1") {

                        var param;
                        var envio = new Object();
                        envio.nombre = $scope.nombre;
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
    }]);