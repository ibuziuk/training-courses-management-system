'use strict';

angular.module('tabs.controllers')
		.controller('visitedController', ['$scope', 'tabsService', function ($scope, tabsService) {
			var type = 'visited';
			tabsService.get(type)
					.then(function (data) {
						$scope.visitedTrainings = tabsService.parse(data.data, type);
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});
		}]);