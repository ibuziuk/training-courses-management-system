'use strict';

angular.module('newTrainingApp').controller('paginationCtrl', function ($scope) {
  window.scope = $scope;

  $scope.totalItems = 5;
  $scope.currentPage = 1;
  $scope.itemsPerPage = 1;
    
  $scope.dynamic = 1;
    
  $scope.showDynamic = function(page){
      $scope.dynamic = page;
  };    

  $scope.setPage = function (pageNo) {
    $scope.currentPage = pageNo;
  };
});