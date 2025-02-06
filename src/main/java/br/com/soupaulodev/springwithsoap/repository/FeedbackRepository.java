package br.com.soupaulodev.springwithsoap.repository;

import br.com.soupaulodev.springwithsoap.entity.FeedbackEntity;
import br.com.soupaulodev.springwithsoap.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, UUID> {
    Optional<FeedbackEntity> findByCompany(String company);

    Optional<FeedbackEntity> findByUser(UserEntity user);
}
