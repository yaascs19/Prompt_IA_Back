package com.ecolivros.repository;

import com.ecolivros.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId OR m.receiver.id = :userId) ORDER BY m.sentAt ASC")
    List<Message> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :a AND m.receiver.id = :b) OR (m.sender.id = :b AND m.receiver.id = :a) ORDER BY m.sentAt ASC")
    List<Message> findConversation(@Param("a") Long aId, @Param("b") Long bId);

    @Query("SELECT DISTINCT CASE WHEN m.sender.id = :userId THEN m.receiver ELSE m.sender END FROM Message m WHERE m.sender.id = :userId OR m.receiver.id = :userId")
    List<com.ecolivros.entity.User> findContacts(@Param("userId") Long userId);
}
