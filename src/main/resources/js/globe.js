google.load("earth", "1");

var waldo = waldo || {};

waldo.globe = (function () {
  var globe = null;
  var speed = 15;  // degrees per second
  var lastMillis = (new Date()).getTime();
  var initialAltitude;

  function initialise() {
    google.earth.createInstance("map3d", initialiseCallback, failureCallback);
  }

  function failureCallback(object) {
  }

  function icon() {
    var icon = globe.createIcon('');
    icon.setHref('http://maps.google.com/mapfiles/kml/paddle/red-circle.png');
    return icon;
  }

  function style() {
    var style = globe.createStyle('');
    style.getIconStyle().setIcon(icon());
    style.getIconStyle().setScale(2.0);
    return style;
  }

  function pointFor(latitude, longitude) {
    var point = globe.createPoint('');
    point.setLatitude(latitude);
    point.setLongitude(longitude);
    return point;
  }

  function placemarkFor(location, latitude, longitude) {
    var placemark = globe.createPlacemark('');

    placemark.setName(location);
    placemark.setGeometry(pointFor(latitude, longitude));
    placemark.setStyleSelector(style());

    return placemark;
  }

  function initialiseCallback(object) {
    globe = object;
    globe.getWindow().setVisibility(true);
    globe.getOptions().setFlyToSpeed(globe.SPEED_TELEPORT);
    var lookAt = globe.getView().copyAsLookAt(globe.ALTITUDE_ABSOLUTE);
    initialAltitude = lookAt.getAltitude();

    globe.getFeatures().appendChild(placemarkFor("San Francisco Office", 37.76944, -122.43444));
    globe.getFeatures().appendChild(placemarkFor("Chicago Office", 41.88194, -87.62778));

    //    google.earth.addEventListener(globe, "frameend", rotateEarth);
  }

  function rotateEarth() {
    var now = (new Date()).getTime();
    // dt is the delta-time since last tick, in seconds
    var dt = (now - lastMillis) / 1000.0;
    lastMillis = now;
    var lookAt = globe.getView().copyAsLookAt(globe.ALTITUDE_ABSOLUTE);
    lookAt.setLongitude(lookAt.getLongitude() + speed * dt);
    lookAt.setHeading(0);
    lookAt.setAltitude(initialAltitude);
    globe.getView().setAbstractView(lookAt);
  }

  return {
    initialise:initialise
  }
})();
