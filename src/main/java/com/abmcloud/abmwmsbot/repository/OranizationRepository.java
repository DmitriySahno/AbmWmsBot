package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OranizationRepository extends JpaRepository<Organization, Long> {
}
