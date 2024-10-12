package com.fleetmanagement.api_rest.business.service;

import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.persistence.repository.TaxiRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender mailSender;
	private final TaxiRepository taxiRepository;

	@Value("${spring.mail.username}")
	private String emailUsername;


	public void sendWithAttachmentExcel(String email, Integer taxiId, String date, ByteArrayOutputStream excelStream)
			throws MessagingException {

		validateInput(taxiId, date, email);

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

		mimeMessageHelper.setFrom(emailUsername);
		mimeMessageHelper.setTo(email);
		mimeMessageHelper.setSubject("Fleet Management API Report " + getFormattedDateTime());

		long fileSizeInBytes = excelStream.size();
		double fileSizeInKB = fileSizeInBytes / (1024.0);

		String html = "<p>Hello, the following attachment includes a report of:</p>" +
				"<table border='1'>" +
				"<tr><th>Taxi ID</th><th>Date</th><th>File Size</th></tr>" +
				"<tr>" +
				"<td>" + taxiId + "</td>" +
				"<td>" + date + "</td>" +
				"<td>" + String.format("%.2f KB", fileSizeInKB) + "</td>" +
				"</tr>" +
				"</table>";

		mimeMessageHelper.setText(html, true);

		InputStreamSource attachmentSource = new ByteArrayResource(excelStream.toByteArray());

		mimeMessageHelper.addAttachment("trajectories_" + taxiId + "_" + date + ".xlsx", attachmentSource,
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		mailSender.send(message);
	}

	private String getFormattedDateTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return now.format(formatter);
	}

	public void sendPlainTextEmail(String email) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(emailUsername);
		message.setTo(email);
		message.setSubject("Fleet Management API " + getFormattedDateTime());
		message.setText("Test email: plain email text sent.");

		mailSender.send(message);

	}

	public void sendWithAttachment(String email) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
		mimeMessageHelper.setFrom(emailUsername);
		mimeMessageHelper.setTo(email);
		mimeMessageHelper.setSubject("Fleet Management API " + getFormattedDateTime());
		mimeMessageHelper.setText("Test email: email with attachment sent.");

		FileSystemResource file = new FileSystemResource(new File("src/main/resources/data/query-roles.txt"));
		mimeMessageHelper.addAttachment("query-roles.txt", file);

		mailSender.send(message);

	}

	private void validateInput(Integer taxiId, String dateString, String email) {
		if (taxiId == null) {
			throw new InvalidParameterException("Missing taxiId value.");
		}

		if (!taxiRepository.existsById(taxiId)) {
			throw new ValueNotFoundException("Taxi ID " + taxiId + " not found.");
		}

		if (dateString == null || dateString.isEmpty()) {
			throw new InvalidParameterException("Missing date value.");
		}
		if (email == null || email.isEmpty()) {
			throw new InvalidParameterException("Missing email value.");
		}
	}

}
