package crawler.CrawlerInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParserTool {
	// 获取�?��网站上的链接,filter 用来过滤链接
	public static void extracLinks(String url, FileOutputStream outStream, FileOutputStream errorURL, FileOutputStream log, FileOutputStream oldpage)
			throws IOException {

		try {
			URL oneurl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) oneurl.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			connection.setRequestProperty("Cookie", "foo=bar");
			Parser parser = new Parser(connection);
			parser.setEncoding("utf-8");

			//��ȡ��ҳ��
			NodeFilter zoneFilter_newpage = new AndFilter(new TagNameFilter("div"),
					new OrFilter(new HasAttributeFilter("class", "breadcrumb"),
							new HasAttributeFilter("class", "basic-info default nug_shop_ab_pv-a")));
			NodeList list_newpage = parser.extractAllNodesThatMatch(zoneFilter_newpage);
			if(list_newpage.size()==2) {
				for (int i = 0; i < list_newpage.size(); i++) {
					Node tag = list_newpage.elementAt(i);
					Div div = (Div) tag;
					String con = div.getStringText();
					if (i == 0) {
						if(con.indexOf("�Ϻ�����") == -1){
							System.out.println(", Not shopping");
							return;
						}
						else
							outStream.write((url.substring(url.indexOf("shop/")+5) + "###").getBytes());
						String res[] = process1(con);
						for (int a = 0; a < res.length; a++) {
							byte[] inOutb = res[a].getBytes();
							outStream.write(inOutb);
							outStream.write("###".getBytes());
						}
					}
					if (i == 1) {
						String res[] = process2(con);
						for (int a = 0; a < res.length; a++) {
							byte[] inOutb = res[a].getBytes();
							outStream.write(inOutb);
							outStream.write("###".getBytes());
						}
						outStream.write("\n".getBytes());
						System.out.println("Yes");
					}
				}
			}
			else{
				System.out.println("old page!");
				oldpage.write((url+"\n").getBytes());
				return;
			}
			connection.disconnect();
			log.write((url + "\n").getBytes());
			CrawlerShoppingInfo.errorstep= 0;
		} catch (ParserException e) {
			e.printStackTrace();
			errorURL.write((url + "\n").getBytes());
			CrawlerShoppingInfo.errorstep++;
			if(CrawlerShoppingInfo.errorstep >= 5){
				System.out.println("����������¼����ϵͳֹͣ��");
				System.exit(0);
			}
		}
	}

	private static String[] process1(String str) {
		// TODO Auto-generated method stub
		int lastIndex = 0, index, k = 0;
		String res[] = new String[5];
		for (int i = 0; i < 4; i++) {
			index = str.indexOf("url", lastIndex);
			res[k++] = str.substring(index + 5, str.indexOf('<', index)).trim();
			lastIndex = index + 1;
		}
		int index_shopname = str.indexOf("<span>",lastIndex);
		res[4] = str.substring(index_shopname+6,str.indexOf("<",index_shopname+6));
		return res;
	}

	private static String[] process2(String str) {
		// TODO Auto-generated method stub
		String res[] = new String[8];

		int index_rankstar = str.indexOf("<span title") + 13;
		res[0] = str.substring(index_rankstar, str.indexOf("\"", index_rankstar + 1));
		if(res[0].equals("���̻������Ǽ�"))
			res[0] = "null";
//		System.out.println("�Ǽ�:"+res[0]);

		int index_commentnum = str.indexOf("item") + 6;
		try{
			res[1] = str.substring(index_commentnum, str.indexOf("������", index_commentnum + 1));
		}
		catch(Exception e){
			res[1] = "0";
		}
//		System.out.println("������:"+res[1]);

		int index_avgcost = str.indexOf("���ѣ�") + 3;
		int end_avgcost = str.indexOf("Ԫ", index_avgcost + 1);
		if(end_avgcost == -1 || end_avgcost - index_avgcost > 50)
			res[2] = "null";
		else
			res[2] = str.substring(index_avgcost, end_avgcost);
//		System.out.println("�˾�:"+res[2]);

		int index_taste = str.indexOf("��Ʒ��") + 3;
		if(str.charAt(index_taste) == '-')
			res[3] = "null";
		else
			res[3] = str.substring(index_taste, index_taste + 3);
		if(res[3].equals("10."))
			res[3] += "0";
//		System.out.println("��ζ:"+res[3]);

		int index_environment = str.indexOf("������") + 3;
		if(str.charAt(index_environment) == '-')
			res[4] = "null";
		else
			res[4] = str.substring(index_environment, index_environment + 3);
		if(res[4].equals("10."))
			res[4] += "0";
//		System.out.println("����:"+res[4]);

		int index_service = str.indexOf("����") + 3;
		if(str.charAt(index_service) == '-')
			res[5] = "null";
		else
			res[5] = str.substring(index_service, index_service + 3);
		if(res[5].equals("10."))
			res[5] += "0";
//		System.out.println("����:"+res[5]);

		int index_location = str.indexOf("street-address\" title") + 23;
		if(index_location == 22)
			res[6] = "null";
		else
			res[6] = str.substring(index_location, str.indexOf(">", index_location + 1)-1);
//		System.out.println("��ַ:"+res[6]);

		int index_telephone = str.indexOf("itemprop=\"tel") + 15;
		if(index_telephone == 14)
			res[7] = "null";
		else
			res[7] = str.substring(index_telephone, str.indexOf("<", index_telephone + 1));
//		System.out.println("�绰:"+res[7]);
			
		return res;
	}
}
