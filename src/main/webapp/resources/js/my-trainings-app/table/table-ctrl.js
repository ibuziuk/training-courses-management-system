"use strict";

var table = angular.module('table', []);

table.controller('tableController', ['$scope', '$http', 'tableService', function ($scope, $http, tableService) {
	$scope.trainings = [];

	$http.get('/rest/calendar').then(function (response) {
		$scope.trainings = tableService.parse(response.data);
	}, function () {
		console.log('error');
	});
}]);