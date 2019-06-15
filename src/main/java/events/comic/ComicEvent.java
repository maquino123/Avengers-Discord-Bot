package events.comic;

import com.karumi.marvelapiclient.MarvelApiConfig;
import com.karumi.marvelapiclient.MarvelApiException;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ComicEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        String[] message = event.getMessage().getContentRaw().split(" ");
        MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(ComicResource.MARVEL_PUBLIC_API, ComicResource.MARVEL_PRIVATE_API).debug().build();
        ComicClient comicClient = new ComicClient(marvelApiConfig);
        try {
            if (message[0].equalsIgnoreCase("!comic")) {
                if (message.length < 2) {
                    comicClient.getComic("", event);
                    return;
                }
                StringBuilder characterBuilder = new StringBuilder();
                //Start at index 1 from message since index 0 is !comic
                for (int i = 1; i < message.length; i++) {
                    characterBuilder.append(message[i]);
                    characterBuilder.append(" ");
                }
                String character = characterBuilder.toString();
                comicClient.getComic(character, event);
                return;
            }
        } catch (MarvelApiException e) {
            channel.sendMessage("An error occured with the Marvel API").queue();
            return;
        }
    }
}