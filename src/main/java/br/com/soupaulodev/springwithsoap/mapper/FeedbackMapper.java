package br.com.soupaulodev.springwithsoap.mapper;

import br.com.soupaulodev.springwithsoap.entity.FeedbackEntity;
import br.com.soupaulodev.springwithsoap.entity.UserEntity;
import br.com.soupaulodev.springwithsoap.generated.*;
import br.com.soupaulodev.springwithsoap.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedbackMapper {

    public FeedbackEntity createRequestToEntity(CreateFeedbackRequest request, UserEntity user) {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setCompany(request.getCompany());
        feedbackEntity.setRating(request.getRating());
        feedbackEntity.setComment(request.getComment());
        feedbackEntity.setUser(user);

        return feedbackEntity;
    }

    public Feedback entityToTypeObj(FeedbackEntity entity) {
        Feedback feedback = new Feedback();
        feedback.setId(entity.getId().toString());
        feedback.setCompany(entity.getCompany());
        feedback.setRating(entity.getRating());
        feedback.setComment(entity.getComment());
        feedback.setCreatedAt(DateTimeUtil.toXMLGregorianCalendar(entity.getCreatedAt()));
        feedback.setUpdatedAt(DateTimeUtil.toXMLGregorianCalendar(entity.getUpdatedAt()));

        return feedback;
    }

    public CreateFeedbackResponse typeObjToCreateResponse(Feedback typeObj) {
        CreateFeedbackResponse response = new CreateFeedbackResponse();
        response.setFeedbackId(typeObj.getId());
        response.setFeedback(typeObj);

        return response;
    }

    public CompanyList stringListToCompanyList(List<String> companies) {
        CompanyList companyList = new CompanyList();
        companyList.getCompany().addAll(companies);

        return companyList;
    }

    public ListCompaniesResponse companyListToListCompaniesResponse(CompanyList companyList) {
        ListCompaniesResponse response = new ListCompaniesResponse();
        response.setCompanies(companyList);

        return response;
    }
}
