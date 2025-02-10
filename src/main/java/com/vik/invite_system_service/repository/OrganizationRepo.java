package com.vik.invite_system_service.repository;

import com.vik.invite_system_service.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, UUID> {
    Optional<Organization> findByDomain(String domain);
    boolean existsByDomain(String domain);
}
