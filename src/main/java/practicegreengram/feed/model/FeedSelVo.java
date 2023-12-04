package practicegreengram.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FeedSelVo {
    private int ifeed; // 게시글 pk번호
    private String contents; // 게시글 내용
    private String location; // 위치
    private String createdAt; // 게시글 작성시간
    private int writerIuser; // 작성자 pk번호
    private String writerNm; // 글작성자 이름
    private String writerPic; // 글작성자 프로필사진
    private int isFav; // 좋아요
    private List<String> pics; // 게시글 사진(중복가능)
    private List<FeedCommentSelVo> comments; // Feed에 댓글 보기 4개로설정되어있음
    private int isMoreComment; // 만약 댓글이 4개라면 3번방 댓글 삭제 하고 댓글 더보기 버튼 활성화됨
}
