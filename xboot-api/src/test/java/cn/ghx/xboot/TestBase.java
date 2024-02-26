package cn.ghx.xboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestBase {

    @Autowired
    private ObjectMapper mapper;

    public void print(Object obj){
        if(obj instanceof String){
            System.out.println(obj);
        }else{
            try {
                System.out.println(mapper.writeValueAsString(obj));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
