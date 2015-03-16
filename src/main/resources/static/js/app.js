/**
  xonda
*/
var stockReaderApp=angular.module('stockReaderApp', []);

stockReaderApp.controller('stockReaderController',['$scope', '$http', function(scope, http){
    scope.mostraVolume=false;
    scope.mostraMovimento=false;

    scope.showAll = function() {
        scope.mostraMovimento = true;
        scope.mostraVolume=false;
        http.get('/all').success(function(data){
            scope.movimentacao = data;
        });
    };

    scope.fechamentoMaximo = function() {
        scope.mostraMovimento = true;
        scope.mostraVolume=false;
        http.get('/fechamentoMaximo').success(function(data){
            scope.movimentacao = data;
        });
    };

    scope.fechamentoMinimo = function() {
        scope.mostraMovimento = true;
        scope.mostraVolume=false;
        http.get('/fechamentoMinimo').success(function(data){
            scope.movimentacao = data;
        });
    };

    scope.retornoMaximo = function() {
        scope.mostraMovimento = true;
        scope.mostraVolume=false;
        http.get('/retornoMaximo').success(function(data){
            scope.movimentacao = data;
        });
    };

    scope.retornoMinimo = function() {
        scope.mostraMovimento = true;
        scope.mostraVolume=false;
        http.get('/retornoMinimo').success(function(data){
            scope.movimentacao = data;
        });
    };

    scope.volumeMedio = function() {
        scope.mostraMovimento = false;
        scope.mostraVolume=true;
        http.get('/volumeMedio').success(function(data){
            scope.volumes = data;
        });
    };

//            scope.showAll();
}]);

