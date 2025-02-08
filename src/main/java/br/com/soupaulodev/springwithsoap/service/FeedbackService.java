package br.com.soupaulodev.springwithsoap.service;

import br.com.soupaulodev.springwithsoap.entity.FeedbackEntity;
import br.com.soupaulodev.springwithsoap.entity.UserEntity;
import br.com.soupaulodev.springwithsoap.enums.OperationStatus;
import br.com.soupaulodev.springwithsoap.generated.*;
import br.com.soupaulodev.springwithsoap.mapper.FeedbackMapper;
import br.com.soupaulodev.springwithsoap.repository.FeedbackRepository;
import br.com.soupaulodev.springwithsoap.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final FeedbackMapper feedbackMapper;

    public CreateFeedbackResponse createFeedback(CreateFeedbackRequest request) {
        UserEntity user = userRepository.findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> {
                    logger.warn("User with id {} not found", request.getUserId());
                    return new RuntimeException("User not found");
                });
        logger.info("User found");

        FeedbackEntity feedbackEntity = feedbackMapper.createRequestToEntity(request, user);
        feedbackEntity.setId(UUID.randomUUID());

        FeedbackEntity savedFeedback = feedbackRepository.save(feedbackEntity);
        Feedback feedback = feedbackMapper.entityToTypeObj(savedFeedback);
        CreateFeedbackResponse response = feedbackMapper.typeObjToCreateResponse(feedback);
        response.setOperationStatus(OperationStatus.SUCESS.toString());

        logger.info("Feedback created sucessfully with id {}", feedback.getId());
        return response;
    }

    @Cacheable("companies")
    public ListCompaniesResponse getCompanies() {
        CompanyList companyList = feedbackMapper.stringListToCompanyList(feedbackRepository.findAllCompanies());

        logger.info("Company list found successfully with {} elements", (long) companyList.getCompany().size());
        return feedbackMapper.companyListToListCompaniesResponse(companyList);
    }

    @Cacheable("feedbacks")
    public ListFeedbackByCompanyResponse getFeedbackByCompany(String company) {
        FeedbackList feedbackList = new FeedbackList();
        feedbackList.getFeedback()
                .addAll(feedbackRepository
                        .findByCompany(company)
                        .stream()
                        .map(feedbackMapper::entityToTypeObj)
                        .toList());
        ListFeedbackByCompanyResponse response = new ListFeedbackByCompanyResponse();
        response.setFeedbacks(feedbackList);

        logger.info("Feedback list found successfully with {} elements", (long) feedbackList.getFeedback().size());
        return response;
    }

    @CachePut("feedbacks")
    public UpdateFeedbackResponse updateFeedback(UpdateFeedbackRequest request) {
        FeedbackEntity feedback = feedbackRepository.findById(UUID.fromString(request.getFeedbackId()))
                .orElseThrow(() -> {
                    logger.warn("Feedback with id {} not found", request.getFeedbackId());
                    return new RuntimeException("Feedback not found");
                });
        logger.info("Feedback found");

        if (request.getRating() == null && request.getComment() == null) {
            logger.warn("All fields are null");
            throw new IllegalArgumentException("All fields are null");
        }

        feedback.setRating(request.getRating() != null ? request.getRating() : feedback.getRating());
        feedback.setComment(request.getComment() != null ? request.getComment() : feedback.getComment());

        feedbackRepository.save(feedback);

        UpdateFeedbackResponse response = new UpdateFeedbackResponse();
        response.setOperationStatus(OperationStatus.SUCESS.toString());

        logger.info("Feedback with id {} updated", feedback.getId());
        return response;
    }

    public DeleteFeedbackResponse deleteFeedback(UUID feedbackId) {
        FeedbackEntity feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> {
                    logger.warn("Feedback with id {} not found", feedbackId);
                    return new RuntimeException("Feedback not found");
                });
        logger.info("Feedback found");

        feedbackRepository.delete(feedback);

        DeleteFeedbackResponse response = new DeleteFeedbackResponse();
        response.setOperationStatus(OperationStatus.SUCESS.toString());

        logger.info("Feedback with id {} deleted successfully", feedbackId);
        return response;
    }
}
