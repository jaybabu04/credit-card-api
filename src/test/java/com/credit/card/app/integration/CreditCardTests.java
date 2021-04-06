package com.credit.card.app.integration;

import com.credit.card.app.model.CardInfo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreditCardAppTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Value("${spring.security.user.name}")
	private String username;

	@Value("${spring.security.user.password}")
	private String password;

	@Test
	@Order(1)
	public void should_return_empty_result_with_200_status() {
		ResponseEntity<CardInfo[]> responseEntity = getAllCardInfos();
		assertEquals(responseEntity.getStatusCodeValue(),200);
		CardInfo cardInfo[] =new CardInfo[0];
		assertArrayEquals(responseEntity.getBody(),cardInfo);
	}

	@Test
	@Order(2)
	public void should_return_one_card_info_with_200_status() {
		saveNewCardInfo(createCardInfo("79927398713"));
		ResponseEntity<CardInfo[]> responseEntity = getAllCardInfos();
		Object[] objects = responseEntity.getBody();
		assertEquals(responseEntity.getStatusCodeValue(),200);
		assertNotNull(objects);
		assertEquals(1, objects.length);
	}

	@Test
	public void add_new_cardinfo_should_return_201_status() {
		ResponseEntity responseEntity = saveNewCardInfo(createCardInfo("377846021826939"));
		assertEquals(responseEntity.getStatusCodeValue(),201);
	}

	@Test
	public void should_return_422_status_with_card_already_exist() {
		saveNewCardInfo(createCardInfo("374008426902954"));
		ResponseEntity exceptionResponse = saveNewCardInfo(createCardInfo("374008426902954"));
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), exceptionResponse.getStatusCodeValue());
		assertTrue(exceptionResponse.getBody().toString().contains("Card number is already in use"));
	}

	@Test
	public void should_return_bad_request_status_for_empty_name() {
		CardInfo cardInfo = createCardInfo("374008426902954");
		cardInfo.setName("");
		ResponseEntity exceptionResponse = saveNewCardInfo(cardInfo);
		assertEquals(HttpStatus.BAD_REQUEST.value(), exceptionResponse.getStatusCodeValue());
	}

	@Test
	public void should_return_bad_request_status_for_empty_cardNo() {
		CardInfo cardInfo = createCardInfo("");
		ResponseEntity exceptionResponse = saveNewCardInfo(cardInfo);
		assertEquals(HttpStatus.BAD_REQUEST.value(), exceptionResponse.getStatusCodeValue());
	}

	private ResponseEntity saveNewCardInfo(CardInfo cardInfo) {
		return restTemplate.withBasicAuth(username, password).postForEntity( "/api/v1/credit-card/", cardInfo, Object.class);
	}

	private ResponseEntity<CardInfo[]> getAllCardInfos() {
		return restTemplate.withBasicAuth(username, password).getForEntity("/api/v1/credit-card/", CardInfo[].class, Collections.emptyMap());
	}

	private CardInfo createCardInfo(String cardNumber){ //374008426902954
		CardInfo cardInfo = new CardInfo();
		cardInfo.setUpdatedDateTime(LocalDateTime.now());
		cardInfo.setCreatedDateTime(LocalDateTime.now());
		cardInfo.setCreditlimit(1000);
		cardInfo.setCreatedBy("System");
		cardInfo.setName("Ganesh Siva");
		cardInfo.setActive(false);
		cardInfo.setUpdatedBy("");
		cardInfo.setCardNumber(cardNumber);
		cardInfo.setBalance(0);
		return cardInfo;
	}
}
