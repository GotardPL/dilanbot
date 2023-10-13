package com.eukon05.dilanbot.music.exception;

import com.eukon05.dilanbot.DilanException;
import com.eukon05.dilanbot.Message;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;

public class PlayerNotPausedException extends DilanException {
    @Override
    public void handle(InteractionFollowupMessageBuilder responder, String locale) {
        responder.setContent(Message.NOT_PAUSED.get(locale)).send();
    }
}
