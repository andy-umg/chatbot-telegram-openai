package com.miumg.chatbottelegram.Service;

import com.miumg.chatbottelegram.model.BotCommand;
import com.miumg.chatbottelegram.repository.BotCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BotCommandService {

    @Autowired
    private BotCommandRepository botCommandRepository;

    public void save(BotCommand botCommand) {
        botCommandRepository.save(botCommand);
    }

    public List<Object[]> findMostUsedCommands() {
        return botCommandRepository.findTopCommands();
    }
}

