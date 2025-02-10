package com.vik.invite_system_service.repository;

import com.vik.invite_system_service.entity.OrganizationRelation;
import com.vik.invite_system_service.entity.OrganizationRelationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRelationRepo extends JpaRepository<OrganizationRelation, OrganizationRelationId> {

    List<OrganizationRelation> findAll();
    List<OrganizationRelation> findById_UserId(Long userId);

    Optional<OrganizationRelation> findByIdOrganizationUuidAndIdUserId(String organizationUuid, Long userId);
}
