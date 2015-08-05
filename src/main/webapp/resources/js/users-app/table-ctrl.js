'use strict';

angular.module('table', [])
		.controller('tableController', ['$scope', '$location', 'tableService', 'ngTableParams', function ($scope, $location, tableService, ngTableParams) {
			$scope.url = {
				all: '/rest/user/all',
				search: '/rest/user/search'
			};
			$scope.defaultConfig = {
				page: 1,
				count: 10,
				sorting: {
					name: 'asc'
				},
				searching: {
					type: '',
					value: ''
				}
			};

			$scope.tableParams = new ngTableParams($scope.defaultConfig, {
				counts: [10, 25, 50, 100],
				getData: function ($defer, params) {
					if ($scope.defaultConfig.searching.value) {
						tableService.getSearch($scope.url.search, params.$params)
								.then(function (result) {
									var tmp = tableService.parsing(result.data);
									params.total(tmp.size);
									$defer.resolve(tmp.list);
								});
					} else {
						tableService.getAll($scope.url.all, params.$params)
								.then(function (result) {
									var tmp = tableService.parsing(result.data);
									params.total(tmp.size);
									$defer.resolve(tmp.list);
								});
					}
				}
			});
		}]);