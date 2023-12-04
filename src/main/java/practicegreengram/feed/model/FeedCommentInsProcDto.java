package practicegreengram.feed.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCommentInsProcDto {
    private int ifeed;
    private int iuser;
    private String comment;
    private int ifeedComment;

    public FeedCommentInsProcDto(FeedCommentInsDto dto){
        ifeed= dto.getIfeed();
        iuser= dto.getIuser();
        comment= dto.getComment();
    }
}
