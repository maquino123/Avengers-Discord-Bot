package events.hero;

import events.music.GuildMusicManager;
import events.music.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import events.web_scrape.WebScraper;

import java.io.IOException;
import java.util.Arrays;


public class JarvisEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] message = event.getMessage().getContentRaw().split(" ");
        TextChannel channel = event.getChannel();
        Member member = event.getMember();

        AudioManager audioManager = event.getGuild().getAudioManager();
        PlayerManager manager = PlayerManager.getInstance();

        if (message[0].equalsIgnoreCase("!jarvis")){
            if (message.length < 2){
                channel.sendMessageFormat("What is your command %s?", member).queue();
                return;
            }
            if (message[1].equalsIgnoreCase("play")){
                GuildVoiceState voiceState = event.getMember().getVoiceState();
                if (!voiceState.inVoiceChannel()){
                    channel.sendMessage("Please join a voice channel to use JARVIS").queue();
                    return;
                }

                if (message.length < 3){
                    channel.sendMessage("JARVIS does not know what song to play!").queue();
                    return;
                }

                VoiceChannel voiceChannel = voiceState.getChannel();
                //Member object of the currently logged in account in the guild.
                Member selfMember = event.getGuild().getSelfMember();

                //Check if selfmember can connect to voice channel
                if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)){
                    channel.sendMessageFormat("Access denied to %s", voiceChannel).queue();
                    return;
                }

                channel.sendMessageFormat("As you wish %s", member).queue();
                audioManager.openAudioConnection(voiceChannel);
                String trackUrl = message[2];
                manager.loadAndPlay(event.getChannel(), trackUrl);
                manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
                return;
            }

            if (message[1].equalsIgnoreCase("define")){
                if (message.length < 3){
                    channel.sendMessage("JARVIS does not know what word to define").queue();
                    return;
                }
                try{
                    //TODO define words containing , case-sensitive words
                    WebScraper webScraper = WebScraper.getInstance();
                    String word = message[2];
                    webScraper.defineWord(word, event);
                    return;
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            channel.sendMessage("Unknown command").queue();
            return;

        }
    }
}
