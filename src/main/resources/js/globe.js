google.load("earth", "1");

      var ge = null;
      var speed =  15;  // degrees per second
      var lastMillis = (new Date()).getTime();
      var initialAltitude;

      function init() {
        google.earth.createInstance("map3d", initCallback, failureCallback);
        toggleButtons("start");
      }

      function failureCallback(object) {
      }

      function initCallback(object) {
        ge = object;
        ge.getWindow().setVisibility(true);
        ge.getOptions().setFlyToSpeed(ge.SPEED_TELEPORT);
        var lookAt = ge.getView().copyAsLookAt(ge.ALTITUDE_ABSOLUTE);
        initialAltitude = lookAt.getAltitude();

        var placemark = ge.createPlacemark('');
        placemark.setName("San Francisco Office");
        ge.getFeatures().appendChild(placemark);

        // Create style map for placemark
        var icon = ge.createIcon('');
        icon.setHref('http://maps.google.com/mapfiles/kml/paddle/red-circle.png');
        var style = ge.createStyle('');
        style.getIconStyle().setIcon(icon);
        style.getIconStyle().setScale(15.0);
        placemark.setStyleSelector(style);

        // Create point
        // var la = ge.getView().copyAsLookAt(ge.ALTITUDE_RELATIVE_TO_GROUND);
        var point = ge.createPoint('');
        point.setLatitude(37.76944);
        point.setLongitude(-122.43444);
        placemark.setGeometry(point);

        google.earth.addEventListener(ge, "frameend", rotateEarth);
      }

      function rotateEarth(){
        var now = (new Date()).getTime();
        // dt is the delta-time since last tick, in seconds
        var dt = (now - lastMillis) / 1000.0;
        lastMillis = now;
        var lookAt = ge.getView().copyAsLookAt(ge.ALTITUDE_ABSOLUTE);
        lookAt.setLongitude(lookAt.getLongitude() + speed*dt);
        lookAt.setHeading(0);   // Workaround for heading bug, issue #148
        lookAt.setAltitude(initialAltitude);
        ge.getView().setAbstractView(lookAt);
      }

      function startRotation() {
        google.earth.addEventListener(ge, "frameend", rotateEarth);
        rotateEarth();
      }

      function stopRotation() {
        google.earth.removeEventListener(ge, "frameend", rotateEarth);
      }

      function toggleButtons(val) {
        frm=document.forms[0]
        if(val=="start") {
          frm.start.disabled=true;frm.stop.disabled=false;
        }
        if(val=="stop") {
          frm.start.disabled=false;frm.stop.disabled=true;
        }
      }


      google.setOnLoadCallback(init);
