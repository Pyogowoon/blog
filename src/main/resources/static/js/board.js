let index = {

init: function(){
    $("#btn-save").on("click", ()=>{
         this.save();
    });
    $("#btn-delete").on("click", ()=>{
         this.deleteById();
    });
     $("#btn-reply-save").on("click", ()=>{
         this.replySave();
    });
    $("#btn-reply-delete").on("click", ()=>{
             this.replyDelete();
        });


 },
save: function(){

             let data = {
                 title: $("#title").val(),
                 content: $("#content").val(),

             };

             $.ajax({
                  type: "POST",
                   url: "/api/board",
                   data: JSON.stringify(data), //http body 데이터
                   contentType:"application/json; charset=utf-8",
                   dataType:"json"

             }).done(function(resp){

                     alert("글쓰기가 완료되었습니다.");
     //
                     location.href="/";
             }).fail(function(error){
                 alert(JSON.stringify(error));
             });

         },

            deleteById: function(){
            let id= $("#id").text();

                      $.ajax({
                           type: "DELETE",
                            url: "/api/board/"+id,
                            dataType: "json"
                      }).done(function(resp){
                              alert("삭제가 완료되었습니다.");
                              location.href="/";
                      }).fail(function(error){
                          alert(JSON.stringify(error));
                      });

                  },

   replySave: function(){

             let data = {
               userId: $("#userId").val(),
               boardId: $("#boardId").val(),
               content: $("#reply-content").val()

                        };



             $.ajax({
             type: "POST",
             url: `/api/board/${data.boardId}/reply` ,   //백틱 건 이유 : 자바스크립트의 변수값이 문자열에 들어감    //즉 게시물 번호를 동적으로 받기위해
             data: JSON.stringify(data), //http body 데이터
             contentType:"application/json; charset=utf-8",
             dataType:"json"

             }).done(function(resp){

              alert(" 댓글작성이 완료되었습니다.");
                 location.href=`/board/${data.boardId}`;
              }).fail(function(error){
                    alert(JSON.stringify(error));
              });

               },

    replyDelete: function(boardId, replyId){

                 $.ajax({
                   type: "DELETE",
                   url: `/api/board/${boardId}/${replyId}` ,   //백틱 건 이유 : 자바스크립트의 변수값이 문자열에 들어감    //즉 게시물 번호를 동적으로 받기위해
                   dataType:"json"

                     }).done(function(resp){
                       alert(" 댓글삭제가 완료되었습니다.");
                        location.href=`/board/${boardId}`;
                     }).fail(function(error){
                         alert(JSON.stringify(error));
                         });

                       },
        }

    index.init();