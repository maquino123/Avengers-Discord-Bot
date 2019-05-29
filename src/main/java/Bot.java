import events.command.LeaveCommand;
import events.command.ShutdownCommand;
import events.hero.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot extends resource{

    public static void main(String[] args) throws Exception{

        //Token for bot
        JDA jda = new JDABuilder(resource.token).build();
        
        jda.addEventListener(new ThanosEvent());
        jda.addEventListener(new CapEvent());
        jda.addEventListener(new LeaveCommand());
        jda.addEventListener(new ThorEvent());
        jda.addEventListener(new HawkeyeEvent());
        jda.addEventListener(new HulkEvent());
        jda.addEventListener(new ShutdownCommand());
        jda.addEventListener(new JarvisEvent());
    }
}
