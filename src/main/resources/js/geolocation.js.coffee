window.waldo = window.waldo || {}

window.waldo.location = (->
  geocoder = new google.maps.Geocoder()
  coordinatesFor = (cityName, callback) ->
    geocoder.geocode({ 'address': cityName }, (results) ->
      location = results[0].geometry.location
      outLocation =
        lat: location.Xa,
        long: location.Ya

      callback(outLocation)
    )
  coordinatesFor: coordinatesFor
)()
