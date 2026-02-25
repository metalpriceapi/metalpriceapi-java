# metalpriceapi

metalpriceapi is the official Java wrapper for MetalpriceAPI.com. This allows you to quickly integrate our metal price API and foreign exchange rate API into your application. Check https://metalpriceapi.com documentation for more information.

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.metalpriceapi</groupId>
    <artifactId>metalpriceapi</artifactId>
    <version>1.3.0</version>
</dependency>
```

### Gradle

Add the following to your `build.gradle`:

```groovy
implementation 'com.metalpriceapi:metalpriceapi:1.3.0'
```

## Usage

```java
import com.metalpriceapi.client.MetalpriceApiClient;

String apiKey = "SET_YOUR_API_KEY_HERE";
MetalpriceApiClient client = new MetalpriceApiClient(apiKey);

// Or use EU server:
// MetalpriceApiClient client = new MetalpriceApiClient(apiKey, "eu");
```
---
## Server Regions

MetalpriceAPI provides two regional endpoints. Choose the one closest to your servers for optimal performance.

| Region | Base URL |
|--------|----------|
| United States (default) | `https://api.metalpriceapi.com/v1` |
| Europe | `https://api-eu.metalpriceapi.com/v1` |

```java
import com.metalpriceapi.client.MetalpriceApiClient;

// Default (US)
MetalpriceApiClient client = new MetalpriceApiClient("SET_YOUR_API_KEY_HERE");

// Europe
MetalpriceApiClient client = new MetalpriceApiClient("SET_YOUR_API_KEY_HERE", "eu");
```

---
## Documentation

#### fetchSymbols()
```java
client.fetchSymbols();
```

[Link](https://metalpriceapi.com/documentation#api_symbol)

---
#### setServer(server)

- `server` <[string]> Pass `"eu"` to use the EU server (`api-eu.metalpriceapi.com`), or `"us"` for the US server. Defaults to US if not specified.

```java
client.setServer("eu");
```

---
#### fetchLive(base, currencies, unit, purity, math)

- `base` <[string]> Optional. Pass in a base currency, defaults to USD.
- `currencies` <[List]<[string]>> Optional. Pass in a list of currencies to return values for.
- `unit` <[string]> Optional. Pass in a unit for metal prices (e.g. `troy_oz`, `gram`, `kilogram`).
- `purity` <[string]> Optional. Pass in a purity level for metal prices.
- `math` <[string]> Optional. Pass in a math expression to apply to the rates.

```java
client.fetchLive("USD", List.of("XAU", "XAG", "XPD", "XPT"), "troy_oz", "", "");
```

[Link](https://metalpriceapi.com/documentation#api_realtime)

---
#### fetchHistorical(date, base, currencies, unit)

- `date` <[string]> Required. Pass in a string with format `YYYY-MM-DD`
- `base` <[string]> Optional. Pass in a base currency, defaults to USD.
- `currencies` <[List]<[string]>> Optional. Pass in a list of currencies to return values for.
- `unit` <[string]> Optional. Pass in a unit for metal prices (e.g. `troy_oz`, `gram`, `kilogram`).

```java
client.fetchHistorical("2024-02-05", "USD", List.of("XAU", "XAG", "XPD", "XPT"), "troy_oz");
```

[Link](https://metalpriceapi.com/documentation#api_historical)

---
#### hourly(base, currency, unit, startDate, endDate, math, dateType)

- `base` <[string]> Optional. Pass in a base currency, defaults to USD.
- `currency` <[string]> Required. Specify currency you would like to get hourly rates for.
- `unit` <[string]> Optional. Pass in a unit for metal prices (e.g. `troy_oz`, `gram`, `kilogram`).
- `startDate` <[string]> Required. Specify the start date using the format `YYYY-MM-DD`.
- `endDate` <[string]> Required. Specify the end date using the format `YYYY-MM-DD`.
- `math` <[string]> Optional. Pass in a math expression to apply to the rates.
- `dateType` <[string]> Optional. Pass in a date type, overrides date parameters if passed in.

```java
client.hourly("USD", "XAU", "troy_oz", "2025-11-03", "2025-11-03", "", "");
```

[Link](https://metalpriceapi.com/documentation#api_hourly)

---
#### fetchOHLC(base, currency, date, unit, dateType)

- `base` <[string]> Optional. Pass in a base currency, defaults to USD.
- `currency` <[string]> Required. Specify currency you would like to get OHLC for.
- `date` <[string]> Required. Specify date to get OHLC for specific date using format `YYYY-MM-DD`.
- `unit` <[string]> Optional. Pass in a unit, defaults to troy_oz.
- `dateType` <[string]> Optional. Pass in a date type, overrides date parameter if passed in.

```java
client.fetchOHLC("USD", "XAU", "2024-02-05", "troy_oz", "");
```

[Link](https://metalpriceapi.com/documentation#api_ohlc)

---
#### convert(fromCurrency, toCurrency, amount, date, unit)

- `fromCurrency` <[string]> Optional. Pass in a base currency, defaults to USD.
- `toCurrency` <[string]> Required. Specify currency you would like to convert to.
- `amount` <[number]> Required. The amount to convert.
- `date` <[string]> Optional. Specify date to use historical midpoint value for conversion with format `YYYY-MM-DD`. Otherwise, it will use live exchange rate date if value not passed in.
- `unit` <[string]> Optional. Pass in a unit for metal prices (e.g. `troy_oz`, `gram`, `kilogram`).

```java
client.convert("USD", "EUR", 100, "2024-02-05", "");
```

[Link](https://metalpriceapi.com/documentation#api_convert)

---
#### timeframe(startDate, endDate, base, currencies, unit)

- `startDate` <[string]> Required. Specify the start date of your timeframe using the format `YYYY-MM-DD`.
- `endDate` <[string]> Required. Specify the end date of your timeframe using the format `YYYY-MM-DD`.
- `base` <[string]> Optional. Pass in a base currency, defaults to USD.
- `currencies` <[List]<[string]>> Optional. Pass in a list of currencies to return values for.
- `unit` <[string]> Optional. Pass in a unit for metal prices (e.g. `troy_oz`, `gram`, `kilogram`).

```java
client.timeframe("2024-02-05", "2024-02-06", "USD", List.of("XAU", "XAG", "XPD", "XPT"), "troy_oz");
```

[Link](https://metalpriceapi.com/documentation#api_timeframe)

---
#### change(startDate, endDate, base, currencies, dateType)

- `startDate` <[string]> Required. Specify the start date of your timeframe using the format `YYYY-MM-DD`.
- `endDate` <[string]> Required. Specify the end date of your timeframe using the format `YYYY-MM-DD`.
- `base` <[string]> Optional. Pass in a base currency, defaults to USD.
- `currencies` <[List]<[string]>> Optional. Pass in a list of currencies to return values for.
- `dateType` <[string]> Optional. Pass in a date type, overrides date parameters if passed in.

```java
client.change("2024-02-05", "2024-02-06", "USD", List.of("XAU", "XAG", "XPD", "XPT"), "");
```

[Link](https://metalpriceapi.com/documentation#api_change)

---
#### carat(base, currency, date)

- `base` <[string]> Optional. Pass in a base currency, defaults to USD.
- `currency` <[string]> Optional. Pass in a metal code to get carat prices for (defaults to XAU).
- `date` <[string]> Optional. Specify date to get Carat for specific date using format `YYYY-MM-DD`. If not specified, uses live rates.

```java
client.carat("USD", "XAU", "2024-02-05");
```

[Link](https://metalpriceapi.com/documentation#api_carat)

---
#### usage()
```java
client.usage();
```

[Link](https://metalpriceapi.com/documentation#api_usage)

---
**[Official documentation](https://metalpriceapi.com/documentation)**

---
## FAQ

- How do I get an API Key?

    Free API Keys are available [here](https://metalpriceapi.com).

- I want more information

    Checkout our FAQs [here](https://metalpriceapi.com/faq).


## Support

For support, get in touch using [this form](https://metalpriceapi.com/contact).


[List]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/List.html 'List'
[number]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Number.html 'Number'
[string]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html 'String'
