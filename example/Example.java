import io.github.metalpriceapi.client.MetalpriceApiClient;
import org.json.JSONObject;

import java.util.List;

public class Example {
    public static void main(String[] args) throws Exception {
        String apiKey = "REPLACE_ME";
        MetalpriceApiClient client = new MetalpriceApiClient(apiKey);

        // Or use EU server:
        // MetalpriceApiClient client = new MetalpriceApiClient(apiKey, "eu");

        JSONObject result;

        result = client.fetchSymbols();
        System.out.println(result);

        result = client.fetchLive("USD", List.of("XAU", "XAG", "XPD", "XPT"), "troy_oz", "", "");
        System.out.println(result);

        result = client.fetchHistorical("2024-02-05", "USD", List.of("XAU", "XAG", "XPD", "XPT"), "troy_oz");
        System.out.println(result);

        result = client.hourly("USD", "XAU", "troy_oz", "2025-11-03", "2025-11-03", "", "");
        System.out.println(result);

        result = client.fetchOHLC("USD", "XAU", "2024-02-06", "troy_oz", "");
        System.out.println(result);

        result = client.convert("USD", "EUR", 100, "2024-02-05", "");
        System.out.println(result);

        result = client.timeframe("2024-02-05", "2024-02-06", "USD", List.of("XAU", "XAG", "XPD", "XPT"), "troy_oz");
        System.out.println(result);

        result = client.change("2024-02-05", "2024-02-06", "USD", List.of("XAU", "XAG", "XPD", "XPT"), "");
        System.out.println(result);

        result = client.carat("USD", "XAU", "2024-02-06");
        System.out.println(result);

        result = client.usage();
        System.out.println(result);
    }
}
