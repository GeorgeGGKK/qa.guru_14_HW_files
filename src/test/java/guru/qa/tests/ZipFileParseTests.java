package guru.qa.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipFileParseTests {
    String zipFileName = "ziptest.zip";
    String pdfFileName = "index.pdf";
    String csvFileName = "testcsv.csv";
    String xlsFileName = "xls.xlsx";
    ClassLoader classLoader = ZipFileParseTests.class.getClassLoader();

    @DisplayName("Чтение pdf файла из zip архива")
    @Test
    public void readingPdfFileFromZipArchive() throws Exception {
        InputStream is = classLoader.getResourceAsStream(zipFileName);
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zf = new ZipFile(new File("src/test/resources/" + zipFileName));
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().equals(pdfFileName)) {
                try (InputStream stream = zf.getInputStream(entry)) {
                    PDF pdf = new PDF(stream);
                    assertThat(pdf.text).contains("What is JUnit 5?");
                    assertThat(pdf.numberOfPages).isEqualTo(145);
                }
            }
        }

    }

    @DisplayName("Чтение xls файла из zip архива")
    @Test
    public void readingXlsFileFromZipArchive() throws Exception {
        InputStream is = classLoader.getResourceAsStream(zipFileName);
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zf = new ZipFile(new File("src/test/resources/" + zipFileName));
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().equals(xlsFileName)) {
                try (InputStream stream = zf.getInputStream(entry)) {
                    XLS xls = new XLS(stream);
                    assertThat(xls.excel.getSheetAt(0).getRow(2).getCell(4).getStringCellValue()).isEqualTo("СПБ Корона");
                    assertThat(xls.excel.getSheetAt(0).getRow(8).getCell(4).getStringCellValue()).isEqualTo("октябрь");
                    assertThat(xls.excel.getSheetAt(0).getRow(12).getCell(8).getNumericCellValue()).isEqualTo(94);
                }
            }
        }

    }

    @DisplayName("Чтение csv файла из zip архива")
    @Test
    public void readingCsvFileFromZipArchive() throws Exception {
        InputStream is = classLoader.getResourceAsStream(zipFileName);
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zf = new ZipFile(new File("src/test/resources/" + zipFileName));
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().equals(csvFileName)) {
                try (InputStream stream = zf.getInputStream(entry);
                     CSVReader csvReader = new CSVReader(new InputStreamReader(stream, UTF_8))) {
                    List<String[]> csv = csvReader.readAll();
                    assertThat(csv).contains(
                            new String[]{"iphon4", " 32gb", " black"},
                            new String[]{"iphon5s", " 64gb", " black"},
                            new String[]{"iphon11", " 256gb", " green"}

                    );
                }
            }
        }

    }
}
