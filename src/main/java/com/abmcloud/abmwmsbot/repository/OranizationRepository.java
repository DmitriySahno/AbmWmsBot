package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OranizationRepository extends JpaRepository<Organization, Long> {
}
