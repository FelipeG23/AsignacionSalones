/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var listaSalones = new Array();


var app = angular.module('angularApp');

app.controller("AsignacionManualController", ['$scope', 'materiasConsulta', '$http', function ($scope, materiasConsulta, $http) {
        $scope.materiasAll = materiasConsulta.materiasConsulta;
        var json = eval($scope.materiasAll);
        iniciarAutocomplete(json);
        traerSalones();


        function traerSalones() {
            var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/SalonesService/consultarSalones/";
            $http({
                method: 'GET',
                url: pUrl
            }).then(function (response) {
                $scope.listaSalones = response.data.objeto;
            }).catch(function (err) {
                alert(err);
            });
        }



        $scope.filtrarMaterias = function () {
            var item = json.find(item => item.nombreMateria === $("#materias").val());

            $scope.codigoMateria = item.codigo;
        };

        $scope.consultarAsignacionXMateria = function () {
            if ($("#materias").val() == "" || $("#materias").val() == undefined) {
                swal(
                        'Advertencia',
                        'Digite una materia',
                        'warning'
                        );
                return false;
            }
            if ($("#fecha").val() == "" || $("#fecha").val() == undefined) {
                swal(
                        'Advertencia',
                        'Digite una fecha y hora',
                        'warning'
                        );
                return false;
            }
            var param;
            $scope.filtrarMaterias();
            var envio = new Object();
            envio.materia = $scope.codigoMateria;
            envio.fecha = replaceAll($scope.fecha, "/", "-");
            param = JSON.stringify(envio);
            var pUrl = "" + location.protocol + "//" + location.host + "/Proyecto/v1/AsignacionService/consultarAsignacionxMateria/" + param;
            $http({
                method: 'GET',
                url: pUrl
            }).then(function (response) {
                if (response.data.objeto != "Objeto Nulo") {
                    var lista = response.data.objeto;
                    listaSalones = lista;
                    var butones = "";
                    var salonesHTML = "";
                    var divPrincipal = $("#resultados");
                    divPrincipal.empty();
                    $(lista).each(function (index, element) {
                        salonesHTML = "";
                        butones += '<br/>';
                        butones += '<button data-toggle="collapse" class="btn btn-primary" style="margin-right: 40%;" data-target="#tabla' + index + '">Ver detalle +</button>';
                        butones += '<br/>';
                        salonesHTML += '<select style="height: 30px;" class="form-control" id="select' + index + '">';
                        $($scope.listaSalones).each(function (index, element) {
                            salonesHTML += '<option value=' + element.codigo + '>' + element.nombre + '</option>';
                        });
                        salonesHTML += '</select>';
                        butones += '<table id="tabla' + index + '" style="width: 50%;" class="table table-striped collapse"><tr style="text-align: center"><td colspan="4"><h1>MATERIA</h1></td></tr><tr><td>Código:</td> <td>' + element.codigoMateria + '</td><td>Nombre:</td><td>' + element.nombreMateria + '</td></tr><tr><td>Fecha:</td><td>' + element.fechaAsignada + '</td><td>Dia Semana:</td><td>' + element.diaSemana + '</td></tr><tr><td>Hora Inicio:</td><td>' + element.horaInicio + '</td><td>Hora Fin:</td><td>' + element.horaFin + '</td></tr><tr> <td>Salón:</td><td  colspan="3">' + salonesHTML + '</td></tr><tr><td><button class="btn btn-danger" onClick="eliminarSalon(' + index + ')" >Eliminar Salón</button><button class="btn btn-success botonActualizar" type="button" onclick="actualizarSalon(\''+index+'\')" >Actualizar Salón</button></td></tr></table>';
                        butones += '<br/>';


                    });
                    divPrincipal.html(butones);
                    $(".collapse").collapse();

                }

            }).catch(function (err) {
                alert(err);
            });

        };
        function replaceAll(str, find, replace) {
            return str.replace(new RegExp(escapeRegExp(find), 'g'), replace);
        }
        function escapeRegExp(str) {
            return str.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
        }
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
        }
        ;


    }]);