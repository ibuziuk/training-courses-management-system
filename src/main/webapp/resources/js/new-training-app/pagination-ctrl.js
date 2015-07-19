var paginationCtrl = angular.module('paginationCtrl', []);

paginationCtrl.controller('paginationCtrl', function ($scope) {
  $scope.totalItems = 30;
  $scope.currentPage = 1;
  $scope.itemsPerPage = 1;
  $scope.toShow = function(){}
  
  $scope.setPage = function (pageNo) {
    $scope.currentPage = pageNo;
  };
    
  /*function getDays(trainingRepet){
      if (trainingRepet == 'Weekly' || trainingRepet == 'Continuous'){    
          return $scope.days;
      }
      else
          return 1;
  }
  
  function getArr(arrObj){
    var tempArr = [];
    for (var i = 0; i < arrObj.length; i++){
        if(arrObj.checked){
            tempArr.push(arrObj.name);
        }
    }
    return tempArr;
  }    
    
  $scope.trainngCreation = function(){    
      var training = {};
      training.tags = getArr($scope.checkboxTags);
      training.audience = getArr($scope.checkboxAudiences);
      training.type = $scope.toShowType.replace(/\s/g, '');
      training.repetition = $scope.toShowRepet.replace(/\s/g, '');
      training.days = getDays(training.repetition);
      
  }*/
});