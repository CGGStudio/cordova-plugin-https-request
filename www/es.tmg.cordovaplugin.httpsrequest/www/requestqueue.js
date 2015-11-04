cordova.define("es.tmg.cordovaplugin.httpsrequest.requestqueue", function(require, exports, module) {
	
 var exec = require('cordova/exec'),
    cordova = require('cordova');

 var RequestQueue = function () {
 };

//-------------------------------------------------------------------
 RequestQueue.prototype.sendRequest = function (href, params, type, header, body, successCallback, errorCallback, show, hide) {
    if (errorCallback == null) {
        errorCallback = function () {
        }
    }

    if (typeof errorCallback != "function") {
        console.log("RequestQueue.sendRequest failure: failure parameter not a function");
        return
    }

    if (typeof successCallback != "function") {
        console.log("RequestQueue.sendrequest failure: success callback parameter must be a function");
        return
    }

    var showLoading = true;
    if (show != undefined && !show) {
    	showLoading = false;
    }
    
    var hideLoading = true;
    if (hide != undefined && !hide) {
    	hideLoading = false;
    }
    
    cordova.exec(successCallback, errorCallback, 'RequestQueue', 'sendRequest', [
        href, params, type, header, body, showLoading, hideLoading
	]);
};

var requestQueue = new RequestQueue();
module.exports = requestQueue;
});
