package com.group.doconnect.service;

import java.util.List;
import java.util.Optional;

import com.group.doconnect.dto.QuestionRequestDTO;
import com.group.doconnect.dto.QuestionResponseDTO;
import com.group.doconnect.dto.QuestionUpdateDTO;
import com.group.doconnect.dto.StatusDTO;
import com.group.doconnect.entity.Question;
import com.group.doconnect.repository.QuestionRepository;
import com.group.doconnect.repository.UserRepository;
import com.group.doconnect.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utilities utilities;

    public StatusDTO<List<QuestionResponseDTO>> getAllQuestions(String status, String search, String topic) {
        if (search == null && topic == null) {
            if (status.equalsIgnoreCase("all")) {
                return new StatusDTO<List<QuestionResponseDTO>>("", true, utilities.convertQuestionListToQuestionResponseDTOList(questionRepository.findAll()));
            } else if (status.equalsIgnoreCase("approved")) {
                return new StatusDTO<List<QuestionResponseDTO>>("", true, utilities.convertQuestionListToQuestionResponseDTOList(questionRepository.findByIsApprovedTrue()));
            } else if (status.equalsIgnoreCase("unapproved")) {
                return new StatusDTO<List<QuestionResponseDTO>>("", true, utilities.convertQuestionListToQuestionResponseDTOList(questionRepository.findByIsApprovedFalse()));
            } else {
                return new StatusDTO<List<QuestionResponseDTO>>("Provided invalid status. Should be one of 'all', 'approved' or 'unapproved'.", false, null);
            }
        } else if (search != null && topic == null) {
            if (status.equalsIgnoreCase("approved")) {
                return new StatusDTO<List<QuestionResponseDTO>>("", true, utilities.convertQuestionListToQuestionResponseDTOList(questionRepository.findByQuestionContainingIgnoreCaseAndIsApprovedTrue(search)));
            } else {
                return new StatusDTO<List<QuestionResponseDTO>>("Question search only works with 'approved' status.", false, null);
            }
        } else if (search == null && topic != null) {
            if (status.equalsIgnoreCase("approved")) {
                return new StatusDTO<List<QuestionResponseDTO>>("", true, utilities.convertQuestionListToQuestionResponseDTOList(questionRepository.findByTopicContainingIgnoreCaseAndIsApprovedTrue(topic)));
            } else {
                return new StatusDTO<List<QuestionResponseDTO>>("Question search only works with 'approved' status.", false, null);
            }
        } else {
            if (status.equalsIgnoreCase("approved")) {
                return new StatusDTO<List<QuestionResponseDTO>>("", true, utilities.convertQuestionListToQuestionResponseDTOList(questionRepository.findByTopicContainingIgnoreCaseAndQuestionContainingIgnoreCaseAndIsApprovedTrue(topic, search)));
            } else {
                return new StatusDTO<List<QuestionResponseDTO>>("Question search only works with 'approved' status.", false, null);
            }
        }
    }


    public StatusDTO<QuestionResponseDTO> getQuestionById(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isEmpty()) {
            return new StatusDTO<QuestionResponseDTO>("Question with ID " + questionId + " does not exist.", false, null);
        }
        return new StatusDTO<QuestionResponseDTO>("", true, utilities.convertQuestionToQuestionResponseDTO(optionalQuestion.get()));
    }


    public StatusDTO<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO, String postedBy) {
        Question question = new Question(questionRequestDTO.getQuestion(), questionRequestDTO.getTopic(), questionRequestDTO.getImages(), postedBy);
        Question savedQuestion = questionRepository.save(question);
        return new StatusDTO<QuestionResponseDTO>("", true, utilities.convertQuestionToQuestionResponseDTO(savedQuestion));
    }


    public StatusDTO<QuestionResponseDTO> updateQuestion(QuestionUpdateDTO questionUpdateDTO, Long questionId, String approvedBy) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isEmpty()) {
            return new StatusDTO<QuestionResponseDTO>("Question with ID " + questionId + " does not exist.", false, null);
        }
        Question question = optionalQuestion.get();
        question.setIsApproved(questionUpdateDTO.getIsApproved());
        question.setApprovedBy(approvedBy);
        return new StatusDTO<QuestionResponseDTO>("", true, utilities.convertQuestionToQuestionResponseDTO(questionRepository.save(question)));
    }


    public StatusDTO<Boolean> deleteQuestionById(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isEmpty()) {
            return new StatusDTO<Boolean>("Question with id " + questionId + " does not exist.", false, null);
        }
        Question question = optionalQuestion.get();
        if (question.getIsApproved()) {
            return new StatusDTO<Boolean>("Cannot delete an approved question.", false, false);
        }
        questionRepository.delete(question);
        return new StatusDTO<Boolean>("", true, true);
    }

}
