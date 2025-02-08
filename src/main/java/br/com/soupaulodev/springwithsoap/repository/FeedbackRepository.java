package br.com.soupaulodev.springwithsoap.repository;

import br.com.soupaulodev.springwithsoap.entity.FeedbackEntity;
import br.com.soupaulodev.springwithsoap.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, UUID> {
    List<FeedbackEntity> findByCompany(String company, Pageable pageable);

    @Query("SELECT DISTINCT f.company FROM FeedbackEntity f")
    List<String> findAllCompanies();

    Optional<FeedbackEntity> findByUser(UserEntity user);
}
