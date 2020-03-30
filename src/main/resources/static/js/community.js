//添加一级评论和刷新页面
function postComment() {
    let questionId = $("#question-id").val();
    let content = $("#comment_content").val();

    comment2target(questionId,1,content);
}

function comment2target(targetId,type,content) {

    if (!content){
        alert("不能回复空内容哦…^_^");
        return ;
    }

    $.ajax({
        type: "post",
        url: "/comment",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
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
        }
    });
}

function comment(e) {
    let id = e.getAttribute("data-id");
    let content = $("#input-"+id).val();
    comment2target(id,2,content);
}

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, commentDTO) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": commentDTO.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": commentDTO.user.name
                    })).append($("<div/>", {
                        "html": commentDTO.comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(commentDTO.comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    let dataTag = e.getAttribute("data-tag");
    let previous = $("#tag").val();
    if (previous){
        if (previous.indexOf(dataTag) == -1){
            $("#tag").val(previous+','+dataTag);
        }
    }else {
        $("#tag").val(dataTag);
    }
}