package com.fleetmanagement.api_rest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class DataLoader {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ResourceLoader resourceLoader;

	@PostConstruct
	public void loadData() throws IOException {
		for (int i = 1; i <= 12; i++) {
			Resource resource = resourceLoader.getResource("classpath:data" + i + ".sql");
			if (resource.exists()) {
				try (Stream<String> stream = Files.lines(Paths.get(resource.getURI()))) {
					String sql = stream.reduce("", (acc, line) -> acc + line + "\n");
					jdbcTemplate.execute(sql);
				}
			}
		}
	}
}
