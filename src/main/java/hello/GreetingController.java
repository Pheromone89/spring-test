package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class GreetingController {

    private static final String template = "What's up, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return x;
    }
    
    @Autowired
    @Qualifier("def")
    private Greeting x;
    
    @Autowired
    private EntityManagerFactory em;
    
    @CrossOrigin(origins= {"*"})
    @RequestMapping("/actors")
    public List<Actor> allActors(){
    	return em.createEntityManager().createQuery("from Actor").getResultList();
    }

    @CrossOrigin(origins= {"*"})
    @RequestMapping("/films")
    public List<Film> allFilms(){
    	return em.createEntityManager().createQuery("from Film").getResultList();
    }
    
    @RequestMapping("/languages")
    public List<Language> allLanguages(){
    	return em.createEntityManager().createQuery("from Language").getResultList();
    }
    
    @RequestMapping("/filmactors")
    public List<FilmActor> allFilmActors(){
    	return em.createEntityManager().createQuery("from FilmActor").getResultList();
    }
    
    @RequestMapping("/data")
    public List<String> dataNegara(@RequestParam("contain") String prefix){
    	List<String> data = new ArrayList<String>();
    	data.add("Indonesia");
    	data.add("Malaysia");
    	data.add("Brunei");
    	data.add("Timor Leste");
       	return data.stream().filter(line -> line.startsWith(prefix)).collect(Collectors.toList());    	
    }
    
    @RequestMapping("/countries")
    public String getCountries() throws IOException {
    	// bikin url
    URL url = new URL ("http://www.webservicex.net/country.asmx/GetCountries");
    	// buka koneksi
    URLConnection connection = url.openConnection();
// 	  kalo method get
//    connection.setDoInput(true);
//    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//    connection.setRequestProperty("Content-Length", "0");
    	// bikin aliran data
    InputStream stream = connection.getInputStream();
    	// bikin class reader buat baca aliran data
    InputStreamReader reader = new InputStreamReader(stream);
    BufferedReader buffer = new BufferedReader(reader);
    
    String line;
    StringBuilder builder = new StringBuilder();
	    while((line = buffer.readLine()) != null) {
	    	builder.append(line);
	    }
    return builder.toString();
    }
}