var paginationCtrl = angular.module('paginationCtrl', []);

paginationCtrl.controller('paginationCtrl', function ($scope, $log) {
  $scope.totalItems = 30;
  $scope.currentPage = 1;
  $scope.itemsPerPage = 1;

  //window.scope = $scope;
  $scope.setPage = function (pageNo) {
    $scope.currentPage = pageNo;
  };
});