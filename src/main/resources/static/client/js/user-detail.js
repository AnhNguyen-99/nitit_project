$(document).ready(function() {
    $("#update-user").on("click",function (){
        if ($('#fullName').val().trim() === '' ||
            $('#username').val().trim() === '' ||
            $('#address').val().trim() === '' ||
            $('#phone').val().trim() === '') {
            swal(
                'Error',
                'Vui lòng điền các trường còn thiếu !',
                'warning'
            );
            return ;
        }
        if($("#age").val() <= 10 || $('#age').val() > 100){
            swal(
                'Error',
                'Vui lòng nhập tuổi đúng của bạn!',
                'warning'
            );
            return;
        }
        var data = {
            fullName: $('#fullName').val().trim(),
            username: $('#username').val().trim(),
            address: $('#address').val().trim(),
            phoneNumber: $('#phone').val().trim(),
            age: $("#age").val().trim()
        }

        $.ajax ({
            url: "/user/detail",
            type: "PUT",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(res){
                if(res.httpStatus === 'OK') {
                    swal(
                        'Thành công!',
                        res.msg,
                        'success'
                    );
                } else if(res.httpStatus === 'BAD_REQUEST'){
                    swal(
                        'Error',
                        res.msg,
                        'error'
                    );
                }
                location.reload();
            }
        })
    });

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('.user-avatar').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }


    $("#input-avatar").change(function(e) {
        readURL(this);
    });


    // $("#myForm").submit(function (e) {
    //     e.preventDefault();
    //     var formData = new FormData();
    //     NProgress.start();
    //     formData.append('file', $("#input-avatar")[0].files[0]);
    //     axios.post("/api/upload", formData).then(function(res){
    //         NProgress.done();
    //         if(res.data.success) {
    //             $("#avatar").val(res.data.link);
    //         }
    //         $("#myForm")[0].submit();
    //     }, function(err){
    //         NProgress.done();
    //         $("#myForm")[0].submit();
    //     });
    // });

});