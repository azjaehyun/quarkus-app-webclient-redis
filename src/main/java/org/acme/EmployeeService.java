package org.acme;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.smallrye.mutiny.Uni;

@Singleton
public class EmployeeService {

    @Inject
    RedisClient redisClient;

    @Inject
    ReactiveRedisClient reactiveRedisClient;

    public void insert(Employee employee) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(employee);
        redisClient.set(Arrays.asList("employee-"+employee.id, str ));
    }

    public Employee get(String key) throws JsonMappingException, JsonProcessingException {

        String str = redisClient.get(key).toString();

        ObjectMapper mapper = new ObjectMapper();
        Employee emp = mapper.readValue(str, Employee.class);

        return emp;
    }

    public Uni<Void> delete(String key) {
        return reactiveRedisClient.del(Arrays.asList(key))
                .map(response -> null);
    }

}
