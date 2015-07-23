'use strict';

angular.module('pageCtrl', []).controller('pageCtrl', ['$scope', '$http', 'ngNotify', function ($scope, $http, ngNotify) {
    $scope.submitLink = '#';
    
    ngNotify.config({
        theme: 'pastel',
        position: 'bottom',
        duration: 3000,
        type: 'error',
        sticky: true,
        html: false
    });

    ngNotify.set('You shoud choose at list one tag!');

    /* Checkboxes Page 1 */
    
    $scope.checkboxTags = [
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
	];

	$scope.selectAll = false;
	
	$scope.toggleSeleted = function() {
        $scope.selectAll = !$scope.selectAll;
       	for (var i = 0; i < $scope.checkboxAudiences.length; i++) {
            $scope.checkboxAudiences[i].checked = $scope.selectAll;
        }
    };

    function getIndex(aud){
    	for (var i = 0; i < $scope.checkboxAudiences.length; i++) {
    		if ($scope.checkboxAudiences[i].audience == aud.audience)
    			return i;
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
    
    $scope.toShow = function(){     
        if ($scope.toShowRepet == 'One-off ' || $scope.toShowRepet == 'Weekly ')
            $scope.qDescr = 1;
        else if ($scope.toShowRepet == 'Continuous ')
            $scope.qDescr = $scope.days;
    
        $scope.descriptions = [];
        for (var i = 0; i < $scope.qDescr; i++) {
            $scope.descriptions.push({text: ''});
        }
        
        $scope.datepickers = [];
        $scope.qDates = ($scope.toShowRepet == 'Weekly ') ? $scope.days : $scope.qDescr;
        
        for (var i = 0; i < $scope.qDates; i++){
            $scope.datepickers[i] = {'dt' : null, 'time': new Date(), 'toShowWeekDay': 'Monday '};
        }
        
        $scope.today();
        return true;
    };
    
    /* Date and time Page 5 */
    
    $scope.datepickers = [];
    
    /* Date */
    $scope.today = function() {
        for (var i = 0; i < $scope.qDates; i++)
            $scope.datepickers[i].dt = new Date();
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
        var trainings = [];
        
        if ($scope.qDescr == 0){
            //$scope.errorText = 'You shoud choose the repetition of your training!';
            return;
        }
            
        for (var i = 0; i < $scope.qDescr; i++){
            var training = {};

            /* Tags */
            training.tags = [];
            for (var j in $scope.checkboxTags){
                if ($scope.checkboxTags[j].checked == true){
                    training.tags.push($scope.checkboxTags[j].tag.substring(1));
                }
            }

            if (training.tags.length == 0){
                //ngNotify.set('You shoud choose at list one tag!');
                return;
            }
            else {
                //$scope.errorText = '';
            }

            /* Audience */
            training.audience = [];
            for (var j in $scope.checkboxAudiences){
                if ($scope.checkboxAudiences[j].checked == true){
                    training.audience.push($scope.checkboxAudiences[j].audience);
                }
            }

            if (training.audience.length == 0){
                //$scope.errorText = 'You shoud choose an audience for your training!';
                return;
            }
            else {
                //$scope.errorText = '';
            }

            /* Type of training */
            if ($scope.toShowType != 'Select type ')
                training.type = ($scope.toShowType == 'Inner training ') ? false : true;
            else {
                //$scope.errorText = 'You shoud choose the type of your training!';
                return;
            }
            
            /* Repetition frequency */
            if ($scope.toShowRepet != 'Select repetition ')
                training.regular = ($scope.toShowRepet == 'Weekly ') ? true : false;
            else {
                //$scope.errorText = 'You shoud choose the repetition of your training!';
                return;
            }
            
            if ($scope.toShowRepet == 'Weekly ' || $scope.toShowRepet == 'Continuous '){
                if ($scope.days == undefined || $scope.days.length == 0){
                    //$scope.errorText = 'You shoud enter quantity of days!';
                    return;
                }
                else
                    $scope.errorText = '';
            }
            
            /* Training language */
            if ($scope.toShowLanguage != 'Select language ')
                training.language = $scope.toShowLanguage.substring(0, $scope.toShowLanguage.length - 1);
            else {
                //$scope.errorText = 'You shoud choose the language of your training!';
                return;
            }
            
            /* Training name */
            training.title = $scope.trainingName;
            
            if (training.title == undefined || training.title.length == 0){
                //$scope.errorText = 'You should enter the name of your training!';
                return;
            }
            else {
                $scope.errorText = '';
            }
            
            if ($scope.toShowRepet == 'Continuous '){
                training.title += (' #' + i);
            }
            
            /* Training description */
            training.description = $scope.descriptions[i].text;
            
            if (training.description == undefined || training.description.length == 0){
                //$scope.errorText = 'You should enter the description of your training!';
                return;
            }
            else {
                //$scope.errorText = '';
            }
            
            /* Training visitors */
            training.visitors = $scope.guests;
            
            if (training.visitors == undefined || training.visitors.length == 0){
                //$scope.errorText = 'You should enter quantity of guests!';
                return;
            }
            else {
                //$scope.errorText = '';
            }
            
            /* Training duration */
            training.duration = $scope.duration;
            
            if (training.duration == undefined || training.duration.length == 0){
                //$scope.errorText = 'You should enter duration!';
                return;
            }
            else {
                //$scope.errorText = '';
            }
            
            /* Training dates */
            training.days = '';
            training.startTime = [];
            training.rooms = [];
            training.dates = [];
            
            if ($scope.toShowRepet == 'Weekly '){
                for (var j = 0; j < $scope.datepickers.length; j++){
                    training.startTime.push($scope.datepickers[j].time.toLocaleTimeString());
                    training.days += days.indexOf($scope.datepickers[j].toShowWeekDay) + ' ';
                    training.rooms += $scope.datepickers[j].room;
                }
            }
            else if ($scope.toShowRepet == 'Continuous ' || $scope.toShowRepet == 'One-off '){
                for (var j = 0; j < $scope.datepickers.length; j++){
                    training.startTime.push($scope.datepickers[j].time.toLocaleTimeString());
                    training.rooms += $scope.datepickers[j].room;
                    training.dates.push($scope.datepickers[j].dt.toDateString());
                }
            }
            
           var res = $http.post('/rest/training', training);
		   res.success(function(data, status, headers, config) {
               $scope.message = data;
		   });
		   res.error(function(data, status, headers, config) {
               alert( "failure message: " + JSON.stringify({data: data}));
           });
            
           trainings.push(training);
        } 
    }
}]);