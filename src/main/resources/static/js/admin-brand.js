$(document).ready(function() {

    var brand = {};
    var idBrand;

    //Mở popup thêm mới
    $("#new-brand").on("click", function () {
        brand = {};
        $('#input-brand-name').val("");
        $('#input-brand-intro').val("");
        $('#input-brand-location').val('')
        $("#modal-create-brand").modal();
    });

    //CLick Thêm mới
    $(".btn-save-brand").on("click", function () {
        if( $("#input-brand-name").val() === "" ||
            $("#input-brand-location").val() === "" ||
            $("#input-brand-intro").val() === '' ||
            $("#input-brand-name").val().trim() === "" ||
            $("#input-brand-location").val().trim() === "" ||
            $("#input-brand-intro").val().trim() === '') {
            toastr.warning('Vui lòng nhập đầy đủ các trường!');
            return ;
        }
        brand.name = $('#input-brand-name').val().trim();
        brand.introduce = $('#input-brand-intro').val().trim();
        brand.location = $('#input-brand-location').val().trim();
        $.ajax ({
            url: "/admin/brand",
            type: "POST",
            data: JSON.stringify(brand),
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
                    toastr.error(response.msg);
                }
            }
        });
    });

    //Mở popup sửa
    $(".btn-edit-brand").on("click", function () {
        idBrand = $(this).attr("idBrand");
        $.ajax ({
            url: "/admin/brand/"+ idBrand,
            type: "GET",
            success: function (response) {
                if(response.httpStatus === 'OK'){
                    $('#input-brand-name-update').val(response.data.name);
                    $('#input-brand-intro-update').val(response.data.introduce);
                    $('#input-brand-location-update').val(response.data.location);
                    $('#modal-update-brand').modal();
                }
            }
        });
    });

    //Sửa
    $("#btn-update-brand").on("click", function () {
        brand = {};
        brand.id = idBrand;
        if ($("#input-brand-name-update").val() === "" ||
            $("#input-brand-intro-update").val() === "" ||
            $("#input-brand-location-update").val() === "" ||
            $("#input-brand-name-update").val().trim() === "" ||
            $("#input-brand-intro-update").val().trim() === "" ||
            $("#input-brand-location-update").val().trim() === "") {
            toastr.warning('Vui lòng nhập đầy đủ các trường');
            return;
        }

        brand.name = $('#input-brand-name-update').val().trim();
        brand.introduce = $('#input-brand-intro-update').val().trim();
        brand.location = $('#input-brand-location-update').val().trim();

        $.ajax ({
            url: "/admin/brand",
            type: "PUT",
            data: JSON.stringify(brand),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                if (response.httpStatus === 'OK') {
                    swal(
                        'Thành công!',
                        response.msg,
                        'success'
                    ).then(function (){
                       location.reload();
                    });
                }else{
                    toastr.error(response.msg);
                }
            }
        });
    });


    //delete
    $(".btn-delete-brand").on("click", function () {
        var id = $(this).attr("idBrand");
        swal({
            title: 'Xóa thương hiệu',
            text: "Bạn có muốn xóa thương hiệu này?",
            type: 'warning',
            showCancelButton: true
        }).then(function (result) {
            if (result.value) {
                $.ajax({
                    url: "/admin/brand/"+ id,
                    type: "DELETE",
                    success: function (response) {
                        if(response.httpStatus === 'OK'){
                            swal(
                                'Thành công!',
                                response.msg,
                                'success'
                            ).then(function (){
                                location.reload();
                            })
                        }else{
                            toastr.error(response.msg);
                        }
                    }
                });
            }
        })
    });
});
