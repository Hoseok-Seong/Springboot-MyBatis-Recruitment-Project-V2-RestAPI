package shop.mtcoding.job.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostReqDto.SaveRecruitmentPostReqDto;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostReqDto.UpdateRecruitmentPostReqDto;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostRespDto.RecruitmentPostCategoryRespDto;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostRespDto.RecruitmentPostSearchRespDto;
import shop.mtcoding.job.handler.exception.CustomApiException;
import shop.mtcoding.job.handler.exception.CustomException;
import shop.mtcoding.job.model.recruitmentPost.RecruitmentPost;
import shop.mtcoding.job.model.recruitmentPost.RecruitmentPostRepository;
import shop.mtcoding.job.model.recruitmentSkill.RecruitmentSkillRepository;

@RequiredArgsConstructor
@Service
public class RecruitmentService {
    private final RecruitmentPostRepository recruitmentPostRepository;

    private final RecruitmentSkillRepository recruitmentSkillRepository;

    @Transactional
    public void 채용공고쓰기(SaveRecruitmentPostReqDto saveRecruitmentPostReqDto, int enterpriseId) {

        // Base64로 인코딩된 이미지 데이터를 디코딩합니다.
        String base64Data = saveRecruitmentPostReqDto.getEnterpriseLogo();
        String encodedData = base64Data.replaceAll("^data\\:[^;]*;base64,", "");
        byte[] imageData = Base64.getDecoder().decode(encodedData);

        // 저장할 파일 경로와 파일 이름을 결합합니다.
        String fileName = "prefix_" + System.currentTimeMillis() + ".png";
        String filePath = "src/main/resources/static/images/" + fileName;

        // 이미지 데이터를 파일로 저장합니다.
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(imageData);
        } catch (IOException e) {
            // 에러 처리
        }

        filePath = "/images/" + fileName;

        RecruitmentPost recruitmentPost = saveRecruitmentPostReqDto.toModel(enterpriseId, filePath);

        // 저장된 파일의 경로를 DB에 저장
        int result = recruitmentPostRepository.insert(recruitmentPost);
        if (result != 1) {
            throw new CustomException("채용공고 작성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        RecruitmentPost savedPost = recruitmentPostRepository.findByEnterpriseLogo(filePath);
        if (savedPost == null) {
            throw new CustomException("파일 경로를 찾을 수 없습니다.");
        }

        saveRecruitmentPostReqDto.setId(savedPost.getId());
        for (Integer checkSkill : saveRecruitmentPostReqDto.getSkill()) {
            result = recruitmentSkillRepository.insert(saveRecruitmentPostReqDto.getId(), checkSkill);
            if (result != 1) {
                throw new CustomException("실패");
            }
        }
    }

    @Transactional
    public void 채용공고수정(int id, UpdateRecruitmentPostReqDto updateRecruitmentPostReqDto, int enterpriseId) {
        RecruitmentPost recruitmentPS = recruitmentPostRepository.findById(id);
        if (recruitmentPS == null) {
            throw new CustomApiException("존재하지 않는 채용공고를 수정할 수 없습니다");
        }
        if (recruitmentPS.getEnterpriseId() != enterpriseId) {
            throw new CustomApiException("채용공고를 수정할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        // Base64로 인코딩된 이미지 데이터를 디코딩합니다.
        String base64Data = updateRecruitmentPostReqDto.getEnterpriseLogo();
        String encodedData = base64Data.replaceAll("^data\\:[^;]*;base64,", "");
        byte[] imageData = Base64.getDecoder().decode(encodedData);

        // 저장할 파일 경로와 파일 이름을 결합합니다.
        String fileName = "prefix_" + System.currentTimeMillis() + ".png";
        String filePath = "src/main/resources/static/images/" + fileName;

        // 이미지 데이터를 파일로 저장합니다.
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(imageData);
        } catch (IOException e) {
            // 에러 처리
        }

        filePath = "/images/" + fileName;

        RecruitmentPost recruitmentPost = updateRecruitmentPostReqDto.toModel(id, enterpriseId, filePath);

        int result = recruitmentPostRepository.updateById(recruitmentPost);
        if (result != 1) {
            throw new CustomApiException("채용공고 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        RecruitmentPost updatedPost = recruitmentPostRepository.findByEnterpriseLogo(filePath);
        if (updatedPost == null) {
            throw new CustomException("파일 경로를 찾을 수 없습니다.");
        }

        // 기존에 있던 기술스택 삭제
        recruitmentSkillRepository.deleteByRecruitmentId(id);

        // 새로운 기술스택 생성
        updateRecruitmentPostReqDto.setId(updatedPost.getId());
        for (Integer checkSkill : updateRecruitmentPostReqDto.getSkill()) {
            result = recruitmentSkillRepository.insert(updateRecruitmentPostReqDto.getId(), checkSkill);
            if (result != 1) {
                throw new CustomException("채용공고 기술스택 생성 실패");
            }
        }

    }

    @Transactional
    public void 채용공고삭제(int id, int enterpriseId) {
        RecruitmentPost recruitmentPS = recruitmentPostRepository.findById(id);
        if (recruitmentPS == null) {
            throw new CustomApiException("존재하지 않는 채용공고입니다");
        }
        if (recruitmentPS.getEnterpriseId() != enterpriseId) {
            throw new CustomApiException("해당 채용공고를 삭제할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        try {
            recruitmentPostRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public List<RecruitmentPostSearchRespDto> 채용정보검색(RecruitmentPostSearchRespDto postRespDto) {
        List<RecruitmentPostSearchRespDto> postPSList = recruitmentPostRepository.findByTitleOrContent(postRespDto);
        return postPSList;
    }

    @Transactional
    public List<RecruitmentPostCategoryRespDto> 카테고리검색(RecruitmentPostCategoryRespDto recruitmentPostCategoryRespDto) {
        List<RecruitmentPostCategoryRespDto> postPSList = recruitmentPostRepository
                .findByCategory(recruitmentPostCategoryRespDto);
        return postPSList;
    }
}