/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var app = angular.module('angularApp');


app.controller("VistazoController", ['$scope', '$http', 'programasConsulta', function ($scope, $http, programasConsulta) {
        $scope.listaProgramas = programasConsulta.programasConsulta;
        var json = eval($scope.listaProgramas);
        iniciarAutocomplete(json);



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
            $("#programas").easyAutocomplete(options);
        }
        ;


    }]);