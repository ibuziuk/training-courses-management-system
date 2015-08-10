'use strict';

angular.module('tabs.controllers')
		.controller('feedbacksController', ['$scope', 'tabsService', function ($scope, tabsService) {
			var type = 'feedbacks';
			tabsService.get(type)
					.then(function (data) {
						$scope.feedbacks = tabsService.parse(data.data, type)
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});
		}]);