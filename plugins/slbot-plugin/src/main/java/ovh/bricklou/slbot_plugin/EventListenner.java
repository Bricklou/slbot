package ovh.bricklou.slbot_plugin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.jetbrains.annotations.NotNull;
import ovh.bricklou.slbot_plugin.config.CommandConfig;
import ovh.bricklou.slbot_plugin.config.PluginConfig;

import java.util.HashMap;

public class EventListenner extends ListenerAdapter {
    private final PluginConfig config;

    public EventListenner(PluginConfig c) {
        this.config = c;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        CommandConfig cmdConfig = null;

        for (var c : this.config.commands()) {
            if (c.name.equals(event.getName())) {
                cmdConfig = c;
                break;
            }
        }
        if (cmdConfig == null) return;

        var m = new MessageCreateBuilder();
        if (cmdConfig.embed != null) {
            var embed = new EmbedBuilder();
            embed.setTitle(cmdConfig.embed.title);
            embed.setDescription(cmdConfig.message);

            if (cmdConfig.embed.color != null) {
                embed.setColor(cmdConfig.embed.color);
            }
            m.addEmbeds(embed.build());
        } else {
            m.addContent(cmdConfig.message);
        }

        event.reply(m.build()).queue();
    }
}
