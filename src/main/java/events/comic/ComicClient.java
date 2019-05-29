package events.comic;

import com.karumi.marvelapiclient.ComicApiClient;
import com.karumi.marvelapiclient.MarvelApiConfig;
import com.karumi.marvelapiclient.MarvelApiException;
import com.karumi.marvelapiclient.model.ComicDto;
import com.karumi.marvelapiclient.model.ComicsDto;
import com.karumi.marvelapiclient.model.MarvelResponse;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import retrofit2.Call;

public final class ComicClient extends MarvelApiClient {
    public ComicClient(MarvelApiConfig marvelApiConfig){
        super(marvelApiConfig);
    }

    public MarvelResponse<ComicDto> getComic(String comicId, GuildMessageReceivedEvent event) throws MarvelApiException{
        TextChannel channel = event.getChannel();
        if (comicId == null || comicId == ""){
            throw new IllegalArgumentException("The Comic Name must not be null or empty");
        }
        ComicApiRest api = getApi(ComicApiRest.class);

        Call<MarvelResponse<ComicsDto>> call = api.getComic(comicId);
        MarvelResponse<ComicsDto> comics = execute(call);
        ComicsDto comicsDto = comics.getResponse();
        if(comicsDto != null && comicsDto.getCount() > 0){
            ComicDto comicDto = comicsDto.getComics().get(0);
            MarvelResponse<ComicDto> comicResponse = new MarvelResponse<ComicDto>(comics);
            comicResponse.setResponse(comicDto);
            return comicResponse;
        }else{
            throw new MarvelApiException("Comic not found", null);
        }
    }
}
