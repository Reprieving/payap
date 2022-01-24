package com.byritium.respository;

import com.byritium.entity.AccountBalance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends PagingAndSortingRepository<AccountBalance, String> {
}
