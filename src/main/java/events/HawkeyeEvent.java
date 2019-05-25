package events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class HawkeyeEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message[0].equalsIgnoreCase("!shoot")){
            List<Member> listOfMembers = event.getMessage().getMentionedMembers(); //Put mentioned members in list
            TextChannel channel = event.getChannel();
            Member member = event.getMember();  //Member that sent the message
            Member selfMember = event.getGuild().getSelfMember(); //Get bot

            if (message.length < 2){
                channel.sendMessage("Hawekeye does not know who to shoot!").queue();
                return;
            }

            Member target = listOfMembers.get(0); //Get that member

            //Checks if the members has permission or has the authority to ban (cannot ban equal or higher members)
            if (!member.hasPermission(Permission.KICK_MEMBERS) && !member.canInteract(target)){
                channel.sendMessageFormat("Nick Fury does not give you permission to kick %s!", target).queue();
                return;
            }

            //Checks if the bot has permission to ban or has the authority to ban
            if (!selfMember.hasPermission(Permission.KICK_MEMBERS) ||  !selfMember.canInteract(target)){
                channel.sendMessageFormat("Hawkeye is unable to engage %s", target).queue();
                return;
            }


            event.getGuild().getController().kick(target).queue();
            channel.sendMessageFormat("%s has kicked %s for liking mayo on his hotdog.", member, target).queue();
        }
    }
}
