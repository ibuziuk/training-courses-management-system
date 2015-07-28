<<<<<<< HEAD
var pageCtrl = angular.module('pageCtrl', []);
pageCtrl.controller('pageCtrl', ['$scope', function($scope){
=======
'use strict';

angular.module('trainingApp').controller('pageCtrl', ['$scope', function($scope){
    /* Accordion */
    $scope.oneAtATime = true;
    
>>>>>>> pages
    /* Rating */
    $scope.rate = 5;
    $scope.max = 10;
    $scope.percent = 100 * ($scope.rate / $scope.max);

    $scope.hoveringOver = function(value) {
        $scope.overStar = value;
        $scope.percent = 100 * (value / $scope.max);
    };
    
    /* Training info */
<<<<<<< HEAD
    $scope.training = {title: "Angular.js", isAproved: true, genRate: 9};
=======
    $scope.training = {title: "Angular.js", isAproved: true, genRate: 9, register: 1};
    $scope.training.feedbacks = [{user: 'Yanuha', text: 'Amazing training!', rate: 10}, {user: 'Slavka', text: 'Bad training!', rate: 2}];
    
    for (var k = 0; k < $scope.training.feedbacks.length; k++){
        $scope.training.feedbacks[k].percent = 100 * ($scope.training.feedbacks[k].rate / $scope.max);
    }
    
>>>>>>> pages
    $scope.genPercent = 100 * ($scope.training.genRate / $scope.max);
    
    $scope.training.description = 'HTML is great for declaring static documents, but it falters when we try to use it for declaring dynamic views in web-applications. AngularJS lets you extend HTML vocabulary for your application. The resulting environment is extraordinarily expressive, readable, and quick to develop.';
    
<<<<<<< HEAD
    if ($scope.training.isAproved){
        $scope.aproveColor = 'green';
        $scope.aproveText = 'Your training is aproved';
    }
    else{
        $scope.aproveColor = 'red';
        $scope.aproveText = 'Your training is not aproved';
    }
=======
    function trainingAproveLettering(){
        if ($scope.training.isAproved){
            $scope.aproveColor = 'green';
            $scope.aproveText = 'The training is aproved';
        }
        else{
            $scope.aproveColor = 'brown';
            $scope.aproveText = 'The training is not aproved';
        }
    }
    
    trainingAproveLettering();
    
    function trainingRegisterLettering(){
        if ($scope.training.register == 0){
            $scope.registerColor = 'green';
            $scope.registerText = 'You are going to visit this training';
        }
        else if ($scope.training.register == 1){
            $scope.registerColor = 'brown';
            $scope.registerText = 'You are in the waiting list';
        }
        else {
            $scope.registerText = '';
        }
    }
    
    trainingRegisterLettering();
    
    /* Feedback */
    
    /* Impression */
    $scope.trainingImpression = "Leave your impression ";
    var impressions = ['Happy, that took part ', 'Not disappointed, that took part ', 'Disappointed, that took part '];
    $scope.chooseImpression = function (rep){
        $scope.trainingImpression = impressions[rep];
    };
    
    /* Intelligibility */
    $scope.trainingIntelligibility = "Choose the intelligibility ";
    var intelligibilities = ['Everything was clear ', 'Nothing was clear ', 'Something wasn\'t clear '];
    $scope.chooseIntelligibility = function (rep){
        $scope.trainingIntelligibility = intelligibilities[rep];
    };
    
    /* Interest */
    $scope.trainingInterest = "Choose the level of interest ";
    var interests = ['Wasn\'t interesting at all ', 'Was boring in some moments ', 'Was very interesting '];
    $scope.chooseInterest = function (rep){
        $scope.trainingInterest = interests[rep];
    };
    
    /* New knowledge */
    $scope.trainingUpdate = "Choose the amount of new information ";
    var updates = ['Learnt nothing ', 'Learnt something ', 'Everything was new '];
    $scope.chooseUpdate = function (rep){
        $scope.trainingUpdate = updates[rep];
    };
    
    /* Effectiveness */
    $scope.trainingEffectiveness = "Was the training effective? ";
    $scope.chooseEffectiveness = function (rep){
        $scope.trainingEffectiveness = rep + ' ';
    };
    
    var answers = ['Yes ', 'No '];
    
    /* Recommendation */
    $scope.trainingRecommendation = "Choose an option ";
    $scope.chooseRecommendation = function(rep){
        $scope.trainingRecommendation = answers[rep];
    };
    
    /* Trainer */
    $scope.trainingTrainer = "Choose an option ";
    $scope.chooseTrainer = function(rep){
        $scope.trainingTrainer = answers[rep];
    };
    
>>>>>>> pages
}]);