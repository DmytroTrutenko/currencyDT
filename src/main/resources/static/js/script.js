$(document).ready( ()=> {
    $("#convertForm").submit(e => {
        e.preventDefault();
       ajax_submit();
    });
});

const ajax_submit = () => {
    let data = {};
    data["fromCurrency"] = $('#fromCurrency').val();
    data["toCurrency"] = $('#toCurrency').val();
    data["amount"] = $("#amount").val();
    $("#btn-convert").prop("disabled", true);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/main",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 600000,
        success: data => {
            $('#result').html(data['convertResult']);

            let tr;
            tr = $('<tr/>');
            tr.append("<td>" + data['msg'] + "</td>");
            $('#table-history').append(tr);

            console.log("SUCCESS : ", data);
            $("#btn-convert").prop("disabled", false);
        },
        error: e => {
            console.log("ERROR: ", e);
            $("#btn-convert").prop("disabled", false);
        }
    });
}