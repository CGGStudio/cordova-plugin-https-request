#Cordova plugin to make HTTPS requests for Android (REST/JSON)
Cordova plugin to make HTTPS requests for Android (REST/JSON)

#Important note
This plugin was tested with Cordova 3.3 and 5.1

##Structure

    config.xml
    cordova_plugins.js
    strings.xml
    -- src
      -- ConnectionUtils.java
      -- ExSSLSocketFactory.java
      -- PluginDefs.java
      -- RequestQueuePlugin.java
      -- RequestQueueTask.java
      -- RequestUtils.java
      -- RequestVO.java
    -- www
      -- es.tmg.cordovaplugin.httpsrequest
        -- www
          -- requestqueue.js

##config.xml
The config.xml file is loosely based on the W3C's Widget Config spec.
It is in XML to facilitate transfer of nodes from this cross platform manifest to native XML manifests (AndroidManifest.xml).

##JavaScript API
It's not necessary to include the reference for the js file. The js plugin file is loaded because the configuration of the file cordova_plugins.js, that includes the reference for the plugin js file.

The request function is called as `queue`.

###Methods

####sendRequest

 sendRequest(href, params, type, header, body successCallback, errorCallback, show, hide)

Executes a REST web service request on background with the specified URL.

Function parameters:

* `href`: URL of the REST web service
* `params`: URL params. If no params put empty string ("")
* `type`: Type of request --> POST, GET, PUT, or DELETE.
* `header`: Header params in JSON format
* `body`: Body params in JSON format.
* `successCallback`: Type of request --> POST, GET, PUT, or DELETE.
* `errorCallback`: Type of request --> POST, GET, PUT, or DELETE.
* `show` and `hide`: 
*     If show and hide are true, a progress dialog opens at the beginning and closes at the end of request.
*     If show and hide are false, anything happens
*     If show and not hide, a progress dialog opens at the beginning but not close at the end (useful if you need to make several requests and this is not the last request)
*     If not show and hide, a progress dialog closes at the end but not opens at the beginning (useful if you need to make several requests and this is the last request)

Example:

    var successCallback = function(result) {
        if(result.ok){ 
          ...
        }
    };
    var errorCallback = function(result) {
        alert(result.response.message);
    };
    queue.sendRequest("http://domain:port/context/method", "lastUpdate=2015-11-04", "POST", 
              {"Authorization": ticket}, {"BodyParam1": "Value1", "BodyParam2": "Value2"},
              successCallback, errorCallback, true, true);

## License
MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
Copyright 2015, Jose Antonio Nogales Rincón
