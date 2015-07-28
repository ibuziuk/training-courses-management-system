var pageCtrl = angular.module('pageCtrl', []);
pageCtrl.controller('pageCtrl', ['$scope', function($scope){
    /* Rating */
    $scope.rate = 5;
    $scope.max = 10;
    $scope.percent = 100 * ($scope.rate / $scope.max);

    $scope.hoveringOver = function(value) {
        $scope.overStar = value;
        $scope.percent = 100 * (value / $scope.max);
    };
    
    /* Training info */
    $scope.training = {title: "Angular.js", isAproved: true, genRate: 9};
    $scope.genPercent = 100 * ($scope.training.genRate / $scope.max);
    
    $scope.training.description = 'HTML is great for declaring static documents, but it falters when we try to use it for declaring dynamic views in web-applications. AngularJS lets you extend HTML vocabulary for your application. The resulting environment is extraordinarily expressive, readable, and quick to develop.';
    
    if ($scope.training.isAproved){
        $scope.aproveColor = 'green';
        $scope.aproveText = 'Your training is aproved';
    }
    else{
        $scope.aproveColor = 'red';
        $scope.aproveText = 'Your training is not aproved';
    }
}]);