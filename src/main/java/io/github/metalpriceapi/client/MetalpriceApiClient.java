package io.github.metalpriceapi.client;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class MetalpriceApiClient {

    private static final Map<String, String> SERVERS = Map.of(
        "us", "https://api.metalpriceapi.com/v1",
        "eu", "https://api-eu.metalpriceapi.com/v1"
    );

    private final String apiKey;
    private final HttpClient httpClient;
    private String baseUrl;

    public MetalpriceApiClient(String apiKey) {
        this(apiKey, "us");
    }

    public MetalpriceApiClient(String apiKey, String server) {
        this.apiKey = apiKey;
        this.baseUrl = SERVERS.getOrDefault(server, SERVERS.get("us"));
        this.httpClient = HttpClient.newHttpClient();
    }

    public void setServer(String server) {
        this.baseUrl = SERVERS.getOrDefault(server, SERVERS.get("us"));
    }

    public JSONObject fetchSymbols() throws IOException, InterruptedException {
        return get("/symbols", Map.of());
    }

    public JSONObject fetchLive() throws IOException, InterruptedException {
        return fetchLive("", null, "", "", "");
    }

    public JSONObject fetchLive(String base, List<String> currencies, String unit, String purity, String math)
            throws IOException, InterruptedException {
        Map<String, String> params = removeEmpty(new LinkedHashMap<>() {{
            put("base", base);
            put("currencies", currencies != null ? String.join(",", currencies) : "");
            put("unit", unit);
            put("purity", purity);
            put("math", math);
        }});
        return get("/latest", params);
    }

    public JSONObject fetchHistorical(String date) throws IOException, InterruptedException {
        return fetchHistorical(date, "", null, "");
    }

    public JSONObject fetchHistorical(String date, String base, List<String> currencies, String unit)
            throws IOException, InterruptedException {
        Map<String, String> params = removeEmpty(new LinkedHashMap<>() {{
            put("base", base);
            put("currencies", currencies != null ? String.join(",", currencies) : "");
            put("unit", unit);
        }});
        return get("/" + date, params);
    }

    public JSONObject hourly(String base, String currency, String unit, String startDate, String endDate,
                             String math, String dateType) throws IOException, InterruptedException {
        Map<String, String> params = removeEmpty(new LinkedHashMap<>() {{
            put("base", base);
            put("currency", currency);
            put("unit", unit);
            put("start_date", startDate);
            put("end_date", endDate);
            put("math", math);
            put("date_type", dateType);
        }});
        return get("/hourly", params);
    }

    public JSONObject fetchOHLC(String base, String currency, String date, String unit, String dateType)
            throws IOException, InterruptedException {
        Map<String, String> params = removeEmpty(new LinkedHashMap<>() {{
            put("base", base);
            put("currency", currency);
            put("date", date);
            put("unit", unit);
            put("date_type", dateType);
        }});
        return get("/ohlc", params);
    }

    public JSONObject convert(String fromCurrency, String toCurrency, Object amount, String date, String unit)
            throws IOException, InterruptedException {
        Map<String, String> params = removeEmpty(new LinkedHashMap<>() {{
            put("from", fromCurrency);
            put("to", toCurrency);
            put("amount", amount != null ? amount.toString() : "");
            put("date", date);
            put("unit", unit);
        }});
        return get("/convert", params);
    }

    public JSONObject timeframe(String startDate, String endDate, String base, List<String> currencies, String unit)
            throws IOException, InterruptedException {
        Map<String, String> params = removeEmpty(new LinkedHashMap<>() {{
            put("start_date", startDate);
            put("end_date", endDate);
            put("base", base);
            put("currencies", currencies != null ? String.join(",", currencies) : "");
            put("unit", unit);
        }});
        return get("/timeframe", params);
    }

    public JSONObject change(String startDate, String endDate, String base, List<String> currencies, String dateType)
            throws IOException, InterruptedException {
        Map<String, String> params = removeEmpty(new LinkedHashMap<>() {{
            put("start_date", startDate);
            put("end_date", endDate);
            put("base", base);
            put("currencies", currencies != null ? String.join(",", currencies) : "");
            put("date_type", dateType);
        }});
        return get("/change", params);
    }

    public JSONObject carat() throws IOException, InterruptedException {
        return carat("", "", "");
    }

    public JSONObject carat(String base, String currency, String date) throws IOException, InterruptedException {
        Map<String, String> params = removeEmpty(new LinkedHashMap<>() {{
            put("base", base);
            put("currency", currency);
            put("date", date);
        }});
        return get("/carat", params);
    }

    public JSONObject usage() throws IOException, InterruptedException {
        return get("/usage", Map.of());
    }

    private Map<String, String> removeEmpty(Map<String, String> params) {
        return params.entrySet().stream()
                .filter(e -> e.getValue() != null && !e.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    private JSONObject get(String endpoint, Map<String, String> params) throws IOException, InterruptedException {
        Map<String, String> allParams = new LinkedHashMap<>(params);
        allParams.put("api_key", apiKey);

        String query = allParams.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint + "?" + query))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }
}
