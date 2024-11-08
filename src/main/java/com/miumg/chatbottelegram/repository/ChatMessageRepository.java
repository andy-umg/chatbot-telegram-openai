package com.miumg.chatbottelegram.repository;

import com.miumg.chatbottelegram.model.BotCommand;
import com.miumg.chatbottelegram.model.ChatMessage;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import javax.persistence.CascadeType;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT c FROM ChatMessage c WHERE c.client = :client ORDER BY c.creationDate DESC")
    List<ChatMessage> findLast3MessagesByClient(@Param("client") String client);

    @OneToMany(mappedBy = "chatMessage", cascade = CascadeType.ALL)
    private List<BotCommand> commands;
}
