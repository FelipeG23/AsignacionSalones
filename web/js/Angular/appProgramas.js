var app = angular.module('angularApp');
app.controller("ProgramasController", ['$scope', 'programasConsulta', '$http', '$mdDialog', function ($scope, programasConsulta, $http, $mdDialog) {
        $scope.programas = programasConsulta.programasConsulta;
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
            $mdDialog.show({
                locals: {dataToPass: item},
                controller: ['$scope', 'dataToPass', function ($scope, dataToPass) {
                        $scope.item = dataToPass;
                        $scope.eliminarPrograma = function () {
                            var param;
                            console.log($scope.item.codigo);
                            var envio = new Object();
                            envio.codigo = $scope.item.codigo;
                            param = JSON.stringify(envio);
                            var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/Programas/eliminarPrograma/" + param;
                            $http({
                                method: 'GET',
                                url: pUrl
                            }).then(function (response) {

                                swal({
                                    title: "Accion Correcta",
                                    text: "El programa se ha eliminado correctamente!",
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
                        $scope.actualizarPrograma = function () {
                            var param;
                            console.log($scope.item);
                            var envio = new Object();
                            envio.nombre = $scope.item.nombre;
                            envio.codigo = $scope.item.codigo;
                            param = JSON.stringify(envio);
                            var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/Programas/actualizarPrograma/" + param;
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
                templateUrl: 'acciones/utiles/modal/modalActualizarPrograma.html'
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
                var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/Programas/insertarPrograma/" + param;
                $http({
                    method: 'GET',
                    url: pUrl
                }).then(function (response) {
                    if (response.data.objeto == 'OK') {
                        swal(
                                'Correcto',
                                'Se ha insertado un programa correctamente',
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