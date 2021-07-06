$(document).ready(function() {
    $("#btn-sign-in").on("click",function (){
        if($('#sign-in-username').val().trim() === '' || $('#sign-in-password').val().trim() === '') {
            swal(
                'Error',
                'Vui lòng nhập đầy đủ các trường còn thiếu',
                'error'
            );
            return;
        }

        var data = {
            username: $('#sign-in-username').val(),
            password: $('#sign-in-password').val()
        }
        $.ajax ({
            url: "/do-login",
            type: "POST",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(res){
                console.log(res);
                if(res.httpStatus === 'OK') {
                    swal(
                        'Good job!',
                        res.msg,
                        'success'
                    ).then(function (){
                        location.replace("/client/home");
                    });
                } else  {
                    swal(
                        'Error',
                        res.msg,
                        'error'
                    );
                }
            }
        });
    });
});