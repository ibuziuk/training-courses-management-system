'use strict';

angular.module('accordions', [])
		.controller('accordionsController', ['$scope', 'accordionsService', function ($scope, accordionsService) {
			accordionsService.get().then(function (result) {
				$scope.trainings = accordionsService.parse(result.data);
			});
		}]);