package events.comic;

import com.karumi.marvelapiclient.model.CharactersDto;
import com.karumi.marvelapiclient.model.MarvelResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.Map;

interface CharacterApiRest{
    @GET("characters")
    Call<MarvelResponse<CharactersDto>> getCharacters(
            @QueryMap Map<String, Object> characterFilter);

    @GET("characters/{id}/comics") Call<MarvelResponse<CharactersDto>> getCharacter(
            @Path("id") String characterId);

}