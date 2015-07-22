'use strict';

var waitTabCtrl = angular.module('waitTabCtrl', []);

waitTabCtrl.controller('waitTabCtrl', ['$scope', function($scope) {
    $scope.switcher = {view: "table"};
    var vm = this;

    vm.calendarDay = new Date();
    vm.calendarView = 'month';
    vm.events = [
        {
            title: 'Front-end',
            type: 'warning',
            startsAt: new Date(2015,6,1,14),
            endsAt: new Date(2015,6,5,16),
            editable: true,
            deletable: true,
            draggable: false,
            resizable: true,
            //recursOn: 'month',
            //cssClass: 'css-class'
        }, {
            title: 'Back-end',
            type: 'info',
            startsAt: new Date(2015,6,2,13),
            endsAt: new Date(2015,6,6,14),
            editable: true,
            deletable: true,
            draggable: false,
            resizable: true,
            //recursOn: 'month',
            //cssClass: 'css-class'
        }, {
            title: 'SQL',
            type: 'important',
            startsAt: new Date(2015,6,3,12),
            endsAt: new Date(2015,6,7,13),
            editable: true,
            deletable: true,
            draggable: false,
            resizable: true,
            //recursOn: 'month',
            //cssClass: 'css-class'
        }
    ];


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