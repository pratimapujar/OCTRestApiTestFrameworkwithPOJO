package com.employees;

import static org.hamcrest.Matchers.lessThan;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.employees.pojo.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Employees {
	
	private static String createdId;
	
	@Test(priority=1)
	public static void GetUser() {
		
		
		
		RequestSpecification req = RestAssured.given();
		Response res = req.when().get("https://dummy.restapiexample.com/api/v1/employees");
					
		res.prettyPrint();
			
		res.then().statusCode(200);
		res.then().time(lessThan(5000L));
		
		EmployeeResponsePojo result = res.as(EmployeeResponsePojo.class);
		Assert.assertEquals(result.getStatus(), "success");
		System.out.println("num of data =" +result.getData().size());
		
	}
	
	@Test(priority=2)
	public static void CreateUser() {

		
		CreateEmployeeInfoPojo requestBody = new CreateEmployeeInfoPojo();
		requestBody.setName("test");
		requestBody.setSalary("123");
		requestBody.setAge("23");
	
		RequestSpecification req = RestAssured.given();
				
		String s = "0";
		Response res = req.body(requestBody)
				.contentType(ContentType.JSON)
				.when()
				.post("https://dummy.restapiexample.com/api/v1/create");
		
		res.prettyPrint();
		
		res.then().statusCode(200);
		res.then().time(lessThan(5000L));
		
		CreateEmployeeResultPojo result = res.as(CreateEmployeeResultPojo.class);
		Assert.assertEquals(result.getStatus(), "success");
		Assert.assertEquals(result.getData().getName(), requestBody.getName());
		Assert.assertEquals(result.getData().getSalary(), requestBody.getSalary());
		Assert.assertEquals(result.getData().getAge(), requestBody.getAge());
		System.out.println("Created User id: " + result.getData().getId());
		createdId = result.getData().getId().toString();
		
	}
	
	@Test(priority=3)
	public static void DeleteUser() {
		Response res = RestAssured
				.given()
				.contentType(ContentType.JSON)
				.when()
				.delete("https://dummy.restapiexample.com/api/v1/delete/" + createdId);
					
		res.prettyPrint();
			
		res.then().statusCode(200);
		res.then().time(lessThan(5000L));
		
		DeleteEmployeeResponsePojo result = res.as(DeleteEmployeeResponsePojo.class);
		Assert.assertEquals(result.getStatus(), "success");
		System.out.println(result.getData());
		Assert.assertEquals(result.getData(), createdId);
		
	}
	
	@Test(priority=4)
	public static void DeleteUser1() {
		Response res = RestAssured
				.given()
				.contentType(ContentType.JSON)
				.when()
				.delete("https://dummy.restapiexample.com/api/v1/delete/0");
					
		res.prettyPrint();
			
		res.then().statusCode(400);
		res.then().time(lessThan(5000L));
		
		DeleteEmployee1ResponsePojo result = res.as(DeleteEmployee1ResponsePojo.class);
		Assert.assertEquals(result.getStatus(), "error");
		System.out.println("msg data =" +result.getMessage());
		
	}
	
	@Test(priority=5)
	public static void GetUserId2() {
		
		RequestSpecification req = RestAssured.given();
		Response res = req.when().get("https://dummy.restapiexample.com/api/v1/employee/2");
					
		res.prettyPrint();
			
		res.then().statusCode(200);
		res.then().time(lessThan(5000L));
		res.then().contentType(ContentType.JSON);
		
		GetEmployeeResponsePojo1 result = res.as(GetEmployeeResponsePojo1.class);
		System.out.println(result);
		
		Assert.assertEquals(result.getData().getEmployeeName(), "Garrett Winters");
		Assert.assertEquals(result.getData().getEmployeeAge(), 63);
		Assert.assertEquals(result.getData().getEmployeeSalary(), 170750);
	
	}

	
}
