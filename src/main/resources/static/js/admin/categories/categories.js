let button, row, id, categoryName, path, level, parentRow;

$(document).ready(function () {
    setLocaleFields();
});

fetch('/categories/getadmintree')
    .then(function (response) {
        return response.json()
    })
    .then(function (json) {
        for (let i in json) {
            id = json[i][0];
            categoryName = json[i][1];
            path = json[i][2];
            level = json[i][3];

            button = '<button type="button" class="btn btn-primary" onclick="edit('+ json[i][0] +')">Edit</button>';
            row = "<tr>" +
                "<td>" + id + "</td>" +
                "<td>" + categoryName + "</td>" +
                "<td>" + path + "</td>" +
                "<td>" + level + "</td>" +
                "<td>" + button + "</td>" +
                "</tr>";
            $('#category_tree').append(row);

            parentRow = "<option value='"+ id +"'>" + categoryName + "</option>";
            $('#category_add_parent').append(parentRow);
        }
    });

function addNewCategory() {
    let category = {
        categoryName : $('#categoryName').val(),
        parentId : $('#category_add_parent').val()
    };
    fetch('/categories/addCategory', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(category)
    })
}
