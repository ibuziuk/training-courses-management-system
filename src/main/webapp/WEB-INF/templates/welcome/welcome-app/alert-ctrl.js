var alertCtrl = angular.module('alertCtrl', []);

alertCtrl.controller('alertCtrl', ['$scope', '$sce', function($scope, $sce){
	$scope.alerts = [
	    { type: 'info', msg: ' Your training $$trainingName$$ is aproved. ', time: '2016-07-21 18:20', link: '../training.html', trainingName: 'Assembler: best-practice'},
	    { type: 'danger', msg: ' You have got training $$trainingName$$ in an hour! ', time: '2016-07-21 15:10', link: '../training.html', trainingName: 'C++'},
	    { type: 'success', msg: ' You have got training $$trainingName$$ in a day! ', time: '2016-07-21 12:07', link: '../training.html', trainingName: 'Scala and Go'},
	    { type: 'warning', msg: ' User $$userName$$ sent you new $$trainingName$$ to aprove. ', time: '2016-07-21 12:07', link: '../training.html', trainingName: 'Scala and Go', userName: 'Yana Yaroshevich', userLink: '../user.html'}
  	];

  $scope.messages = $scope.alerts.map(function(alert) {
  	var s = '<i class="fa fa-info-circle"></i> ';
  	s += '<strong><time>' + alert.time + '</time></strong>' + alert.msg;
  	if (alert.type == 'warning'){
  		s = s.replace('$$userName$$', '<a href="' + alert.userLink + '"class="alert-link">' + alert.userName + '</a>');
  	}
  	s = $sce.trustAsHtml(s.replace('$$trainingName$$', '<a href="' + alert.link + '"class="alert-link">' + alert.trainingName + '</a>'));
  	return s;
  });

  $scope.getAlerts = function() {
  	//TODO
  };

  $scope.closeAlert = function(index) {
    $scope.alerts.splice(index, 1);
  };
}]);