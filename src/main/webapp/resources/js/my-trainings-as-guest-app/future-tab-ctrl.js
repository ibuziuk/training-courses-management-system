'use strict';

var futureTabCtrl = angular.module('futureTabCtrl', ['ngTable']);

futureTabCtrl.controller('futureTabCtrl', ['$scope', '$http', function($scope, $http, moment) {
    $scope.switcher = {view: "table"};
    var vm = this,
        i;

    vm.calendarDay = new Date();
    vm.calendarView = 'month';
    $scope.events = [];

    $http.get('/rest/calendar').success (function(data, status, headers, config) {

        for (i = 0; i < data.length; i++) {
            vm.events.push(
                {
                    title: data[i].title,
                    type: 'info',
                    startsAt: new Date(data[i].date),
                    endsAt: new Date(data[i].date + data[i].duration*3600000),
                    editable: false,
                    deletable: false,
                    draggable: false,
                    resizable: false,
                    incrementsBadgeTotal: true
                }
            );
        }


        console.log((new Date(data[0].date)).toString());
        console.log(data);
        console.log(status);
    }).error(function() {
        vm.events = [];
    });


    /***title**: 'My event title', // The title of the event
     **type**: 'info', // The type of the event (determines its color). Can be important, warning, info, inverse, success or special
     **startsAt**: new Date(2013,5,1,1), // A javascript date object for when the event starts
     **endsAt**: new Date(2014,8,26,15), // Optional - a javascript date object for when the event ends
     **editable**: false, // If edit-event-html is set and this field is explicitly set to false then dont make it editable.
     **deletable**: false, // If delete-event-html is set and this field is explicitly set to false then dont make it deleteable
     **draggable**: true, //Allow an event to be dragged and dropped
     **resizable**: true, //Allow an event to be resizable
     **incrementsBadgeTotal: true, //If set to false then will not count towards the badge total amount on the month and year view
     **recursOn**: 'year', // If set the event will recur on the given period. Valid values are year or month
     **cssClass**: 'a-css-class-name' //A CSS class (or more, just separate with spaces) that will be added to the event when it is displayed on each view. Useful for marking an event as selected / active etc*/

    //$http.get('/training/my/q').then(function(data){
    //    service.getJson2(data.id);
    //}).catch(function(data){
    //    $scope.error = data.message;
    //}).finally(function(){
    //    $scope.loading = false;
    //});
    //
    //$q.all(
    //    json1: $http.get().
    //)
    //
    //getJson: function(){
    //    return $http.get()
    //}


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