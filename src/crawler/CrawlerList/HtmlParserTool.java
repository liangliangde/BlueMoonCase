package crawler.CrawlerList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParserTool {
	// 获取�?��网站上的链接,filter 用来过滤链接
	public static int extracLinks(String url, FileOutputStream outStream, FileOutputStream errorURL, FileOutputStream log)
			throws IOException {

		try {
			URL oneurl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) oneurl.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			connection.setRequestProperty("Cookie", "foo=bar");
			Parser parser = new Parser(connection);
			parser.setEncoding("utf-8");

			NodeFilter zoneFilter = new AndFilter(new TagNameFilter("li"), new HasAttributeFilter("class", ""));
			NodeList list = parser.extractAllNodesThatMatch(zoneFilter);
			System.out.println(list.size()+"rescords;");
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				String con = tag.toHtml();
				String res[] = process(con.replaceAll(" ", "").replaceAll("\n", ""));
				for (int a = 0; a < res.length; a++) {
					byte[] inOutb = res[a].getBytes();
					outStream.write(inOutb);
					outStream.write("###".getBytes());
				}
				outStream.write("\n".getBytes());
			}
			connection.disconnect();
			log.write((url + "\n").getBytes());
			CrawlerShoppingList.errorstep = 0;
			return list.size();
		} catch (ParserException e) {
			e.printStackTrace();
			errorURL.write((url + "\n").getBytes());
			CrawlerShoppingList.errorstep++;
			if(CrawlerShoppingList.errorstep >= 5)
				System.exit(0);//��������URL��ʧ��ʱ����������
			return -1;
		}
	}

	private static String[] process(String str) {
		// TODO Auto-generated method stub
		String res[] = new String[8];

		int index_type = str.indexOf("class=\"tag\"")+12;
		res[0]=str.substring(index_type, str.indexOf("<", index_type));

		int index_region = str.lastIndexOf("class=\"tag\"")+12;
		res[1]=str.substring(index_region, str.indexOf("<", index_region));

		int index_shopid = str.indexOf("cl_to_s") + 9;
		res[2]=str.substring(index_shopid, str.indexOf(")", index_shopid));

		int index_shopname = str.indexOf("<h4>") + 4;
		res[3]=str.substring(index_shopname, str.indexOf("<", index_shopname));

		int index_rankstar = str.indexOf("sml-str") + 17;
		res[4] = str.substring(index_rankstar, str.indexOf("\"", index_rankstar + 1));
		if(res[4].equals("�̻������Ǽ�"))
			res[4] = "null";
		//		System.out.println("�Ǽ�:"+res[4]);

		int index_commentnum = str.indexOf("highlight") + 14;
		if(index_commentnum!=13)
			res[5] = str.substring(index_commentnum, str.indexOf("<", index_commentnum + 1));
		else res[5] = "0";
		//		System.out.println("������:"+res[5]);

		int index_avgcost = str.indexOf("��") + 1;
		if(index_avgcost!=0){
			res[6] = str.substring(index_avgcost, str.indexOf("<", index_avgcost));
		}
		else{
			res[6] = "null";
		}
		//		System.out.println("�˾�:"+res[6]);

		int index_location = str.indexOf("class=\"addr\"") + 13;
		if(str.charAt(index_location)=='<')
			res[7] = "null";
		else
			res[7] = str.substring(index_location, str.indexOf("<", index_location + 1));
		//		System.out.println("��ַ:"+res[7]);
		return res;
	}
}
