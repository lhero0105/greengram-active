package practicegreengram.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchPicDto {
    private int iuser;
    private String pic;
    //로그인한 유저pk, 변경하고 싶은 사진
}
