package com.vik.invite_system_service.controller;

import com.vik.invite_system_service.dto.OrganizationDTO;
import com.vik.invite_system_service.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/create")
    public OrganizationDTO createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        return organizationService.createOrganization(organizationDTO);
    }
}
