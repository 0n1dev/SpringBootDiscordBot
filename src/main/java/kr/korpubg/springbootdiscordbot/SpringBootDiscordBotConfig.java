package kr.korpubg.springbootdiscordbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.security.auth.login.LoginException;

@Configuration
@EnableConfigurationProperties
@PropertySource("classpath:application.yml")
public class SpringBootDiscordBotConfig {

    private static final Logger logger = LogManager.getLogger(SpringBootDiscordBotConfig.class);

    @Bean
    JDA jda(@Value("${jda.token}") final String jdaToken) {
        final JDABuilder builder = JDABuilder.createDefault(jdaToken);
        builder.setActivity(Activity.watching("KorPUBG vBeta"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.NONE);
        builder.setLargeThreshold(250);

        try {
            final JDA jda = builder.build();
            jda.awaitReady();
            logger.info(jda.getInviteUrl(Permission.ADMINISTRATOR));
            return jda;
        } catch (final LoginException e) {
            logger.error(e.getMessage(), e);
        } catch (final InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
