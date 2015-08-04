'use strict';

angular.module('table', [])
		.controller('tableController', ['$scope', '$location', 'tableService', 'ngTableParams', function ($scope, $location, tableService, ngTableParams) {
			$scope.url = '/rest/user/all';
			$scope.defaultConfig = {
				page: 1,
				count: 10,
				sorting: {
					name: 'asc'
				}
			};

			$scope.absUrl = $location.absUrl();

			console.log($scope.absUrl);

			$scope.tableParams = new ngTableParams($scope.defaultConfig, {
				counts: [10, 25, 50, 100],
				getData: function ($defer, params) {
					tableService.get($scope.url, params.$params)
							.then(function (result) {
								var tmp = tableService.parsing(result.data);
								params.total(tmp.size);
								$defer.resolve(tmp.list);
							});
				}
			});
		}]);