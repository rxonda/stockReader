/**
  xonda
*/
var stockReaderApp=angular.module('stockReaderApp', []);

stockReaderApp.controller('stockReaderController', function($scope, $http){
//            $scope.movimentacao = [
//                {id: 'BRAD', date:'2014-01-01', close: '10.00', volume: '200'}
//            ];
    $http.get('/stocks').success(function(data){
        $scope.movimentacao = data;
    });
});

