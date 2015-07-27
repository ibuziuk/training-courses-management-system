"use strict";

var table = angular.module('table', []);

table.controller('tableController', ['$scope', '$http', 'tableService', function ($scope, $http, tableService) {
	$scope.trainings = [];

	var eventsAsVisitor = [],
			eventsAsTrainer = [],
			trainer = 'trainer',
			visitor = 'visitor';

	$http.get('/rest/calendar/visitor').then(function (response) {
		eventsAsVisitor = tableService.parse(response.data, visitor);
	}, function () {

	});

	$http.get('/rest/calendar/trainer').then(function (response) {
		eventsAsTrainer = tableService.parse(response.data, trainer);
	}, function () {

	});

	$scope.trainings = eventsAsVisitor.concat(eventsAsTrainer);
}]);