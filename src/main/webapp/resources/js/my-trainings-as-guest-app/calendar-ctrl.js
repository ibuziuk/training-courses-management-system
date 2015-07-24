'use strict';

var calendarController = angular.module('calendarController', []);

calendarController.controller('calendarController', ['$scope', '$http', 'event', 'moment', function($scope, $http, event, moment){
    var vm = this;

    vm.calendarDay = new Date();
    vm.calendarView = 'month';
	$scope.events = [];

    $http.get('/rest/calendar').then(function(response) {
	    $scope.events = event.parse(response.data);
	    vm.events = $scope.events;
    }, function() {
        console.log('error');
    });

    vm.eventClicked = function(event) {

    };

    vm.eventEdited = function(event) {

    };

    vm.eventDeleted = function(event) {
        var answer = confirm("Do you really want delete this training?");

        if (answer) {
            var x = vm.events.indexOf(event);
            vm.events.splice(x, 1);
        }
    };

    vm.eventTimesChanged = function(event) {

    };

    vm.toggle = function($event, field, event) {
        $event.preventDefault();
        $event.stopPropagation();
        event[field] = !event[field];
    };
}]);