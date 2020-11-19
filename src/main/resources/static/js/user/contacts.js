//Добавление контактов на страницу contacts
URL_CONTACTS = "api/contacts"


async function getContact() {
    let response = await fetch(URL_CONTACTS).then((res)=>res.json());
    console.log(response[0].email)
    let contact = response[0]
    $('.email-contacts').append("Email: "+contact.email)
    $('.phone-contacts').append("Phone number: "+contact.phoneNumber)
    return response;
}


getContact()
