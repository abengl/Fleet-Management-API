package com.fleetmanagement.api_rest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Configuration
public class ShutdownConfig {

	@Autowired
	private DataSource dataSource;

	@PreDestroy
	public void onShutdown() {
		try (Connection connection = dataSource.getConnection();
			 Statement statement = connection.createStatement()) {
			statement.execute("DROP TABLE IF EXISTS api.trajectories CASCADE");
			statement.execute("DROP TABLE IF EXISTS api.taxis CASCADE");
			statement.execute("DROP TABLE IF EXISTS api.users CASCADE");
			statement.execute("DROP TABLE IF EXISTS api.roles CASCADE");
			statement.execute("DROP TABLE IF EXISTS api.permissions CASCADE");
			statement.execute("DROP TABLE IF EXISTS api.user_roles CASCADE");
			statement.execute("DROP TABLE IF EXISTS api.role_permissions CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}