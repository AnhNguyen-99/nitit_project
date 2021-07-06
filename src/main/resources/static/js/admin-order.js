$(document).ready(function() {
    var id;
    //Duyệt đơn hàng
    $(".browse-menus").on("click", function () {
        id = $(this).attr('idOrder');
        $.ajax ({
            url: "/admin/order/confirm/"+ id,
            type: "GET",
            success: function (response) {
                if(response.httpStatus === 'OK'){
                    swal(
                        'Thành công',
                        response.msg,
                        'success'
                    ).then(function (){
                       location.reload();
                    });
                }else{
                    toastr.warning(response.msg);
                }
            }
        });
    })

    //Thay đổi status
    $("#change-status" ).on('change', function() {
        id = $(this).attr('idOrders');
        var status = $(this).val();
        if(status === '-1'){
            return;
        }
        var data = {};
        data.id = id;
        data.status = status;
        $.ajax ({
            url: "/admin/order/status",
            type: "PUT",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                if(response.httpStatus === 'OK'){
                    swal(
                        'Thành công',
                        response.msg,
                        'success'
                    ).then(function (){
                        location.reload();
                    });
                }else{
                    toastr.warning(response.msg);
                }
            }
        });
    });
})
