package guru.qa.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.domain.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonParseTest {
    ClassLoader classLoader = JsonParseTest.class.getClassLoader();

    @DisplayName("Чтение и проверка содержимого json файла")
    @Test
    public void parseJson() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("123.json")) {
            ObjectMapper mapper = new ObjectMapper();
            Rule rule = mapper.readValue(is, Rule.class);
            assertThat(rule.getSplitCode()).isEqualTo("SOP");
            assertThat(rule.getProductCode()).isEqualTo(22);
            assertThat(rule.getBranchCode()).isEqualTo(452);
        }
    }
}
