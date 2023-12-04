package practicegreengram.feed.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FeedCommentSelDto {
    private int ifeed; // 더보기에 활용
    private int startIdx; // 더보기에 활용
    private int rowCount; // 더보기에 활용
    private List<Integer> ifeedList; // 전체 코멘트에 활용
}
