package com.bootload.zoho.crm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootload.zoho.crm.entity.OpenDeals;

@Repository
public interface ReportRepo extends JpaRepository<OpenDeals, Long> {

}
