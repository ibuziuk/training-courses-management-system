'use strict';

var allTrainingsApp = angular.module('allTrainingsApp', ['collapseCtrl', 'ngRoute', 'tabsCtrl', 'ui.bootstrap']);

allTrainingsApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/my-trainings-as-trainer', {
                templateUrl: 'my-trainings-template.html',
                controller: 'asTrainerCtrl'
            }).
            when('/my-trainings-as-guest', {
                templateUrl: 'my-trainings-template.html',
                controller: 'asGuestCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }
]);

allTrainingsApp.controller('asTrainerCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.header = "Trainer";
}]);

allTrainingsApp.controller('asGuestCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.header = "Guest";
}]);