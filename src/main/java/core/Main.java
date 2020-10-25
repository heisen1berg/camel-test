package core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
Испрвления и доработки:
1. Чтобы провести тесты, необходимо было запустить сначала приложение. Исправлено.
2. Доработаны JSON преобразования, костыли убраны.
3. Поддержка других сервисов погоды. Был добавлен класс и методы, которые облегчат
 работу с разными сервисами погоды и позволят легко добавлять и удалять сервисы.
*/
@SpringBootApplication
public class Main {
    private static final RestTemplate restTemplate= new RestTemplate();
    private static final ObjectMapper objectMapper=new ObjectMapper();

    /*public static void main(String[] args) {
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        SpringApplication.run(Main.class, args);

    }*/

    @Resource
    private sService ss;
    public static void main(String[] args)
    {
        SpringApplication.run(Main.class);
        Main m=new Main();
        m.myvoid();
    }
    void myvoid(){
        ss.myvoid();
    }
    @Service
    public class sService{
        @Resource
        private PrototypeService ps;

        public sService(){}

        public void myvoid(){
            ps.myvoid();
        }
    }
    @Service
    @Scope("prototype")
    public class PrototypeService {
        List<String> names = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
            add("4");
        }};

        private String user;

        public PrototypeService() {
            int index = new Random().nextInt(4);
            this.user = names.get(index);
        }

        void myvoid() {
            System.out.println(user);
        }
    }
}