package kr.korpubg.springbootdiscordbot.utils;

import net.dv8tion.jda.api.entities.Guild;

public class DiscordActions {

    public static void ban(Guild guild, String memberId, String reason) {
        guild.getMemberById(memberId).ban(7, reason).complete();
    }
}
