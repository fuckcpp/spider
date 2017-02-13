package com.ljx.spider.test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ljx.spider.bean.LinkTypeData;
import com.ljx.spider.core.ExtractService;
import com.ljx.spider.rule.Rule;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class Test
{
	@org.junit.Test
	public void getDatasByClass() throws RowsExceededException, WriteException, IOException
	{
		List<LinkTypeData> datas_ = new ArrayList<LinkTypeData>();
		Set<String> hrefs = new HashSet<String>();
		Set<String> hrefsUsed = new HashSet<String>();
		String startWith="https://book.douban.com/tag/编程?start=60&type=T";//种子
		hrefs.add(startWith);
		Rule rule = new Rule(startWith,
				"subject-item", Rule.CLASS, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule,datas_,hrefs,hrefsUsed);
		outPutResult(extracts);
	}



	public void outPutResult(List<LinkTypeData> datas) throws IOException, RowsExceededException, WriteException
	{
		
		 WritableWorkbook workbook = null;
		 try {
	
		    workbook = Workbook.createWorkbook(new File("C:/Users/LJX/Desktop/爱立信/douban.xls"));
	        //创建新的一页
	        WritableSheet sheet = workbook.createSheet("First Sheet", 0);
	        //创建要显示的具体内容
	        Label num_ = new Label(0,0,"序号");
	        sheet.addCell(num_);
	        Label bookname_ = new Label(1,0,"书名");
	        sheet.addCell(bookname_);
	        Label point_ = new Label(2,0,"评分");
	        sheet.addCell(point_);
	        Label people_ = new Label(3,0,"评价人数");
	        sheet.addCell(people_);
	        Label auhtor_ = new Label(4,0,"作者");
	        sheet.addCell(auhtor_);
	        Label publisher_ = new Label(5,0,"出版社");
	        sheet.addCell(publisher_);
	        Label date_ = new Label(6,0,"出版日期");
	        sheet.addCell(date_);
	        Label price_ = new Label(7,0,"价格");
	        sheet.addCell(price_);
	        
	        
	        int i=1;
	        for(LinkTypeData data : datas)
	        {
	        	
	        	if(i<=40){
	        	Label num = new Label(0,i,""+i);
		        sheet.addCell(num);
		        Label bookname = new Label(1,i,data.getBookname());
		        sheet.addCell(bookname);
		        Label point = new Label(2,i,String.valueOf(data.getPoint()));
		        sheet.addCell(point);
		        Label people = new Label(3,i,""+data.getpeople());
		        sheet.addCell(people);
		        Label auhtor = new Label(4,i,data.getBookauthor());
		        sheet.addCell(auhtor);
		        Label publisher = new Label(5,i,data.getPublisher());
		        sheet.addCell(publisher);
		        Label date = new Label(6,i,data.getDate());
		        sheet.addCell(date);
		        Label price = new Label(7,i,data.getPrice());
		        sheet.addCell(price);
		        i++;
	        	}
	        }
	     
	        //把创建的内容写入到输出流中，并关闭输出流
	        workbook.write();
	        System.out.println("程序执行完毕...");
	        
	       
		    } catch (Exception e) {
			           System.out.println(e);
			        }finally{
			            if(workbook!=null){
			                try {
			                	workbook.close();
			                } catch (Exception e) {
			                   e.printStackTrace();
			                 } 
			            }
			        }

	}
}
