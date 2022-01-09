package com.byritium.respository;

import com.byritium.entity.AccountEntity;
import com.byritium.entity.AccountEntityType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityTypeRepository extends PagingAndSortingRepository<AccountEntityType, String> {
    List<AccountEntityType> findByInitFlag(boolean flag);
}
