package practicegreengram.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSigninVo {
    private int result;
    private int iuser;
    private String nm;
    private String pic;
    //로그인 시 프론트에 보낼 vo
    //result(1):성공 (2):아이디 없음 (3):비밀번호 틀림
}
