/**
  xonda
*/
var stockReaderApp=angular.module('stockReaderApp', ['ngRoute']);

stockReaderApp.config(function($routeProvider){
   $routeProvider.
       when('/', {
           templateUrl: './table-movimento.html',
           controller: 'stockReaderController'
       }).
       when('/fechamentoMaximo', {
           templateUrl: './table-movimento.html',
           controller: 'fechamentoMaximoController'
       }).
       when('/fechamentoMinimo', {
           templateUrl: './table-movimento.html',
           controller: 'fechamentoMinimoController'
       }).
       when('/retornoMaximo', {
           templateUrl: './table-movimento.html',
           controller: 'retornoMaximoController'
       }).
       when('/retornoMinimo', {
           templateUrl: './table-movimento.html',
           controller: 'retornoMinimoController'
       }).
       when('/volumeMedio', {
           templateUrl: './table-volume.html',
           controller: 'volumeMedioController'
       }).
       otherwise({
           redirectTo: '/'
       });
});

stockReaderApp.controller('stockReaderController',['$scope', '$http', function(scope, http){
    http.get('/all').success(function(data){
        scope.movimentacao = data;
    });
}]);

stockReaderApp.controller('fechamentoMaximoController',['$scope', '$http', function(scope, http) {
    http.get('/fechamentoMaximo').success(function(data){
        scope.movimentacao = data;
    });
}]);

stockReaderApp.controller('fechamentoMinimoController',['$scope', '$http', function(scope, http) {
    http.get('/fechamentoMinimo').success(function(data){
        scope.movimentacao = data;
    });
}]);

stockReaderApp.controller('retornoMaximoController',['$scope', '$http', function(scope, http) {
    http.get('/retornoMaximo').success(function(data){
        scope.movimentacao = data;
    });
}]);

stockReaderApp.controller('retornoMinimoController',['$scope', '$http', function(scope, http) {
    http.get('/retornoMinimo').success(function(data){
        scope.movimentacao = data;
    });
}]);

stockReaderApp.controller('volumeMedioController',['$scope', '$http', function(scope, http) {
    http.get('/volumeMedio').success(function(data){
        scope.volumes = data;
    });
}]);