var waldo = waldo || {};

waldo.location = (function(){
  var geocoder = new google.maps.Geocoder();
  var coordinatesFor = function (cityName, callback) {
    geocoder.geocode({ 'address': cityName },
      function(results) {
        var location = results[0].geometry.location,
            outLocation = {
              lat: location.Xa,
              long: location.Ya
            };
        callback(outLocation);
      });
  };

  return {
    coordinatesFor: coordinatesFor
  };
})();
