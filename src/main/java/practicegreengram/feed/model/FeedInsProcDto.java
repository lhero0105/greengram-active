package practicegreengram.feed.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FeedInsProcDto {
    private int iuser;
    private String contents;
    private String location;
    private List<String> pics;
    private int ifeed;

}
