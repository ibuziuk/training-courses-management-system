'use strict';

angular.module('trainingApp').controller('pageCtrl', ['$scope', '$http', '$window', '$location', 'FileUploader', 'ngNotify', function ($scope, $http, $window, $location, FileUploader, ngNotify) {
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
	$scope.files = [];
	$scope.loading = true;

	/* File Upload */
	var Url = $location.absUrl();
	Url += '/uploadfile';
	var uploader = $scope.uploader = new FileUploader({
		url: Url
	});

	uploader.withCredentials = true;

	// CALLBACKS

	uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
		console.info('onWhenAddingFileFailed', item, filter, options);
	};
	uploader.onAfterAddingFile = function (fileItem) {
		console.info('onAfterAddingFile', fileItem);
	};
	uploader.onAfterAddingAll = function (addedFileItems) {
		console.info('onAfterAddingAll', addedFileItems);
	};
	uploader.onBeforeUploadItem = function (item) {
		console.info('onBeforeUploadItem', item);
	};
	uploader.onProgressItem = function (fileItem, progress) {
		console.info('onProgressItem', fileItem, progress);
	};
	uploader.onProgressAll = function (progress) {
		console.info('onProgressAll', progress);
	};
	uploader.onSuccessItem = function (fileItem, response, status, headers) {
		console.info('onSuccessItem', fileItem, response, status, headers);
	};
	uploader.onErrorItem = function (fileItem, response, status, headers) {
		console.info('onErrorItem', fileItem, response, status, headers);
	};
	uploader.onCancelItem = function (fileItem, response, status, headers) {
		console.info('onCancelItem', fileItem, response, status, headers);
	};
	uploader.onCompleteItem = function (fileItem, response, status, headers) {
		console.info('onCompleteItem', fileItem, response, status, headers);
		$scope.files.push(response);
		if (response != '') {
			$scope.files[$scope.files.length - 1].fileLink = 'rest/downloadfile/' + $scope.files[$scope.files.length - 1].uploadId;
		}
	};
	uploader.onCompleteAll = function () {
		console.info('onCompleteAll');
	};

	/* Accordion */
	$scope.oneAtATime = true;

	/* Rating */
	$scope.rate = 5;
	$scope.overStar = 5;
	$scope.max = 10;
	$scope.percent = 100 * ($scope.rate / $scope.max);
	$scope.toShowNoRatings = false;

	$scope.hoveringOver = function (value) {
		$scope.overStar = value;
		$scope.percent = 100 * (value / $scope.max);
	};

	/* Getting info about the training */
	var urlParts = window.location.pathname.split('/');
	$http.get('rest/training/' + urlParts[urlParts.length - 1])
			.then(function (obj) {
				var contains = function (tags, name) {
					for (var k = 0; k < tags.length; k++) {
						if (tags[k].name == name)
							return true;
					}
					return false;
				};

				var getPic = function () {
					if (contains(obj.data.training.tags, 'Java'))
						return '1.jpg';
					if (contains(obj.data.training.tags, 'PHP'))
						return '2.png';
					if (contains(obj.data.training.tags, 'JavaScript'))
						return '3.png';
					if (contains(obj.data.training.tags, 'C++'))
						return '4.png';
					if (contains(obj.data.training.tags, 'Scala'))
						return '5.gif';
					if (contains(obj.data.training.tags, 'Go'))
						return '6.jpg';
					if (contains(obj.data.training.tags, 'English'))
						return '7.png';
					if (contains(obj.data.training.tags, 'General'))
						return '8.gif';
					if (contains(obj.data.training.tags, 'C#'))
						return '9.png';
					if (contains(obj.data.training.tags, 'Python'))
						return '10.png';
					if (contains(obj.data.training.tags, 'Ruby'))
						return '11.png';
					if (contains(obj.data.training.tags, 'Android'))
						return '12.png';
					if (contains(obj.data.training.tags, 'IOS'))
						return '13.png';
					if (contains(obj.data.training.tags, 'SQL'))
						return '14.png';
				};

				$scope.img = getPic();

				$scope.approvingSettings = function () {
					$window.location.href = 'training/approve/' + obj.data.training.trainingId;
				};

				$scope.editTraining = function () {
					$window.location.href = 'training/edit/' + obj.data.training.trainingId;
				};

				$http.get('rest/training/' + urlParts[urlParts.length - 1] + '/files').then(function (objFiles) {
							$scope.files = objFiles.data;
							for (var k = 0; k < $scope.files.length; k++) {
								$scope.files[k].fileLink = 'rest/downloadfile/' + $scope.files[k].uploadId;
							}
						}, function (err) {
							ngNotify.set(err.statusText);
						}
				);

				$scope.continuous = obj.data.training.continuous;

				if ($scope.continuous) {
					$scope.parts = obj.data.parts;
					for (var k in $scope.parts) {
						$scope.parts[k].partLink = 'training/' + $scope.parts[k].trainingId;
					}
				}

				$scope.external = obj.data.training.externalType;

				$scope.trainerLink = 'user/' + obj.data.training.trainer.userId;
				/* Training info */
				$scope.vote = obj.data.vote;
				$scope.isAdmin = obj.data.isAdmin;

				$scope.training = {};
				$scope.training.isEditing = obj.data.training.isEditing;

				$scope.openFeedback = function () {
					var date = new Date();
					if (!obj.data.training.regular) {
						var time = obj.data.training.time;
						time = time.split("-")[0];
						var hour = time.split(":")[0];
						var min = time.split(":")[1];
						var trainDate = new Date(obj.data.training.date);
						return (date > new Date(trainDate.getFullYear(), trainDate.getMonth(), trainDate.getDate(), hour, min));
					}
					else {
						var trainStart = new Date(obj.data.training.start);
						return (date > new Date(trainStart.getFullYear(), trainStart.getMonth(), trainStart.getDate(), 0, 0));
					}
				};


				$scope.isFuture = function () {
					var date = new Date();
					if (!obj.data.training.regular) {
						var time = obj.data.training.time;
						time = time.split("-")[0];
						var hour = time.split(":")[0];
						var min = time.split(":")[1];
						var trainDate = new Date(obj.data.training.date);
						return (date < new Date(trainDate.getFullYear(), trainDate.getMonth(), trainDate.getDate(), hour, min));
					}
					else {
						var trainStart = new Date(obj.data.training.start);
						return (date < new Date(trainStart.getFullYear(), trainStart.getMonth(), trainStart.getDate(), 0, 0));
					}
				};

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
					$scope.training = angular.copy(obj.data.training);

					getRating(obj.data.rating);

					$scope.training.register = obj.data.register;
					$scope.training.language = obj.data.training.language.value;
					$scope.training.maxVis = obj.data.training.maxVisitorsCount;
					$scope.training.isApproved = obj.data.training.approved;

					if (!$scope.training.regular) {
						if (obj.data.training.location)
							$scope.room = obj.data.training.location;
						$scope.training.date = obj.data.training.dateOnString;
						$scope.training.time = obj.data.training.time;
					}
					else {
						$scope.training.start = obj.data.training.start;
						$scope.training.days = [];
						var weekDays = obj.data.training.days.substring(0, obj.data.training.days.length - 1).split(" ");
						var times = obj.data.training.time.substring(0, obj.data.training.time.length - 1).split(" ");
						if (obj.data.training.location)
							var rooms = obj.data.training.location.split(" ");
						for (var k = 0; k < weekDays.length; k++) {
							$scope.training.days[k] = {};
							$scope.training.days[k].day = days[weekDays[k]];
							$scope.training.days[k].time = times[k];
							if (rooms && rooms[k])
								$scope.training.days[k].room = rooms[k];
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
						$scope.registerColor = '#f0ad4e';
						$scope.registerText = 'You are in the waiting list';
					}
					else if ($scope.training.register == 3) {
						$scope.registerColor = 'brown';
						$scope.registerText = 'You are not visiting this training';
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
						$scope.training.feedbacks[k].impression = impressions[$scope.training.feedbacks[k].impression] || '';
						$scope.training.feedbacks[k].intelligibility = intelligibilities[$scope.training.feedbacks[k].intelligibility] || '';
						$scope.training.feedbacks[k].interest = interests[$scope.training.feedbacks[k].interest] || '';
						$scope.training.feedbacks[k].update = updates[$scope.training.feedbacks[k].update] || '';

						if ($scope.training.feedbacks[k].text == undefined)
							$scope.training.feedbacks[k].text = '';

						if ($scope.training.feedbacks[k].effectiveness == undefined)
							$scope.training.feedbacks[k].effectiveness = '';

						if ($scope.training.feedbacks[k].recommending != undefined)
							$scope.training.feedbacks[k].recommending = ($scope.training.feedbacks[k].recommending) ? 'Yes' : 'No';
						else
							$scope.training.feedbacks[k].recommending = '';
						if ($scope.training.feedbacks[k].trainerRecommending != undefined)
							$scope.training.feedbacks[k].trainerRecommending = ($scope.training.feedbacks[k].trainerRecommending) ? 'Yes' : 'No';
						else
							$scope.training.feedbacks[k].trainerRecommending = '';
					}

					$scope.feedbacks = $scope.training.feedbacks.slice(0, 5);
					$scope.totalItems = $scope.training.feedbacks.length;
				};

				getFeedbacks(obj.data.feedbacks);

				$scope.sendFeedback = function () {
					myFeedbackToSend.text = $scope.myFeedback.text;
					myFeedbackToSend.rate = $scope.overStar;
					$http.post('rest/feedback/training/' + obj.data.training.trainingId, myFeedbackToSend).then(function (obj) {
						$scope.vote = true;
						getFeedbacks(obj.data.feedbacks);
						getRating(obj.data.rating)
					}, function (err) {
						ngNotify.set(err.statusText);
					});
				};

				$scope.trainingReg = function (flag) {
					if (flag) {
						$http.post('rest/register/training/' + obj.data.training.trainingId, 'registering').then(function (obj) {
							$window.location.reload();
						}, function (err) {
							ngNotify.set(err.statusText);
						});
					}
					else {
						$http.post('rest/unregister/training/' + obj.data.training.trainingId, 'unregistering').then(function (obj) {
							$window.location.reload();
						}, function (err) {
							ngNotify.set(err.statusText);
						});
					}
				}
			}, function (data) {
				ngNotify.set(data);
			})
			.finally(function () {
				$scope.loading = false;
			});
}]);