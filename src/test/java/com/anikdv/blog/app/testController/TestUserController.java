
package com.anikdv.blog.app.testController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.anikdv.blog.app.controllers.UserController;
import com.anikdv.blog.app.payloads.UserDto;
import com.anikdv.blog.app.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.constraints.Positive;

/**
 * This is Test Case Class of User Controller
 *
 * @author AnikDV
 */

@WebMvcTest(value = UserController.class)
public class TestUserController {

	@MockBean
	private UserService service;

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Test For create an User
	 *
	 * @author AnikDV
	 * @info Testing createUserTest() Method
	 * @throws Exception
	 * @see Positive Scenario
	 */
	@Test
	void createUserRecordTest_Positives() throws Exception {

		// create an user
		UserDto user = new UserDto(123, "anikdv", "testing this method", "anik@gmail.com", "Anik1234",
				"image.jpg", "active", LocalDateTime.now(), null);

		// Implement the mocking
		when(this.service.createUser(ArgumentMatchers.any())).thenReturn(user);

		ObjectMapper mapper = new ObjectMapper();
		// For that writeValueAsString Method can be used to serialize any Java value as
		// a String.
		String userValue = mapper.writeValueAsString(user);
		// build for request
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/user/create")
				.contentType(MediaType.APPLICATION_JSON).content(userValue);

		// perform the request
		ResultActions perform = this.mockMvc.perform(requestBuilder);
		// return the result
		MvcResult result = perform.andReturn();
		// get the response as status
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();

		// assertion
		assertEquals(201, status);
	}

	/**
	 * Test For Error when Create an User
	 *
	 * @info Testing createUserTest02() Method
	 * @author AnikDV
	 * @see Negative Scenario
	 * @throws Exception
	 */
	@Test
	void createUserRecordTest_Nagetive() throws Exception {

		// create an user
		UserDto user = new UserDto(123, "anikdv", "testing this method", "anik@gmail.com", "Anik1234",
				"image.jpg", "active", LocalDateTime.now(),null);

		// Implement the mocking
		when(this.service.createUser(ArgumentMatchers.any())).thenThrow(InternalServerError.class);

		ObjectMapper mapper = new ObjectMapper();
		// For that writeValueAsString Method can be used to serialize any Java value as
		// a String.
		String userValue = mapper.writeValueAsString(user);
		// build for request
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/user/create")
				.contentType(MediaType.APPLICATION_JSON).content(userValue);

		// perform the request
		ResultActions perform = this.mockMvc.perform(requestBuilder);
		// return the result
		MvcResult result = perform.andReturn();
		// get the response as status
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();

		// assertion
		assertEquals(500, status);
	}

	/**
	 * Test For User Resource Update
	 *
	 * @author AnikDV
	 * @throws Exception
	 * @info Testing updateUserTest() Method
	 * @see Positive Scenario
	 */
	@Test
	void updateUserRecordTest_Positive() throws Exception {
		Integer resourceId = 8;
		UserDto updateUser = UserDto.builder().userid(resourceId).name("TestName").about("This is a Test About")
				.email("test@gmail.com").password("TestPassword#1").status("active").build();

		// Implement the mocking
		// when(this.service.getUserById(resourceId)).thenReturn(updateUser);
		when(this.service.updateUser(updateUser, resourceId)).thenReturn(updateUser);

		ObjectMapper mapper = new ObjectMapper();
		// For that writeValueAsString Method can be used to serialize any Java value as
		// a String.
		String updatedUserContent = mapper.writeValueAsString(updateUser);
		// build for request
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/user/update/" + resourceId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(updatedUserContent);
		// perform the request
		ResultActions resultActions = this.mockMvc.perform(mockRequest).andExpect(status().isOk());
		// return the result
		MvcResult returnResult = resultActions.andReturn();
		// get response the result
		MockHttpServletResponse response = returnResult.getResponse();
		int status = response.getStatus();

		// assertion
		assertEquals(200, status);
	}

	/**
	 * Test For ResourceNotFoundException | When UserID is Not Valid
	 *
	 * @author AnikDV
	 * @throws Exception
	 * @info Testing updateUserTest() Method
	 * @see Negetive Scenario
	 */
//	@Test
//	void updateUserRecordTest_Negetive01() throws Exception {
//
//		int resourceId = 4564;// <- | -> Invalid User ID.
//
//		UserDto updateUserContent = UserDto.builder()
//				.userId(resourceId)
//				.name("TestName")
//				.role("TESTDEMO")
//				.about("This is a Demo Test About")
//				.email("testdemo@gmail.com")
//				.password("TestPassword#2")
//				.image_url("test2.jpg")
//				.status("deactive")
//				.build();
//		// Implement the mocking
//		when(this.service.getUserById(resourceId)).thenThrow(ResourceNotFoundException.class);
//		when(this.service.updateUser(updateUserContent, updateUserContent.getUserId())).thenThrow(ResourceNotFoundException.class);
//
//		ObjectMapper mapper = new ObjectMapper();
//		// For that writeValueAsString Method can be used to serialize any Java value as a String.
//		String userInfo = mapper.writeValueAsString(updateUserContent);
//
//		// build the request
//		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/user/update/"+resourceId)
//		.contentType(MediaType.APPLICATION_JSON)
//		.accept(MediaType.APPLICATION_JSON)
//		.content(userInfo);
//
//		ResultActions performResult = mockMvc.perform(mockRequest);
//
//		// return the result
//		MvcResult returnResult = performResult.andReturn();
//		// get response the result
//		MockHttpServletResponse response = returnResult.getResponse();
//		int status = response.getStatus();
//
//		// assertion
//		assertEquals(404, status);
//	}

	/**
	 * Test For BadRequestException | When User Informations NULL or UserID NULL
	 *
	 * @author AnikDV
	 * @throws Exception
	 * @info Testing updateUserTest() Method
	 * @see Negetive Scenario
	 */
	@Test
	void updateUserRecordTest_Negetive02() throws Exception {
		Integer resourceId = null;

		// Implement the mocking
		// when(this.service.getUserById(resourceId)).thenThrow(ResourceNotFoundException.class);
		when(this.service.updateUser(null, null)).thenThrow(BadRequest.class);

		ObjectMapper mapper = new ObjectMapper();
		// For that writeValueAsString Method can be used to serialize any Java value as
		// a String.
		String updatedUserContent = mapper.writeValueAsString(null);

		// build for request
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/user/update/" + resourceId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(updatedUserContent);
		// perform the request
		ResultActions resultActions = this.mockMvc.perform(mockRequest).andExpect(status().isBadRequest());

		// return the result
		MvcResult returnResult = resultActions.andReturn();
		// get response the result
		MockHttpServletResponse response = returnResult.getResponse();
		int status = response.getStatus();

		// assertion
		assertEquals(400, status);
	}

}
