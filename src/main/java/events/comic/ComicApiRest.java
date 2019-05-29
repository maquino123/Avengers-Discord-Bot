package events.comic;

import com.karumi.marvelapiclient.model.ComicsDto;
import com.karumi.marvelapiclient.model.MarvelResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.Map;

interface ComicApiRest{
    @GET("comics")
    Call<MarvelResponse<ComicsDto>> getComics(
            @QueryMap Map<String, Object> comicFilter);

    @GET("comics/{id}") Call<MarvelResponse<ComicsDto>> getComic(
            @Path("id") String comicId);
}
