package practicegreengram.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoVo {
    private String nm;
    private String pic;
    private String createdAt;
    private int feedCnt;
    //등록한 피드 수
    private int favCnt;
    //등록한 피드에 달린 좋아요 수
}
