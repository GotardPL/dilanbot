package com.eukon05.dilanbot.music.exception;

import com.eukon05.dilanbot.DilanException;
import com.eukon05.dilanbot.Message;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;

public class EmptyQueueException extends DilanException {
    @Override
    public void handle(InteractionFollowupMessageBuilder responder, String locale) {
        responder.setContent(Message.QUEUE_EMPTY.get(locale)).send();
    }
}
