package httptest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * HttpTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-10-21 : base version.
 */
public class HttpTest {
  public static final String ROOT_URI = "/tdb-union/cloud/tdb/tdbp/quota/";
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String[] urls =
      new String[] {
        "/principal_mapper/18401112616627314668",
        "/ingredient_mapper/18401112616627314668",
        "/company_mapper/18401112616627314668",
        "/shareholder_mapper/18401112616627314668",
        "/market_mapper/18401112616627314668",
        "/split_market_mapper/18401112616627314668",
        "/ipo_mapper/18401112616627314668",
        "/ipo_process_mapper/18401112616627314668",
        "/ipo_valuation_mapper/18401112616627314668",
        "/ipo_rival_mapper/18401112616627314668",
        "/ipo_invest_mapper/18401112616627314668",
        "/ipo_intermediary_mapper/18401112616627314668",
        "/ipo_issue_mapper/18401112616627314668",
        "/ipo_cost_mapper/18401112616627314668",
        "/ipo_strategy_mapper/18401112616627314668",
        "/ipo_business_mapper/18401112616627314668",
        "/ipo_customer_mapper/18401112616627314668",
        "/ipo_supplier_mapper/18401112616627314668",
        "/ipo_technology_mapper/18401112616627314668",
        "/fin_mapper/18401112616627314668",
        "/fin_quota_mapper/18401112616627314668",
        "/fin_data_mapper/18401112616627314668"
      };

  public static void main(String[] args) throws IOException, URISyntaxException {
    //    page();
    try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
      for (String url : urls) {
        final URI build =
            new URIBuilder()
                .setScheme("http")
                .setHost("127.0.0.1:9002")
                .setPath(ROOT_URI + url)
                .build();
        final HttpGet get = new HttpGet(build);
        final CloseableHttpResponse httpResponse = httpclient.execute(get);
        final HttpEntity httpResponseEntity = httpResponse.getEntity();
        final String s = EntityUtils.toString(httpResponseEntity);
        System.out.println(s);
      }
    }
  }

  static void page() throws IOException {
    try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
      final URI uri =
          new URIBuilder()
              .setScheme("http")
              .setHost("127.0.0.1:9002")
              .setPath(ROOT_URI + "cursor_mapper_page")
              .setParameter("page", "1")
              .setParameter("size", "10")
              .build();
      System.out.println(uri.toString());
      final HttpGet httpget = new HttpGet(uri);
      final CloseableHttpResponse response = httpclient.execute(httpget);
      final HttpEntity responseEntity = response.getEntity();
      final String toString = EntityUtils.toString(responseEntity);
      System.out.println(toString);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }
}
