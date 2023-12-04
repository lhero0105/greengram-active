package practicegreengram.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practicegreengram.ResVo;
import practicegreengram.feed.model.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;

    //피드 작성
    public ResVo postFeed(FeedInsDto dto){
        if(dto.getPics().size()==0){
            return new ResVo(2);
        }
        //입력받은 사진이 없으면 ResVo 객체의 result에 2를 담아 리턴
        FeedInsProcDto pDto= FeedInsProcDto.builder().
                iuser(dto.getIuser())
                .contents(dto.getContents())//
                .location(dto.getLocation())
                .pics(dto.getPics())
                .build();
        /*ifeed와 pics를 담을 수 있는 FeedInsProcDto를 builder()를 통해서 객체 생성
        ifeed는 첫번째 insert 때 생성되는 ifeed값을 useGeneratedKeys="true" keyProperty="ifeed"
        를 이용해서 실행 완료 시 피드 pk를 FeedInsProcDto의 ifeed에 담는다.
         */
        int affectedFeedCnt= mapper.insFeed(pDto);//첫 번째 insert: t_feed에 insert
        if(affectedFeedCnt==0||pDto.getIfeed()==0){
            return new ResVo(0);
            //피드가 insert 안되거나 피드pk를 못 받았으면 ResVo 객체의 result에 0을 담아 리턴
        }

        int affectedPicCnt= mapper.insFeedPics(pDto);//두 번째 insert: 피드 작성 시 t_feed_pics에 사진 등록
        //위에서 얻은 ifeed값과 dto로 받았던 pics을 이용하여 실행
        if(affectedPicCnt!=dto.getPics().size()){
            return new ResVo(3);
            //사진이 제대로 등록이 안 됐으면 result: 3
        }
        return new ResVo(pDto.getIfeed());//insert 성공 시 피드 pk를 result에 담아 리턴
    }
    //피드 리스트
    public List<FeedSelVo> getFeedAll(FeedSelDto dto) {
        List<FeedSelVo> list = mapper.selFeedAll(dto);

        List<Integer> ifeedList = new ArrayList();
        Map<Integer, FeedSelVo> feedMap = new HashMap();

        for (FeedSelVo vo : list) {
            vo.setPics(new ArrayList());
            ifeedList.add(vo.getIfeed());
            feedMap.put(vo.getIfeed(), vo);
        }

        if (ifeedList.size() > 0) {
            List<FeedPicsVo> feedPicsList = mapper.selFeedPics(ifeedList);

            for (FeedPicsVo vo : feedPicsList) {
                FeedSelVo feedVo = feedMap.get(vo.getIfeed());
                List<String> strPicsList = feedVo.getPics();
                strPicsList.add(vo.getPic());
            }

            List<FeedCommentSelVo> comments = mapper.selCommentAll(FeedCommentSelDto.builder()
                    .ifeedList(ifeedList).build());
            Map<Integer, List<FeedCommentSelVo>> feedCommentMap = new HashMap(); // 수정: 단일 객체 대신 목록으로 변경

            for (FeedCommentSelVo vo : comments) {
                if (!feedCommentMap.containsKey(vo.getIfeed())) { // 빈 방에 ifeed, 주솟값 넣기
                    feedCommentMap.put(vo.getIfeed(), new ArrayList<>()); //값을 주기 전 주솟값을 줍니다
                }
                feedCommentMap.get(vo.getIfeed()).add(vo);// 빈 주솟값 방에 값을 줍니다.
            }

            for (FeedSelVo vo : list) {
                List<FeedCommentSelVo> feedComments = feedCommentMap.get(vo.getIfeed());
                if (feedComments != null) {
                    vo.setComments(feedComments);
                    while(vo.getComments().size()>4){
                        vo.getComments().remove(vo.getComments().size()-1);
                    }
                    if (vo.getComments().size() == 4) {
                        vo.setIsMoreComment(1);
                        vo.getComments().remove(vo.getComments().size() - 1);
                    }
                }
            }
        }
        return list;
    }
    //피드 삭제
    public ResVo delFeed(FeedDelDto dto){
        Integer targetIfeed= mapper.selFeed(dto);
        //해당 댓글이 로그인한 유저가 쓴 댓글이 맞는지 확인
        if(targetIfeed==null){return new ResVo(0);}
        //댓글이 없거나 그 유저가 쓴글이 아니면 NULL > result에 0을 담은 ResVo객체를 리턴
        int affectedComment= mapper.delCommentByIfeed(targetIfeed);
        //먼저 해당 댓글의 댓글 삭제
        int affectedPics= mapper.delPicsByIfeed(targetIfeed);
        //해당 댓글의 사진들 삭제
        int affectedFav= mapper.delFavByIfeed(targetIfeed);
        //해당 댓글의 좋아요 삭제
        int affectedFeed= mapper.delFeed(targetIfeed);//피드 삭제
        return new ResVo(1);//삭제 완료 시 result에 1을 담은 ResVo객체를 리턴
    }
    //좋아요 토글 처리
    public ResVo toggleFeedFav(FeedFavDto dto){
        int affectedFavCnt= mapper.delFeedFav(dto);//좋아요 삭제를 해본다.
        if(affectedFavCnt==1){return new ResVo(0);}
        //위의 delete가 실행되면 result에 0을 담은 ResVo객체를 리턴
        affectedFavCnt= mapper.insFeedFav(dto);
        return new ResVo(1);//result에 1을 담은 ResVo객체를 리턴
    }
    //댓글 작성
    public ResVo postComment(FeedCommentInsDto dto){
        FeedCommentInsProcDto pDto= new FeedCommentInsProcDto(dto);
        //insert가 실행되면서 생기는 ifeedComment를 담기위해 pDto 생성(FeedCommentInsProcDto확인)
        int affectedRows= mapper.insFeedComment(pDto);
        return new ResVo(pDto.getIfeedComment());
        //실행 완료시 ResVo에 ifeedComment를 담아서 리턴
    }
    //댓글 더보기
    public List<FeedCommentSelVo> getCommentAll(int ifeed){
        return mapper.selCommentAll(FeedCommentSelDto.builder().
                ifeed(ifeed).startIdx(3).rowCount(999).build());
        //댓글 더보기 클릭이 indexNumber 4부터 9999개의 댓글을 select
        //builder를 이용하여 select문을 실행할 parameter(FeedCommentSelDto) 생성
    }
    //댓글 삭제
    public ResVo delComment(FeedCommentDelDto dto){
        int affectedRows= mapper.delFeedComment(dto);
        //로그인한 유저가 실행하는 선택한 댓글 삭제(FeedCommentDelDto 확인)
        return new ResVo(affectedRows);//성공: 1, 실패: 0
    }
}
