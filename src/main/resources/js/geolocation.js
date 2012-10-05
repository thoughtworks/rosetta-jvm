if (typeof waldo === "undefined") { waldo = {}; }

waldo.location = (function(){
  var geocoder = new google.maps.Geocoder();
  var coordinatesFor = function(cityName, k, error) {
    geocoder.geocode({ 'address': cityName },
      function(results) {
        var location = results[0].geometry.location,
            outLocation = {
              lat: location.Xa,
              long: location.Ya
            };
        k(outLocation);
      });
  }

  return {
    coordinatesFor: coordinatesFor
  };
})();
