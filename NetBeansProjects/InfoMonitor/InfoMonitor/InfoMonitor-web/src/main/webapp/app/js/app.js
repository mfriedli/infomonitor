'use strict';


// Declare app level module which depends on filters, and services
var infoMonitorApp = angular.module('infoMonitorApp', [
  'ngRoute',
  'ngTable',
  'ngAnimate',
  'infoMonitorApp.filters',
  'infoMonitorApp.services',
  'infoMonitorApp.directives',
  'infoMonitorApp.controllers'
]);
