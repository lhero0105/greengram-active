package practicegreengram.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSigninDto {
    private String uid;
    private String upw;
    //로그인 시 입력받는 아이디, 비밀번호
}
