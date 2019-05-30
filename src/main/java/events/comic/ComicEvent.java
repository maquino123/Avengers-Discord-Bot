package events.comic;

import com.karumi.marvelapiclient.model.*;
import events.comic.MarvelApiClient;
import com.karumi.marvelapiclient.ComicApiClient;
import com.karumi.marvelapiclient.MarvelApiConfig;
import com.karumi.marvelapiclient.MarvelApiException;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class ComicEvent extends ListenerAdapter{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        TextChannel channel = event.getChannel();
        String[] message = event.getMessage().getContentRaw().split(" ");
        MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(ComicResource.MARVEL_PUBLIC_API, ComicResource.MARVEL_PRIVATE_API).debug().build();
        ComicClient comicClient = new ComicClient(marvelApiConfig);
        try {
            if (message[0].equalsIgnoreCase("!comic")) {
                if (message.length < 2){
                    comicClient.getComic("", event);
                    return;
                }
                String character = message[1];
                comicClient.getComic(character, event);
                return;
            }
        }catch(MarvelApiException e){
            return;
        }
    }
}
