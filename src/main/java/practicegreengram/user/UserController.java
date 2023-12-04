package practicegreengram.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import practicegreengram.ResVo;
import practicegreengram.user.model.UserInfoVo;
import practicegreengram.user.model.UserSigninDto;
import practicegreengram.user.model.UserSigninVo;
import practicegreengram.user.model.UserSignupDto;
import practicegreengram.user.model.UserPatchPicDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name= "유저 API", description = "유저 관련 처리")
public class UserController {
    private final UserService service;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 처리")
    @Parameters(value = {
            @Parameter(name= "uid", description = "아이디" ),
            @Parameter(name= "upw", description = "비밀번호"),
            @Parameter(name= "nm", description = "이름"),
            @Parameter(name= "pic", description = "프로필 사진")
    })
    public ResVo userSignup(@RequestBody UserSignupDto dto) {
        return service.userSignup(dto);
        //ResVo 객체에 insert 실행 시 auto_increment되는 레코드의 pk를 담아서 응답 처리
    }
    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "아이디와 비밀번호를 활용한 로그인 처리")
    @Parameters(value = {
            @Parameter(name="uid", description = "아이디"),
            @Parameter(name="upw", description = "비밀번호")
    })
    public UserSigninVo userSignin(@RequestBody UserSigninDto dto) {
        return service.userSignin(dto);
        //로그인 성공 시 해당 유저의 pk, nm, pic을 vo에 넣어서 응답 처리
    }
    @GetMapping
    @Operation(summary = "유저 정보", description = "프로필 화면에서 사용할 프로필 유저 정보")
    @Parameter(name="target_iuser", description = "프로필 주인 유저 pk" )
    public UserInfoVo getUserInfo(@RequestParam("target_iuser") int targetIuser) {
        return service.getUserInfo(targetIuser);
        //해당 유저(targetIuser)에 대한 nm,pic,createdAt,feedCnt,favCnt를 vo에 담아서 리턴
    }


    @PatchMapping("/pic")
    @Operation(summary = "프로필 사진 변경",
            description = "로그인한 유저가 프로필 사진 클릭 시 원하는 사진으로 변경 처리")
    public ResVo patchUserPic(@RequestBody UserPatchPicDto dto) {
        return service.patchUserPic(dto);
        //수정 성공 result:1, 수정 실패 result: 0
    }
}
