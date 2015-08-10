'use strict';

angular.module('tabs.controllers')
		.controller('refusedController', ['$scope', 'tabsService', function ($scope, tabsService) {
			var type = 'refused';
			$scope.loading = true;
			tabsService.get(type)
					.then(function (data) {
						$scope.refusedTrainings = tabsService.parse(data.data, type);
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});
		}]);