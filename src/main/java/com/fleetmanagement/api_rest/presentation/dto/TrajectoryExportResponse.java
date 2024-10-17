package com.fleetmanagement.api_rest.presentation.dto;

public record TrajectoryExportResponse(Integer taxiId, String plate, String date, double latitude,
									   double longitude) {
}
