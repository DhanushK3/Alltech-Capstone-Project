package com.group.doconnect.service;

import java.util.List;
import java.util.Optional;

import com.group.doconnect.dto.AnswerRequestDTO;
import com.group.doconnect.dto.AnswerResponseDTO;
import com.group.doconnect.dto.AnswerUpdateDTO;
import com.group.doconnect.dto.StatusDTO;
import com.group.doconnect.entity.Answer;
import com.group.doconnect.entity.Question;
import com.group.doconnect.repository.AnswerRepository;
import com.group.doconnect.repository.QuestionRepository;
import com.group.doconnect.repository.UserRepository;
import com.group.doconnect.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utilities utilities;

    public StatusDTO<List<AnswerResponseDTO>> getAllAnswers(String answerStatus) {
        if (answerStatus.equalsIgnoreCase("all")) {
            return new StatusDTO<List<AnswerResponseDTO>>("", true, utilities.convertAnswerListToAnswerResponseDTOList(answerRepository.findAll()));
        } else if (answerStatus.equalsIgnoreCase("approved")) {
            return new StatusDTO<List<AnswerResponseDTO>>("", true, utilities.convertAnswerListToAnswerResponseDTOList(answerRepository.findByIsApprovedTrue()));
        } else if (answerStatus.equalsIgnoreCase("unapproved")) {
            return new StatusDTO<List<AnswerResponseDTO>>("", true, utilities.convertAnswerListToAnswerResponseDTOList(answerRepository.findByIsApprovedFalse()));
        } else {
            return new StatusDTO<List<AnswerResponseDTO>>("Invalid Status...", false, null);
        }
    }

    public StatusDTO<List<AnswerResponseDTO>> getAllAnswersForQuestionId(Long questionId, String answerStatus) {
        if (answerStatus.equalsIgnoreCase("all")) {
            return new StatusDTO<List<AnswerResponseDTO>>("", true, utilities.convertAnswerListToAnswerResponseDTOList(answerRepository.findByQuestionId(questionId)));
        } else if (answerStatus.equalsIgnoreCase("approved")) {
            return new StatusDTO<List<AnswerResponseDTO>>("", true, utilities.convertAnswerListToAnswerResponseDTOList(answerRepository.findByQuestionIdAndIsApprovedTrue(questionId)));
        } else if (answerStatus.equalsIgnoreCase("unapproved")) {
            return new StatusDTO<List<AnswerResponseDTO>>("", true, utilities.convertAnswerListToAnswerResponseDTOList(answerRepository.findByQuestionIdAndIsApprovedFalse(questionId)));
        } else {
            return new StatusDTO<List<AnswerResponseDTO>>("Provided invalid status. Should be one of 'all', 'approved' or 'unapproved'.", false, null);
        }
    }

    public StatusDTO<AnswerResponseDTO> createAnswerForQuestionId(Long questionId, AnswerRequestDTO answerRequestDTO, String postedBy) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isEmpty()) {
            return new StatusDTO<AnswerResponseDTO>("Question with id " + questionId + " does not exist.", false, null);
        }
        Answer answer = new Answer(answerRequestDTO.getAnswer(), answerRequestDTO.getImages(), postedBy, optionalQuestion.get());
        Answer savedAnswer = answerRepository.save(answer);
        return new StatusDTO<AnswerResponseDTO>("", true, utilities.convertAnswerToAnswerResponseDTO(savedAnswer));
    }


    public StatusDTO<AnswerResponseDTO> updateAnswerForQuestionId(Long questionId, Long answerId, AnswerUpdateDTO answerUpdateDTO, String approvedBy) {
        boolean questionExists = questionRepository.existsById(questionId);
        if (!questionExists) {
            return new StatusDTO<AnswerResponseDTO>("Question " + questionId + " NOT FOUND.", false, null);
        }
        Optional<Answer> optionalAnswer = answerRepository.findByIdAndQuestionId(answerId, questionId);
        if (optionalAnswer.isEmpty()) {
            return new StatusDTO<AnswerResponseDTO>("Answer " + answerId + " NOT FOUND " + questionId + ".", false, null);
        }
        Answer answer = optionalAnswer.get();
        answer.setIsApproved(answerUpdateDTO.getIsApproved());
        answer.setApprovedBy(approvedBy);
        return new StatusDTO<AnswerResponseDTO>("", true, utilities.convertAnswerToAnswerResponseDTO(answerRepository.save(answer)));
    }


    public StatusDTO<Boolean> deleteAnswerForQuestionById(Long questionId, Long answerId) {
        boolean questionExists = questionRepository.existsById(questionId);
        if (!questionExists) {
            return new StatusDTO<Boolean>("Question " + questionId + " NOT FOUND.", false, null);
        }
        Optional<Answer> optionalAnswer = answerRepository.findByIdAndQuestionId(answerId, questionId);
        if (optionalAnswer.isEmpty()) {
            return new StatusDTO<Boolean>("Answer " + answerId + " NOT FOUND" + questionId + ".", false, null);
        }
        Answer answer = optionalAnswer.get();
        if (answer.getIsApproved()) {
            return new StatusDTO<Boolean>("DELETION FAILED", false, false);
        }
        answerRepository.delete(answer);
        return new StatusDTO<Boolean>("", true, true);
    }

}
