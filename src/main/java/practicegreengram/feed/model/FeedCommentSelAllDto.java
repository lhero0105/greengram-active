package practicegreengram.feed.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedCommentSelAllDto {
    private int ifeed;
    private int startIdx;
    private int rowCount;
}
