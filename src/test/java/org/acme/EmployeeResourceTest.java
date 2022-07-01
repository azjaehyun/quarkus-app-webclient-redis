package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;

@QuarkusTest
public class EmployeeResourceTest {

    @Test
    public void deveExcluirInserirRetornarEmpregado() {
        //excluindo empregado
        given()
          .when()
             .delete("/employees")
          .then()
             .statusCode(204);

        
        Child child = new Child();
        child.id = "11";
        child.name = "Enzo";
        List<Child> childs = new ArrayList<>();
        childs.add(child);

        Employee emp = new Employee();
        emp.id = "1";
        emp.name = "Alisson";
        emp.salary = new BigDecimal(100);

        emp.childs = childs;

        //inserindo empregado
        given()
            .contentType(ContentType.JSON)
            .body(emp)
          .when()
             .post("/employees")
          .then()
             .statusCode(204);

        //retornando empregado
        given()
            .when()
                .get("/employees/employee")
            .then()
                .statusCode(200)
                .body( Matchers.notNullValue() );
    }
}