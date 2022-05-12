package com.eukon05.dilanbot.Commands;

import com.eukon05.dilanbot.DTOs.RedditSubmissionDTO;
import com.eukon05.dilanbot.DTOs.ServerDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class RedditCommand extends Command {

    private final Gson gson;

    @Autowired
    public RedditCommand(Gson gson, CommandMap commandMap){
        super(commandMap);
        this.gson = gson;
        addToCommands("reddit");
    }

    @Override
    public void run(MessageCreateEvent event, ServerDTO serverDTO, String[] arguments) {

        new Thread(() -> {

            String value = fuseArguments(arguments);

            ServerTextChannel channel = event.getServerTextChannel().get();

            try {

                //For some reason this throws an exception when Reddit redirects the HTTP client to a URL containing non-ascii characters
                HttpResponse<String> response = Unirest
                        .get("https://reddit.com/r/" + URLEncoder.encode(value, StandardCharsets.UTF_8.toString()) + "/random.json")
                        .asString();

                switch (response.getStatus()) {

                    case 200:
                        break;

                    case 404: {
                        channel.sendMessage("This subreddit doesn't exist");
                        return;
                    }

                    case 403: {
                        channel.sendMessage("This subreddit is private");
                        return;
                    }

                    default: {
                        channel.sendMessage("An unexpected Reddit API error has occurred: " + response.getBody());
                        return;
                    }

                }

                RedditSubmissionDTO submission = gson
                        .fromJson(gson.fromJson(response.getBody(), JsonArray.class)
                                .get(0).getAsJsonObject().get("data")
                                .getAsJsonObject().get("children").getAsJsonArray().get(0).getAsJsonObject().get("data")
                                .getAsJsonObject(), RedditSubmissionDTO.class);

                if (submission.isOver_18() && !channel.isNsfw()) {
                    channel.sendMessage("This isn't a time and place for that, use an NSFW-enabled channel");
                    return;
                }

                MessageBuilder builder = new MessageBuilder();

                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setAuthor(submission.getAuthor())
                        .setTitle(submission.getTitle())
                        .setDescription(submission.getSelfText())
                        .setUrl("https://reddit.com" + submission.getPermalink())
                        .setFooter("A random post from " + submission.getSubreddit_name_prefixed());

                if (submission.getUrl() != null && !submission.is_video())
                    embedBuilder.setImage(submission.getUrl());

                builder.setEmbed(embedBuilder);
                builder.send(channel);

                if (submission.is_video())
                    new MessageBuilder()
                            .setEmbed(new EmbedBuilder()
                                    .setDescription("The above Reddit post contains a video, visit it in your browser to see it!"))
                            .send(channel);
            }
            catch(Exception ex){
                channel.sendMessage("An unexpected backend error has occurred: " + ex.getMessage());
                ex.printStackTrace();
            }

        }).start();

    }
}
