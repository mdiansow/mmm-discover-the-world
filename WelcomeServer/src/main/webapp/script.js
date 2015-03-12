'use strict';
angular.module('ngAppDemo', []).controller('ngAppDemoController', function($scope) {
  $scope.a = 1;
  $scope.b = 2;
})
.controller('toto', function($scope) {
  $scope.a = 3;
  $scope.c = 2;
})
.controller('car', function($scope, $http){
        $http({method: 'GET', url: 'posts.json'}).success(function(data) {
            $scope.posts = data;
        });
})
.controller('user', function($scope, $http){
        $http({method: 'GET', url: '/rest/user/'}).success(function(data) {
            $scope.posts = data;
        });
})
;