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
                {name: 'Bild (gif,png,jpg)', value: 'PICTURE'}
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
                if (thisCtrlCtx.formData.selectedItem.value === 'PICTURE') {
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
        .controller('HeaderCtrl', function HeaderCtrl($scope, $location) {
            var thisCtrlCtx = this;
            $scope.isActive = function(viewLocation) {
                return viewLocation === $location.path();
            };
        });

