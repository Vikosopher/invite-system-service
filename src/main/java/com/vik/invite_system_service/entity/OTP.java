package com.vik.invite_system_service.entity;

import com.vik.invite_system_service.enums.IdentityType;
import com.vik.invite_system_service.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "otps")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String otp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IdentityType identityType;

    @Column(nullable = false)
    private String identity;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Timestamp createdAt;
    private boolean active;
    private Timestamp expiry;

    @PrePersist
    protected void onCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        createdAt = now;
        expiry = new Timestamp(now.getTime() + (10 * 60 * 1000));
        active = true;
        status = Status.CREATED;
    }

}
