package com.vik.invite_system_service.entity;

import com.vik.invite_system_service.enums.IdentityType;
import com.vik.invite_system_service.enums.Status;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_invites")
public class UserInvite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String invitedByUuid;

  private Long invitedByUserId;

  @Enumerated(EnumType.STRING)
  private IdentityType invitedToRefType;

  private String invitedToRefId;

  @Enumerated(EnumType.STRING)
  private Status status;
}
