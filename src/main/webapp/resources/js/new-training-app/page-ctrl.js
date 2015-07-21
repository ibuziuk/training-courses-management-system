
'use strict';

var pageCtrl = angular.module('pageCtrl', []);

pageCtrl.controller('pageCtrl', function ($scope) {
    /* Checkboxes Page 1 */
    
    window.scope = $scope;
    
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
    }
    
    /* Dropdowns Page 2 */
    
    var repetitions = ['One-off ', 'Weekly ', 'Continuous '];
    var types = ['Inner training ', 'Outer training '];
    var languages = ['English ', 'Russian '];
    
    $scope.toShowRepet = repetitions[0];
    $scope.toShowType = types[0];
    $scope.toShowLanguage = languages[0];

    $scope.chooseRepet = function(rep){
        $scope.toShowRepet = repetitions[rep];
    }

    $scope.chooseType = function(rep){
        $scope.toShowType = types[rep];
    }
  
    $scope.chooseLanguage = function(rep){
        $scope.toShowLanguage = languages[rep];
    }
    
    /* Descriptions Page 3 */
    
    $scope.qDescr = 1;
    $scope.descriptions = [];
    
    $scope.toShow = function(){     
        if ($scope.toShowRepet == 'One-off ' || $scope.toShowRepet == 'Weekly ')
            $scope.qDescr = 1;
        else 
            $scope.qDescr = $scope.days;
    
        for (var i = 0; i < $scope.qDescr; i++) {
            $scope.descriptions.push('');
        }
        
        $scope.qDates = ($scope.toShowRepet == 'Weekly ') ? $scope.days : $scope.qDescr;
        
        for (var i = 0; i < $scope.qDates; i++){
            $scope.datepickers[i] = {'dt' : null, 'time': new Date(), 'toShowWeekDay': 'Monday '};
        }
        
        $scope.today();
        return true;
    }
    
    /* Text-fields Page 4 */
    
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

    $scope.ismeridian = true;
   
    $scope.changed = function () {
        $log.log('Time changed to: ' + $scope.time);
    };
    
    var days = ['Monday ', 'Tuesday ', 'Wednesday ', 'Thursday ', 'Friday ', 'Saturday ', 'Sunday '];
    
    $scope.chooseWeekDay = function(rep, index){
        $scope.datepickers[index].whatChosen = rep;
        $scope.datepickers[index].toShowWeekDay = days[rep];
    }
    
    $scope.toShow();
    
    /* Final (on submit button) */
    
    $scope.trainingCreation = function(){
        var trainings = [];
        
        for (var i = 0; i < $scope.qDescr; i++){
        
            var training = {};

            /* Tags */
            training.tags = [];
            for (var i in $scope.checkboxTags){
                if ($scope.checkboxTags[i].checked == true){
                    training.tags.push($scope.checkboxTags[i].tag.substring(1));
                }
            }

            if (training.tags.length == 0){
                alert('You shoud choose at list one tag!');
                return false;
            }

            /* Audience */
            training.audience = [];
            for (var i in $scope.checkboxAudiences){
                if ($scope.checkboxAudiences[i].checked == true){
                    training.audience.push($scope.checkboxAudiences[i].audience);
                }
            }

            if (training.audience.length == 0){
                alert('You shoud choose an audience for your training!');
                return false;
            }

            /* Type of training */
            training.type = ($scope.toShowType == 'Inner training ') ? false : true;
            
            /* Repetition frequency */
            training.regular = ($scope.toShowRepet == 'Weekly ') ? true : false;
            
            /* Training language */
            training.language = $scope.toShowLanguage.substring(0, $scope.toShowLanguage.length - 1);
            
            /* Training name */
            training.title = $scope.trainingName;
            
            if (training.title.length == 0){
                alert('You should enter the name of your training!');
                return false;
            }
            
            if ($scope.toShowRepet == 'Continuous '){
                training.title += (' #' + i);
            }
            
            /* Training description */
            training.description += $scope.descriptions[i];
            
            if (training.description.length == 0){
                alert('You should enter the name of your training!');
                return false;
            }
            
            /* Training visitors */
            training.visitors = $scope.guests;
            
            if (training.visitors.length == 0){
                alert('You should enter quantity of guests!');
                return false;
            }
            
            /* Training duration */
            training.duration = $scope.duration;
            
            if (training.duration.length == 0){
                alert('You should enter quantity of guests!');
                return false;
            }
            
            /* Training dates */
            training.days = '';
            training.startTime = [];
            training.rooms = [];
            
            if ($scope.toShowRepet == 'Weekly '){
                for (var i = 0; i < $scope.datepickers.length; i++){
                    training.startTime.push($scope.datepickers[i].time.toTimeString());
                    //training.days += 
                }
                    
            }
            
            
            
            trainings.push(training);
        }
        
    }
});