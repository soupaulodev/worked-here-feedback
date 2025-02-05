package br.com.soupaulodev.springwithsoap.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "feedback_tb")
public class FeedbackEntity implements Serializable {

    private static final Long SERIAL_VERSION_UID = 1L;

    @Id
    private Long id;

    @Column(nullable = false)
    private String company;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private String comment;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
