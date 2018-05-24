/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var app = angular.module('angularApp');
app.controller("AsignacionMasivaController", ['$scope', 'programasConsulta', '$http', function ($scope, programasConsulta, $http) {
        $scope.listaProgramas = programasConsulta.programasConsulta;
        var json = eval($scope.listaProgramas);
        console.log(json);
        iniciarAutocomplete(json);


        $scope.asignarMasivo = function () {
            $scope.filtrarProgramas();
            var param;
            var envio = new Object();
            envio.codigo = $scope.codigoPrograma;
            param = JSON.stringify(envio);
            var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/AsignacionService/asignacionMasiva/" + param;
            $http({
                method: 'GET',
                url: pUrl
            }).then(function (response) {
                console.log(response)
            }).catch(function (err) {
                alert(err);
            });
        };


        $scope.filtrarProgramas = function () {
            var item = json.find(item => item.nombre === $("#programa").val());

            $scope.codigoPrograma = item.codigo;
        };

        function iniciarAutocomplete(materias) {
            var options = {
                data: materias,
                template: {
                    type: "description",
                    fields: {
                        description: "codigo"
                    }
                }, list: {
                    maxNumberOfElements: 8,
                    match: {
                        enabled: true
                    },
                    sort: {
                        enabled: true
                    }
                }, getValue: function (element) {
                    return element.nombre;
                },
                theme: "blue-light"

            };
            $("#programa").easyAutocomplete(options);
        }
        ;
    }]);