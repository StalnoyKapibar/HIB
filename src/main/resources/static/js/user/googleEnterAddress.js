var placeSearch, autocomplete;
var componentForm = {
    street_number: 'short_name',
    route: 'long_name',
    locality: 'long_name',
    administrative_area_level_1: 'short_name',
    country: 'long_name',
    postal_code: 'short_name'
};

$(document).ready(function () {
    if (currentLang === '') {
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }
    getLanguage();
    setLocaleFields();
})

function initAutocomplete() {
    // Create the autocomplete object, restricting the search to geographical
    // location types.
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {!HTMLInputElement} */(document.getElementById('autocomplete')),
        {types: ['geocode']});

    // When the user selects an address from the dropdown, populate the address
    // fields in the form.
    autocomplete.addListener('place_changed', fillInAddress);
}

function fillInAddress() {
    // Get the place details from the autocomplete object.
    var place = autocomplete.getPlace();

    for (var component in componentForm) {
        document.getElementById(component).value = '';
        document.getElementById(component).disabled = false;
    }

    // Get each component of the address from the place details
    // and fill the corresponding field on the form.
    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById(addressType).value = val;
        }
    }
}

// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            var circle = new google.maps.Circle({
                center: geolocation,
                radius: position.coords.accuracy
            });
            autocomplete.setBounds(circle.getBounds());
        });
    }
}

async function enterAddress() {
    let data = enterData();
    if (data!=''){
        alert('enter '+data+' !');
        return ;
    }
      let address = {
        flat: $("#flat").val(),
        house: $("#street_number").val(),
        street: $("#route").val(),
        city: $("#locality").val(),
        state:$("#administrative_area_level_1").val(),
        postalCode:$("#postal_code").val(),
        country:$("#country").val(),
        firstName:$("#firstName").val(),
        lastName:$("#lastName").val()
    };
    let response = fetch('/enterAddress',{
        method:'POST',
        headers:{
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(address)
    });
}

function enterData() {
    let data = '';
    if ($("#street_number").val()==''){data='house';}
    if ($("#route").val()==''){data='street'}
    if ($("#locality").val()==''){data='city'}
    if ($("#administrative_area_level_1").val()==''){data='state'}
    if ($("#postal_code").val()==''){data='zip code'}
    if ($("#country").val()==''){data='country'}
    if ($("#firstName").val()==''){data='first name'}
    if ($("#lastName").val()==''){data='last name'}
    return data;
}