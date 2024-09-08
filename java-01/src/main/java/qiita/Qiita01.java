package qiita;

import java.io.IOException;

import com.zaneli.qiita.QiitaClient;
import com.zaneli.qiita.QiitaException;
import com.zaneli.qiita.model.request.SearchRequest;
import com.zaneli.qiita.model.response.ItemInfo;
import com.zaneli.qiita.model.response.PageableResponse;

public class Qiita01 {

	public static void main(String[] args) throws IOException, QiitaException {
		System.out.println("This is Qiita01");
		QiitaClient client = new QiitaClient(System.getenv("READ_QIITA"));
		PageableResponse<ItemInfo> searchResultItemsPage = client.searchItems(new SearchRequest("javacommons"));
		//ItemInfo[] searchResultItems = searchResultItemsPage.getContents();
		ItemInfo[] searchResultItems = searchResultItemsPage.getFirst().getContents();
	}

}
