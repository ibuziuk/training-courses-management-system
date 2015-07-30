'use strict';

angular.module('tabsControllers', [])
		.controller('futureController', ['$scope', '$http', 'tableService', 'ngTableParams', function ($scope, $http, tableService, ngTableParams) {
			$scope.trainings = [];
			$scope.type = '';
			$scope.futureRequest = '';

			$http.get('/rest/training/future').then(function (result) {
				$scope.trainings = tableService.parsing(result.data);

				$scope.tableParams = new ngTableParams({
					page: 1,
					count: 10
				}, {
					total: $scope.trainings.length,
					data: $scope.trainings,
					getData: function ($defer, params) {
						$defer.resolve($scope.trainings.slice((params.page() - 1) * params.count(), params.page() * params.count()));
					}
				});
			});

			$scope.searchType = function (type) {
				$scope.type = type;
			};

			$scope.onSearchButtonClick = function () {
				if (!$scope.futureRequest) {
					$http.get('/rest/training/future').then(function (result) {
						$scope.trainings = tableService.parsing(result.data);
					});
				} else {
					if ($scope.type) {
						$http.get('/rest/training/future/search/' + $scope.type + '/' + $scope.futureRequest).then(function (result) {
							$scope.trainings = tableService.parsing(result.data);
						});
					}
				}
			};

			$scope.$watch('trainings', function () {
				$scope.tableParams.reload();
				$scope.tableParams.getData();
			});
		}])
		.controller('recommendationController', ['$scope', function ($scope) {

		}])
		.controller('historyController', ['$scope', '$http', 'tableService', 'ngTableParams', function ($scope, $http, tableService, ngTableParams) {
			$scope.trainings = [];
			$scope.historyRequest = '';
			$scope.type = '';

			$http.get('/rest/training/past').then(function (result) {
				$scope.trainings = tableService.parsing(result.data);

				$scope.tableParams = new ngTableParams({
					page: 1,
					count: 10
				}, {
					total: $scope.trainings.length,
					data: $scope.trainings,
					getData: function ($defer, params) {
						$defer.resolve($scope.trainings.slice((params.page() - 1) * params.count(), params.page() * params.count()));
					}
				});
			});

			$scope.searchType = function (type) {
				$scope.type = type;
			};

			$scope.onSearchButtonClick = function () {
				if (!$scope.historyRequest) {
					$http.get('/rest/training/past').then(function (result) {
						$scope.trainings = tableService.parsing(result.data);
					});
				} else {
					if ($scope.type) {
						$http.get('/rest/training/past/search/' + $scope.type + '/' + $scope.historyRequest).then(function (result) {
							$scope.trainings = tableService.parsing(result.data);
						});
					}
				}
			};

			$scope.$watch('trainings', function () {
				$scope.tableParams.reload();
				$scope.tableParams.getData();
			});
		}]);