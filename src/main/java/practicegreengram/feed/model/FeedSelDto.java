package practicegreengram.feed.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedSelDto {
    private int loginedIuser;
    private int targetIuser;
    private int startIdx;
    private int rowCount;
}
