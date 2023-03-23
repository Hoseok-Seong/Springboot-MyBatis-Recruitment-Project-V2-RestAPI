package shop.mtcoding.job.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostRespDto.RecruitmentPostListRespDto;
import shop.mtcoding.job.model.recruitmentPost.RecruitmentPostRepository;

@RequiredArgsConstructor
@Service
public class MainService {

    private final RecruitmentPostRepository recruitmentPostRepository;

    @Transactional
    public List<RecruitmentPostListRespDto> 게시글목록보기() {

        List<RecruitmentPostListRespDto> posts = recruitmentPostRepository.findByPost();

        // d-day 계산
        for (RecruitmentPostListRespDto post : posts) {
            post.calculateDiffDays(); // D-Day 계산
        }
        return posts;
    }
}