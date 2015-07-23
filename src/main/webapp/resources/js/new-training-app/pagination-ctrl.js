'use strict';

angular.module('paginationCtrl', []).controller('paginationCtrl', function ($scope) {
  $scope.totalItems = 45;
  $scope.currentPage = 1;
  $scope.itemsPerPage = 1;
    
  $scope.dynamic = 20;    
    
  $scope.showDynamic = function(page){
      $scope.dynamic = page * 20;
  };    

  $scope.setPage = function (pageNo) {
    $scope.currentPage = pageNo;
  };
});