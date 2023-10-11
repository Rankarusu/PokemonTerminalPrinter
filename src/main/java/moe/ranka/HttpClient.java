package moe.ranka;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;


public class HttpClient {
    public static final String imgBaseUrl = "https://veekun.com/dex/media/pokemon/icons/";
    public static final String pokemonBaseUrl = "https://pokeapi.co/api/v2/pokemon-species/"; // takes id or name


    public BufferedImage getSpriteByIdOrName(String idOrName) {
        int id;
        if (idOrName.matches("\\d+")) {
            id = Integer.parseInt(idOrName);
        } else {
            id = this.fetchPokemonId(idOrName);
        }

        return fetchSprite(id);
    }

    private int fetchPokemonId(String name) {

        URI uri;
        try {
            uri = new URI(pokemonBaseUrl + name);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL()
                                                                  .openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                connection.disconnect();

                JSONObject json = new JSONObject(response.toString());
                return Integer.parseInt(json.get("id").toString());

            } else {
                throw new RuntimeException(
                        "Did not receive HTTP_OK Response when trying to get pokemon id");
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Something went wrong while fetching the pokemon Id");
        }

    }

    private BufferedImage fetchSprite(int dexNr) {

        URI uri = null;
        try {
            uri = new URI(imgBaseUrl + dexNr + ".png");
            return ImageIO.read(uri.toURL());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Something went wrong while fetching the sprite");
        }

    }
}
