package events.web_scrape;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScraper extends ListenerAdapter {

    private static WebScraper INSTANCE;

    public Document defineWord(String input, GuildMessageReceivedEvent event) throws IOException{
        TextChannel channel = event.getChannel();
        String messageReplace = input.replace(" ", "%20");
        String baseUrl = "https://www.urbandictionary.com/define.php?term=" + messageReplace;
        Connection connection = Jsoup.connect(baseUrl).followRedirects(false).timeout(10000);
        //Cannot read status if unable to connect to server
        Connection.Response response = connection.ignoreHttpErrors(true).execute();
        Document document = null;
        try {
            if (response.statusCode() == 200) {
                document = connection.get();
                Elements word = document.select("div.def-panel:first-child");
                String meaning = word.select("div.meaning").text();
                channel.sendMessageFormat("Here's what I found for %s", input).queue();
                channel.sendMessage(meaning).queue();
                return document;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        channel.sendMessageFormat("Sorry, I couldn't find a definition for %s", input).queue();
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
