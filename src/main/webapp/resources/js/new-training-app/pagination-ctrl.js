'use strict';

var paginationCtrl = angular.module('paginationCtrl', []);

paginationCtrl.controller('paginationCtrl', function ($scope) {
  $scope.totalItems = 45;
  $scope.currentPage = 1;
  $scope.itemsPerPage = 1;
  
  $scope.setPage = function (pageNo) {
    $scope.currentPage = pageNo;
  };
});