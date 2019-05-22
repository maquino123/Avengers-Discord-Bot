package events;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class ThanosEvent extends ListenerAdapter {

    //Listen for !thanos message being sent on Discord server
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //Collect the message and store it
        //Stores the message of that event into the String messageSent. Returns the string version of that message.
        //event object comes with different methods
        //Put each item into an array, whenever there is a space, put it into an array
        String messageSent = event.getMessage().getContentRaw();


        //Clears half of the messages in discord chat
        if (messageSent.equalsIgnoreCase("!snap")) {
            List<Message> messages;
            Random random = new Random();

            //Get most recent 100 channel messages
            //There is a limit where you can only delete a max of 100 messages
            //TODO: Find a way to bypass that 100 message limit

            messages = event.getChannel().getHistory().retrievePast(100).complete();
            for ( int i = 0; i < (messages.size() / 2); i++){
                int randomIndex = random.nextInt(messages.size());
                messages.remove(randomIndex);
            }
            event.getChannel().deleteMessages(messages).queue();

        }
    }
}

