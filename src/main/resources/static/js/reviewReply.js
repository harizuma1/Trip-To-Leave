'use strict';

let reviewReplyIndex = {
    init: function () {
        $("#reviewReply-btn-save").on("click", () => {
            this.reviewReplySave();
        });
    },

    reviewReplySave: function () {
        let data = {
            content: $("#reviewReply-content").val(),
        }
        let reviewId = $("#reviewId").val();
        console.log(data);
        console.log(reviewId);
        $.ajax({
            type: "POST",
            url: `/api/v1/review/${reviewId}/reviewReply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "text"
        }).done(function (res) {
            alert("댓글작성이 완료되었습니다.");
            location.href = `/review/${reviewId}`;
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },



    reviewReplyDelete: function (reviewId, reviewReplyId) {
        $.ajax({
            type: "DELETE",
            url: `/api/v1/review/${reviewId}/reviewReply/${reviewReplyId}`,
            dataType: "text"
        }).done(function (res) {
            alert("댓글삭제가 완료되었습니다.");
            location.href = `/review/${reviewId}`;
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },

}
reviewReplyIndex.init();