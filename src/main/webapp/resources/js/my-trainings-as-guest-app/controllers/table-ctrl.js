"use strict";

var tableController = angular.module('tableController', []);

tableController.controller('tableController', ['$scope', '$http', 'tableEvent', function ($scope, $http, tableEvent) {
	$scope.trainings = [];

	$http.get('/rest/calendar').then(function (response) {
		$scope.trainings = tableEvent.parse(response.data);
	}, function () {
		console.log('error');
	});
}]);