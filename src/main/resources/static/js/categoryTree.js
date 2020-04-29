let row, primary;
getCategoryTree();

function getCategoryTree() {
    fetch('/categories/gettree', {
    })    .then(function (response) {
        return response.json()
    })
        .then(function (json) {
            cateroryArr = [];
            for (let i in json) {
                categoryId = json[i][0];
                categoryName = json[i][1];
                categoryPath = json[i][2];
                categoryParent = json[i][3];
                const category = {
                    id: categoryId,
                    categoryName: categoryName,
                    path: categoryPath,
                    parentId: categoryParent
                };
                cateroryArr.push(category);
            }
            let tree = getUnflatten(cateroryArr, null);
            setTreeView(tree);
        })
}

function getUnflatten(arr, parentid) {
    let output = [];
    for(const obj of arr) {
        if(obj.parentId == parentid) {
            let children = getUnflatten(arr, obj.id);

            if(children.length) {
                obj.childrens = children
            }
            output.push(obj)
        }
    }
    return output
}


function setTreeView(category) {
        for (let i in category) {
            row =
                `<li>
                    <span class="caret" onclick="toggle(this)"></span><a href="/category${category[i].path}">${category[i].categoryName}</a>
                    <ul class="nested active">
                    ${setChilds(category[i].childrens)}
                    </ul>
                </li>`;
            $('#category_tree').append(row);
        }
}

function setChilds(category) {
    let row = '';
    for (let i in category) {
        if (category[i].childrens === undefined) {
            row +=`<li><a href="/category${category[i].path}">${category[i].categoryName}</a></li>`;
        } else {
            row +=
                `<li>
                    <span class="caret" onclick="toggle(this)"></span><a href="/category${category[i].path}">${category[i].categoryName}</a>
                    <ul class="nested active">
                    ${setChilds(category[i].childrens)}
                    </ul>
                </li>`;
        }
    }
    return row;
}

function toggle(element) {
    element.parentElement.querySelector('.nested').classList.toggle('active');
    element.classList.toggle("caret-down");
}


