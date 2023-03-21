package shop.mtcoding.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.job.dto.resume.SaveResumeDto;
import shop.mtcoding.job.dto.resume.UpdateResumeDto;
import shop.mtcoding.job.handler.exception.CustomApiException;
import shop.mtcoding.job.model.resume.Resume;
import shop.mtcoding.job.model.resume.ResumeRepository;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Transactional
    public void 이력서쓰기(SaveResumeDto saveResumeDto, int userId) {

        int result = resumeRepository.insert(userId, saveResumeDto.getTitle(), saveResumeDto.getContent(),
                saveResumeDto.getCareer(), saveResumeDto.getEducation(), saveResumeDto.getSkill(),
                saveResumeDto.getAward(), saveResumeDto.getLanguage(), saveResumeDto.getLink(),
                saveResumeDto.getBirthdate(), saveResumeDto.getAddress(),
                saveResumeDto.isFinish());
        if (result != 1) {
            throw new CustomApiException("이력서 작성이 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void 이력서삭제(int id, int userId) {
        Resume resumePS = resumeRepository.findById(id);
        if (resumePS == null) {
            throw new CustomApiException("존재하지 않는 이력서입니다");
        }
        if (resumePS.getUserId() != userId) {
            throw new CustomApiException("해당 이력서를 삭제할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        // 제어권이 없으므로 try, catch
        try {
            resumeRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
            // 로그를 남겨야 함 (DB or File)
        }
    }

    @Transactional
    public void 이력서수정(int id, UpdateResumeDto updateResumeDto, int principalId) {
        Resume resumePS = resumeRepository.findById(id);
        if (resumePS == null) {
            throw new CustomApiException("존재하지 않는 이력서입니다");
        }
        if (resumePS.getUserId() != principalId) {
            throw new CustomApiException("해당 이력서를 수정할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        try {
            int result = resumeRepository.updateById(id, updateResumeDto.getTitle(),
                    updateResumeDto.getContent(),
                    updateResumeDto.getCareer(), updateResumeDto.getSkill(), updateResumeDto.getAward(),
                    updateResumeDto.getAddress(), updateResumeDto.getBirthdate(),
                    updateResumeDto.getLink(), updateResumeDto.getEducation(),
                    updateResumeDto.getLanguage(), updateResumeDto.isFinish());

            if (result != 1) {
                throw new CustomApiException("이력서 수정에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 발생했습니다");
        }
    }
}
