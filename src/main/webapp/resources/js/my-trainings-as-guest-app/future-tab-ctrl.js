'use strict';

var futureTabCtrl = angular.module('futureTabCtrl', ['ngTable']);

futureTabCtrl.controller('futureTabCtrl', ['$scope', '$http', function($scope, $http, moment) {
    $scope.switcher = {view: "table"};
    var vm = this;

    vm.calendarDay = new Date();
    vm.calendarView = 'month';

    $http.get('events.json').success (function(data, status, headers, config) {
        vm.events = data;
    }).error(function() {
        vm.events = [];
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

    $scope.tags = [{
        value: '#js',
        color: 'info'
    }, {
        value: '#java',
        color: 'danger'
    }, {
        value: '#c++',
        color: 'warning'
    }];

    $scope.trainings = [{
        title: 'Front-end',
        date: '5 May 2015',
        location: '243',
        trainerName: 'Shchaurouski',
        places: '10/15',
        time: '14:00',
        tags: $scope.tags
    }, {
        title: 'Front-end',
        date: '5 Jan 2015',
        location: '243',
        trainerName: 'Shchaurouski',
        places: '10/15',
        time: '14:00',
        tags: $scope.tags
    }, {
        title: 'Front-end',
        date: '5 June 2015',
        location: '243',
        trainerName: 'Shchaurouski',
        places: '10/15',
        time: '14:00',
        tags: $scope.tags
    }];
}]);