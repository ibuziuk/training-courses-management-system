'use strict';

angular.module('tabs.controllers')
		.controller('waitingController', ['$scope', 'tabsService', function ($scope, tabsService) {
			var type = 'waitingList';
			$scope.loading = true;
			tabsService.get(type)
					.then(function (data) {
						$scope.waitingTrainings = tabsService.parse(data.data, type);
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});
		}]);