const JSON_HEADER = {'Content-Type': 'application/json;charset=utf-8'};

const POST = async (url, data, headers) => {
    return await fetch(url, {
        method: 'POST',
        body: data,
        headers: headers
    })
};

const GET = async (url) => {
    return await fetch(url);
};

function json(response) {
    return response.json()
}