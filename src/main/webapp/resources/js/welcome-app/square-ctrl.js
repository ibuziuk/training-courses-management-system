'use strict';

var squareCtrl = angular.module('squareCtrl', []);

squareCtrl.controller('squareCtrl', ['$scope', function ($scope) {
	$scope.hot = 26;
	$scope.new = 12;
	$scope.recommend = 5;
	$scope.general = 13;
}]);