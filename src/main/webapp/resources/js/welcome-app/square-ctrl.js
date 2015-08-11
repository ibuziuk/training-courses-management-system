'use strict';

var squareCtrl = angular.module('squareCtrl', []);

squareCtrl.controller('squareCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	var getNums = function () {
		$http.get('rest/number').then(function (obj) {
			$scope.hot = obj.data[0];
			$scope.new = obj.data[1];
			$scope.recommend = obj.data[2];
			$scope.general = obj.data[3];

			$timeout(getNums, 10000);

		}).catch(function (err) {
			console.log(err.statusText);
			getNums();
		});
	};

	getNums();
}]);