'use strict';

var calendarController = angular.module('calendarController', []);

calendarController.controller('calendarController', ['$scope', '$http', 'event', function($scope, $http, event){
    var vm = this;

    vm.calendarDay = new Date();
    vm.calendarView = 'month';

    $http.get('/rest/calendar').then(function(response) {
        vm.events = event.parse(response.data);
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