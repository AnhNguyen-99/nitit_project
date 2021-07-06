$(document).ready(function() {

    var dataProduct = {};
    var listImg=[];
    var isUpdateImg = false;

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    //Upload image khi thêm mới
    $("#input-select-img-product").change(function() {
        readURL(this);
        var formData = new FormData();
        NProgress.start();
        formData.append('image', $("#input-select-img-product")[0].files[0]);
        if(!formData){
            return;
        }
        axios.post("/admin/product/upload", formData).then(function(res){
            NProgress.done();
            if(res.data.httpStatus === 'OK') {
                toastr.success(res.data.msg);
                $('.product-main-image').attr('src', res.data.data);
                listImg.push(res.data.data)
            }else{
                toastr.error(res.data.msg);
            }
        }, function(err){
            NProgress.done();
        });
    });

    //Mở modal
    $("#new-product").on("click", function () {
        dataProduct = {};
        listImg=[];
        $('#input-product-code').val("")
        $('#input-product-name').val("");
        $('#input-product-desc').val("");
        $("#input-product-category").val("");
        $("#input-product-feature").val("");
        $("#input-product-price").val("");
        $("#input-product-sale-price").val('')
        $("#input-product-reduce-price").val('')
        $('.product-main-image').attr('src', 'https://www.vietnamprintpack.com/images/default.jpg');
        $("#modal-create-product").modal();
    });

    //Thêm mới sản phẩm
    $(".btn-save-product").on("click", function () {
        if( $('#input-product-code').val() === '' ||
            $('#input-product-category').val() === '' ||
            $("#input-product-name").val() === "" ||
            $("#input-product-desc").val() === "" ||
            $("#input-product-price").val()==="" ||
            $("#input-product-sale-price").val()==="") {
            toastr.warning('Vui lòng nhập đầy đủ thông tin cần thiết sản phẩm');
            return;
        }
        if(listImg.length == 0){
            toastr.warning('Vui lòng chọn ảnh sản phẩm');
            return;
        }
        var imageDTOList = [];
        for(let i=0; i< listImg.length; i++){
            imageDTOList[i]={};
            imageDTOList[i].link = listImg[i];
        }
        dataProduct.code = $('#input-product-code').val();
        dataProduct.name = $('#input-product-name').val();
        dataProduct.description = $('#input-product-desc').val();
        dataProduct.feature = $('#input-product-feature').val();
        dataProduct.idBrand = $("#input-product-category").val();
        dataProduct.imageDTOList = imageDTOList;
        dataProduct.price = $("#input-product-price").val();
        dataProduct.salePrice = $("#input-product-sale-price").val();
        dataProduct.reducePrice = $("#input-product-reduce-price").val();
        // NProgress.start();
        var url = "/admin/product";
        $.ajax ({
            url: url,
            type: "POST",
            data: JSON.stringify(dataProduct),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(res){
                // NProgress.done();
                if(res.httpStatus === 'OK') {
                    swal(
                        'Thành công',
                        res.msg,
                        'success'
                    ).then( function (){
                        location.reload();
                    })
                } else  if(res.httpStatus === 'BAD_REQUEST'){
                    toastr.error(res.msg);
                }
            }
        });
    });

    //upload image khi sửa
    $("#input-select-img-product-update").change(function() {
        readURL(this);
        var formData = new FormData();
        NProgress.start();
        formData.append('image', $("#input-select-img-product-update")[0].files[0]);
        axios.post("/admin/product/upload", formData).then(function(res){
            NProgress.done();
            if(res.data.httpStatus === 'OK') {
                toastr.success(res.data.msg);
                $('.product-main-image').attr('src', res.data.data);
                listImg.push(res.data.data)
            }else{
                isUpdateImg = true;
                toastr.error(res.data.msg);
            }
        }, function(err){
            NProgress.done();
        });
    });

    //Mở dialog sửa
    $(".btn-edit-product").on("click", function () {
        var idWatch = $(this).attr("idWatch");
        dataProduct = {};
        listImg=[];
        isUpdateImg = false;
        dataProduct.id = idWatch;
        $.ajax ({
            url: "/admin/product/find/"+idWatch,
            type: "GET",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(res){
                console.log(res)
                if(res.httpStatus === 'OK') {
                    dataProduct = res.data;
                    $('#input-product-code-update').val(dataProduct.code);
                    $('#input-product-name-update').val(dataProduct.name);
                    $('#input-product-desc-update').val(dataProduct.description);
                    $('#input-product-feature-update').val(dataProduct.feature);
                    $("#input-product-category-update").val(dataProduct.idBrand);
                    $("#input-product-price-update").val(dataProduct.price);
                    $("#input-product-sale-price-update").val(dataProduct.salePrice);
                    $("#input-product-reduce-price-update").val(dataProduct.reducePrice);
                    $('.product-main-image').attr('src', dataProduct.imageDTOList[0].link);
                    dataProduct.imageDTOList = [];
                    $("#modal-update-product").modal();
                }
            }
        });
    });

    //Sửa sản phẩm
    $("#btn-update-product").on("click", function () {
        if( $('#input-product-code-update').val() === '' ||
            $("#input-product-name-update").val() === "" ||
            $("#input-product-desc-update").val() === "" ||
            $("#input-product-category-update").val()==="" ||
            $("#input-product-price-update").val() === '' ||
            $("#input-product-sale-price-update").val() === ''
        ){
            toastr.warning("Vui lòng nhập đầy đủ các trường yêu cầu");
            return;
        }

        if(isUpdateImg && listImg.length == 0){
            toastr.warning('Vui lòng chọn ảnh cho sản phẩm');
            return;
        }

        if(!isUpdateImg && listImg.length > 0){
            var imageDTOList = [];
            for(let i =0; i<listImg.length; i++){
                imageDTOList[i] = {};
                imageDTOList[i].link = listImg[i];
            }
            dataProduct.imageDTOList = imageDTOList;
        }
        dataProduct.code =  $('#input-product-code-update').val();
        dataProduct.name = $('#input-product-name-update').val();
        dataProduct.description = $('#input-product-desc-update').val();
        dataProduct.feature = $('#input-product-feature-update').val()
        dataProduct.categoryId = $("#input-product-category-update").val();
        dataProduct.images = $('.product-main-image').attr('src');
        dataProduct.price = $("#input-product-price-update").val();
        dataProduct.salePrice = $("#input-product-sale-price-update").val();
        dataProduct.reducePrice = $("#input-product-reduce-price-update").val();

        $.ajax ({
            url: "/admin/product/",
            type: "PUT",
            data: JSON.stringify(dataProduct),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(res){
                console.log(res)
                if(res.httpStatus === 'OK'){
                    swal(
                        'Thành công',
                        res.msg,
                        'success'
                    ).then( function (){
                        location.reload();
                    })
                }else {
                    toastr.error(res.msg);
                }
            }
        });
    });

    //Xóa sản phẩm
    $(".btn-delete-product").on("click", function () {
        var id = $(this).attr("idWatch");
        swal({
            title: 'Xóa sản phẩm',
            text: "Bạn có muốn xóa sản phẩm này ?",
            type: 'warning',
            showCancelButton: true
        }).then(function (result) {
            //Đồng ý
            if (result.value) {
                NProgress.start();
                var url= "/admin/product/" + id;
                $.ajax ({
                    url: url,
                    type: "DELETE",
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function(res){
                        NProgress.done();
                        if(res.httpStatus === 'OK') {
                            swal(
                                'Thành công!',
                                res.msg,
                                'success'
                            ).then(function() {
                                location.replace("/admin/product");
                            });
                        } else  if(res.httpStatus === 'BAD_REQUEST'){
                            toastr.error(res.msg);
                        }
                    }
                });
            }
        })
    });
});
