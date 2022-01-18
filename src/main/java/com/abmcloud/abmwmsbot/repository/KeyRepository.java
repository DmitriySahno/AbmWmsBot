package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.model.Key;
import com.abmcloud.abmwmsbot.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface KeyRepository extends JpaRepository<Key, Long> {

    List<Key> findAllByOrganization(Organization organization);

}
