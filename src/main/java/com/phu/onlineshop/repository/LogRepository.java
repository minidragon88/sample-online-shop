package com.phu.onlineshop.repository;

import com.phu.onlineshop.model.log.UserActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<UserActionLog, String>
{
}
