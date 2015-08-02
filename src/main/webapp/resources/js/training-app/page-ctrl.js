'use strict';

angular.module('trainingApp').controller('pageCtrl', ['$scope', '$http', 'FileUploader', function ($scope, $http, FileUploader) {
	/* Special arrays for feedback form */
	var impressions = ['Happy, that took part ', 'Not disappointed, that took part ', 'Disappointed, that took part '];
	var intelligibilities = ['Everything was clear ', 'Nothing was clear ', 'Something wasn\'t clear '];
	var interests = ['Wasn\'t interesting at all ', 'Was boring in some moments ', 'Was very interesting '];
	var updates = ['Learnt nothing ', 'Learnt something ', 'Everything was new '];
	var answers = ['Yes ', 'No '];
	var days = ['Monday ', 'Tuesday ', 'Wednesday ', 'Thursday ', 'Friday ', 'Saturday ', 'Sunday '];

	$scope.registerText = '';
	$scope.approveText = '';

	/* File Upload */
	$scope.uploader = new FileUploader();

	/* Accordion */
	$scope.oneAtATime = true;

	/* Rating */
	$scope.rate = 5;
	$scope.max = 10;
	$scope.percent = 100 * ($scope.rate / $scope.max);
	$scope.toShowNoRatings = false;

	$scope.hoveringOver = function (value) {
		$scope.overStar = value;
		$scope.percent = 100 * (value / $scope.max);
	};

	$http.get('/rest' + window.location.pathname).then(function(obj){
		/* Training info */
		console.log(obj.data);

		$scope.vote = obj.data.vote;
		$scope.isAdmin = obj.data.isAdmin;

		$scope.training = {};

		if (obj.data.rating != -1) {
			$scope.training.genRate = obj.data.rating;
			$scope.toShowNoRatings = false;
			$scope.genPercent = 100 * (obj.data.rating / $scope.max);
		}
		else {
			$scope.training.genRate = 0;
			$scope.genPercent = 0;
			$scope.toShowNoRatings = true;
		}

		$scope.training.description = obj.data.training.description;
		$scope.training.title = obj.data.training.title;
		$scope.training.isAproved = obj.data.training.approved;
		$scope.training.register = obj.data.register;
		$scope.training.regular = obj.data.training.regular;

		if (!$scope.training.regular) {
			$scope.training.date = obj.data.training.dateOnString;
			$scope.training.time = obj.data.training.time;
		}
		else {
			$scope.training.start = obj.data.training.start;
			$scope.training.days = obj.data.training.days.substring(0, obj.data.training.days.length - 1).split(" ");
			$scope.training.times = obj.data.training.time.substring(0, obj.data.training.time.length - 1).split(" ");
			console.log($scope.training.times);
			for (var k = 0; k < $scope.training.days.length; k++){
				$scope.training.days[k] = days[$scope.training.days[k]];
				$scope.training.days[k] += ' ' + $scope.training.times[k];
			}
		}

		$scope.training.trainerName = obj.data.training.trainer.firstName + ' ' + obj.data.training.trainer.lastName;

		function trainingApproveLettering() {
			if ($scope.training.isApproved) {
				$scope.approveColor = 'green';
				$scope.approveText = 'The training is approved';
			}
			else {
				$scope.approveColor = 'brown';
				$scope.approveText = 'The training is not approved';
			}
		}

		trainingApproveLettering();

		function trainingRegisterLettering() {
			if ($scope.training.register == 0) {
				$scope.registerColor = 'green';
				$scope.registerText = 'You are going to visit this training';
			}
			else if ($scope.training.register == 1) {
				$scope.registerColor = 'brown';
				$scope.registerText = 'You are in the waiting list';
			}
			else {
				$scope.registerText = '';
			}
		}

		trainingRegisterLettering();

		/* Feedback */
		$scope.myFeedback = {};
		$scope.myFeedbackToSend = {};
		/* Impression */
		$scope.myFeedback.impression = "Leave your impression ";
		$scope.chooseImpression = function (rep) {
			$scope.myFeedback.impression = impressions[rep];
			$scope.myFeedbackToSend.impression = rep;
		};

		/* Intelligibility */
		$scope.myFeedback.intelligibility = "Choose the intelligibility ";
		$scope.chooseIntelligibility = function (rep) {
			$scope.myFeedback.intelligibility = intelligibilities[rep];
			$scope.myFeedbackToSend.intelligibility = rep;
		};

		/* Interest */
		$scope.myFeedback.interest = "Choose the level of interest ";
		$scope.chooseInterest = function (rep) {
			$scope.myFeedback.interest = interests[rep];
			$scope.myFeedbackToSend.interest = rep;
		};

		/* New knowledge */
		$scope.myFeedback.update = "Choose the amount of new information ";
		$scope.chooseUpdate = function (rep) {
			$scope.myFeedback.update = updates[rep];
			$scope.myFeedbackToSend.update = rep;
		};

		/* Effectiveness */
		$scope.myFeedback.effectiveness = "Was the training effective? ";
		$scope.chooseEffectiveness = function (rep) {
			$scope.myFeedback.effectiveness = rep + ' ';
			$scope.myFeedbackToSend.effectiveness = rep;
		};

		/* Recommendation */
		$scope.myFeedback.recommending = "Choose an option ";
		$scope.chooseRecommendation = function (rep) {
			$scope.myFeedback.recommending = answers[rep];
			$scope.myFeedbackToSend.recommending = (rep == 0);
		};

		/* Trainer */
		$scope.myFeedback.trainerRecommending = "Choose an option ";
		$scope.chooseTrainer = function (rep) {
			$scope.myFeedback.trainerRecommending = answers[rep];
			$scope.myFeedbackToSend.trainerRecommending = (rep == 0);
		};

		$scope.training.feedbacks = obj.data.feedbacks;
		$scope.feedbacks = $scope.training.feedbacks.slice(0, 5);

		$scope.pageChanged = function(){
			$scope.feedbacks = $scope.training.feedbacks.slice(($scope.currentPage - 1) * 5, ($scope.currentPage - 1) * 5 + 5);
		};

		$scope.totalItems = $scope.training.feedbacks.length;
		$scope.currentPage = 1;
		$scope.itemsPerPage = 2;

		for (var k = 0; k < $scope.training.feedbacks.length; k++) {
			$scope.training.feedbacks[k].rate = obj.data.feedbacks[k].starCount;
			$scope.training.feedbacks[k].percent = 100 * ($scope.training.feedbacks[k].rate / $scope.max);
			$scope.training.feedbacks[k].impression = impressions[$scope.training.feedbacks[k].impression];
			$scope.training.feedbacks[k].intelligibility = intelligibilities[$scope.training.feedbacks[k].intelligibility];
			$scope.training.feedbacks[k].interest = interests[$scope.training.feedbacks[k].interest];
			$scope.training.feedbacks[k].update = updates[$scope.training.feedbacks[k].update];
			$scope.training.feedbacks[k].recommendation = ($scope.training.feedbacks[k].recommendation) ? 'Yes' : 'No';
			$scope.training.feedbacks[k].trainer = ($scope.training.feedbacks[k].trainer) ? 'Yes' : 'No';
		}

		$scope.sendFeedback = function(){
			$scope.myFeedbackToSend.rate = $scope.myFeedback.rate;
			$http.post('/rest/feedback' + window.location.pathname, $scope.myFeedbackToSend).then(function(obj){
				$scope.training.feedbacks.push($scope.myFeedbackToSend);
			});
		}
	}).catch(function(data){
		console.log(data);
	});

	//$scope.training.feedbacks = [
	//			{user: 'Yanuha', text: 'Amazing training!', rate: 10, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
	//			{user: 'Slavka', text: 'Bad training!', rate: 2, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
	//			{user: 'Yanuha', text: 'Amazing training!', rate: 7, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
	//			{user: 'Slavka', text: 'Bad training!', rate: 8, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
	//			{user: 'Yanuha', text: 'Amazing training!', rate: 9, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
	//			{user: 'Slavka', text: 'Bad training!', rate: 10, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
	//			{user: 'Yanuha', text: 'Amazing training!', rate: 1, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
	//			{user: 'Slavka', text: 'Bad training!', rate: 2, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
	//			{user: 'Yanuha', text: 'Amazing training!', rate: 3, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
	//			{user: 'Slavka', text: 'Bad training!', rate: 4, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
	//			{user: 'Yanuha', text: 'Amazing training!', rate: 5, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
	//			{user: 'Slavka', text: 'Bad training!', rate: 6, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
	//			{user: 'Yanuha', text: 'Amazing training!', rate: 7, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
	//			{user: 'Slavka', text: 'Bad training!', rate: 8, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
	//			{user: 'Yanuha', text: 'Amazing training!', rate: 9, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
	//			{user: 'Slavka', text: 'Bad training!', rate: 10, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false}
	//		];
	//
}]);