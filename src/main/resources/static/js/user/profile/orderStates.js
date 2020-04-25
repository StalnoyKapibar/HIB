$(document).ready(function () {
    let fields = ['profile-name', 'profile-in-progress', 'profile-delivered', 'profile-orders', 'profile-bucket'];
    let fieldDataSources = [getUserName, getInProgressOrders, getDeliveredOrders, getSummOrders, getBooksInBucket];
    handleProfile(fields, fieldDataSources);
});

$(document).on('click', ".myprof-loc",function () {
    let fields = ['profile-name', 'profile-in-progress', 'profile-delivered', 'profile-orders', 'profile-bucket'];
    let fieldDataSources = [getUserName, getInProgressOrders, getDeliveredOrders, getSummOrders, getBooksInBucket];
    handleProfile(fields, fieldDataSources);
});

async function getInProgressOrders() {
    let inProgress = 0;
    let response = await fetch("/order/getorders")
        .then(json);
    $.each(response, function () {
        inProgress++;
    });
    return inProgress;
}

async function getBooksInBucket() {
    let books = 0;
    let response = await fetch("/cart")
        .then(json);
    $.each(response, function () {
        books++;
    });
    return books;
}

async function getDeliveredOrders() {
    return 0;
}

async function getSummOrders() {
    let summOrders = 0;
    await getInProgressOrders()
        .then(value => summOrders += value);
    await getDeliveredOrders()
        .then(value => summOrders += value);
    return summOrders;
}

async function getUserName() {
    let name;
    let response = await fetch("/api/current-user")
        .then(json);
    name = response.firstName + ' ' + response.lastName;
    return name;
}

function handleProfile(fields, FieldDataSources) {
    fields.forEach(function (field, i, fields) {
        let handlingField = document.getElementById(field);
        FieldDataSources[i]()
            .then(value => handlingField.innerText = String(value));
    })
}