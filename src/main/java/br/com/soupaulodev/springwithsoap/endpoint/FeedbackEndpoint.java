package br.com.soupaulodev.springwithsoap.endpoint;

import br.com.soupaulodev.springwithsoap.generated.*;
import br.com.soupaulodev.springwithsoap.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;

@Endpoint
@RequiredArgsConstructor
public class FeedbackEndpoint {

    private static final String NAMESPACE_URI = "https://soupaulodev.com.br/feedback";

    private final FeedbackService feedbackService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateFeedbackRequest")
    @ResponsePayload
    public CreateFeedbackResponse createFeedback(@RequestPayload CreateFeedbackRequest request) {
        return feedbackService.createFeedback(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListCompaniesRequest")
    @ResponsePayload
    public ListCompaniesResponse listCompanies() {
        return feedbackService.getCompanies();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListFeedbackByCompanyRequest")
    @ResponsePayload
    public ListFeedbackByCompanyResponse listFeedbackByCompany(@RequestPayload ListFeedbackByCompanyRequest request) {
        return feedbackService.getFeedbackByCompany(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateFeedbackRequest")
    @ResponsePayload
    public UpdateFeedbackResponse updateFeedback(@RequestPayload UpdateFeedbackRequest request) {
        return feedbackService.updateFeedback(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteFeedbackRequest")
    @ResponsePayload
    public DeleteFeedbackResponse deleteFeedback(@RequestPayload DeleteFeedbackRequest request) {
        return feedbackService.deleteFeedback(UUID.fromString(request.getFeedbackId()));
    }
}
