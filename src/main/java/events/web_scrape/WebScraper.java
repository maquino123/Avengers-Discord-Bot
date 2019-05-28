package events.web_scrape;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScraper extends ListenerAdapter {

    private static WebScraper INSTANCE;

    public Document test(String input, GuildMessageReceivedEvent event) throws IOException{
        String messageReplace = input.replace(" ", "%20");
        final Document document = Jsoup.connect("https://www.urbandictionary.com/define.php?term=" + messageReplace).get();
        Elements word = document.select("div.def-panel");
        String meaning = word.select("div.meaning").text();
        TextChannel channel = event.getChannel();
        channel.sendMessageFormat("Here's what I found for %s", input).queue();
        channel.sendMessage(meaning).queue();
        return document;
    }

    //constructor for Webscraper
    public static synchronized WebScraper getInstance() {
        if (INSTANCE == null){
            INSTANCE = new WebScraper();
        }

        return INSTANCE;
    }
}
