const URL_BOOKS = '/api/allBookForLiveSearch';
let authorsList = ''
const postList = document.querySelector('.list-group')


async function getAuthors() {
    let response = await fetch(URL_BOOKS).then((res)=>res.json())
    response.forEach(function (entry) {
        authorsList+='<li class="list-group-item">'+entry.author+'</li>'
    })
    postList.innerHTML=authorsList
}

getAuthors()