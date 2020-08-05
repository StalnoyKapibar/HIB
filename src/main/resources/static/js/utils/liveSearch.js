function findEl(el, array, value) {
    var coincidence = false;
    el.empty();
    for (var i = 0; i < array.length; i++) {
        if (array[i].match(value) || array[i].toLowerCase().match(value)) {
            el.children('li').each(function () {
                if (array[i] === $(this).text()) {
                    coincidence = true;
                }
            });
            if (coincidence === false) {
                el.append('<a class="js-searchInput dropdown-item" style="padding-left:' + array[i] + '</a>');
            }
        }
    }
}

var filterInput = $('#searchInput'),
    filterUl = $('.ul-books');

filterInput.bind('input propertychange', async function () {
    if ($(this).val() !== '') {
        filterUl.fadeIn(100);
        let data = await getAllBooksForLiveSearch();
        let array = [];
        for (let key in data) {
            if (data.hasOwnProperty(key)) {
                let book = data[key];
                for (let field in book) {
                    const value = book[field];
                    if (typeof value === 'string') {
                        if (field.startsWith("name") && !book.show) {
                            array.push('0px"><img src="../../static/images/outOfStock.png" style="max-width: 20px;" >' + value)
                        } else {
                            array.push('20px">' + value);
                        }
                    }
                }
            }
        }
        findEl(filterUl, array, $(this).val());
    } else {
        filterUl.fadeOut(100);
    }
});

filterUl.on('click', '.js-searchInput', function (e) {
    $('#searchInput').val('');
    filterInput.val($(this).text());
    filterInput.trigger('change');
    filterUl.fadeOut(100);
});

async function getAllBooksForLiveSearch() {
    const url = '/api/allBookForLiveSearch';
    const res = await fetch(url);
    const data = await res.json();
    return data;
}
