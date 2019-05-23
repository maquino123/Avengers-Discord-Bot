import events.CapEvent;
import events.ThanosEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

    public static void main(String[] args) throws Exception{

        //Token for bot
        JDA jda = new JDABuilder("NTgwMDY4NTU5NDk4MTE3MTQz.XOLVfQ.Vx3E_IMpv_jO_rNcw-QQ40AOjeE").build();
        
        jda.addEventListener(new ThanosEvent());
        jda.addEventListener(new CapEvent());
    }
}
