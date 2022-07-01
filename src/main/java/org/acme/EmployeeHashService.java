package org.acme;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.smallrye.mutiny.Uni;
import io.vertx.redis.client.Response;

@Singleton
public class EmployeeHashService {

//    @Inject
//    RedisClient redisClient;

    @Inject
    ReactiveRedisClient reactiveRedisClient;

    public void insert(Employee employee) throws JsonProcessingException{
        reactiveRedisClient.hset(Arrays.asList("employee-"+employee.id, "id", employee.id));
        reactiveRedisClient.hset(Arrays.asList("employee-"+employee.id, "name", employee.name));
        reactiveRedisClient.hset(Arrays.asList("employee-"+employee.id, "salary", employee.salary.toString()));
    }

    public Employee get(String key) throws JsonMappingException, JsonProcessingException {

        //Response res = redisClient.get(key);

        //String str = redisClient.get(key).toString();

        //String str = redisClient.hget(arg0, arg1) (key).toString();

        //Response resp = redisClient.hgetall(key);

        //@fixme o retorno nao e passivel de converter em Employee, pois nao retorna um JSON
        String str = reactiveRedisClient.hgetall(key).toString();

        ObjectMapper mapper = new ObjectMapper();
        Employee emp = mapper.readValue(str, Employee.class);

        return emp;
    }

    public Uni<Void> delete(String key) {
        return reactiveRedisClient.hdel( Arrays.asList(key, "id") ).map(response -> null);
    }

}
