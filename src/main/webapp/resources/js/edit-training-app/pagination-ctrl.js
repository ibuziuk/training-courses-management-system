'use strict';

angular.module('editTrainingApp').controller('paginationCtrl', function ($scope) {
	$scope.totalItems = 25;
	$scope.currentPage = 1;
	$scope.itemsPerPage = 1;

	$scope.dynamic = 33;

	$scope.showDynamic = function (page) {
		if (page < 3)
			$scope.dynamic = page * 33;
		else
			$scope.dynamic = 100;

	};

	$scope.setPage = function (pageNo) {
		$scope.currentPage = pageNo;
	};
});