google.load("earth", "1");

var waldo = waldo || {};

waldo.globe = (function () {
  var globe = null;
  var speed = 15;  // degrees per second
  var lastMillis = (new Date()).getTime();
  var initialAltitude;

  function initialise() {
    google.earth.createInstance("map3d", initialiseCallback, failureCallback);
    toggleButtons("start");
  }

  function failureCallback(object) {
  }

  function initialiseCallback(object) {
    globe = object;
    globe.getWindow().setVisibility(true);
    globe.getOptions().setFlyToSpeed(globe.SPEED_TELEPORT);
    var lookAt = globe.getView().copyAsLookAt(globe.ALTITUDE_ABSOLUTE);
    initialAltitude = lookAt.getAltitude();

    var placemark = globe.createPlacemark('');
    placemark.setName("San Francisco Office");
    globe.getFeatures().appendChild(placemark);

    // Create style map for placemark
    var icon = globe.createIcon('');
    icon.setHref('http://maps.google.com/mapfiles/kml/paddle/red-circle.png');
    var style = globe.createStyle('');
    style.getIconStyle().setIcon(icon);
    style.getIconStyle().setScale(15.0);
    placemark.setStyleSelector(style);

    // Create point
    // var la = globe.getView().copyAsLookAt(globe.ALTITUDE_RELATIVE_TO_GROUND);
    var point = globe.createPoint('');
    point.setLatitude(37.76944);
    point.setLongitude(-122.43444);
    placemark.setGeometry(point);

    google.earth.addEventListener(globe, "frameend", rotateEarth);
  }

  function rotateEarth() {
    var now = (new Date()).getTime();
    // dt is the delta-time since last tick, in seconds
    var dt = (now - lastMillis) / 1000.0;
    lastMillis = now;
    var lookAt = globe.getView().copyAsLookAt(globe.ALTITUDE_ABSOLUTE);
    lookAt.setLongitude(lookAt.getLongitude() + speed * dt);
    lookAt.setHeading(0);   // Workaround for heading bug, issue #148
    lookAt.setAltitude(initialAltitude);
    globe.getView().setAbstractView(lookAt);
  }

  function toggleButtons(val) {
    form = document.forms[0];
    if (val == "start") {
      form.start.disabled = true;
      form.stop.disabled = false;
    }
    if (val == "stop") {
      form.start.disabled = false;
      form.stop.disabled = true;
    }
  }

  return {
    initialise: initialise
  }
})();
