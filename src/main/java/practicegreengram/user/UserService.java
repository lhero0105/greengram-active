package practicegreengram.user;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import practicegreengram.ResVo;
import practicegreengram.user.model.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    //회원가입
    public ResVo userSignup(UserSignupDto dto){
        String hashedPw= BCrypt.hashpw(dto.getUpw(),BCrypt.gensalt());
        //회원가입 시 입력받은 비밀번호를 복잡한 문자열로 섞어준다.
        UserSignupProcDto pDto= UserSignupProcDto.builder().
                uid(dto.getUid()).upw(hashedPw).nm(dto.getNm()).
                pic(dto.getPic()).build();
        /*UserSignupProcDto pDto2 = new UserSignupProcDto();
        pDto.setUid(dto.getUid());
        pDto.setUpw(hashedPw);
        pDto.setNm(dto.getNm());
        pDto.setPic(dto.getPic());*/
        //위의 비밀번호와 입력받은 회원정보를 procDto에 박스갈이
        //useGeneratedKeys와 keyProperty를 이용하여 유저의 pk를 받기 위해 박스갈이
        //우리가 받는 dto와 보내는 vo에는 필드를 최소한으로 필요한 정보를
        //받거나 보내기 위해 박스갈이를 한다.(내 생각)(procDto, procVo를 만드는 이유)
        int affectedRowCnt= mapper.insUser(pDto);
        //박스갈이를 한 pDto를 파라미터로 받는 insert 실행
        if(affectedRowCnt==0){return new ResVo(0);}
        //영향받은 행이 없으면 ResVo 객체를 만들어서 0을 담은 후 리턴
        return new ResVo(pDto.getIuser());
        //성공적으로 실행됬으면 auto_increment된 유저의 pk를 ResVo 객체에 담은 후 리턴
    }
    //로그인
    public UserSigninVo userSignin(UserSigninDto dto) {
        UserSigninProcVo pVo= mapper.selUserByUid(dto.getUid());
        //dto로 받은 비밀번호와 db에 있는 비밀번호를 비교하기 위해 procVo를
        //selUserByUid의 리턴타입으로 했다.
        //프론트에 보낼 vo에 db에 있는 비밀번호를 담으면 안될 것 같아서? (내 생각)
        UserSigninVo vo= new UserSigninVo();
        //프론트에 보낼 vo 객체 생성
        if(pVo==null) {
            vo.setResult(2); // ID없음
            return vo;
            //dto로 받은 아이디가 없으면 pVo가 null이기 때문에
            //vo에 있는 result 필드에 2를 담아서 리턴(아이디 없음)
        } else if(!BCrypt.checkpw(dto.getUpw(),pVo.getUpw())){
            vo.setResult(3);
            return vo;
            //db에 있는 비밀번호랑 dto로 받은 비밀번호를 비교해서
            //다르면 vo에 있는 result 필드에 3을 담아서 리턴(비밀번호 틀림)
        }
        vo.setResult(1);
        vo.setIuser(pVo.getIuser());
        vo.setNm(pVo.getNm());
        vo.setPic(pVo.getPic());
        return vo;
        //로그인 성공 시 result에는 1, iuser, nm, pic은 db에 있던 정보들을
        //setter를 이용하여 vo 객체에 있는 필드들에 담은 후 vo 리턴
    }
    //프로필 사진 클릭 시 나타나는 그 유저의 정보
    public UserInfoVo getUserInfo(int targetIuser){
        return mapper.selUserInfo(targetIuser);
        //targetIuser는 클릭한 프로필 사진의 유저pk
    }
    //프로필 사진 변경
    public ResVo patchUserPic(UserPatchPicDto dto){
        return new ResVo(mapper.updUserPic(dto));
        //mapper.updUserPic(dto)는 영향받은 행 -> 0(수정 실패) 또는 1(수정 성공)
    }
}
