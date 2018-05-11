/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var app = angular.module('angularApp');
app.controller("AsignacionManualController", ['$scope', 'materiasConsulta', '$http', function ($scope, materiasConsulta, $http) {
        $scope.materiasAll = materiasConsulta.materiasConsulta;
        var json = eval($scope.materiasAll);
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
                    return element.nombreMateria;
                },
                theme: "blue-light"

            };
            $("#materias").easyAutocomplete(options);
        };
       

    }]);