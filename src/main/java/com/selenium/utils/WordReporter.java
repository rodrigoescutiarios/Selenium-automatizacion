package com.selenium.utils;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WordReporter {
    private XWPFDocument document;
    private String testName;
    private String status;
    private long startTime;
    private long endTime;
    private List<TestStep> testSteps;
    private static WordReporter instance;

    private WordReporter() {
        this.testSteps = new ArrayList<>();
    }

    public static WordReporter getInstance() {
        if (instance == null) {
            instance = new WordReporter();
        }
        return instance;
    }

    public void startTest(String testName) {
        try {
            this.document = new XWPFDocument();
            this.testName = testName;
            this.startTime = System.currentTimeMillis();
            this.testSteps.clear();
            this.status = "PASSED";

            // Configurar márgenes del documento
            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar = sectPr.addNewPgMar();
            pageMar.setTop(BigInteger.valueOf(1440));
            pageMar.setRight(BigInteger.valueOf(1440));
            pageMar.setBottom(BigInteger.valueOf(1440));
            pageMar.setLeft(BigInteger.valueOf(1440));

        } catch (Exception e) {
            System.err.println("Error iniciando el documento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addStep(String description, String screenshotPath, boolean passed) {
        TestStep step = new TestStep(description, screenshotPath, passed);
        testSteps.add(step);
        if (!passed) {
            this.status = "FAILED";
        }
    }

    public void endTest() {
        this.endTime = System.currentTimeMillis();
        generateReport();
    }

    private void generateReport() {
        try {
            if (document == null) {
                System.err.println("El documento no fue inicializado correctamente");
                return;
            }

            // Título principal centrado con color azul
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            title.setSpacingAfter(300);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("INFORME DE EJECUCIÓN DE PRUEBAS");
            titleRun.setBold(true);
            titleRun.setFontSize(18);
            titleRun.setColor("1E3A8A");
            titleRun.setFontFamily("Calibri");

            // Subtítulo con el nombre del caso de prueba
            XWPFParagraph subtitle = document.createParagraph();
            subtitle.setAlignment(ParagraphAlignment.CENTER);
            subtitle.setSpacingAfter(400);
            XWPFRun subtitleRun = subtitle.createRun();
            subtitleRun.setText("Caso de Prueba: " + testName);
            subtitleRun.setFontSize(14);
            subtitleRun.setColor("1E3A8A");
            subtitleRun.setFontFamily("Calibri");

            // Crear tabla de información general con diseño específico
            XWPFTable infoTable = document.createTable(3, 4);
            infoTable.setWidth("100%");

            // Configurar estilos de la tabla
            CTTblPr tblPr = infoTable.getCTTbl().getTblPr();
            if (tblPr == null) {
                tblPr = infoTable.getCTTbl().addNewTblPr();
            }
            CTTblBorders borders = tblPr.addNewTblBorders();
            borders.addNewTop().setVal(STBorder.SINGLE);
            borders.addNewBottom().setVal(STBorder.SINGLE);
            borders.addNewLeft().setVal(STBorder.SINGLE);
            borders.addNewRight().setVal(STBorder.SINGLE);
            borders.addNewInsideH().setVal(STBorder.SINGLE);
            borders.addNewInsideV().setVal(STBorder.SINGLE);

            // Primera fila
            setTableCell(infoTable.getRow(0).getCell(0), "Nombre", true, "1E3A8A", "FFFFFF");
            setTableCell(infoTable.getRow(0).getCell(1), testName, false, null, "000000");
            setTableCell(infoTable.getRow(0).getCell(2), "Estado", true, "1E3A8A", "FFFFFF");
            String statusColor = status.equals("PASSED") ? "10B981" : "EF4444";
            String statusBg = status.equals("PASSED") ? "E6F7F1" : "FFE4E4";
            setTableCell(infoTable.getRow(0).getCell(3), status, true, statusBg, statusColor);

            // Segunda fila
            setTableCell(infoTable.getRow(1).getCell(0), "Duración", true, "1E3A8A", "FFFFFF");
            long duration = (endTime - startTime) / 1000;
            setTableCell(infoTable.getRow(1).getCell(1), duration + " segundos", false, null, "000000");
            setTableCell(infoTable.getRow(1).getCell(2), "Navegador", true, "1E3A8A", "FFFFFF");
            setTableCell(infoTable.getRow(1).getCell(3), "Chrome", false, null, "000000");

            // Tercera fila
            setTableCell(infoTable.getRow(2).getCell(0), "Fecha y Hora de Ejecución", true, "1E3A8A", "FFFFFF");
            // Combinar las celdas para la fecha
            XWPFTableCell dateCell = infoTable.getRow(2).getCell(1);
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            setTableCell(dateCell, timestamp, false, null, "000000");
            // Hacer span de las columnas
            CTTcPr tcPr = dateCell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = dateCell.getCTTc().addNewTcPr();
            }
            CTDecimalNumber gridSpan = tcPr.addNewGridSpan();
            gridSpan.setVal(BigInteger.valueOf(3));
            // Eliminar las celdas extra
            infoTable.getRow(2).removeCell(3);
            infoTable.getRow(2).removeCell(2);

            document.createParagraph().createRun().addBreak();

            // Título de PASOS EJECUTADOS centrado
            XWPFParagraph stepsTitle = document.createParagraph();
            stepsTitle.setAlignment(ParagraphAlignment.CENTER);
            stepsTitle.setSpacingBefore(400);
            stepsTitle.setSpacingAfter(400);
            XWPFRun stepsTitleRun = stepsTitle.createRun();
            stepsTitleRun.setText("PASOS EJECUTADOS");
            stepsTitleRun.setBold(true);
            stepsTitleRun.setFontSize(16);
            stepsTitleRun.setColor("1E3A8A");
            stepsTitleRun.setFontFamily("Calibri");

            // Agregar cada paso con su tabla individual
            int stepNumber = 1;
            for (TestStep step : testSteps) {
                // Crear tabla para el paso con 2 filas: título y evidencia
                XWPFTable stepTable = document.createTable(2, 1);
                stepTable.setWidth("100%");

                // Configurar bordes de la tabla del paso
                CTTblPr stepTblPr = stepTable.getCTTbl().getTblPr();
                if (stepTblPr == null) {
                    stepTblPr = stepTable.getCTTbl().addNewTblPr();
                }
                CTTblBorders stepBorders = stepTblPr.addNewTblBorders();
                stepBorders.addNewTop().setVal(STBorder.SINGLE);
                stepBorders.addNewBottom().setVal(STBorder.SINGLE);
                stepBorders.addNewLeft().setVal(STBorder.SINGLE);
                stepBorders.addNewRight().setVal(STBorder.SINGLE);

                // Fila del título del paso con fondo azul
                XWPFTableCell titleCell = stepTable.getRow(0).getCell(0);
                titleCell.setColor("1E3A8A");
                XWPFParagraph stepTitlePara = titleCell.getParagraphs().get(0);
                stepTitlePara.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun stepTitleRun = stepTitlePara.createRun();
                stepTitleRun.setText("PASO " + stepNumber + ": " + step.description.toUpperCase());
                stepTitleRun.setBold(true);
                stepTitleRun.setColor("FFFFFF");
                stepTitleRun.setFontSize(12);
                stepTitleRun.addTab();
                stepTitleRun.setText(step.passed ? " ✓" : " ✗");

                // Fila de la evidencia (screenshot)
                XWPFTableCell evidenceCell = stepTable.getRow(1).getCell(0);
                XWPFParagraph evidencePara = evidenceCell.getParagraphs().get(0);
                evidencePara.setAlignment(ParagraphAlignment.CENTER);

                if (step.screenshotPath != null && new File(step.screenshotPath).exists()) {
                    try {
                        FileInputStream fis = new FileInputStream(step.screenshotPath);
                        XWPFRun imageRun = evidencePara.createRun();
                        imageRun.addPicture(fis, XWPFDocument.PICTURE_TYPE_PNG,
                                step.screenshotPath, Units.toEMU(500), Units.toEMU(300));
                        fis.close();
                    } catch (Exception e) {
                        XWPFRun noImageRun = evidencePara.createRun();
                        noImageRun.setText("Sin evidencia");
                        noImageRun.setColor("999999");
                    }
                } else {
                    XWPFRun noImageRun = evidencePara.createRun();
                    noImageRun.setText("Sin evidencia");
                    noImageRun.setColor("999999");
                }

                stepNumber++;

                // Agregar espacio entre pasos
                document.createParagraph().setSpacingAfter(200);
            }

            // Guardar el documento
            String reportName = "test-reports/Reporte_" + testName.replaceAll("[^a-zA-Z0-9]", "_")
                    + "_" + System.currentTimeMillis() + ".docx";

            File reportDir = new File("test-reports");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            FileOutputStream out = new FileOutputStream(reportName);
            document.write(out);
            out.close();
            document.close();

            System.out.println("Reporte generado: " + reportName);

        } catch (Exception e) {
            System.err.println("Error generando reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setTableCell(XWPFTableCell cell, String text, boolean bold, String bgColor, String textColor) {
        // Limpiar el párrafo existente
        for (int i = cell.getParagraphs().size() - 1; i >= 0; i--) {
            cell.removeParagraph(i);
        }

        XWPFParagraph para = cell.addParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = para.createRun();
        run.setText(text);
        run.setBold(bold);
        run.setFontFamily("Calibri");
        run.setFontSize(11);

        if (textColor != null) {
            run.setColor(textColor);
        }

        if (bgColor != null) {
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }
            CTShd shd = tcPr.addNewShd();
            shd.setFill(bgColor);
        }
    }

    // Clase interna para representar un paso
    private static class TestStep {
        String description;
        String screenshotPath;
        boolean passed;

        TestStep(String description, String screenshotPath, boolean passed) {
            this.description = description;
            this.screenshotPath = screenshotPath;
            this.passed = passed;
        }
    }
}