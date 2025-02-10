package com.vik.invite_system_service.repository;

import com.vik.invite_system_service.entity.UserInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInviteRepo extends JpaRepository<UserInvite, Long> {}
