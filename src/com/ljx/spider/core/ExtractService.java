package com.ljx.spider.core;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ljx.spider.bean.LinkTypeData;
import com.ljx.spider.rule.Rule;
import com.ljx.spider.rule.RuleException;
import com.ljx.spider.util.TextUtil;

/**
 * 
 * @author LJX
 * 
 */
public class ExtractService
{
	/**
	 * 链接管理：管理两个链接容器的内容
	 * @param newlinks
	 * @param hrefs 将重新获得的链接放到hrefs，每次从hrefs获取一个路径进行访问
	 * @param hrefs_used  链接管理，将访问过得链接放到hrefs_used容器中，hrefs_used中的所有链接都不可再次访问
	 * 
	 */

	public static  void hrefManage(Elements newlinks,Set<String> hrefs ,Set<String> hrefs_used)
	{
		for (Element newlink : newlinks)
		{
			Elements links_name = newlink.getElementsByTag("a");//书名
			for (Element link_name : links_name)
			{
				String link = link_name.attr("href");
				String link_add="https://book.douban.com"+link;
				if(!hrefs_used.contains(link_add)&&!hrefs.contains(link_add))
				hrefs.add(link_add);
				//System.out.println("新链接"+link);
			}
		}
	}
	/**爬取结果排序
	 * @param datas
	 * 
	 */
	public static  void sortDatas(List<LinkTypeData> datas)
	{
		 Collections.sort(datas, new Comparator<LinkTypeData>() {

				@Override

			      public int compare(LinkTypeData o1, LinkTypeData o2) {

			        return new Double(o2.getPoint()).compareTo(new Double(o1.getPoint()));

			      }

			    });
	}
	/**
	 * 从网页上获取到书籍信息，将信息放入datas容器
	 * @param results 网页
	 * @param datas 盛放书籍信息
	 */
	public static  void getElements(Elements results,List<LinkTypeData> datas)
	{
		LinkTypeData data = null;
	for (Element result : results)
	{
		Elements links_bookname = result.getElementsByTag("h2");//书名
		Elements links_star = result.getElementsByClass("rating_nums");//评分
		Elements links_people = result.getElementsByClass("pl");//评价人数
		Elements links_pub = result.getElementsByClass("pub");//出版社
		data = new LinkTypeData();
			
		for (Element link : links_bookname)
		{
			//必要的筛选
			//String linkHref = link.attr("href");
			String linkText = link.text();
			System.out.println("书名"+linkText);
			data.setBookname(linkText);
		}
		for(Element link:links_star)
		{
			System.out.println("评分"+link.text());
			data.setPoint( Double.parseDouble(link.text()));		
		}
		for(Element link:links_people)
		{
		
			String peoplenum=link.text();
			int len=peoplenum.length();
			String piresult = "";
			for(int i=0;i<len;i++)
			{
				if((peoplenum.charAt(i)>='0')&&(peoplenum.charAt(i)<='9'))
					piresult+=peoplenum.charAt(i);
			}
			System.out.println("评价人数"+piresult);
			data.setpeople( Integer.parseInt(piresult));			
		}	
		for(Element link:links_pub)
		{
			System.out.println(link.text());
			String pubInfo=link.text();
			
			String price=null;
			
			String date = null  ;
			
			String publisher = null;
			
			String author = null;
			
			int i,j,k=1;
			j=pubInfo.length();
			for(i=pubInfo.length()-1;i>0;i--)
			{
				//System.out.println("****"+pubInfo.charAt(i));
				if('/'==pubInfo.charAt(i))
				{
				switch(k)	
				{
				case 1:
					price= pubInfo.substring(i+1, j);
					j=i;k++;
					break;
				case 2:
					date= pubInfo.substring(i+1, j);
					j=i;k++;
					break;
				case 3:
					publisher= pubInfo.substring(i+1, j);
					j=i;k++;
					break;
				default:break;
				}
				}
				
			}
			j=0;
			for(i=0;i<pubInfo.length();i++)
			{
				if(pubInfo.charAt(i)=='/')
				{
					author=pubInfo.substring(j, i-1);
				}
				
			}
			
			
			data.setBookauthor(author);
			data.setDate(date);
			data.setPrice(price);
			
			
			
			data.setPublisher(publisher);	
			
			
			
			
			
		}
		if(data.getpeople()>=1000)
		datas.add(data);
	}
	}
	
	public static  List<LinkTypeData> extract(Rule rule,List<LinkTypeData> datas,Set<String> hrefs ,Set<String> hrefs_used)
	{
		// 进行对rule的必要校验
		validateRule(rule);

		while(hrefs.size()>0){			
		try
		{
			/**
			 * 解析rule
			 */
			Iterator<String> it = hrefs.iterator();
			it = hrefs.iterator();
			
			String url = it.next();
			System.out.println("访问  ****   "+url);
			String resultTagName = rule.getResultTagName();
			int type = rule.getType();
			int requestType = rule.getRequestMoethod();

			Connection conn = Jsoup.connect(url);
			System.out.println("尚有链接数：##"+hrefs.size());
			hrefs.remove(url);
			hrefs_used.add(url);
			System.out.println("尚有链接数：##"+hrefs.size());			
			
			// 设置请求类型
			Document doc = null;
			switch (requestType)
			{
			case Rule.GET:
				doc = conn.timeout(100000).get();
				break;
			case Rule.POST:
				doc = conn.timeout(100000).post();
				break;
			}

			//处理返回数据
			Elements results = new Elements();
			Elements newlinks = new Elements();
			switch (type)
			{
			case Rule.CLASS:
				results = doc.getElementsByClass(resultTagName);
				newlinks=doc.getElementsByClass("paginator");
				break;
			case Rule.ID:
				Element result = doc.getElementById(resultTagName);
				results.add(result);
				break;
			case Rule.SELECTION:
				results = doc.select(resultTagName);
				break;
			default:
				//当resultTagName为空时默认去body标签
				if (TextUtil.isEmpty(resultTagName))
				{
					results = doc.getElementsByTag("body");
				}
			}
			hrefManage( newlinks,hrefs ,hrefs_used);
			getElements( results, datas);
			

			if(it.hasNext())
				System.out.println("继续抓取");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		}		
		
		sortDatas(datas);		

		return datas;
	}

	/**
	 * 对传入的参数进行必要的校验
	 */
	private static void validateRule(Rule rule)
	{
		String url = rule.getUrl();
		System.out.println(url+" url的格式");
		if (TextUtil.isEmpty(url))
		{
			throw new RuleException("url不能为空！");
		}
		if (!url.startsWith("https://"))
		{
			System.out.println(url+"url的格式不正确！");
			throw new RuleException("url的格式不正确！");
		}

	}


}
