package crawler.CrawlerInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class CrawlerShoppingInfo {

	public static int errorstep = 0;
	public static void main(String[] args) throws IOException, InterruptedException{
		HtmlParserTool conparser=new HtmlParserTool();
		FileOutputStream out= new FileOutputStream("data/shopinfo.txt",true);
		FileOutputStream errorURL= new FileOutputStream("data/errorURL.txt",true);
		FileOutputStream oldpage= new FileOutputStream("data/oldpage.txt",true);
		FileOutputStream log= new FileOutputStream("data/log.txt",true);
		File file=new File("data/shoplist_irrepeated.txt");

		//��ȡlog.txt���һ�м�¼
		FileReader fr = new FileReader("data/log.txt");  
		BufferedReader br = new BufferedReader(fr);  
		String str = null;  
		String lastline = null;
		while ((str = br.readLine()) != null) {  
			lastline = str;  
		}  
		br.close();  
		fr.close();
		if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), "GBK");// ���ǵ������ʽ ,���ļ��ж������ڴ�
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			int i = 0;
			if (lastline != null)
				while (!lastline.equals(lineTxt)){
					lineTxt = bufferedReader.readLine();
				}
			while ((lineTxt = bufferedReader.readLine()) != null) {
				Random rand = new Random();
				System.out.print(lineTxt + ", ");
				conparser.extracLinks(lineTxt, out, errorURL, log, oldpage);
				i++;
				if (i % 2 == 0) {
					rand = new Random();
					Integer r = rand.nextInt(10) + 1;
					Thread.sleep(r * 1000);
					System.out.println("sleep:" + r + "s");
					//					i = 0;
				}
				if(i>50){
					Integer r2 = rand.nextInt(40) + 40;
					Thread.sleep(r2 * 1000);
					System.out.println("long sleep:" + r2 + "s");
					i=0;
				}
			}
		}
		out.close();
		errorURL.close();
		log.close();
		System.out.println("!job finish!");
	}
}