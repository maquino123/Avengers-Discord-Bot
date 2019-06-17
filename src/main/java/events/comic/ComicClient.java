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

import java.awt.*;
import java.util.*;
import java.util.List;

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

            List<ComicResourceDto> comic = characterDto.getComics().getItems();
            String name = characterDto.getName();
            String id = characterDto.getId();
            String description = characterDto.getDescription();
            if (description.equals("")){
                description = "No description could be found for this character";
            }
            String thumbnail = characterDto.getThumbnail().getImageUrl(MarvelImage.Size.PORTRAIT_FANTASTIC);

            //Initialize new list
            List<String> comicList = new ArrayList<>();
            for (int i = 0; i < comic.size(); i++){
                String comicName = comic.get(i).getName();
                comicList.add(comicName);
            }
            String formattedComicList = comicList.toString().replace("[", "").replace("]","");
            eb.setTitle("Name: " + name);
            eb.setColor(Color.red);
            eb.addField("ID: ", id, false);
            eb.addField("Description: ", description, false );
            eb.addField("Comics: ", formattedComicList, true);
            eb.setImage(thumbnail);
            channel.sendMessage(eb.build()).queue();
            return characterResponse;


        }else{
            channel.sendMessageFormat("The comic for %scould not be found", characterName).queue();
            return null;
        }
    }
}
