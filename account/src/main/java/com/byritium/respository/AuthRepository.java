package com.byritium.respository;

import com.byritium.entity.AccountAuth;
import com.byritium.entity.AccountCore;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends PagingAndSortingRepository<AccountAuth, String> {
}
