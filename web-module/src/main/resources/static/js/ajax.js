var pattern_table_raw = "\
<tr id='<UUID>'>\
    <td><NUMBER></td>\
    <td><NAME></td>\
    <td>\
        <select id='status'>\
            <option value='READY'>Ready</option>\
            <option value='COMPLETED'>Completed</option>\
        </select>\
    </td>\
    <td>\
        <input type='button' name='update' onclick='updateItem(\"<UUID>\")' value='update'>\
    </td>\
    <td>\
        <input type='button' name='delete' onclick='deleteItem(\"<UUID>\")' value='delete'>\
    </td>\
</tr>";

var btn_add_item = document.getElementById("btn_add_item");
btn_add_item.addEventListener('click', addItem, false);

document.addEventListener("DOMContentLoaded", function () {
    getItems();
});

function getItems() {
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: "/api/items/",
        success: function (data) {
            var tbody_content = "";
            if (Array.isArray(data)) {
                for (var item in data) {
                    tbody_content += pattern_table_raw
                            .split("<NUMBER>").join(Number(item) + Number(1))
                            .split("<NAME>").join(data[item].name)
                            .split("<UUID>").join(data[item].uuid);
                }
                document.getElementById('items').children[2].innerHTML = tbody_content;
                for (var item in data) {
                    document.getElementById(data[item].uuid).children[2].children[0].value = data[item].status;
                }
            }
        },
        error: function (errorThrown) {
            alert(errorThrown.responseText);
        }
    });

}

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
        success: function (data) {
            alert("item has added");
            getItems();
        },
        error: function (errorThrown) {
            alert(errorThrown.responseText);
        }
    });
}

function updateItem(uuid) {
    var data = {
        "uuid": uuid,
        "name": document.getElementById(uuid).children[1].textContent,
        "status": document.getElementById(uuid).children[2].children[0].value
    };
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: "/api/items/",
        data: JSON.stringify(data),
        success: function (data) {
            alert("item has updated");
            getItems();
        },
        error: function (errorThrown) {
            alert(errorThrown.responseText);
        }
    });
}

function deleteItem(uuid) {
    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: "/api/items/" + uuid,
        success: function (data) {
            alert("item has delete");
            getItems();
        },
        error: function (errorThrown) {
            alert(errorThrown.responseText);
        }
    });
}

function getItem(id) {
    $.ajax({
        type: 'GET',
        url: "/api/items/" + id,
        success: function (data) {
            alert(data.toString());
        },
        error: function (errorThrown) {
            alert(errorThrown);
        }
    });
}