package dmm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import system.Dynamic;
import system.Sys;

public class DmmMain1 {
    public static void main(String[] args) throws Exception {
        String DMM_API_ID = System.getenv("DMM_API_ID");
        Sys.echo(DMM_API_ID, "DMM_API_ID");
        String DMM_AFT_ID = System.getenv("DMM_AFT_ID");
        Sys.echo(DMM_AFT_ID, "DMM_AFT_ID");
        String url = "https://api.dmm.com/affiliate/v3/ItemList";
        URI uri =
                new org.apache.hc.core5.net.URIBuilder(url)
                        .addParameter("api_id", DMM_API_ID)
                        .addParameter("affiliate_id", DMM_AFT_ID)
                        .addParameter("site", "FANZA")
                        .addParameter("service", "digital")
                        .addParameter("floor", "videoa")
                        .addParameter("hits", "" + 10)
                        .addParameter("sort", "date")
                        .addParameter("output", "json")
                        .addParameter("keyword", "上原亜衣")
                        .build();
        Sys.echo(uri.toString());
        HttpClient client =
                HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_2) // 明示的にHTTP/2を指定
                        .followRedirects(HttpClient.Redirect.NORMAL) // リダイレクトを有効化（HTTPS→HTTPを除く）
                        .build();
        // ビルダーを使用してHttpRequestインスタンスを作成
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        // リクエストを送信
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // レスポンスボディを出力
        Sys.echo(response.body(), "response.body()");
        var bodyObj = Dynamic.fromJson(response.body());
        // Sys.echo(bodyObj, "bodyObj");
        // Sys.echo(bodyObj.toJson(true), "bodyObj.toJson(true)");
        var result = bodyObj.get("result");
        // Sys.echo(result, "result");
        // Sys.echo(result.toJson(true), "result.toJson(true)");
        var items = result.get("items").asList();
        Sys.echo(items.size());
        for (int i = 0; i < items.size(); i++) {
            var item = Dynamic.wrap(items.get(i));
            item.remove("imageURL")
                    .remove("sampleImageURL")
                    .remove("sampleMovieURL")
                    .remove("prices")
                    .remove("iteminfo")
                    .remove("actress")
                    .remove("campaign");
            Sys.echo(item, "item");
        }
    }
}
