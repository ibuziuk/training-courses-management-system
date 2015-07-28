'use strict';

var table = angular.module('table', []);

table.controller('tableController', ['$scope', '$http', '$q', 'tableService', function ($scope, $http, $q, tableService) {
	$scope.trainings = [];

	var trainerGet = $http.get('/rest/calendar/trainer'),
			visitorGet = $http.get('/rest/calendar/visitor');

	$q.all([trainerGet, visitorGet]).then(function (results) {
		$scope.trainings = $scope.trainings.concat(tableService.trainerParsing(results[0].data));
		$scope.trainings = $scope.trainings.concat(tableService.visitorParsing(results[1].data));
	});
}]);