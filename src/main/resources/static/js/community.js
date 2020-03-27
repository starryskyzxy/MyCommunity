//添加一级评论和刷新页面
function postComment() {
    let questionId = $("#question-id").val();
    let content = $("#comment_content").val();

    if (!content){
        alert("不能回复空内容哦…^_^");
        return ;
    }

    $.ajax({
       type: "post",
        url: "/comment",
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        contentType: 'application/json',
        dataType:"json",
        success:function (result) {
           if (result.code == 200){
               window.location.reload(); //浏览器窗口自动刷新
           }else{
               //判断是否登录
               if (result.code==2003){
                   //确认框，返回一个boolean值
                   let isAccepted = confirm(result.message);
                   if (isAccepted == true){
                       //打开一个新地址
                        window.open("https://github.com/login/oauth/authorize?client_id=3bd0936bdd7a8d28efa2&redirect_id=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closeable",true);
                   }
               }else{
                   alert(result.message);
               }
           }
           console.log(result);
        }
    });
    console.log(questionId);
    console.log(content);
}

//二级评论
function collapseComments(e) {
    let dataId = e.getAttribute("data-id");
    let collapse = e.getAttribute("data-collapse");
    let comments = $("#comment-"+dataId);
    if (collapse){
        comments.removeClass("in");  //jquery
        e.removeAttribute("data-collapse");
        e.classList.remove("active"); //javascript
    }else {
        comments.addClass("in");
        e.setAttribute("data-collapse","in");
        e.classList.add("active");
    }
    console.log(dataId);
}