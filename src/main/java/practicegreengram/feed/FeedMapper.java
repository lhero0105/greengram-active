package practicegreengram.feed;

import org.apache.ibatis.annotations.Mapper;
import practicegreengram.feed.model.*;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedInsProcDto dto);//피드 작성
    List<FeedSelVo> selFeedAll(FeedSelDto dto);//피드 리스트
    Integer selFeed(FeedDelDto dto);//피드 삭제 시 확인하는 select문
    int delFeed(int ifeed);//피드 삭제
    int insFeedPics(FeedInsProcDto dto);//피드 작성 시 실행되는 insert문(사진 추가)
    List<FeedPicsVo> selFeedPics(List<Integer> ifeedList);//피드 리스트 실행 시 해당 피드들의 사진들
    int delPicsByIfeed(int ifeed);//피드 삭제 시 해당 피드의 사진들 삭제
    int insFeedFav(FeedFavDto dto);//좋아요 등록
    int delFeedFav(FeedFavDto dto);//좋아요 취소
    int delFavByIfeed(int feed);//피드 삭제 시 해당 피드의 좋아요 삭제
    int insFeedComment(FeedCommentInsProcDto dto);//댓글 작성
    List<FeedCommentSelVo> selCommentAll(FeedCommentSelDto dto);//댓글 더보기 ㅇ
    int delFeedComment(FeedCommentDelDto dto);//댓글 삭제 ㅇ
    int delCommentByIfeed(int ifeed);//피드 삭제 시 해당 피드의 댓글들 삭제 ㅇ

}
