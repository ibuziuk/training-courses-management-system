'use strict';

angular.module('table', []).controller('tableController', ['$scope', '$http', '$q', 'tableService', 'ngTableParams', function ($scope, $http, $q, tableService, ngTableParams) {
	$scope.trainings = [];
	$scope.request = '';
	$scope.type = '';

	var trainerGet = $http.get('/rest/calendar/trainer'),
			visitorGet = $http.get('/rest/calendar/visitor');

	$q.all([trainerGet, visitorGet]).then(function (results) {
		$scope.trainings = $scope.trainings.concat(tableService.trainerParsing(results[0].data));
		$scope.trainings = $scope.trainings.concat(tableService.visitorParsing(results[1].data));

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

	$scope.onSearchButtonClick = function () {
		if (!$scope.request) {
			$q.all([trainerGet, visitorGet]).then(function (results) {
				$scope.trainings = [];
				$scope.trainings = $scope.trainings.concat(tableService.trainerParsing(results[0].data));
				$scope.trainings = $scope.trainings.concat(tableService.visitorParsing(results[1].data));
			});
		} else {
			if ($scope.type) {
				console.log($scope.type);
				var trainerGetSearch = $http.get('/rest/calendar/trainer/search/' + $scope.type + '/' + $scope.request),
						visitorGetSearch = $http.get('/rest/calendar/visitor/search/' + $scope.type + '/' + $scope.request);

				$q.all([trainerGetSearch, visitorGetSearch]).then(function (results) {
					$scope.trainings = [];
					$scope.trainings = $scope.trainings.concat(tableService.trainerParsing(results[0].data));
					$scope.trainings = $scope.trainings.concat(tableService.visitorParsing(results[1].data));
				});
			}
		}
	};

	$scope.$watch('trainings', function () {
		$scope.tableParams.reload();
		$scope.tableParams.getData();
	});
}]);