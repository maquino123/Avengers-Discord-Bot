package events.hero;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class ThorEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        //Split between the command and the user
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message[0].equalsIgnoreCase("!thor")){
            List<Member> listOfMembers = event.getMessage().getMentionedMembers(); //Put mentioned members in list
            TextChannel channel = event.getChannel();
            Member member = event.getMember();  //Member that sent the message
            Member selfMember = event.getGuild().getSelfMember(); //Get bot

            if (message.length < 2){
                channel.sendMessage("Thor does not know who to ban!").queue();
                return;
            }

            Member target = listOfMembers.get(0); //Get that member

            //Checks if the members has permission or has the authority to ban (cannot ban equal or higher members)
            if (!member.hasPermission(Permission.BAN_MEMBERS) && !member.canInteract(target)){
                channel.sendMessageFormat("Odin does not give you permission to ban %s!", target).queue();
                return;
            }

            //Checks if the bot has permission to ban or has the authority to ban
            if (!selfMember.hasPermission(Permission.BAN_MEMBERS) ||  !selfMember.canInteract(target)){
                channel.sendMessageFormat("I am not worthy to use Mjolnir on %s!", target).queue();
                return;
            }


            event.getGuild().getController().ban(target, 1).queue();
            channel.sendMessageFormat("%s has banned %s by the power of Thor!", member, target).queue();
        }
    }
}
