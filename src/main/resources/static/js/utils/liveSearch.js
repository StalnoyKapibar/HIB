/* Live Search */

// Функция поиска совпадений вводимых символов
function findEl(el, array, value) {
    var coincidence = false;
    console.log(array);
    el.empty();    // Очищаем список совпадений
    for (var i = 0; i < array.length; i++) {
        if (array[i].match(value) || array[i].toLowerCase().match(value)) {    // Проверяем каждый эллемент на совпадение побуквенно
            el.children('li').each(function () {    // Проверка на совпадающие эллементы среди выведенных
                if (array[i] === $(this).text()) {
                    coincidence = true;    // Если есть совпадения, то true
                }
            });
            if (coincidence === false) {
                el.append('<a class="js-searchInput dropdown-item" style="padding-left:20px">' + array[i] + '</a>');    // Если совпадений не обнаружено, то добавляем уникальное название в список
            }
        }
    }
}

var filterInput = $('#searchInput'),
    filterUl = $('.ul-books');

// Проверка при каждом вводе символа
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
                        array.push(value);
                    }
                }
            }
        }
        findEl(filterUl, array, $(this).val());
    } else {
        filterUl.fadeOut(100);
    }
});

//  При клике на эллемент выпадающего списка, присваиваем значение в инпут и ставим триггер на его изменение
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
