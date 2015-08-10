'use strict';

angular.module('recommendationTab', [])
		.controller('recommendationController', ['$scope', '$location', 'tableService', 'ngTableParams', '$anchorScroll', function ($scope, $location, tableService, ngTableParams, $anchorScroll) {
			$scope.url = {
				recommend: 'rest/training/recommendations',
				tags: 'rest/training/userTags'
			};
			$scope.defaultConfig = {
				page: 1,
				count: 10
			};
			$scope.loading = true;

			tableService.getTags($scope.url.tags)
					.then(function (data) {
						$scope.tags = tableService.parseTags(data.data);
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});

			$scope.tableParams = new ngTableParams($scope.defaultConfig, {
				counts: [10, 25, 50, 100],
				getData: function ($defer, params) {
					tableService.getRecommend($scope.url.recommend)
							.then(function (data) {
								var tmp = tableService.parse(data.data);
								$defer.resolve(tmp.list);
							}, function (data) {
								console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
							})
							.finally(function () {
								$scope.loading = false;
							});
				}
			});

		}]);