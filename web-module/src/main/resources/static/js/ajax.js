var btn_test = document.getElementById("test");
btn_test.addEventListener('click', testAjax, false);

function testAjax() {
    var data = {
        "name": "name"
    };
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: "/api/items/item",
        data: data,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log(data);
            console.log("all right");
        },
        error: function (errorThrown) {
            console.log(errorThrown);
        }
    });
}