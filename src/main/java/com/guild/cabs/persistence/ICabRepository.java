package com.guild.cabs.persistence;

import com.guild.cabs.model.CabModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ICabRepository extends JpaRepository<CabModel, String>, JpaSpecificationExecutor<CabModel> {}
