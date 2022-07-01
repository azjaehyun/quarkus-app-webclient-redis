package org.acme;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javax.ws.rs.Path;
import javax.ws.rs.POST;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.Set;


@Path("/employees")
public class EmployeeResource {

    @Inject
    EmployeeService employeeService;

    //@Inject
    //EmployeeHashService employeeService;

    @POST
    public void  create(Employee employee) throws JsonProcessingException {
        Multi<Object> sequence = Multi.createFrom().generator(()->1,(n,emitter)->{
            int next = n + 1;
            //System.out.println(next);
            if (n < 10000) {
                try {
                    Employee employeeDump = new Employee();
                    employeeDump.id = Integer.toString(next);
                    employeeDump.name = Integer.toString(next);
                    employeeDump.salary = BigDecimal.valueOf(next * 1000);
                    employeeService.insert(employeeDump);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                emitter.emit(next);

            } else {
                emitter.complete();
            }
            return next;
        });
        // Multi<Long> uni =  Multi.createFrom().item(sequence.subscribe().asStream().count());
        sequence
            .onSubscription().invoke(() -> System.out.println("⬇️ Subscribed"))
            .onItem().invoke(i -> System.out.println("⬇️ Received item: " + i))
            .onFailure().invoke(f -> System.out.println("⬇️ Failed with " + f))
            .onCompletion().invoke(() -> System.out.println("⬇️ Completed"))
            .onCancellation().invoke(() -> System.out.println("⬆️ Cancelled"))
            .onRequest().invoke(l -> System.out.println("⬆️ Requested: " + l));
        // sequence.onItem().transform(s->s).subscribe().with(System.out::println);
    }

    @DELETE
    public void delete(Employee employee) throws JsonProcessingException {
        Multi<Object> sequence = Multi.createFrom().generator(()->1,(n,emitter)->{
            int next = n + 1;
            System.out.println(next);
            if (n < 10000) {
                Employee employeeDump = new Employee();
                employeeDump.id = Integer.toString(next);
                employeeDump.name = Integer.toString(next);
                employeeDump.salary = BigDecimal.valueOf(next * 1000);
                employeeService.delete("employee-"+employee.id);
                emitter.emit(next);

            } else {
                emitter.complete();
            }
            return next;
        });
        sequence.onItem().transform(s->s).subscribe().with(System.out::println);
    }

    @GET
    @Path("/{key}")
    public Employee get(@PathParam("key") String key) throws JsonMappingException, JsonProcessingException {
        return employeeService.get(key);
    }

    @DELETE
    @Path("/{key}")
    public Uni<Void> delete(@PathParam("key") String key) throws JsonProcessingException {
        return employeeService.delete(key);
    }

}
