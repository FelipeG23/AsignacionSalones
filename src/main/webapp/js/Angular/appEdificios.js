var app = angular.module('angularApp');
app.controller("EdificiosController", ['$scope', 'edificiosConsulta', '$http', '$mdDialog', function ($scope, edificiosConsulta, $http, $mdDialog) {
        $scope.edificios = edificiosConsulta.edificiosConsulta;
        $scope.selected = [];
        $scope.limitOptions = [5, 10, 15];


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
                        $scope.eliminarEdificio = function () {
                            var param;
                            var envio = new Object();
                            envio.codigo = $scope.item.codigo;
                            param = JSON.stringify(envio);
                            var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/EdificiosService/eliminarEdificio/" + param;
                            $http({
                                method: 'GET',
                                url: pUrl
                            }).then(function (response) {
                                if (response.data.objeto == "OK") {
                                    swal({
                                        title: "Accion Correcta",
                                        text: "El edificio se ha eliminado correctamente!",
                                        type: "success",
                                        showCancelButton: false,
                                        confirmButtonColor: "#DD6B55",
                                        confirmButtonText: "OK",
                                        closeOnConfirm: false
                                    },
                                            function (isConfirm) {
                                                location.reload();

                                            });
                                } else {
                                    swal(
                                            'Advertencia',
                                            response.data.objeto,
                                            'error'
                                            );

                                }


                            }).catch(function (err) {
                                alert(err);
                            });


                        };
                        $scope.actualizarEdificio = function () {
                            var param;
                            console.log($scope.item);
                            var envio = new Object();
                            envio.nombre = $scope.item.nombre;
                            envio.idEdificio = $scope.item.codigo;
                            param = JSON.stringify(envio);
                            var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/EdificiosService/actualizarEdificio/" + param;
                            $http({
                                method: 'GET',
                                url: pUrl
                            }).then(function (response) {
                                console.log(response);
                                swal(
                                        'Exito',
                                        "Se agrego actualizo un edificio",
                                        'success'
                                        );
                            }).catch(function (err) {
                                alert(err);
                            });
                        };

                    }],
                templateUrl: 'acciones/utiles/modal/modalActualizarEdificio.html'
            }).then(function (answer) {
                console.log(answer);
            }, function () {
                $scope.selected = new Array();
            });

        };



        $scope.insertar = function () {
            if ($scope.nombre != undefined) {
                var param;
                var envio = new Object();
                envio.nombre = $scope.nombre;
                param = JSON.stringify(envio);
                var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/EdificiosService/insertarEdificio/" + param;
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
                    }
                }).catch(function (err) {
                    alert(err);
                });
            } else {
                swal(
                        'Advertencia',
                        'Digite un nombre',
                        'warning'
                        );

            }


        };
    }]);