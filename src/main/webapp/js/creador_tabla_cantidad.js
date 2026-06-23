function addRowCantidad(tableID, jsonID, minimo, maximo, bono) {
    var table = document.getElementById(tableID);
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    var cell1 = row.insertCell(0); //checkbox
    var element1 = document.createElement("input");
    element1.type = "checkbox";
    cell1.appendChild(element1);

    var cell2 = row.insertCell(1); //ley minima
    cell2.innerHTML=document.getElementById(minimo).value;

    var cell3 = row.insertCell(2); //descripcion
    cell3.innerHTML = document.getElementById(maximo).value;

    var cell4 = row.insertCell(3); //cantidad
    cell4.innerHTML = document.getElementById(bono).value;
    //convertir la tabla a JSON y mostrar la cadena en contenido
    document.getElementById(jsonID).value=JSON.stringify($("#"+tableID).tableToJSON({ignoreColumns: [0]}));
}

function deleteRowCantidad(tableID,jsonID) {
    try {
        var table = document.getElementById(tableID);
        var rowCount = table.rows.length;
        for (var i = 0; i < rowCount; i++) {
            var row = table.rows[i];
            var chkbox = row.cells[0].childNodes[0];
            if (null != chkbox && true == chkbox.checked) {
                table.deleteRow(i);
                rowCount--;
                i--;
            }
        }
    }
    catch (e) {
        alert(e);
    }
    document.getElementById(jsonID).value=JSON.stringify($("#"+tableID).tableToJSON({ignoreColumns: [0]}));
}

