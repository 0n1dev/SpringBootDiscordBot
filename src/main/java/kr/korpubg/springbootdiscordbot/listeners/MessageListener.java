package kr.korpubg.springbootdiscordbot.listeners;

import kr.korpubg.springbootdiscordbot.commons.annotation.Listener;
import kr.korpubg.springbootdiscordbot.utils.DiscordActions;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Listener
public class MessageListener extends ListenerAdapter {

    private final Logger log = LoggerFactory.getLogger(MessageListener.class);

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        Invite invite = scanInviteCode(event.getMessage());

        if (!event.getGuild().getId()
                .equals(invite.getGuild().getId())) {
            DiscordActions.ban(event.getGuild(), event.getAuthor().getId(), "타 디스코드 링크");
        }


    }

    @Override
    public void onGuildMessageUpdate(@NotNull GuildMessageUpdateEvent event) {
        super.onGuildMessageUpdate(event);
    }

    @Override
    public void onGuildMessageDelete(@NotNull GuildMessageDeleteEvent event) {
        super.onGuildMessageDelete(event);
    }

    private Invite scanInviteCode(final Message message) {
        String messageContents = message.getContentRaw();

        Pattern pattern = Pattern.compile("discord\\.gg\\/([a-zA-Z0-9]+)");
        Matcher matcher = pattern.matcher(messageContents);

        if (matcher.find()) {
            return Invite.resolve(message.getJDA(), matcher.group(1))
                    .complete();
        }

        return null;
    }
}
