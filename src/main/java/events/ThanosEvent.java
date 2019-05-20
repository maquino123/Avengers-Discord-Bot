package events;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class ThanosEvent extends ListenerAdapter {

    //Listen for !thanos message being sent on Discord server
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        //Collect the message and store it
        //Stores the message of that event into the String messageSent. Returns the string version of that message.
        //event object comes with different methods
        //Put each item into an array, whenever there is a space, put it into an array
        String messageSent = event.getMessage().getContentRaw();

        //Clears half of the messages in discord chat
        if (messageSent.equalsIgnoreCase("!snap")){
            //Count how many messages are sent in channel
            int messageCount = 0;
            Random rand = new Random();
            List<Message> messages = event.getChannel().getHistory().retrievePast(100).complete();
            for (int i = 0; i < messages.size(); i++){
                messageCount = messageCount + 1;
            }

            int halfMessage = messageCount / 2;

            List<Message> toDelete = new ArrayList<Message>();

            for (int j = 0; j < halfMessage; j++){
                int randomIndex = rand.nextInt(messages.size());
                Message randomElement = messages.get(randomIndex);
                toDelete.add(randomElement);
            }

            //Sends the message in the same channel that the message was displayed in
            //Get the channel that the event happened in.
            //Want to add .queue at the end bc bots have lots of things to do at once.
            event.getChannel().sendMessage("I am inevitable.").queue();
            event.getChannel().deleteMessages(toDelete).queue();


        }
    }

    }

