package com.chamelete.flowManagement.service;


import java.util.List;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.security.dto.CompaniesRequest;

public interface CompaniesService {

    Companies findByName(String name);

    Companies create(CompaniesRequest companiesDto);

    List<Companies> getCompaniesByUser(User user);
}
