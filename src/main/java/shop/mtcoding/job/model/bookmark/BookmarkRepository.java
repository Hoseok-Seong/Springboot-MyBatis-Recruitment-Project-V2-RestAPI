package shop.mtcoding.job.model.bookmark;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.job.dto.bookmark.BookmarkReqDto;
import shop.mtcoding.job.dto.entPage.EntPageMyBookmarkRespDto;

@Mapper
public interface BookmarkRepository {
        public List<Bookmark> findAll();

        public Bookmark findById(int id);

        public int insert(Bookmark bookmark);

        public int updateById(Bookmark bookmark);

        public int deleteById(int id);

        public Bookmark findByRecruitmentIdAndUserId(@Param("recruitmentId") int recruitmentId,
                        @Param("userId") int userId);

        public List<BookmarkReqDto> findByUserId(@Param("userId") int userId);

        public List<EntPageMyBookmarkRespDto> findByEnterpriseId(int recruitmentId);
}
