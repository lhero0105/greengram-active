package practicegreengram.feed.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCommentInsDto {
    private int ifeed;
    private int iuser;
    private String comment;

}
