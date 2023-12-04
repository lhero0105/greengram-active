package practicegreengram.user;

import org.apache.ibatis.annotations.Mapper;
import practicegreengram.user.model.*;

@Mapper
public interface UserMapper {
    int insUser(UserSignupProcDto dto);//회원가입
    UserSigninProcVo selUserByUid(String uid);//로그인
    UserInfoVo selUserInfo(int targetIuser);
    //프로필 클릭 시 나타나는 유저 정보 표시
    int updUserPic(UserPatchPicDto dto);//프로필 사진 변경
}
