package events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        String messageSent = event.getMessage().getContentRaw();
        if (messageSent.equalsIgnoreCase("!leave")) {

            if (!audioManager.isConnected()) {
                channel.sendMessage("No connection established").queue();
                return;
            }

            //Get the channel the member is in
            VoiceChannel voiceChannel = audioManager.getConnectedChannel();

            if (!voiceChannel.getMembers().contains(event.getMember())) {
                channel.sendMessage("Currently busy with a mission in another sector.").queue();
                return;
            }

            audioManager.closeAudioConnection();
            Member member = event.getMember();
            channel.sendMessageFormat("Hey %s, I don't feel so good...", member).queue();
        }
    }
}
