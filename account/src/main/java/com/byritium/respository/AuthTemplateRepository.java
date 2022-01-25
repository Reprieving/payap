package com.byritium.respository;

import com.byritium.entity.AccountAuth;
import com.byritium.entity.AccountAuthTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTemplateRepository extends PagingAndSortingRepository<AccountAuthTemplate, String> {
}
