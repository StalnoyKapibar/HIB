let currentPath, categoryName, path, pathForBreadCrumbs, card, categoryId, categoryPath, categoryParent;
path = window.location.pathname.replace('/category', '');
currentPath = window.location.pathname.split('/');
currentPath.splice(0, 2);

setBreadCrumbs();
showBooksByCategory();

function setBreadCrumbs() {
    for (let i in currentPath) {
        categoryName = currentPath[i][0].toUpperCase() + currentPath[i].substr (1);
        if (i == 0) {
            pathForBreadCrumbs = currentPath[i];
        } else {
            pathForBreadCrumbs += '/' + currentPath[i];
        }
        if (i == (currentPath.length - 1)) {
            $('#breadCrumbs').append(`<li class="breadcrumb-item active" aria-current="page">${categoryName}</li>`);
        } else {
            $('#breadCrumbs').append(`<li class="breadcrumb-item"><a href="/category/${pathForBreadCrumbs}">${categoryName}</a></li>`);
        }
    }
}

function showBooksByCategory() {
    fetch('/categories/getbooks?path=' + path, {
    })    .then(function (response) {
        return response.json()
    })
        .then( async function (books) {
            let listOrdersOfCart = [];
            listOrdersOfCart = await getListOrdersOfCart();
            if (books.length === 0) {
                $('#books').append('<h3>There are no books in this category</h3>');
            } else {
                for (let i in books) {
                    let textOfBtn = listOrdersOfCart.includes(books[i].id) ? addedToshoppingCart : addToshoppingCart;
                    let cssOfBtn = listOrdersOfCart.includes(books[i].id) ? "btn-outline-success disabled" : "btn-success";
                    card =
                `<div class="col-md-3 col-sm-6">
      			<img class="card-img" width="250" height="360" src="/images/book${books[i].id}/${books[i].coverImage}" alt="...">
      			<p style="font-weight: bold">${books[i].name}</p>
      			<p>Author: ${books[i].author}</p>
                <p>${books[i].edition}</p>
                <p>Category: ${books[i].category.categoryName}</p>
                <p>$${books[i].price}</p>
      			<hr class="line">
      			<div class="row">
      				<div class="col-md-6 col-sm-6">
      					<button class="btn btn-primary right" onclick="document.location='/page/${books[i].id}'">Read more</button>
      				</div>
      				<div class="col-md-6 col-sm-6">
      					<button class="btn ${cssOfBtn} right" id="buttonToCart" data-id="${books[i].id}">${textOfBtn}</button>
      				</div>
      			</div>
  		</div>`;
                    $('#books').append(card);
                }
            }
        });
}




