$(document).ready(function() {

    $("#btn-register").on("click", function () {
        if ($('#register-name').val().trim() === '' ||
            $('#register-username').val().trim() === '' ||
            $('#register-password').val().trim() === '' ||
            $('#register-password-confirmation').val().trim() === '' ||
            $('#register-address').val().trim() === '' ||
            $('#register-phone').val().trim() === '') {
            swal(
                'Error',
                'Bạn cần điền vào tất cả các giá trị !',
                'error'
            );
            return ;
        }

        if ($('#register-password').val().trim() !== $('#register-password-confirmation').val().trim()) {
            swal(
                'Error',
                'Password không trùng nhau !',
                'error'
            );
            return ;
        }

        var data = {
            fullName: $('#register-name').val().trim(),
            username: $('#register-username').val().trim(),
            password: $('#register-password').val().trim(),
            address: $('#register-address').val().trim(),
            phoneNumber: $('#register-phone').val().trim()
        }

        $.ajax ({
            url: "/register",
            type: "POST",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(res){
                if(res.httpStatus === 'OK') {
                    swal(
                        'Good job!',
                        res.msg,
                        'success'
                    ).then(function (){
                        location.replace("/login");
                    });
                } else  {
                    swal(
                        'Error',
                        res.msg,
                        'error'
                    );
                }
            }
        })
    });

    // function isEmail(email) {
    //     var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    //     return regex.test(email);
    // }
});