package com.fleetmanagement.api_rest.utils;

import com.fleetmanagement.api_rest.business.exception.FileGenerationException;
import com.fleetmanagement.api_rest.presentation.dto.TrajectoryExportResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ExcelExporter {

	private final List<TrajectoryExportResponse> trajectoriesList;
	private final XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public ExcelExporter(List<TrajectoryExportResponse> trajectoriesList) {
		this.trajectoriesList = trajectoriesList;
		workbook = new XSSFWorkbook();
	}

	public void generateExcelFile(HttpServletResponse response) {
		writeHeader();
		write();
		try (ServletOutputStream outputStream = response.getOutputStream()) {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			throw new FileGenerationException(e.getMessage());
		}

	}

	private void writeHeader() {
		sheet = workbook.createSheet("Trajectories Info");
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		createCell(row, 0, "Taxi ID", style);
		createCell(row, 1, "Plate", style);
		createCell(row, 2, "Date", style);
		createCell(row, 3, "Latitude", style);
		createCell(row, 4, "Longitude", style);

	}

	private void write() {
		int rowCount = 1;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		for (TrajectoryExportResponse trajectory : trajectoriesList) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, trajectory.taxiId(), style);
			createCell(row, columnCount++, trajectory.plate(), style);
			createCell(row, columnCount++, trajectory.date(), style);
			createCell(row, columnCount++, trajectory.latitude(), style);
			createCell(row, columnCount, trajectory.longitude(), style);
		}
	}

	private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {

		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);

		switch (valueOfCell) {
			case Integer i -> cell.setCellValue(i);
			case Long l -> cell.setCellValue(l);
			case Double v -> cell.setCellValue(v);
			case Float v -> cell.setCellValue(v);
			case String s -> cell.setCellValue(s);
			case Boolean b -> cell.setCellValue(b);
			case null, default -> throw new IllegalArgumentException(
					"Unsupported data type. ");
		}
		cell.setCellStyle(style);
	}

}
