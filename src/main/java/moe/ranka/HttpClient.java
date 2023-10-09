package moe.ranka;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URI;


public class HttpClient {
    //TODO: use graphql and pokeapi to get data from names and ids

    /*
    * query samplePokeAPIquery {
  species: pokemon_v2_pokemonspecies(where: {pokemon_v2_pokemons: {id: {_eq: 10}}}) {
    name
    id
  }
}
*/
    public static final String baseUrl = "https://veekun.com/dex/media/pokemon/icons/";

    public BufferedImage fetchIcon(String dexNr) {
        try {

            URI url = new URI(baseUrl + dexNr + ".png");
            return ImageIO.read(url.toURL());

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
