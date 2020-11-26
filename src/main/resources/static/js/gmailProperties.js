// if you change it, double properties to resources/gmail.properties
properties = {
    authURL: "https://accounts.google.com/o/oauth2/v2/auth",
    //client_id: "183482457610-9bp9qfkp3ao3de2cv09sqqfv5mt72vjk.apps.googleusercontent.com",
    client_id: "405766529577-ua5fi15ih10kovq3i2mejpr1fqthi34k.apps.googleusercontent.com",
    access_type: "offline",
    prompt: "consent",
    redirect_uri: "http://77.222.63.141.xip.io:8080/gmail/admin",
    //redirect_uri: "http://localhost:8080/gmail/admin",
    response_type: "code",
    scope: "https://mail.google.com/",

}

gmailAccessUrl = {
    fullUrl: properties.authURL + '?'
        + 'client_id=' + properties.client_id + '&'
        + 'access_type=' + properties.access_type + '&'
        + 'prompt=' + properties.prompt + '&'
        + 'redirect_uri=' + properties.redirect_uri + '&'
        + 'response_type=' + properties.response_type + '&'
        + 'scope=' + properties.scope
}

