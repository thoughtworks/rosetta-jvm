google.load("earth", "1")

window.waldo = window.waldo || {}

window.waldo.globe = (->
  globe = null
  speed = 15
  lastMillis = new Date().getTime()
  initialAltitude = null

  initialise = -> google.earth.createInstance("globe", initialiseCallback, failureCallback)

  failureCallback = (object) ->

  icon = -> 
    icon = globe.createIcon('')
    icon.setHref('http://maps.google.com/mapfiles/kml/paddle/red-circle.png')
    icon

  style = ->
    style = globe.createStyle('')
    style.getIconStyle().setIcon(icon())
    style.getIconStyle().setScale(2.0)
    style

  pointFor = (latitude, longitude) ->
    point = globe.createPoint('')
    point.setLatitude(latitude)
    point.setLongitude(longitude)
    point

  placemarkFor = (location, latitude, longitude) ->
    placemark = globe.createPlacemark('')
    placemark.setName(location)
    placemark.setGeometry(pointFor(latitude, longitude))
    placemark.setStyleSelector(style())
    placemark

  initialiseCallback = (object) ->
    globe = object
    globe.getWindow().setVisibility(true)
    globe.getOptions().setFlyToSpeed(globe.SPEED_TELEPORT)
    lookAt = globe.getView().copyAsLookAt(globe.ALTITUDE_ABSOLUTE)
    initialAltitude = lookAt.getAltitude()

    globe.getFeatures().appendChild(placemarkFor("San Francisco Office", 37.76944, -122.43444))
    globe.getFeatures().appendChild(placemarkFor("Chicago Office", 41.88194, -87.62778))

    google.earth.addEventListener(globe, "frameend", rotateEarth)

  rotateEarth = ->
    now = new Date().getTime()
    dt = (now - lastMillis) / 1000.0
    lastMillis = now
    lookAt = globe.getView().copyAsLookAt(globe.ALTITUDE_ABSOLUTE)
    lookAt.setLongitude(lookAt.getLongitude() + speed * dt)
    lookAt.setHeading(0)
    lookAt.setAltitude(initialAltitude)
    globe.getView().setAbstractView(lookAt)

  initialise: initialise
)()
