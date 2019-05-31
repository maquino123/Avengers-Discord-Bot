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

public class HulkEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        AudioManager audioManager = event.getGuild().getAudioManager();
        PlayerManager manager = PlayerManager.getInstance();

        String message = event.getMessage().getContentRaw();

        if (message.equalsIgnoreCase("!smash")){
            GuildVoiceState voiceState = event.getMember().getVoiceState();
            TextChannel channel = event.getChannel();

            if (!voiceState.inVoiceChannel()){
                channel.sendMessageFormat("Please join a voice channel to use Hulk").queue();
                return;
            }

            //if (audioManager.isConnected()){
             //   channel.sendMessage("Hulk is already here").queue();
             //   return;
            //}

            //Get the voice channel the member is in
            VoiceChannel voiceChannel = voiceState.getChannel();
            Member selfMember = event.getGuild().getSelfMember();

            //Check if selfmember can connect to voice channel
            if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)){
                channel.sendMessageFormat("Dr.Banner does not give you permission to join %s", voiceChannel).queue();
                return;
            }

            audioManager.openAudioConnection(voiceChannel);
            channel.sendMessageFormat("The sun's getting real low %s, why don't we chill out", event.getMember()).queue();
            manager.loadAndPlay(event.getChannel(), "https://www.youtube.com/playlist?list=PLXzS0Kp7vtLrupaI5bAbpKEJSEKX4__kr");
            manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
        }
    }
}
