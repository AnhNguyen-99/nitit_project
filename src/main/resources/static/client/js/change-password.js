$(document).ready(function() {
    $("#change-password").on("click", function () {
        if ($('#newPassword').val().trim() === '' ||
            $('#confirmPassword').val().trim() === '') {
            swal(
                'Error',
                'Vui lòng điền các trường còn thiếu !',
                'warning'
            );
            return;
        }
        if ($("#newPassword").val().trim() !== $('#confirmPassword').val().trim()) {
            swal(
                'Error',
                'Mật khẩu không khớp nhau!',
                'warning'
            );
            return;
        }
        var data = {
            newPassword: $("#newPassword").val().trim(),
            confirmPassword: $('#confirmPassword').val().trim()
        }

        $.ajax({
            url: "/user/change-password",
            type: "PUT",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (res) {
                if (res.httpStatus === 'OK') {
                    swal(
                        'Thành công!',
                        res.msg,
                        'success'
                    ).then(function (){
                        location.replace("/user/detail");
                    });
                } else if (res.httpStatus === 'NOT_FOUND') {
                    swal(
                        'Error',
                        res.msg,
                        'error'
                    );
                }
            }
        })
    });
});