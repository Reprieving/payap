package com.byritium.respository;

import com.byritium.entity.AccountCore;
import com.byritium.entity.AccountEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreRepository extends PagingAndSortingRepository<AccountCore, String> {
}
