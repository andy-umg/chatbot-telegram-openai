package com.miumg.chatbottelegram.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bot_commands")
public class BotCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String command;

    @Column(nullable = false)
    private String client;

    @Column(nullable = false)
    private Long chatId;

    @Column(nullable = false)
    private LocalDateTime commandDate;

    @ManyToOne
    @JoinColumn(name = "chatId")
    private ChatMessage chatMessage;
}
