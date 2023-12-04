package practicegreengram.feed.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedCommentDelDto {
    private int ifeedComment;
    private int loginedIuser;
}
