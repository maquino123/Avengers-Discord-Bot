package events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class CapEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){

        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();

        String messageSent = event.getMessage().getContentRaw();
        if (messageSent.equalsIgnoreCase("!cap")){
            GuildVoiceState voiceState = event.getMember().getVoiceState();

            if (!voiceState.inVoiceChannel()){
                channel.sendMessage("America's ass is currently busy at the moment").queue();
                return;
            }

            if (audioManager.isConnected()){
                channel.sendMessage("On your left").queue();
                return;
            }

            //Get the voice channel the member is in
            VoiceChannel voiceChannel = voiceState.getChannel();
            Member selfMember = event.getGuild().getSelfMember();

            //Check if selfmember can connect to voice channel
            if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)){
                channel.sendMessageFormat("Agent Carter does not give you permission to join %s", voiceChannel).queue();
                return;
            }

            audioManager.openAudioConnection(voiceChannel);
            channel.sendMessage("How about a dance?").queue();
        }
    }
}
