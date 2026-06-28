package com.ecolivros.repository;

import com.ecolivros.entity.Message;
import com.ecolivros.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :a AND m.receiver.id = :b) OR (m.sender.id = :b AND m.receiver.id = :a) ORDER BY m.sentAt ASC")
    List<Message> findConversation(@Param("a") Long aId, @Param("b") Long bId);

    @Query("SELECT DISTINCT m.sender FROM Message m WHERE m.receiver.id = :userId")
    List<User> findWhoSentToMe(@Param("userId") Long userId);

    @Query("SELECT DISTINCT m.receiver FROM Message m WHERE m.sender.id = :userId")
    List<User> findWhoIMessaged(@Param("userId") Long userId);
}
