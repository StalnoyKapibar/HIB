let allOrders;
let iconOfPrice = " €";
let statusOfOrder = "Processing";
let btnDisplay = "d-inline";

$(document).ready(showListOrders());

function convertPrice(price) {
    return price / 100;
}


$('#statusCheckbox').change(function () {
    if ($(this).prop('checked') === true) {
        statusOfOrder = "Processing";
        btnDisplay = "d-inline";
    } else {
        statusOfOrder = "Completed";
        btnDisplay = "d-none";
    }
    showListOrders();
});


function showListOrders() {
    fetch("/api/admin/getAllOrders")
        .then(json)
        .then(function (data) {
            $('#adminListOrders').empty();
            allOrders = data;
            let order;
            let html = `<thead ><tr><th>№</th>
                             <th>Email</th>
                             <th>First name</th>
                             <th>Last Name</th>
                             <th>Date of Order</th>
                             <th>Status</th>
                             <th>Details of Order</th>
                             <th>Edit</th>
                             <th></th></tr></thead>`;
            $.each(data, function (index) {
                order = data[index];
                if (order.status === statusOfOrder) {
                    html += `<tbody ><tr > <td> ${order.id}</td>`;
                    for (let key in order.userDTO) {
                        if (key === "email" || key === "firstName" || key === "lastName") {
                            html += `<td > ${order.userDTO[key]}</td>`;
                        }
                    }
                    html += `<td>${order.data}</td>
                         <td>${order.status} </td>`;
                    html += `<td><a  href="#" data-toggle="modal" data-target="#adminOrderModal" onclick="showModalOfOrder(${index})" > Show details </a></td>
                          <td><button class="btn btn-danger " onclick=orderDelete(${order.id})>Delete</button></td>
                          <td><button class="btn btn-success ${btnDisplay} " onclick=orderComplete(${order.id})>Complete</button></td></tr>`;

                    $('#adminListOrders').html(html);
                }
            });

        });

}

function showModalOfOrder(index) {
    let order = allOrders[index];
    let items = order.items;
    let html = ``;
    $('#modalTitle').html(`Order № ${order.id}`)
    html += `<thead><tr><th>Image</th>
                             <th>Name | Author</th>
                             <th></th>
                             <th>Price</th></tr></thead>`;
    $.each(items, function (index) {
        let book = items[index].book;
        html += `<tr><td class="align-middle"><img src="/images/book${book.id}/${book.coverImage}" style="max-width: 80px"></td>
                             <td width="350">${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}</td>
                             <td></td>
                             <td>${convertPrice(book.price)}${iconOfPrice}</td></tr>`;
    });
    html += `<tr><td></td><td></td><td>Subtotal :</td><td> ${convertPrice(order.itemsCost)}${iconOfPrice}</td></tr>
                 <tr><td></td><td></td></tr>
                 <tr><td></td><td></td><td>Total :</td><td>${convertPrice(order.itemsCost + order.shippingCost)}${iconOfPrice}</td></tr>`;
    $('#modalBody').html(html);


}

function orderComplete(id) {
    fetch("/api/admin/completeOrder/" + id, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(id),
    }).then(r => showListOrders())
}

function orderDelete(id) {
    fetch("/api/admin/deleteOrder/" + id, {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(id),
    }).then(r => showListOrders())

}

