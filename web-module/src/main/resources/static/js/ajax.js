var btn_add_item = document.getElementById("btn_add_item");
btn_add_item.addEventListener('click', addItem, false);
//var btn_add_item = document.getElementById("btn_add_item");
//btn_add_item.addEventListener('click', addItem, false);

function addItem() {
    var form = document.getElementById("add_item");
    var data = {
        "name": form.children.name.value,
        "status": form.children.status.value
    };
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "/api/items/",
        data: JSON.stringify(data),
        success: function (data, a, b) {
            alert("item has added");
            console.log(data);
            console.log("all right");
            console.log(a);
            console.log(b);
        },
        error: function (errorThrown, a, b) {
            console.log(errorThrown);
            console.log(a);
            console.log(b);
        }
    });
}
function updateItem() {
    var data = {
        "id": 3,
        "name": "test",
        "status": "READY"
    };
    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: "/api/items/",
        data: JSON.stringify(data),
//        cache: false,
//        timeout: 600000,
        success: function (data, a, b) {
            console.log(data);
            console.log("all right");
            console.log(a);
            console.log(b);
        },
        error: function (errorThrown, a, b) {
            console.log(errorThrown);
            console.log(a);
            console.log(b);
        }
    });
}

function getItem(id) {
    var data = {
        "id": id
    };
    $.ajax({
        type: 'GET',
//        contentType: 'application/json',
        url: "/api/items/" + id,
//        data: JSON.stringify(data),
        success: function (data, a, b) {
            console.log(data);
            console.log("all right");
            console.log(a);
            console.log(b);
        },
        error: function (errorThrown, a, b) {
            console.log(errorThrown);
            console.log(a);
            console.log(b);
        }
    });
}