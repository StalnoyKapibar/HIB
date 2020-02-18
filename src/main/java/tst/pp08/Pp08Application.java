package tst.pp08;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Pp08Application {

    public static void main(String[] args) {
        
        SpringApplication.run(Pp08Application.class, args);

        }

    @Bean
    public RestTemplate restTemplate() {
       RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "password"));
        return restTemplate;
    }




}
