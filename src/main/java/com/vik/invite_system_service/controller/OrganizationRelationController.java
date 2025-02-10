package com.vik.invite_system_service.controller;

import com.vik.invite_system_service.dto.OrganizationRelationDTO;
import com.vik.invite_system_service.service.OrganizationRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("organizationRelations")
public class OrganizationRelationController {

    @Autowired
    private OrganizationRelationService organizationRelationService;

    @GetMapping("/getAll")
    public List<OrganizationRelationDTO> getAllOrganizationRelations() {
        return organizationRelationService.getAllOrganizationRelations();
    }
}
