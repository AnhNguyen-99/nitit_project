$(document).ready(function () {

    $(".change-product-amount").change(function () {
        if($(this).val() > 10 || $(this).val() <= 0){
            swal(
                'Lỗi!',
                'Vui lòng nhập số lượng từ 1 đến 10',
                'error'
            ).then(function (){
                location.reload();
            });
            return;
        }
        data = {};
        data.id = $(this).attr("idCartWatch");
        data.count = $(this).val();
        NProgress.start();
        var url = "/client/cart";

        $.ajax ({
            url: url,
            type: "PUT",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(res){
                NProgress.done();
                if(res.httpStatus === 'OK') {
                    swal(
                        'Thành công!',
                        res.msg,
                        'success'
                    ).then(function (){
                        location.reload();
                    });
                } else {
                    swal(
                        'Lỗi!',
                        res.msg,
                        'error'
                    );
                }
            }
        });
    });

    $(".delete-cart-product").on("click", function () {
        var id = $(this).attr("idItem");
        NProgress.start();
        var url = "/client/cart/" + id;

        $.ajax ({
            url: url,
            type: "DELETE",
            contentType: "application/json; charset=utf-8",
            success: function(res){
                NProgress.done();
                if(res.httpStatus === 'OK') {
                    swal(
                        'Thành công!',
                        res.msg,
                        'success'
                    ).then(function (){
                        location.reload();
                    });
                } else {
                    swal(
                        'Lỗi!',
                        res.msg,
                        'error'
                    );
                }
            }
        });
    })


});