//package com.projectIndustriel.server.controller;
//
//import com.projectIndustriel.server.dto.SignatureDTO;
//import com.projectIndustriel.server.interfaces.SessionServiceable;
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.stringContainsInOrder;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest
//@Import(SessionController.class)
//public class ServerControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private SessionServiceable serverService; // Mock the service directly
//
//    @Test
//    public void testIndex() throws Exception {
//        MvcResult result = mockMvc.perform(get("/index.html"))
//                .andExpect(status().isOk())
//                .andReturn();
//        String html = result.getResponse().getContentAsString();
//        assertEquals("hello world!", html);
//    }
//
//    @Test
//    public void testSignature() throws Exception {
//        // Given
//        UUID sigId0 =  UUID.fromString("aabd33ba-2c89-43e7-903d-0cd15295128e");
//        UUID sigId1 =  UUID.fromString("aabd33ba-3c89-43e7-903d-2ce15295128e");
//        List<SignatureDTO> sigs = List.of(
//                new SignatureDTO(sigId0, "TestName0",
//                        "TestFirstName0",
//                        UUID.randomUUID(),
//                        "12345"),
//                new SignatureDTO(sigId1, "TestName1",
//                        "TestFirstName1",
//                        UUID.randomUUID(),
//                        "12345")
//        );
//        when(serverService.getSignatures()).thenReturn(sigs);
//        // When
//        MvcResult result = mockMvc.perform(get("/signatures"))
//                .andExpect(status().isOk())
//                .andReturn();
//        String html = result.getResponse().getContentAsString();
//        // Then
//        assertThat(html, stringContainsInOrder("ID", "User specialityName", "User first specialityName", "Cours specialityName"));
//        assertThat(html, stringContainsInOrder(
//                "aabd33ba-2c89-43e7-903d-0cd15295128e",
//                "TestName0",
//                "TestFirstName0",
//                "TestCoursName0"));
//        assertThat(html, stringContainsInOrder(
//                "aabd33ba-3c89-43e7-903d-2ce15295128e",
//                "TestName1",
//                "TestFirstName1",
//                "TestCoursName1"));
//    }
//}
//
