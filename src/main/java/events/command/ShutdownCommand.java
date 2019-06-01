package events.command;

import events.music.GuildMusicManager;
import events.music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ShutdownCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String message = event.getMessage().getContentRaw();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(event.getGuild());
        TextChannel channel = event.getChannel();

        if (message.equalsIgnoreCase("!shutdown")){
            guildMusicManager.scheduler.getQueue().clear(); //Clears the queue
            guildMusicManager.player.stopTrack();   //Stop the track currently playing
            guildMusicManager.player.setPaused(false);  //Not paused

            channel.sendMessage("Stopping the player and clearing the queue").queue();
            return;
        }
    }
}
