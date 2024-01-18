package com.project.shopapp.jobs;

import com.project.shopapp.dtos.email.EmailId;
import com.project.shopapp.dtos.email.EmailRequestDto;
import com.project.shopapp.dtos.email.templates.FailureOrderReportEmailTemplate;
import com.project.shopapp.enums.OrderStatus;
import com.project.shopapp.models.Order;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScanFailureOrderJob {

    private final OrderRepository orderRepository;
    private final DateTimeFormatter UTC_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy_HH:mm'Z'");
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private final String REPORT_NAME = "failure_order_report";
    private final List<String> TITLES = Arrays.asList("S/N", "Id", "UserId");

    private final EmailService emailService;

    @Value("${email.daily-failure-order-report.subject}")
    private String subject;

    @Value("${email.daily-failure-order-report.to-address}")
    private String toAddress;

    /**
     * scan daily failure orders, generate the report, and send email
     */
    @Scheduled(cron = "${job.scan.failure.order}", zone = "Asia/Saigon")
    public void scan() {
        List<Order> failureOrders = orderRepository.findByStatus(OrderStatus.CANCELLED);
        File tmpFile = generateXlsxReport(failureOrders, REPORT_NAME);
        String attachedFileName = String.format("%s_%s.xlsx", REPORT_NAME, LocalDate.now().format(DATE_FORMATTER));
        EmailRequestDto emailRequestDto = EmailRequestDto.builder()
                .subject(subject)
                .tos(Collections.singletonList(new EmailId("", toAddress)))
                .attachmentFileName(attachedFileName)
                .file(tmpFile)
                .emailTemplate(new FailureOrderReportEmailTemplate())
                .build();
        emailService.sendEmail(emailRequestDto);
    }

    public File generateXlsxReport(List<Order> orders, String reportName) {
        log.info("Starting generate failure order report!");
        File tmpFile = null;
        try (Workbook workbook = new XSSFWorkbook()) {
            buildXlsxReport(workbook, orders);
            tmpFile = File.createTempFile(reportName + System.currentTimeMillis(), ".xlsx");
            OutputStream os = Files.newOutputStream(tmpFile.toPath());
            workbook.write(os);
            log.info("Failure order report was generated!");
        } catch (Exception e) {
            log.error("> ERROR BaseReportUtils.generateXlsxReport Something went wrong:", e);
        }
        return tmpFile;
    }

    private void buildXlsxReport(Workbook workbook, List<Order> orders) {
        Sheet sheet = buildSheet(workbook);
        int rowNumber = 0;
        rowNumber = buildTitle(workbook, sheet, TITLES, rowNumber);
        buildReportRows(orders, sheet, rowNumber);
    }

    private void buildReportRows(List<Order> orders, Sheet sheet, int rowNumber) {
        // create each row and its cells
        for (Order order : orders) {
            Row row = sheet.createRow(rowNumber);

            Cell serialNumberCell = row.createCell(0);
            serialNumberCell.setCellValue(rowNumber - 1);

            Cell idCell = row.createCell(1);
            idCell.setCellValue(order.getId());

            Cell userIdCell = row.createCell(2);
            userIdCell.setCellValue(order.getUser().getId());

            // update row number to create the next row
            rowNumber++;
        }
    }

    private int buildTitle(Workbook workbook, Sheet sheet, List<String> titles, int rowNumber) {
        Row titleRow = sheet.createRow(rowNumber);
        for (int i = 0; i < titles.size(); i++) {
            Cell titleCell = titleRow.createCell(i);
            titleCell.setCellValue(titles.get(i));
            setBoldText(workbook, titleCell);
        }
        rowNumber++;
        return rowNumber;
    }

    private void setBoldText(Workbook workbook, Cell cell) {
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setFontName("Calibri");
        font.setColor(IndexedColors.BLACK.getIndex());
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        cell.setCellStyle(style);
    }

    private Sheet buildSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Failure Orders");
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(0, 40 * 256);
        return sheet;
    }

    private static void writeOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (workbook) {
            OutputStream os = new FileOutputStream(excelFilePath);
            workbook.write(os);
        } catch (Exception e) {
            log.error("Something went wrong during writing report");
        }
    }

    public void saveXlsxReport(List<Order> orders, String directory, String reportName) {
        log.info("Starting generate failure order report!");
        try (Workbook workbook = new XSSFWorkbook()) {
            buildXlsxReport(workbook, orders);
            String reportPath = directory + reportName + System.currentTimeMillis() + ".xlsx";
            OutputStream os = Files.newOutputStream(Path.of(reportPath));
            workbook.write(os);
            log.info("Failure order report was saved!");
        } catch (Exception e) {
            log.error("> ERROR BaseReportUtils.saveXlsxReport Something went wrong:", e);
        }
    }


}
