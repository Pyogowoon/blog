let index = {

init: function(){
// on(이벤트 , 무엇을할건지) 파라메터 2개 들어갈수있음
    $("#btn-save").on("click", ()=>{ //function(){} 대신 ()=>{} 사용 이유는
    // this를 바인딩하기 위해서 this의 window 객체 호출을 막기위함으로 알고있으면 된다.
         this.save();
    });

     $("#btn-update").on("click", ()=>{ //function(){} 대신 ()=>{} 사용 이유는
        // this를 바인딩하기 위해서 this의 window 객체 호출을 막기위함으로 알고있으면 된다.
             this.update();
        });

// 구 로그인 영역
//      $("#btn-login").on("click", ()=>{ //function(){} 대신 ()=>{} 사용 이유는
//        // this를 바인딩하기 위해서 this의 window 객체 호출을 막기위함으로 알고있으면 된다.
//             this.login();
//        });
 },

save: function(){

             let data = {
                 username: $("#username").val(),
                 password: $("#password").val(),
                 email: $("#email").val()
             };

         //ajax 호출시 default가 비동기 호출
         // ajax 통신으로 3개의 데이터를 json으로 변경하여 insert 요청
         // ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환
             $.ajax({
                  type: "POST",
                   url: "/auth/joinProc",
                   data: JSON.stringify(data), //http body 데이터
                   contentType:"application/json; charset=utf-8", //body데이터가 어떤 타입인지
                   dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 문자열인데,
                    //생긴게 json이라면 javascript 오브젝트로 변경해줌

             }).done(function(resp){ //응답 값이 여기로 담김, json이 object로 변환되서 이곳에
                    if(resp.status === 500){
                    alert("회원가입에 실패하였습니다.");
                    }else{
                     alert("회원가입이 완료되었습니다.");
                     location.href="/";
                     }
             }).fail(function(error){
                 alert("중복된 아이디입니다.");
             });

         },


update: function(){

        let data = {
            id : $("#id").val(),
            username : $("#username").val(),
            password : $("#password").val(),
            email : $("#email").val()
        };

            $.ajax({
             type: "PUT",
             url: "/user",
             data: JSON.stringify(data),
             contentType: "application/json; charset=UTF-8",
             dataType: "json"
            }).done(function(resp){
            alert("수정이 완료되었습니다.");
            location.href="/";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
           },


        }

    index.init();






    //구 로그인 영역
    //         login: function(){
    //
    //                      let data = {
    //                          username: $("#username").val(),
    //                          password: $("#password").val()
    //
    //                      };
    //
    //                      $.ajax({
    //                           type: "POST",
    //                            url: "/api/user/login",
    //                            data: JSON.stringify(data), //http body 데이터
    //                            contentType:"application/json; charset=utf-8", //body데이터가 어떤 타입인지
    //                            dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 문자열인데,
    //                             //생긴게 json이라면 javascript 오브젝트로 변경해줌
    //
    //                      }).done(function(resp){ //응답 값이 여기로 담김, json이 object로 변환되서 이곳에
    //
    //                              alert("로그인이 완료되었습니다..");
    //              //                console.log(resp);
    //                              location.href="/";
    //                      }).fail(function(error){
    //                          alert(JSON.stringify(error));
    //                      });
    //
    //
    //         }