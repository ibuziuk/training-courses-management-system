'use strict';

angular.module('newTrainingApp').controller('pageCtrl', ['$scope', '$http', '$q', 'ngNotify', function ($scope, $http, $q, ngNotify) {
    ngNotify.config({
        theme: 'pastel',
        position: 'bottom',
        duration: 3000,
        type: 'error',
        sticky: true,
        html: false
    });

    /* Checkboxes Page 1 */

    $http.get('/rest/tag').then(function(obj){
        $scope.checkboxTags = obj.data;
        for (var k in $scope.checkboxTags){
            $scope.checkboxTags[k].name = '#' + $scope.checkboxTags[k].name;
        }

        $http.get('/rest/audience').then(function(obj){
            $scope.checkboxAudiences = obj.data;
            $scope.selectAll = false;
            $scope.dateWeekly = '';

            $scope.toggleSeleted = function(){
                $scope.selectAll = !$scope.selectAll;
                for (var i = 0; i < $scope.checkboxAudiences.length; i++) {
                    $scope.checkboxAudiences[i].checked = $scope.selectAll;
                }
            };

            function getIndex(aud){
                for (var i = 0; i < $scope.checkboxAudiences.length; i++) {
                    if ($scope.checkboxAudiences[i].value === aud.value) {
                        return i;
                    }
                }
            }

            $scope.allChecked = function(aud) {
                var j = getIndex(aud);
                $scope.checkboxAudiences[j].checked = !$scope.checkboxAudiences[j].checked;
                for (var i = 0; i < $scope.checkboxAudiences.length; i++) {
                    if (!$scope.checkboxAudiences[i].checked){
                        $scope.selectAll = false;
                        return;
                    }
                }
                $scope.selectAll = true;
            };

            /* Dropdowns Page 2 */

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

            /* Descriptions Page 3 */

            $scope.qDescr = 0;

            $scope.toShow = function() {
                if ($scope.toShowRepet === 'One-off ' || $scope.toShowRepet === 'Weekly ') {
                    $scope.qDescr = 1;
                }
                else if ($scope.toShowRepet === 'Continuous ') {
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
                        'dt': null,
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

            var days = ['Monday ', 'Tuesday ', 'Wednesday ', 'Thursday ', 'Friday ', 'Saturday ', 'Sunday '];

            $scope.chooseWeekDay = function(rep, index){
                $scope.datepickers[index].whatChosen = rep;
                $scope.datepickers[index].toShowWeekDay = days[rep];
            };

            $scope.toShow();

            /* Final (on submit button) */

            $scope.trainingCreation = function(){
                var trainingsRequests = [];

                if ($scope.qDescr === 0){
                    ngNotify.set('You should fill all fields!');
                    return;
                }

                for (var i = 0; i < $scope.qDescr; i++){
                    var training = {};

                    /* Tags */
                    training.tags = [];
                    for (var j in $scope.checkboxTags){
                        if ($scope.checkboxTags[j].checked){
                            training.tags.push($scope.checkboxTags[j].name.substring(1));
                        }
                    }
                    if (training.tags.length === 0){
                        ngNotify.set('You should choose at list one tag!');
                        return;
                    }

                    /* Audience */
                    training.audience = [];
                    for (var j in $scope.checkboxAudiences){
                        if ($scope.checkboxAudiences[j].checked){
                            training.audience.push($scope.checkboxAudiences[j].value);
                        }
                    }
                    if (training.audience.length === 0){
                        ngNotify.set('You should choose an audience for your training!');
                        return;
                    }

                    /* Type of training */
                    if ($scope.toShowType !== 'Select type ') {
                        training.type = !($scope.toShowType === 'Inner training ');
                    }
                    else {
                        ngNotify.set('You should choose the type of your training!');
                        return;
                    }

                    /* Repetition frequency */
                    if ($scope.toShowRepet !== 'Select repetition ') {
                        training.regular = ($scope.toShowRepet === 'Weekly ');
                    }
                    else {
                        ngNotify.set('You should choose the repetition of your training!');
                        return;
                    }

                    if ($scope.toShowRepet === 'Weekly ' || $scope.toShowRepet === 'Continuous '){
                        if ($scope.days === undefined || $scope.days.length === 0){
                            ngNotify.set('You should enter quantity of days!');
                            return;
                        }
                    }

                    /* Training language */
                    if ($scope.toShowLanguage !== 'Select language ') {
                        training.language = $scope.toShowLanguage.substring(0, $scope.toShowLanguage.length - 1);
                    }
                    else {
                        ngNotify.set('You should choose the language of your training!');
                        return;
                    }

                    /* Training name */
                    training.title = $scope.trainingName;

                    if (training.title === undefined || training.title.length === 0){
                        ngNotify.set('You should enter the name of your training!');
                        return;
                    }

                    if ($scope.toShowRepet === 'Continuous '){
                        training.title += (' #' + (i + 1));
                    }

                    /* Training description */
                    training.description = $scope.descriptions[i].text;

                    if (training.description === undefined || training.description.length === 0){
                        ngNotify.set('You should enter the description of your training!');
                        return;
                    }

                    /* Training visitors */
                    training.visitors = $scope.guests;

                    if (training.visitors === undefined || training.visitors.length === 0){
                        ngNotify.set('You should enter quantity of guests!');
                        return;
                    }

                    /* Training duration */
                    training.duration = $scope.duration;

                    if (training.duration === undefined || training.duration.length === 0){
                        ngNotify.set('You should enter duration!');
                        return;
                    }

                    /* Training dates */
                    training.days = '';
                    training.rooms = [];
                    training.date = '';
                    training.times = [];

                    if ($scope.toShowRepet === 'Weekly '){
                        training.date = $scope.dateWeekly.getDate() + '.' + $scope.dateWeekly.getMonth() + '.' + $scope.dateWeekly.getFullYear();
                        for (var j = 0; j < $scope.datepickers.length; j++){
                            if ($scope.datepickers[j].toShowWeekDay !== 'Select day of week ') {
                                training.days += days.indexOf($scope.datepickers[j].toShowWeekDay) + ' ';
                            }
                            else {
                                ngNotify.set('You should select all week days!');
                                return;
                            }
                            training.times.push($scope.datepickers[j].time.getHours() + ':' + $scope.datepickers[j].time.getMinutes());
                            if ($scope.datepickers[j].room !== undefined && $scope.datepickers[j].room.length !== 0) {
                                training.rooms.push($scope.datepickers[j].room);
                            }
                            else {
                                ngNotify.set('You should enter all rooms!');
                                return;
                            }
                        }
                    }
                    else if ($scope.toShowRepet === 'Continuous ' || $scope.toShowRepet === 'One-off '){
                        if ($scope.datepickers[i].room !== undefined && $scope.datepickers[i].room.length !== 0) {
                            training.rooms.push($scope.datepickers[i].room);
                        }
                        else {
                            ngNotify.set('You should enter all rooms!');
                            return;
                        }
                        var date = $scope.datepickers[i].dt.getDate()+'.'+$scope.datepickers[i].dt.getMonth()+'.'+$scope.datepickers[i].dt.getFullYear();
                        training.date = date;
                        training.times.push($scope.datepickers[i].time.getHours() + ':' + $scope.datepickers[i].time.getMinutes());
                    }
                    trainingsRequests[i] = $http.post('/rest/training', training);
                }
                $q.all(trainingsRequests).then(function(results) {
                    /* console.log(results[0].data); */
                    /* TODO training.html construction using results */
                });
            };
        }).catch(function(data){

        }).finally(function(){
        });
    }).catch(function(data){

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