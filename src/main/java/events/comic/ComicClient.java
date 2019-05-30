package events.comic;

import events.comic.CharacterApiRest;
import com.karumi.marvelapiclient.MarvelApiConfig;
import com.karumi.marvelapiclient.MarvelApiException;
import com.karumi.marvelapiclient.model.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import retrofit2.Call;
import retrofit2.http.QueryMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ComicClient extends MarvelApiClient {
    public ComicClient(MarvelApiConfig marvelApiConfig){
        super(marvelApiConfig);
    }

    public MarvelResponse<CharacterDto> getComic(String characterName, GuildMessageReceivedEvent event) throws MarvelApiException{
        TextChannel channel = event.getChannel();
        EmbedBuilder eb = new EmbedBuilder();
        if (characterName == null || characterName == ""){
            channel.sendMessage("Please specify a character").queue();
            throw new IllegalArgumentException("The Comic Name must not be null or empty");
        }

        CharacterApiRest api = getApi(CharacterApiRest.class);
        CharactersQuery comicFilter = CharactersQuery.Builder.create().withName(characterName).build();
        Map<String, Object> comicMap = comicFilter.toMap();
        Call<MarvelResponse<CharactersDto>> call = api.getCharacters(comicMap);
        MarvelResponse<CharactersDto> characters = execute(call);
        CharactersDto charactersDto = characters.getResponse();

        if(charactersDto != null && charactersDto.getCount() > 0){
            CharacterDto characterDto = charactersDto.getCharacters().get(0);
            MarvelResponse<CharacterDto> characterResponse = new MarvelResponse<>(characters);
            characterResponse.setResponse(characterDto);
            String comic = characterDto.getComics().getItems().get(0).getName();
            String id = characterDto.getId();
            String thumbnail = characterDto.getThumbnail().getImageUrl(MarvelImage.Size.PORTRAIT_FANTASTIC);
            channel.sendMessageFormat("The comic character is %s", characterName).queue();
            channel.sendMessageFormat("ID: %s", id).queue();
            channel.sendMessageFormat("Comics: %s", comic).queue();
            eb.setImage(thumbnail);
            channel.sendMessage(eb.build()).queue();
            return characterResponse;


        }else{
            channel.sendMessageFormat("The comic for %s could not be found", characterName).queue();
            throw new MarvelApiException("Comic not found", null);
        }
    }
}
