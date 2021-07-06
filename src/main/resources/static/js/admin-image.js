$(document).ready(function () {
    //Thêm ảnh vào sản phẩm
    $("#add-product-image").change(function() {
        var formData = new FormData();
        var imageDTO = {};
        imageDTO.idWatch = $(this).attr("idWatch");
        formData.append('image', $("#add-product-image")[0].files[0]);
        axios.post("/admin/product/upload", formData).then(function(res){
            if(res.data.httpStatus === 'OK') {
                imageDTO.link = res.data.data;
                $.ajax ({
                    url: "/admin/image",
                    type: "POST",
                    data: JSON.stringify(imageDTO),
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (response) {
                        if(response.httpStatus === 'OK'){
                            swal(
                                'Thành công!',
                                response.msg,
                                'success'
                            ).then(function() {
                                location.reload();
                            });
                        }else{
                            toastr.error(response.msg);
                        }
                    }
                });
            }else{
                toastr.error(res.data.msg);
            }
        }, function(err){
        });
    });

    //Xóa ảnh khỏi sản phẩm
    $(".delete-image").on("click", function () {
        var id = $(this).attr("idImage");
        swal({
            title: 'Xóa ảnh sản phẩm',
            text: "Bạn có muốn xóa ảnh này không?",
            type: 'warning',
            showCancelButton: true
        }).then(function (result) {
            if (result.value) {
                $.ajax ({
                    url: "/admin/image/"+ id,
                    type: "DELETE",
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (response) {
                        if (response.httpStatus === 'OK') {
                            swal(
                                'Thành công!',
                                response.msg,
                                'success'
                            ).then(function () {
                                location.reload();
                            });
                        } else {
                            toastr.error(response.msg);
                        }
                    }
                });
            }
        })
    });


});
