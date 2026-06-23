function buildHtmlTableToEdit(JSONdata,tableID) {
    var myList = jQuery.parseJSON(document.getElementById(JSONdata).value);

    var columns = addAllColumnHeaders(myList,tableID,"edit");

    for (var i = 0 ; i < myList.length ; i++) {
        var row$ = $('<tr/>');
        row$.append("<td><input type=\"checkbox\"></td>");
        for (var colIndex = 0 ; colIndex < columns.length ; colIndex++) {
            var cellValue = myList[i][columns[colIndex]];

            if (cellValue == null) { cellValue = ""; }

            row$.append($('<td/>').html(cellValue));
        }
        $("#"+tableID).append(row$);
    }
}

// Builds the HTML Table out of myList json data from Ivy restful service.
function buildHtmlTableToShow(JSONdata,tableID) {
    var myList = jQuery.parseJSON(document.getElementById(JSONdata).value);

    var columns = addAllColumnHeaders(myList,tableID,"");

    for (var i = 0 ; i < myList.length ; i++) {
        var row$ = $('<tr/>');
        for (var colIndex = 0 ; colIndex < columns.length ; colIndex++) {
            var cellValue = myList[i][columns[colIndex]];

            if (cellValue == null) { cellValue = ""; }

            row$.append($('<td/>').html(cellValue));
        }
        $("#"+tableID).append(row$);
    }
}

// Adds a header row to the table and returns the set of columns.
// Need to do union of keys from all records as some records may not contain
// all records
function addAllColumnHeaders(jsonstring,tableID,tipo){
    var columnSet = [];
    var headerTr$ = $('<tr/>');

    for (var i = 0 ; i < jsonstring.length ; i++) {
        var rowHash = jsonstring[i];
        //if(tipo=="edit") headerTr$.append($("<th/>"));
        for (var key in rowHash) {
            if ($.inArray(key, columnSet) == -1){
                columnSet.push(key);
                headerTr$.append($('<th/>').html(key));
            }
        }
    }
    if(tipo!="edit")
        $("#"+tableID).append(headerTr$);
    return columnSet;
}