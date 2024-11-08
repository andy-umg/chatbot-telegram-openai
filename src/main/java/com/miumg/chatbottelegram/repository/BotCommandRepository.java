package com.miumg.chatbottelegram.repository;

import com.miumg.chatbottelegram.model.BotCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BotCommandRepository extends JpaRepository<BotCommand, Long> {

    @Query("SELECT c.command, COUNT(c) FROM BotCommand c GROUP BY c.command ORDER BY COUNT(c) DESC")
    List<Object[]> findTopCommands();
}
