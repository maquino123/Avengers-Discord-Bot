package events.comic;

import com.karumi.marvelapiclient.MarvelApiConfig;
import com.karumi.marvelapiclient.MarvelApiException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import retrofit2.Call;
import retrofit2.Response;
import com.google.gson.Gson;
import java.io.IOException;

public class MarvelApiClient {
    private final MarvelApiConfig marvelApiConfig;

    public MarvelApiClient(MarvelApiConfig marvelApiConfig){
        this.marvelApiConfig = marvelApiConfig;
    }

    <T> T getApi(Class<T> apiRest){
        return marvelApiConfig.getRetrofit().create(apiRest);
    }

    public <T> T execute(Call<T> call) throws MarvelApiException{
        Response<T> response = null;
        try{
            response = call.execute();
        }catch (IOException e){
            throw new MarvelApiException("Network error", e);
        }
        if (response.isSuccessful()){
            return response.body();
        } else{
            parseError(response);
            return null;
        }
    }

    private void parseError(Response execute) throws MarvelApiException {
        String marvelCode = "";
        String marvelDescription = "";
        if (execute.errorBody() != null){
            Gson gson = new Gson();
            try{
                String errorBody = execute.errorBody().string();
                MarvelError marvelError = gson.fromJson(errorBody, MarvelError.class);
                marvelCode = marvelError.getCode();
                marvelDescription = marvelError.getMessage();
                if (marvelDescription == null || "".equals(marvelDescription)){
                    marvelDescription = marvelError.getStatus();
                }
            }catch(IOException e){
            }
        }
    }
}
