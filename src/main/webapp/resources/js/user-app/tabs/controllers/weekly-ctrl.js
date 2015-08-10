'use strict';

angular.module('tabs.controllers')
		.controller('weeklyController', ['$scope', 'tabsService', function ($scope, tabsService) {
			var type = 'weekly';
			tabsService.get(type)
					.then(function (data) {
						$scope.weeklyTrainings = tabsService.parse(data.data, type);
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});
		}]);