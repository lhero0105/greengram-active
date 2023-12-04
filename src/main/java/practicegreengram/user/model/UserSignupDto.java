package practicegreengram.user.model;

import lombok.Data;

@Data
public class UserSignupDto {
    private String uid;
    private String upw;
    private String nm;
    private String pic;
    //회원가입 시 받는 회원 정보
}
