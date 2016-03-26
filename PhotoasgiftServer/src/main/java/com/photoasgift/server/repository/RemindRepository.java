package com.photoasgift.server.repository;

import com.photoasgift.server.entity.Remind;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository - это DAO, но немного с другим подходом.
 * Необходимы для того, что бы мы могли работать с нашей таблицей в БД.
 */
public interface RemindRepository extends JpaRepository <Remind, Long> {

}
