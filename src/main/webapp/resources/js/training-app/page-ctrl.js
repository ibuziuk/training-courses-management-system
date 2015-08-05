'use strict';

angular.module('trainingApp').controller('pageCtrl', ['$scope', '$http', '$window', 'FileUploader', 'ngNotify', function ($scope, $http, $window, FileUploader, ngNotify) {
	/* ngNotify */
	ngNotify.config({
		theme: 'pastel',
		position: 'bottom',
		duration: 3000,
		type: 'error',
		sticky: true,
		html: false
	});

	/* Special arrays for feedback form */
	var impressions = ['Happy, that took part ', 'Not disappointed, that took part ', 'Disappointed, that took part '];
	var intelligibilities = ['Everything was clear ', 'Nothing was clear ', 'Something wasn\'t clear '];
	var interests = ['Wasn\'t interesting at all ', 'Was boring in some moments ', 'Was very interesting '];
	var updates = ['Learnt nothing ', 'Learnt something ', 'Everything was new '];
	var answers = ['Yes ', 'No '];
	var days = ['Monday ', 'Tuesday ', 'Wednesday ', 'Thursday ', 'Friday ', 'Saturday ', 'Sunday '];
	$scope.colors = ['red', 'blue', 'brown', 'green', 'orange', 'black', 'gray'];

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

	/* Getting info about the training */
	$http.get('rest/training/' + window.location.pathname.split('/')[3]).then(function (obj) {

		console.log(obj.data);
		/* Training info */
		$scope.vote = obj.data.vote;
		$scope.isAdmin = obj.data.isAdmin;

		$scope.training = {};

		$scope.openFeedback = function () {
			var date = new Date();
			if (!obj.data.training.regular) {
				return $scope.training.register != 2 && (date > obj.data.training.date);
			}
			else
				return $scope.training.register != 2 && (date > obj.data.training.start);
		};

		$scope.openFeed = $scope.openFeedback();

		var getRating = function (rating) {
			if (rating != -1) {
				$scope.training.genRate = rating;
				$scope.toShowNoRatings = false;
				$scope.genPercent = 100 * (rating / $scope.max);
			}
			else {
				$scope.training.genRate = 0;
				$scope.genPercent = 0;
				$scope.toShowNoRatings = true;
			}
		};

		var trainingCreation = function () {
			$scope.training = obj.data.training;

			getRating(obj.data.rating);

			$scope.training.register = obj.data.register;
			$scope.training.language = obj.data.training.language.value;
			$scope.training.maxVis = obj.data.training.maxVisitorsCount;
			$scope.training.isAproved = obj.data.training.approved;

			if (!$scope.training.regular) {
				$scope.training.date = obj.data.training.dateOnString;
				$scope.training.time = obj.data.training.time;
			}
			else {
				$scope.training.start = obj.data.training.start;
				$scope.training.days = obj.data.training.days.substring(0, obj.data.training.days.length - 1).split(" ");
				$scope.training.times = obj.data.training.time.substring(0, obj.data.training.time.length - 1).split(" ");
				console.log($scope.training.times);
				for (var k = 0; k < $scope.training.days.length; k++) {
					$scope.training.days[k] = days[$scope.training.days[k]];
					$scope.training.days[k] += ' ' + $scope.training.times[k];
				}
			}
			$scope.training.trainerName = obj.data.training.trainer.firstName + ' ' + obj.data.training.trainer.lastName;
		};
		trainingCreation();

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
		var myFeedbackToSend = {};

		/* Impression */
		$scope.myFeedback.impression = "Leave your impression ";
		$scope.chooseImpression = function (rep) {
			$scope.myFeedback.impression = impressions[rep];
			myFeedbackToSend.impression = rep;
		};

		/* Intelligibility */
		$scope.myFeedback.intelligibility = "Choose the intelligibility ";
		$scope.chooseIntelligibility = function (rep) {
			$scope.myFeedback.intelligibility = intelligibilities[rep];
			myFeedbackToSend.intelligibility = rep;
		};

		/* Interest */
		$scope.myFeedback.interest = "Choose the level of interest ";
		$scope.chooseInterest = function (rep) {
			$scope.myFeedback.interest = interests[rep];
			myFeedbackToSend.interest = rep;
		};

		/* New knowledge */
		$scope.myFeedback.update = "Choose the amount of new information ";
		$scope.chooseUpdate = function (rep) {
			$scope.myFeedback.update = updates[rep];
			myFeedbackToSend.update = rep;
		};

		/* Effectiveness */
		$scope.myFeedback.effectiveness = "Was the training effective? ";
		$scope.chooseEffectiveness = function (rep) {
			$scope.myFeedback.effectiveness = rep + ' ';
			myFeedbackToSend.effectiveness = rep;
		};

		/* Recommendation */
		$scope.myFeedback.recommending = "Choose an option ";
		$scope.chooseRecommendation = function (rep) {
			$scope.myFeedback.recommending = answers[rep];
			myFeedbackToSend.recommending = (rep == 0);
		};

		/* Trainer */
		$scope.myFeedback.trainerRecommending = "Choose an option ";
		$scope.chooseTrainer = function (rep) {
			$scope.myFeedback.trainerRecommending = answers[rep];
			myFeedbackToSend.trainerRecommending = (rep == 0);
		};

		$scope.pageChanged = function () {
			$scope.feedbacks = $scope.training.feedbacks.slice(($scope.currentPage - 1) * 5, ($scope.currentPage - 1) * 5 + 5);
		};

		$scope.currentPage = 1;
		$scope.itemsPerPage = 2;

		var getFeedbacks = function (feedbacksList) {
			$scope.training.feedbacks = feedbacksList;

			for (var k = 0; k < $scope.training.feedbacks.length; k++) {
				$scope.training.feedbacks[k].date = (new Date(feedbacksList[k].date)).toLocaleString();
				$scope.training.feedbacks[k].rate = feedbacksList[k].starCount;
				$scope.training.feedbacks[k].percent = 100 * ($scope.training.feedbacks[k].rate / $scope.max);

				$scope.training.feedbacks[k].impression = impressions[$scope.training.feedbacks[k].impression];
				$scope.training.feedbacks[k].intelligibility = intelligibilities[$scope.training.feedbacks[k].intelligibility];
				$scope.training.feedbacks[k].interest = interests[$scope.training.feedbacks[k].interest];
				$scope.training.feedbacks[k].update = updates[$scope.training.feedbacks[k].update];
				$scope.training.feedbacks[k].recommending = ($scope.training.feedbacks[k].recommending) ? 'Yes' : 'No';
				$scope.training.feedbacks[k].trainerRecommending = ($scope.training.feedbacks[k].trainerRecommending) ? 'Yes' : 'No';
			}

			$scope.feedbacks = $scope.training.feedbacks.slice(0, 5);
			$scope.totalItems = $scope.training.feedbacks.length;
		};

		getFeedbacks(obj.data.feedbacks);

		$scope.sendFeedback = function () {
			myFeedbackToSend.text = $scope.myFeedback.text;
			myFeedbackToSend.rate = $scope.overStar;
			$http.post('rest/feedback' + window.location.pathname, myFeedbackToSend).then(function (obj) {
				$scope.vote = true;
				getFeedbacks(obj.data.feedbacks);
				getRating(obj.data.rating)
			}, function (err) {
				ngNotify.set(err.statusText);
			});
		};

		$scope.trainingReg = function (flag) {
			if (flag) {
				$http.post('rest/register' + window.location.pathname, 'registering').then(function (obj) {
					$window.location.reload();
				}, function (err) {
					ngNotify.set(err.statusText);
				});
			}
			else {
				$http.post('rest/unregister' + window.location.pathname, 'unregistering').then(function (obj) {
					$window.location.reload();
				}, function (err) {
					ngNotify.set(err.statusText);
				});
			}
		}
	}).catch(function (data) {
		ngNotify.set(data);
	});
}]);