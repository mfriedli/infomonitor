'use strict';

/* Controllers */
var infoMonitorApp = angular.module('infoMonitorApp', []);

infoMonitorApp.controller('LockerRoomCtrl', function LockerRoomCtrl($scope) {
    var thisCtrlCtx = this;
    this.date = "";
    this.lockerrooms = [];
    this.connectionstate;
    var ws = new WebSocket("ws://localhost:8080/InfoMonitor-web/locckerroomendpoint");

    ws.onopen = function() {
        thisCtrlCtx.connectionstate = "Succeeded to open a connection";
    };
    ws.onerror = function() {
        thisCtrlCtx.connectionstate = "Failed to open a connection";
    };
    ws.onmessage = function(message) {
        applyToCtrlScope(message);
    };

    function applyToCtrlScope(message) {
        $scope.$apply(function() {
            thisCtrlCtx.lockerrooms = angular.fromJson(message.data);
            thisCtrlCtx.date = thisCtrlCtx.lockerrooms[0].Datum;
        });
    }

    $scope.$on("$destroy", function() {
        ws.close();
    });
});

infoMonitorApp.controller('LatestResultsCtrl', function LatestResultsCtrl($scope) {
    var thisCtrlCtx = this;
    this.results = [];
    this.connectionstate;
    var ws = new WebSocket("ws://localhost:8080/InfoMonitor-web/latestresultsendpoint");

    ws.onopen = function() {
        thisCtrlCtx.connectionstate = "Succeeded to open a connection";
    };
    ws.onerror = function() {
        thisCtrlCtx.connectionstate = "Failed to open a connection";
    };
    ws.onmessage = function(message) {
        applyToCtrlScope(message);
    };

    function applyToCtrlScope(message) {
        $scope.$apply(function() {
            thisCtrlCtx.results = angular.fromJson(message.data);
        });
    }

    $scope.$on("$destroy", function() {
        ws.close();
    });
});


infoMonitorApp.controller('TotomatCtrl', function TotomatCtrl($scope) {
    var thisCtrlCtx = this;
    this.totomatResults = [];
    this.connectionstate;
    
    var ws = new WebSocket("ws://localhost:8080/InfoMonitor-web/totomatendpoint");

    ws.onopen = function() {
        thisCtrlCtx.connectionstate = "Succeeded to open a connection";
    };
    ws.onerror = function() {
        thisCtrlCtx.connectionstate = "Failed to open a connection";
    };
    ws.onmessage = function(message) {
        applyToCtrlScope(message);
    };

    function applyToCtrlScope(message) {
        $scope.$apply(function() {
            thisCtrlCtx.totomatResults = angular.fromJson(message.data);
        });
    }

    $scope.$on("$destroy", function() {
        ws.close();
    });
});

infoMonitorApp.controller('BreakingNewsCtrl', function BreakingNewsCtrl($scope) {
    var thisCtrlCtx = this;
    this.breakingNews = "Info: Bitte f&uuml;r die Trainings Garderoben links oder rechts benutzen.";
    this.connectionstate;
    
    var ws = new WebSocket("ws://localhost:8080/InfoMonitor-web/breakingnewsendpoint");

    ws.onopen = function() {
        thisCtrlCtx.connectionstate = "Succeeded to open a connection";
    };
    ws.onerror = function() {
        thisCtrlCtx.connectionstate = "Failed to open a connection";
    };
    ws.onmessage = function(message) {
        applyToCtrlScope(message);
    };

    function applyToCtrlScope(message) {
        $scope.$apply(function() {
            thisCtrlCtx.breakingNews = angular.fromJson(message.data);
        });
    }

    $scope.$on("$destroy", function() {
        ws.close();
    });
});

infoMonitorApp.controller('ContentCtrl', function ContentCtrl($scope,$http) {
    var thisCtrlCtx = this;
    this.content;
    this.connectionstate;
    this.img;
    this.showImage = false;
    
    var ws = new WebSocket("ws://localhost:8080/InfoMonitor-web/contentendpoint");
    ws.binaryType = 'arraybuffer';
    ws.onopen = function() {
        thisCtrlCtx.connectionstate = "Succeeded to open a connection";
    };
    ws.onerror = function() {
        thisCtrlCtx.connectionstate = "Failed to open a connection";
    };
    ws.onmessage = function(message) {
        applyToCtrlScope(message);
    };

    function loadImage() {
        $http({
            method: 'POST',
            url: 'http://localhost:8080/InfoMonitor-web/app/fileDownload',
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            data: thisCtrlCtx.content.contentUri
        }).success(function(data)
        {
            thisCtrlCtx.img = data;            
        });
    }
    function applyToCtrlScope(message) {
        $scope.$apply(function() {
            thisCtrlCtx.content = angular.fromJson(message.data);
            if (thisCtrlCtx.content.contentType === 'JPG') {
                loadImage();
                thisCtrlCtx.showImage = true;
            }
        });
    }

    $scope.$on("$destroy", function() {
        ws.close();
    });
});

