'use strict';

angular.module('newTrainingApp').controller('pageCtrl', ['$scope', '$http', '$q', '$window', 'ngNotify', function ($scope, $http, $q, $window, ngNotify) {
    ngNotify.config({
        theme: 'pastel',
        position: 'bottom',
        duration: 3000,
        type: 'error',
        sticky: true,
        html: false
    });

    $scope.select2Options = {
        multiple: true
    };

    /* Dropdowns Pages 1-2 */

    var continuous;
    var repetitions = ['One-off ', 'Weekly ', 'Continuous '];
    var types = ['Inner training ', 'Outer training '];
    var languages = ['English ', 'Russian '];

    $scope.toShowRepet = 'Select repetition ';
    $scope.toShowType = 'Select type ';
    $scope.toShowLanguage = 'Select language ';

    $scope.chooseRepet = function(rep){
        $scope.toShowRepet = repetitions[rep];
    };

    $scope.chooseType = function(rep){
        $scope.toShowType = types[rep];
    };

    $scope.chooseLanguage = function(rep){
        $scope.toShowLanguage = languages[rep];
    };

    /* Checkboxes Page 4 */

    $http.get('/rest/tag').then(function(objTag){
        $scope.checkboxTags = objTag.data;
        for (var k in $scope.checkboxTags){
            $scope.checkboxTags[k].name = '#' + $scope.checkboxTags[k].name;
            $scope.checkboxTags[k].id = $scope.checkboxTags[k].name;
        }

        $http.get('/rest/audience').then(function(objAud){
            $scope.checkboxAudiences = objAud.data;
            for (var k in $scope.checkboxAudiences){
                $scope.checkboxAudiences[k].id = $scope.checkboxAudiences[k].value;
            }

            /* Descriptions Page 3 */

            $scope.qDescr = 0;

            $scope.toShow = function() {
                if ($scope.toShowRepet === 'One-off ' || $scope.toShowRepet === 'Weekly ') {
                    continuous = false;
                    $scope.qDescr = 1;
                }
                else if ($scope.toShowRepet === 'Continuous ') {
                    continuous = true;
                    $scope.qDescr = $scope.days;
                }

                $scope.descriptions = [];
                for (var i = 0; i < $scope.qDescr; i++) {
                    $scope.descriptions.push({text: ''});
                }

                $scope.datepickers = [];
                $scope.qDates = ($scope.toShowRepet === 'Weekly ') ? $scope.days : $scope.qDescr;

                for (var i = 0; i < $scope.qDates; i++) {
                    $scope.datepickers[i] = {
                        'dt': new Date(),
                        'time': new Date(),
                        'toShowWeekDay': 'Select day of week ',
                        'room': ''
                    };
                }
                $scope.today();
            };

            /* Date and time Page 5 */

            $scope.datepickers = [];

            /* Date */

            $scope.today = function() {
                for (var i = 0; i < $scope.qDates; i++) {
                    $scope.datepickers[i].dt = new Date();
                }
                $scope.dateStartWeekly = new Date();
                $scope.dateEndWeekly = new Date();
            };

            $scope.toggleMin = function() {
                $scope.minDate = $scope.minDate ? null : new Date();
            };
            $scope.toggleMin();

            $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
            $scope.format = $scope.formats[0];

            $scope.getDayClass = function(date, mode) {
                if (mode === 'day') {
                    var dayToCheck = new Date(date).setHours(0,0,0,0);

                    for (var i = 0; i < $scope.events.length; i++){
                        var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

                        if (dayToCheck === currentDay) {
                            return $scope.events[i].status;
                        }
                    }
                }
                return '';
            };

            /* Time */

            $scope.hstep = 1;
            $scope.mstep = 10;

            $scope.ismeridian = false;

            $scope.updateTime = function() {
                var d = new Date();
                d.setHours(0);
                d.setMinutes(0);
                $scope.duration = d;
            };

            $scope.updateTime();

            var days = ['Monday ', 'Tuesday ', 'Wednesday ', 'Thursday ', 'Friday ', 'Saturday ', 'Sunday '];

            $scope.chooseWeekDay = function(rep, index){
                $scope.datepickers[index].whatChosen = rep;
                $scope.datepickers[index].toShowWeekDay = days[rep];
            };

            $scope.toShow();

            /* Final (on submit button) */

            $scope.step1 = function(){
                $scope.$parent.totalItems = 5;

                if ($scope.trainingName === undefined || $scope.trainingName.length === 0){
                    //ngNotify.set('You should enter the name of your training!');
                    $scope.$parent.totalItems = 5;
                    return;
                }

                /* Type of training */
                if ($scope.toShowType === 'Select type '){
                    //ngNotify.set('You should choose the type of your training!');
                    $scope.$parent.totalItems = 5;
                    return;
                }

                /* Repetition frequency */
                if ($scope.toShowRepet === 'Select repetition '){
                    //ngNotify.set('You should choose the repetition of your training!');
                    $scope.$parent.totalItems = 5;
                    return;
                }

                /* Quantity of days */
                if ($scope.toShowRepet === 'Weekly ' || $scope.toShowRepet === 'Continuous '){
                    if ($scope.days === undefined || $scope.days.length === 0){
                        //ngNotify.set('You should enter quantity of days!');
                        $scope.$parent.totalItems = 5;
                        return;
                    }
                }
                $scope.$parent.totalItems = 15;
            };

            $scope.step2 = function(){
                $scope.$parent.totalItems = 15;

                /* Training language */
                if ($scope.toShowLanguage === 'Select language ') {
                    //ngNotify.set('You should choose the language of your training!');
                    $scope.$parent.totalItems = 15;
                    return;
                }

                for (var i = 0; i < $scope.qDescr; i++) {
                    if ($scope.descriptions[i].text === undefined || $scope.descriptions[i].text.length === 0) {
                        $scope.$parent.totalItems = 15;
                        //ngNotify.set('You should enter the description of your training!');
                        return;
                    }
                }
                $scope.$parent.totalItems = 25;
            };

            $scope.step3 = function(){
                $scope.$parent.totalItems = 25;
                if ($scope.duration.getHours() === 0 && $scope.duration.getMinutes() === 0){
                    //ngNotify.set('You should enter duration!');
                    $scope.$parent.totalItems = 25;
                    return;
                }

                if ($scope.toShowRepet === 'Weekly ') {
                    for (var j = 0; j < $scope.datepickers.length; j++) {
                        if ($scope.datepickers[j].toShowWeekDay === 'Select day of week ') {
                            //ngNotify.set('You should select all week days!');
                            $scope.$parent.totalItems = 25;
                            return;
                        }
                    }
                }
                $scope.$parent.totalItems = 35;
            };

            $scope.trainingCreation = function(){
                var trainingsRequests = [];

                for (var i = 0; i < $scope.qDescr; i++){
                    var training = {};

                    /* Tags */
                    training.tags = $scope.selectedTags;

                    for (var k = 0; k < training.tags.length; k++){
                        training.tags[k] = training.tags[k].substring(1);
                    }

                    if (training.tags.length === 0){
                        ngNotify.set('You should choose at list one tag!');
                        return;
                    }

                    /* Audience */
                    training.audience = $scope.selectedAudiences;

                    if (training.audience.length === 0){
                        ngNotify.set('You should choose an audience for your training!');
                        return;
                    }

                    training.type = !($scope.toShowType === 'Inner training ');
                    training.regular = ($scope.toShowRepet === 'Weekly ');
                    training.language = $scope.toShowLanguage.substring(0, $scope.toShowLanguage.length - 1);
                    training.continuous = continuous;


                    /* Training name */
                    training.title = $scope.trainingName;

                    if ($scope.toShowRepet === 'Continuous '){
                        training.title += (' #' + (i + 1));
                    }

                    /* Training description */
                    training.description = $scope.descriptions[i].text;

                    /* Training visitors */
                    training.visitors = $scope.guests;

                    if (training.visitors === undefined || training.visitors.length === 0){
                        ngNotify.set('You should enter quantity of guests!');
                        return;
                    }

                    /* Training duration */

                    training.duration = $scope.duration.getHours() * 60 + $scope.duration.getMinutes();

                    /* Training dates */
                    training.days = '';
                    training.rooms = [];
                    training.date = '';
                    training.times = [];

                    if ($scope.toShowRepet === 'Weekly '){
                        training.date = $scope.dateStartWeekly.getDate() + '.' + ($scope.dateStartWeekly.getMonth()+1) + '.' + $scope.dateStartWeekly.getFullYear();
                        training.end = $scope.dateEndWeekly.getDate() + '.' + ($scope.dateEndWeekly.getMonth()+1) + '.' + $scope.dateEndWeekly.getFullYear();
                        for (var j = 0; j < $scope.datepickers.length; j++){
                            training.days += days.indexOf($scope.datepickers[j].toShowWeekDay) + ' ';
                            training.times.push($scope.datepickers[j].time.getHours() + ':' + $scope.datepickers[j].time.getMinutes());
                            if ($scope.datepickers[j].room !== undefined && $scope.datepickers[j].room.length !== 0) {
                                training.rooms.push($scope.datepickers[j].room);
                            }
                        }
                    }
                    else if ($scope.toShowRepet === 'Continuous ' || $scope.toShowRepet === 'One-off '){
                        if ($scope.datepickers[i].room !== undefined && $scope.datepickers[i].room.length !== 0) {
                            training.rooms.push($scope.datepickers[i].room);
                        }
                        var date = $scope.datepickers[i].dt.getDate()+'.'+($scope.datepickers[i].dt.getMonth()+1)+'.'+$scope.datepickers[i].dt.getFullYear();
                        training.date = date;
                        training.times.push($scope.datepickers[i].time.getHours() + ':' + $scope.datepickers[i].time.getMinutes());
                    }
                    trainingsRequests[i] = $http.post('/rest/training', training);
                }
                $q.all(trainingsRequests).then(function(results) {
                    $window.location.href = window.location.origin + '/training/' + results[0].data.id;
                }, function(data){
                    ngNotify.set(data);
                });
            };
        }).catch(function(data){
            ngNotify.set(data.data);
        }).finally(function(){
        });
    }).catch(function(data){
        ngNotify.set(data.data);
    }).finally(function(){
    });

    /*$scope.checkboxTags = [
		{ type: 'checkbox', tag: '#Java', checked: false},
		{ type: 'checkbox', tag: '#PHP', checked: false},
		{ type: 'checkbox', tag: '#JavaScript', checked: false},
		{ type: 'checkbox', tag: '#C++', checked: false},
		{ type: 'checkbox', tag: '#Scala', checked: false},
		{ type: 'checkbox', tag: '#Go', checked: false},
		{ type: 'checkbox', tag: '#English', checked: false}
	];

	$scope.checkboxAudiences = [
		{ type: 'checkbox', audience: 'Java developers', checked: false},
		{ type: 'checkbox', audience: 'PHP developers', checked: false},
		{ type: 'checkbox', audience: 'JavaScript developers', checked: false},
		{ type: 'checkbox', audience: 'C++ developers', checked: false},
		{ type: 'checkbox', audience: 'Scala developers', checked: false},
		{ type: 'checkbox', audience: 'Go developers', checked: false},
        { type: 'checkbox', audience: 'Testers', checked: false}
	]; */
}]);