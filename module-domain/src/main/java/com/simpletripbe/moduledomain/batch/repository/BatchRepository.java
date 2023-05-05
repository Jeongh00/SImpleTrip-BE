package com.simpletripbe.moduledomain.batch.repository;

import com.simpletripbe.moduledomain.batch.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Alarm, Long> {
}
