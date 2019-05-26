package events.hero;
import events.music.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Random;

public class CapEvent extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        AudioManager audioManager = event.getGuild().getAudioManager();
        PlayerManager manager = PlayerManager.getInstance();

        String messageSent = event.getMessage().getContentRaw();
        if (messageSent.equalsIgnoreCase("!cap")){
            GuildVoiceState voiceState = event.getMember().getVoiceState();
            TextChannel channel = event.getChannel();

            if (!voiceState.inVoiceChannel()){
                channel.sendMessage("Please join a voice channel to use America's ass").queue();
                return;
            }

            //if (audioManager.isConnected()){
             //   channel.sendMessage("On your left").queue();
             //   return;
            //}

            //Get the voice channel the member is in
            VoiceChannel voiceChannel = voiceState.getChannel();
            Member selfMember = event.getGuild().getSelfMember();

            //Check if selfmember can connect to voice channel
            if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)){
                channel.sendMessageFormat("Agent Carter does not give you permission to join %s", voiceChannel).queue();
                return;
            }

            audioManager.openAudioConnection(voiceChannel);
            Random random = new Random();
            //Randomize between the 2 songs
            //TODO add more songs if you want
            String[] channelMessages = new String[]{"How about a dance?", "Feeling a little patriotic..."};
            int randomIndex = random.nextInt(channelMessages.length);
            String randomMessage = channelMessages[randomIndex];

            if (channelMessages[0] == randomMessage){
                channel.sendMessageFormat(channelMessages[0]).queue();
                manager.loadAndPlay(event.getChannel(), "https://www.youtube.com/watch?v=Chs2bmqzyUs");
                return;
            }

            if (channelMessages[1] == randomMessage){
                channel.sendMessageFormat(channelMessages[1]).queue();
                manager.loadAndPlay(event.getChannel(), "https://www.youtube.com/watch?v=Go_Xzb3ZN4M");
                return;
            }

            manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);

        }
    }
}
