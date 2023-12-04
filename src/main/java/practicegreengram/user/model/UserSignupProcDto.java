package practicegreengram.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSignupProcDto {
    private int iuser;//insert 실행 시 얻는 pk
    private String uid;
    private String upw;
    private String nm;
    private String pic;
    //받은 dto에서 여기로 옮긴다.(서비스에서 builder를 써서)
}
