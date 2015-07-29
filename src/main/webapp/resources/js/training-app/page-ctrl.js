'use strict';

angular.module('trainingApp').controller('pageCtrl', ['$scope', 'FileUploader', function ($scope, FileUploader) {

	var impressions = ['Happy, that took part ', 'Not disappointed, that took part ', 'Disappointed, that took part '];
	var intelligibilities = ['Everything was clear ', 'Nothing was clear ', 'Something wasn\'t clear '];
	var interests = ['Wasn\'t interesting at all ', 'Was boring in some moments ', 'Was very interesting '];
	var updates = ['Learnt nothing ', 'Learnt something ', 'Everything was new '];
	var answers = ['Yes ', 'No '];

	$scope.uploader = new FileUploader();

	/* Accordion */
	$scope.oneAtATime = true;

	/* Rating */
	$scope.rate = 5;
	$scope.max = 10;
	$scope.percent = 100 * ($scope.rate / $scope.max);

	$scope.hoveringOver = function (value) {
		$scope.overStar = value;
		$scope.percent = 100 * (value / $scope.max);
	};

	/* Training info */
	$scope.training = {title: "Angular.js", isApproved: true, genRate: 9, register: 1, trainerName: 'Alex Kirilchik'};
	$scope.training.feedbacks = [
				{user: 'Yanuha', text: 'Amazing training!', rate: 10, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
				{user: 'Slavka', text: 'Bad training!', rate: 2, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
				{user: 'Yanuha', text: 'Amazing training!', rate: 7, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
				{user: 'Slavka', text: 'Bad training!', rate: 8, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
				{user: 'Yanuha', text: 'Amazing training!', rate: 9, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
				{user: 'Slavka', text: 'Bad training!', rate: 10, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
				{user: 'Yanuha', text: 'Amazing training!', rate: 1, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
				{user: 'Slavka', text: 'Bad training!', rate: 2, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
				{user: 'Yanuha', text: 'Amazing training!', rate: 3, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
				{user: 'Slavka', text: 'Bad training!', rate: 4, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
				{user: 'Yanuha', text: 'Amazing training!', rate: 5, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
				{user: 'Slavka', text: 'Bad training!', rate: 6, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
				{user: 'Yanuha', text: 'Amazing training!', rate: 7, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
				{user: 'Slavka', text: 'Bad training!', rate: 8, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false},
				{user: 'Yanuha', text: 'Amazing training!', rate: 9, impression: 0, intelligibility: 0, interest: 2, update: 2, effectiveness: 5, recommendation: true, trainer: true},
				{user: 'Slavka', text: 'Bad training!', rate: 10, impression: 2, intelligibility: 1, interest: 0, update: 0, effectiveness: 1, recommendation: false, trainer: false}
			];

	$scope.feedbacks = $scope.training.feedbacks.slice(0, 5);

	$scope.pageChanged = function(){
		$scope.feedbacks = $scope.training.feedbacks.slice(($scope.currentPage - 1) * 5, ($scope.currentPage - 1) * 5 + 5);
	};

	$scope.totalItems = $scope.training.feedbacks.length;
	$scope.currentPage = 1;
	$scope.itemsPerPage = 2;

	for (var k = 0; k < $scope.training.feedbacks.length; k++) {
		$scope.training.feedbacks[k].percent = 100 * ($scope.training.feedbacks[k].rate / $scope.max);
		$scope.training.feedbacks[k].impression = impressions[$scope.training.feedbacks[k].impression];
		$scope.training.feedbacks[k].intelligibility = intelligibilities[$scope.training.feedbacks[k].intelligibility];
		$scope.training.feedbacks[k].interest = interests[$scope.training.feedbacks[k].interest];
		$scope.training.feedbacks[k].update = updates[$scope.training.feedbacks[k].update];
		$scope.training.feedbacks[k].recommendation = ($scope.training.feedbacks[k].recommendation) ? 'Yes' : 'No';
		$scope.training.feedbacks[k].trainer = ($scope.training.feedbacks[k].trainer) ? 'Yes' : 'No';
	}

	$scope.genPercent = 100 * ($scope.training.genRate / $scope.max);

	$scope.training.description = 'HTML is great for declaring static documents, but it falters when we try to use it for declaring dynamic views in web-applications. AngularJS lets you extend HTML vocabulary for your application. The resulting environment is extraordinarily expressive, readable, and quick to develop.';

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

	/* Impression */
	$scope.trainingImpression = "Leave your impression ";
	$scope.chooseImpression = function (rep) {
		$scope.trainingImpression = impressions[rep];
	};

	/* Intelligibility */
	$scope.trainingIntelligibility = "Choose the intelligibility ";
	$scope.chooseIntelligibility = function (rep) {
		$scope.trainingIntelligibility = intelligibilities[rep];
	};

	/* Interest */
	$scope.trainingInterest = "Choose the level of interest ";
	$scope.chooseInterest = function (rep) {
		$scope.trainingInterest = interests[rep];
	};

	/* New knowledge */
	$scope.trainingUpdate = "Choose the amount of new information ";
	$scope.chooseUpdate = function (rep) {
		$scope.trainingUpdate = updates[rep];
	};

	/* Effectiveness */
	$scope.trainingEffectiveness = "Was the training effective? ";
	$scope.chooseEffectiveness = function (rep) {
		$scope.trainingEffectiveness = rep + ' ';
	};

	/* Recommendation */
	$scope.trainingRecommendation = "Choose an option ";
	$scope.chooseRecommendation = function (rep) {
		$scope.trainingRecommendation = answers[rep];
	};

	/* Trainer */
	$scope.trainingTrainer = "Choose an option ";
	$scope.chooseTrainer = function (rep) {
		$scope.trainingTrainer = answers[rep];
	};
}]);