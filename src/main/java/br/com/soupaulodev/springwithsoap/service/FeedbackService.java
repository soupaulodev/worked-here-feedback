package br.com.soupaulodev.springwithsoap.service;

import br.com.soupaulodev.springwithsoap.entity.FeedbackEntity;
import br.com.soupaulodev.springwithsoap.entity.UserEntity;
import br.com.soupaulodev.springwithsoap.generated.*;
import br.com.soupaulodev.springwithsoap.mapper.FeedbackMapper;
import br.com.soupaulodev.springwithsoap.repository.FeedbackRepository;
import br.com.soupaulodev.springwithsoap.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final FeedbackMapper feedbackMapper;

    public CreateFeedbackResponse createFeedback(CreateFeedbackRequest request) {
        String userIdStr = request.getUserId();
        if (userIdStr == null || userIdStr.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        UserEntity user = userRepository.findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        FeedbackEntity feedbackEntity = feedbackMapper.createRequestToEntity(request, user);
        FeedbackEntity savedFeedback = feedbackRepository.save(feedbackEntity);
        Feedback feedback = feedbackMapper.entityToTypeObj(savedFeedback);
        CreateFeedbackResponse response = feedbackMapper.typeObjToCreateResponse(feedback);
        response.setStatus("201");

        return response;
    }

    public ListCompaniesResponse getCompanies() {
        CompanyList companyList = feedbackMapper.stringListToCompanyList(feedbackRepository.findAllCompanies());

        return feedbackMapper.companyListToListCompaniesResponse(companyList);
    }

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
        return response;
    }

    public UpdateFeedbackResponse updateFeedback(UpdateFeedbackRequest request) {
        FeedbackEntity feedback = feedbackRepository.findById(UUID.fromString(request.getFeedbackId()))
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        if (request.getRating() != null && request.getComment() != null) {
            throw new IllegalArgumentException("All fields are null");
        }

        feedback.setRating(request.getRating() != null ? request.getRating() : feedback.getRating());
        feedback.setComment(request.getComment() != null ? request.getComment() : feedback.getComment());

        feedbackRepository.save(feedback);

        UpdateFeedbackResponse response = new UpdateFeedbackResponse();
        response.setStatus("203");

        return response;
    }

    public DeleteFeedbackResponse deleteFeedback(UUID feedbackId) {
        FeedbackEntity feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        feedbackRepository.delete(feedback);

        DeleteFeedbackResponse response = new DeleteFeedbackResponse();
        response.setStatus("201");

        return response;
    }
}
