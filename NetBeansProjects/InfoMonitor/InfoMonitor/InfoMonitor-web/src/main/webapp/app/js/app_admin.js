'use strict';


// Declare app level module which depends on filters, and services
angular.module('infoMonitorAdmin', [
  'ngRoute',
  'ngAnimate',
  'infoMonitorAdmin.controllers_admin'
]).
config(['$routeProvider', function($routeProvider) {
     $routeProvider
          .when('/', {templateUrl: 'partials/start.html', controller: 'StartCtrl'})
          .when('/add', {templateUrl: 'partials/add.html', controller: 'AddCtrl'})
          .when('/overview', {templateUrl: 'partials/overview.html', controller: 'OverviewCtrl'})
          .when('/edit/:itemId', {templateUrl: 'partials/edit.html', controller: 'EditCtrl'})
          .when('/start', {templateUrl: 'partials/start.html', controller: 'StartCtrl'})
          .when('/newsoverview', {templateUrl: 'partials/newsoverview.html', controller: 'NewsOverviewCtrl'})
          .when('/addnews', {templateUrl: 'partials/addnews.html', controller: 'AddNewsCtrl'}) 
          .when('/editnews/:itemId', {templateUrl: 'partials/editnews.html', controller: 'EditNewsCtrl'})
          .when('/seasonsoverview', {templateUrl: 'partials/seasonsoverview.html', controller: 'SeasonsOverviewCtrl'})
          .when('/addseason', {templateUrl: 'partials/addseason.html', controller: 'AddSeasonCtrl'}) 
          .when('/editseason/:itemId', {templateUrl: 'partials/editseason.html', controller: 'EditSeasonCtrl'})
          .when('/leaguesoverview', {templateUrl: 'partials/leaguesoverview.html', controller: 'LeaguesOverviewCtrl'})
          .when('/addleague', {templateUrl: 'partials/addleague.html', controller: 'AddLeagueCtrl'}) 
          .when('/editleague/:itemId', {templateUrl: 'partials/editleague.html', controller: 'EditLeagueCtrl'})           
          .otherwise({redirectTo: '/'});
}]);
