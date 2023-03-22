package shop.mtcoding.job.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostRespDto.RecruitmentPostListRespDto;
import shop.mtcoding.job.model.recruitmentPost.RecruitmentPostRepository;
import shop.mtcoding.job.util.DateUtil;

@RequiredArgsConstructor
@Service
public class MainService {

    private final RecruitmentPostRepository recruitmentPostRepository;

    @Transactional
    public List<RecruitmentPostListRespDto> 게시글목록보기() {

        List<RecruitmentPostListRespDto> posts = recruitmentPostRepository.findByPost();

        for (RecruitmentPostListRespDto post : posts) {
            long diffDays = DateUtil.deadline(post.getDeadline());
            post.setDiffDays(diffDays);
        }

        return posts;
    }
}
