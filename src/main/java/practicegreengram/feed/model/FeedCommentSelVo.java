package practicegreengram.feed.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCommentSelVo {
    private int ifeed;
    private int ifeedComment;
    private String comment;
    private String createdAt;
    private int writerIuser;
    private String writerNm;
    private String writerPic;
}
