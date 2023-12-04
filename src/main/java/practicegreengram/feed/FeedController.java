package practicegreengram.feed;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import practicegreengram.ResVo;
import practicegreengram.feed.model.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@Tag(name= "피드 API", description = "피드 관련 처리")
public class FeedController {
    private final FeedService service;

    @PostMapping
    @Operation(summary = "피드 등록", description = "피드 등록 처리")
    @Parameters(value = {
            @Parameter(name="iuser", description = "작성자pk")
            , @Parameter(name="contents", description = "내용")
            , @Parameter(name="location", description = "위치")
            , @Parameter(name="pics", description = "사진")
    })
    public ResVo postFeed(@RequestBody FeedInsDto dto) {
        return service.postFeed(dto);
        /*사진이 하나도 없으면 result: 2
        피드가 insert 안되거나 피드pk를 못 받았으면 result: 0
        사진이 제대로 등록이 안 됐으면 result: 3
        피드와 사진이 정상적으로 등록 result: 피드 pk
        */
    }
    @GetMapping
    @Operation(summary = "피드 리스트", description = "전체 피드 리스트, 특정 사용자 프로필 화면에서 사용할 피드 리스트, 한 페이지 30개 피드 가져옴")
    @Parameters(value = {
            @Parameter(name="page", description = "page값"),
            @Parameter(name="loginedIuser", description = "로그인 유저 pk"),
            @Parameter(name="targetIuser", description = "(생략가능) 특정 사용자 프로필 화면의 주인 유저 pk")
    })
    public List<FeedSelVo> getFeedAll(int page, int loginedIuser,
                                      @RequestParam(required=false, defaultValue="0") int targetIuser) {
        final int ROW_COUNT = 30;//한 페이지에 피드 30개
        return service.getFeedAll(FeedSelDto.builder().
                loginedIuser(loginedIuser).targetIuser(targetIuser).
                startIdx((page-1) * ROW_COUNT).rowCount(ROW_COUNT).build());
        //select 실행에 필요한 값들을 가지고 있는 FeedSelDto를 builder로 생성
        //FeedSelDto 확인
        //프로필 사진을 클릭하지 않으면 targetIuser=0(defaultvalue)
    }
    @DeleteMapping
    @Operation(summary = "피드 삭제", description = "로그인한 유저가 실행하는 피드 삭제 처리" +
            "(해당 피드에 대한 좋아요, 사진, 댓글도 모두 삭제 처리)")
    public ResVo delFeed(FeedDelDto dto) {
        return service.delFeed(dto);
        //댓글이 없거나 그 유저가 쓴글이 아니면 NULL > result에 0을 담은 ResVo객체를 리턴
        //삭제 완료 시 result에 1을 담은 ResVo객체를 리턴
    }
    @GetMapping("/fav")
    @Operation(summary = "좋아요 처리", description = "Toggle로 처리함")
    @Parameters(value = {
            @Parameter(name="ifeed", description = "feed pk")
            , @Parameter(name="iuser", description = "로그인한 유저 pk")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 처리: result(1), 좋아요 취소: result(0)")
    })
    public ResVo toggleFeedFav(FeedFavDto dto) {
        return service.toggleFeedFav(dto);
        //result= insert: 1, delete: 0
    }
    @PostMapping("/comment")
    @Operation(summary = "댓글 작성", description = "로그인한 유저가 작성하는 댓글 처리")
    public ResVo postComment(@RequestBody FeedCommentInsDto dto) {
        return service.postComment(dto);
        //성공 시 result에 pk(ifeedComment)를 담아서 리턴
    }
    @GetMapping("/comment")
    @Operation(summary = "댓글 더보기", description = "댓글 더보기 클릭 시 나머지 댓글까지 전체 보기")
    @Parameter(name = "ifeed", description = "대상 피드")
    public List<FeedCommentSelVo> getCommentAll(int ifeed) {
        return service.getCommentAll(ifeed);
        //해당 피드(ifeed)의 더보기 클릭 시 실행
    }
    @DeleteMapping("/comment")
    @Operation(summary = "댓글 삭제", description = "로그인한 유저가 실행하는 댓글 삭제 처리")
    @Parameters(value = {
            @Parameter(name = "ifeed_comment", description = "댓글 pk"),
            @Parameter(name = "logined_iuser", description = "로그인한 유저 pk")
    })
    public ResVo delComment(@RequestParam("ifeed_comment") int ifeedComment,
                            @RequestParam("logined_iuser") int loginedIuser) {
        return service.delComment(FeedCommentDelDto.builder()
                .ifeedComment(ifeedComment)
                .loginedIuser(loginedIuser)
                .build());
        //delete 실행에 필요한 값들을 가지고 있는 객체(FeedCommentDelDto)를
        //builder를 이용하여 생성하면서 service.delComment의 파라미터로 사용
    }
}
