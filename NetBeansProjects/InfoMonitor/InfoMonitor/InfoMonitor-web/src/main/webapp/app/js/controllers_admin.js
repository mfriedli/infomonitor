'use strict';


/* Controllers */
angular.module('infoMonitorAdmin.controllers_admin', []).
        controller('AddCtrl', function AddCtrl($scope, $http, $location) {
            var thisCtrlCtx = this;
            this.externalUrlDisabled = true;
            this.fileChooserDisabled = true;
          //  this.fileToBeUploaded;
         /*   this.formData = {
                'isActive':true,
                'width':1300, 
                'height':900,
                'interval' : 10000
            };
*/
            this.items = [
                {name: 'Externe Webseite', value: 'WEBPAGE'},
                {name: 'Bild (gif,png,jpg)', value: 'PICTURE'},
                {name: 'Video (mp4)', value: 'VIDEO'}
            ];
/*
            $scope.processForm = function() {
                $http({
                    method: 'POST',
                    url: '/InfoMonitor-web/rest/saveFormData', //'saveFormData',
                    //IMPORTANT!!! You might think this should be set to 'multipart/form-data' 
                    // but this is not true because when we are sending up files the request 
                    // needs to include a 'boundary' parameter which identifies the boundary 
                    // name between parts in this multi-part request and setting the Content-type 
                    // manually will not set this boundary parameter. For whatever reason, 
                    // setting the Content-type to 'false' will force the request to automatically
                    // populate the headers properly including the boundary parameter.
                    headers: { 'Content-Type': '' },
                    // This method will allow us to change how the data is sent up to the server
                    // for which we'll need to encapsulate the model data in 'FormData'
                    transformRequest: function (data) {
                        var formData = new FormData();
                        //need to convert our json object to a string version of json otherwise
                        // the browser will do a 'toString()' on the object which will result 
                        // in the value '[Object object]' on the server.
                        formData.append("model", angular.toJson(data.model));
                        //now add all of the assigned files
                        formData.append("file", data.file);
                        return formData;
                    },
                    //Create an object that contains the model and files which will be transformed
                    // in the above transformRequest method
                    data: { model: thisCtrlCtx.formData, file: thisCtrlCtx.fileToBeUploaded }
                   // data: $.param(thisCtrlCtx.formData), // pass in data as strings x-www-form-urlencoded
                   // headers: {'Content-Type': 'application/multipart/form-data'}  // set the headers so angular passing info as form data (not request payload)
                })
                .success(function(data) {
                    //console.log(data);
                    $location.path('/add');                   
                });
            };
          */ 
            $scope.selectionChanged = function() {
                if (thisCtrlCtx.formData.selectedItem.value === 'WEBPAGE') {
                    thisCtrlCtx.externalUrlDisabled = false;
                    thisCtrlCtx.fileChooserDisabled = true;
                }
                if (thisCtrlCtx.formData.selectedItem.value === 'PICTURE' || thisCtrlCtx.formData.selectedItem.value === 'VIDEO') {
                    thisCtrlCtx.externalUrlDisabled = true;
                    thisCtrlCtx.fileChooserDisabled = false;
                }
            };
        })
        .controller('OverviewCtrl', function OverviewCtrl($scope, $location, $http) {
            var thisCtrlCtx = this;
            this.contentItems = [];
            loadAllContentItems(); // init
            
            
            function loadAllContentItems() {
                $http({
                    method: 'GET',
                    url: '/InfoMonitor-web/rest/contentItems'
                })
                .success(function(serviceResponse) {
                    thisCtrlCtx.contentItems = angular.fromJson(serviceResponse);

                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /contentItems');
                    console.log(data);
                    console.log(status);
                    $location.path('/edit');                   
                });
            };
        
            $scope.edit = function(id) {
                $location.path('/edit/' + id);
            };
            
            $scope.delete = function(id) {
                $http({
                    method: 'DELETE',
                    url: '/InfoMonitor-web/rest/deleteItem/id/'+id
                })
                .success(function(serviceResponse) {
                    loadAllContentItems();
                    $location.path('/overview');
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /delete/id');
                    console.log(data);
                    console.log(status);
                    $location.path('/overview');                  
                });
                
            };
            
        })
        .controller('EditCtrl', function EditCtrl($scope, $http, $routeParams, $location) {
            var thisCtrlCtx = this;
            this.contentItem;
                        
            this.externalUrlDisabled = true;
            this.fileChooserDisabled = true;
            
            $http({
                method: 'GET',
                url: '/InfoMonitor-web/rest/contentItem/id/'+$routeParams.itemId
            })
            .success(function(serviceResponse) {
                thisCtrlCtx.contentItem = angular.fromJson(serviceResponse);
                if (thisCtrlCtx.contentItem.externalWebUrl){
                    thisCtrlCtx.externalUrlDisabled = false;
                } else {
                    thisCtrlCtx.fileChooserDisabled = false;
                }      
            })
            .error(function(data, status) {
                console.log('Error when calling rest service /contentItem/id');
                console.log(data);
                console.log(status);
                $location.path('/edit');                   
            });
            
            $scope.saveItem = function() {
                 $http({
                    method: 'PUT',
                    url: '/InfoMonitor-web/rest/updateItem/id/'+thisCtrlCtx.contentItem.id,                   
                    data: thisCtrlCtx.contentItem, // pass in data as strings x-www-form-urlencoded
                    headers: {'Content-Type': 'application/json'}  // set the headers so angular passing info as form data (not request payload)
                })
                .success(function(data) {
                    //console.log(data);
                    $location.path('/overview');                   
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /updateItem/id');
                    console.log(data);
                    console.log(status);
                    $location.path('/overview');                   
                });
            };
        })
        .controller('StartCtrl', function StartCtrl($scope, $location) {
            $scope.nextPage = function() {
                $location.path('/start');
            };
        })
        .controller('NewsOverviewCtrl', function NewsOverviewCtrl($scope, $location, $http) {
            var thisCtrlCtx = this;
            this.newsItems = [];
            loadAllNewsItems(); // init
            
            
            function loadAllNewsItems() {
                $http({
                    method: 'GET',
                    url: '/InfoMonitor-web/rest/newsItems'
                })
                .success(function(serviceResponse) {
                    thisCtrlCtx.newsItems = angular.fromJson(serviceResponse);

                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /newsItems');
                    console.log(data);
                    console.log(status);
                    $location.path('/newsoverview');                   
                });
            };
        
            $scope.edit = function(id) {
                $location.path('/editnews/' + id);
            };
            
            $scope.delete = function(id) {
                $http({
                    method: 'DELETE',
                    url: '/InfoMonitor-web/rest/deleteNewsItem/id/'+id
                })
                .success(function(serviceResponse) {
                    loadAllNewsItems();
                    $location.path('/newsoverview');
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /delete/id');
                    console.log(data);
                    console.log(status);
                    $location.path('/newsoverview');                  
                });                
            };            
        })
        .controller('AddNewsCtrl', function AddNewsCtrl($scope, $http, $location) {
            var thisCtrlCtx = this;
            this.newsItem;                       
            
            $scope.saveNewsItem = function() {
                 $http({
                    method: 'POST',
                    url: '/InfoMonitor-web/rest/addNewsItem',                   
                    data: thisCtrlCtx.newsItem, // pass in data as strings json
                    headers: {'Content-Type': 'application/json; charset=utf-8'}  // set the headers so angular passing info as form data (not request payload)
                })
                .success(function(data) {
                    $location.path('/newsoverview');                   
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /addNewsItem');
                    console.log(data);
                    console.log(status);
                    $location.path('/newsoverview');                   
                });
            };
        })
        .controller('EditNewsCtrl', function EditNewsCtrl($scope, $http, $routeParams, $location) {
            var thisCtrlCtx = this;
            this.newsItem;
                                    
            $http({
                method: 'GET',
                url: '/InfoMonitor-web/rest/newsItem/id/'+$routeParams.itemId
            })
            .success(function(serviceResponse) {
                thisCtrlCtx.newsItem = angular.fromJson(serviceResponse);
            })
            .error(function(data, status) {
                console.log('Error when calling rest service /newsItem/id');
                console.log(data);
                console.log(status);
                $location.path('/editNews');                   
            });
            
            $scope.saveNewsItem = function() {
                 $http({
                    method: 'PUT',
                    url: '/InfoMonitor-web/rest/updateNewsItem/id/'+thisCtrlCtx.newsItem.id,                   
                    data: thisCtrlCtx.newsItem, // pass in data as strings x-www-form-urlencoded
                    headers: {'Content-Type': 'application/json; charset=utf-8'}  // set the headers so angular passing info as form data (not request payload)
                })
                .success(function(data) {
                    //console.log(data);
                    $location.path('/newsoverview');                   
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /updateNewsItem/id');
                    console.log(data);
                    console.log(status);
                    $location.path('/newsoverview');                   
                });
            };
        })
        .controller('HeaderCtrl', function HeaderCtrl($scope, $location) {
            var thisCtrlCtx = this;
            $scope.isActive = function(viewLocation) {
                return viewLocation === $location.path();
            };
        })
        .controller('LeaguesOverviewCtrl', function LeaguesOverviewCtrl($scope, $location, $http) {
            var thisCtrlCtx = this;
            this.leagueItems = [];
            loadAllLeagueItems(); // init
            
            
            function loadAllLeagueItems() {
                $http({
                    method: 'GET',
                    url: '/InfoMonitor-web/rest/leagueItems'
                })
                .success(function(serviceResponse) {
                    thisCtrlCtx.leagueItems = angular.fromJson(serviceResponse);

                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /leagueItems');
                    console.log(data);
                    console.log(status);
                    $location.path('/leaguesoverview');                   
                });
            };
        
            $scope.edit = function(id) {
                $location.path('/editleague/' + id);
            };
            
            $scope.delete = function(id) {
                $http({
                    method: 'DELETE',
                    url: '/InfoMonitor-web/rest/deleteLeagueItem/id/'+id
                })
                .success(function(serviceResponse) {
                    loadAllLeagueItems();
                    $location.path('/leaguesoverview');
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /deleteLeagueItem/id');
                    console.log(data);
                    console.log(status);
                    $location.path('/leaguesoverview');                  
                });                
            };            
        })
        .controller('AddLeagueCtrl', function AddLeagueCtrl($scope, $http, $location) {
            var thisCtrlCtx = this;
            this.leagueItem;                       
            
            $scope.saveLeagueItem = function() {
                 $http({
                    method: 'POST',
                    url: '/InfoMonitor-web/rest/addLeagueItem',                   
                    data: thisCtrlCtx.leagueItem, // pass in data as strings json
                    headers: {'Content-Type': 'application/json'}  // set the headers so angular passing info as form data (not request payload)
                })
                .success(function(data) {
                    $location.path('/leaguesoverview');                   
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /addLeagueItem');
                    console.log(data);
                    console.log(status);
                    $location.path('/leaguesoverview');                   
                });
            };
        })
        .controller('EditLeagueCtrl', function EditLeagueCtrl($scope, $http, $routeParams, $location) {
            var thisCtrlCtx = this;
            this.leagueItem;
                                    
            $http({
                method: 'GET',
                url: '/InfoMonitor-web/rest/leagueItem/id/'+$routeParams.itemId
            })
            .success(function(serviceResponse) {
                thisCtrlCtx.leagueItem = angular.fromJson(serviceResponse);
            })
            .error(function(data, status) {
                console.log('Error when calling rest service /leagueItem/id');
                console.log(data);
                console.log(status);
                $location.path('/editLeague');                   
            });
            
            $scope.saveLeagueItem = function() {
                 $http({
                    method: 'PUT',
                    url: '/InfoMonitor-web/rest/updateLeagueItem/id/'+thisCtrlCtx.leagueItem.id,                   
                    data: thisCtrlCtx.leagueItem, // pass in data as strings x-www-form-urlencoded
                    headers: {'Content-Type': 'application/json'}  // set the headers so angular passing info as form data (not request payload)
                })
                .success(function(data) {
                    //console.log(data);
                    $location.path('/leaguesoverview');                   
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /updateLeagueItem/id');
                    console.log(data);
                    console.log(status);
                    $location.path('/leaguesoverview');                   
                });
            };
        })       
        .controller('SeasonsOverviewCtrl', function SeasonsOverviewCtrl($scope, $location, $http) {
            var thisCtrlCtx = this;
            this.seasonItems = [];
            loadAllSeasonItems(); // init
            
            
            function loadAllSeasonItems() {
                $http({
                    method: 'GET',
                    url: '/InfoMonitor-web/rest/seasonItems'
                })
                .success(function(serviceResponse) {
                    thisCtrlCtx.seasonItems = angular.fromJson(serviceResponse);

                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /seasonItems');
                    console.log(data);
                    console.log(status);
                    $location.path('/seasonsoverview');                   
                });
            };
        
            $scope.edit = function(id) {
                $location.path('/editseason/' + id);
            };
            
            $scope.delete = function(id) {
                $http({
                    method: 'DELETE',
                    url: '/InfoMonitor-web/rest/deleteSeasonItem/id/'+id
                })
                .success(function(serviceResponse) {
                    loadAllSeasonItems();
                    $location.path('/seasonsoverview');
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /deleteSeasonItem/id');
                    console.log(data);
                    console.log(status);
                    $location.path('/seasonsoverview');                  
                });                
            };            
        })
        .controller('AddSeasonCtrl', function AddSeasonCtrl($scope, $http, $location) {
            var thisCtrlCtx = this;
            this.seasonItem;                       
            
            $scope.saveSeasonItem = function() {
                 $http({
                    method: 'POST',
                    url: '/InfoMonitor-web/rest/addSeasonItem',                   
                    data: thisCtrlCtx.seasonItem, // pass in data as strings json
                    headers: {'Content-Type': 'application/json'}  // set the headers so angular passing info as form data (not request payload)
                })
                .success(function(data) {
                    $location.path('/seasonsoverview');                   
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /addSeasonItem');
                    console.log(data);
                    console.log(status);
                    $location.path('/seasonsoverview');                   
                });
            };
        })
        .controller('EditSeasonCtrl', function EditSeasonCtrl($scope, $http, $routeParams, $location) {
            var thisCtrlCtx = this;
            this.seasonItem;
                                    
            $http({
                method: 'GET',
                url: '/InfoMonitor-web/rest/seasonItem/id/'+$routeParams.itemId
            })
            .success(function(serviceResponse) {
                thisCtrlCtx.seasonItem = angular.fromJson(serviceResponse);
            })
            .error(function(data, status) {
                console.log('Error when calling rest service /seasonItem/id');
                console.log(data);
                console.log(status);
                $location.path('/editSeason');                   
            });
            
            $scope.saveSeasonItem = function() {
                 $http({
                    method: 'PUT',
                    url: '/InfoMonitor-web/rest/updateSeasonItem/id/'+thisCtrlCtx.seasonItem.id,                   
                    data: thisCtrlCtx.seasonItem, // pass in data as strings x-www-form-urlencoded
                    headers: {'Content-Type': 'application/json'}  // set the headers so angular passing info as form data (not request payload)
                })
                .success(function(data) {
                    //console.log(data);
                    $location.path('/seasonsoverview');                   
                })
                .error(function(data, status) {
                    console.log('Error when calling rest service /updateSeasonItem/id');
                    console.log(data);
                    console.log(status);
                    $location.path('/seasonsoverview');                   
                });
            };
        });


