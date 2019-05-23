package events;
import events.music.PlayerManager;
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
            manager.loadAndPlay(event.getChannel(), "https://www.youtube.com/watch?v=Chs2bmqzyUs");

            manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);

        }
    }
}
