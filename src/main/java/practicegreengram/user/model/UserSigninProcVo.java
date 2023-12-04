package practicegreengram.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserSigninProcVo {
    private int iuser;
    private String upw;
    private String nm;
    private String pic;
    //로그인 시 확인할 비밀번호(upw)랑
    //성공 시 vo에 담아 보내줄 나머지 정보들을 담을 procVo

}
