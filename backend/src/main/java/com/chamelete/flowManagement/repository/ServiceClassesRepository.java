package com.chamelete.flowManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.ServiceClasses;

public interface ServiceClassesRepository extends JpaRepository<ServiceClasses, Long> {

    List<ServiceClasses> findByCompany(Companies companyId);

    ServiceClasses findByCompanyAndServiceClass(Companies company, String serviceclass);
}
