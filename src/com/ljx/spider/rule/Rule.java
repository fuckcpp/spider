package com.ljx.spider.rule;

/**
 * 规则类
 * 
 * @author LJX
 * 
 */
public class Rule
{
	/**
	 * 链接
	 */
	private String url;

	/**
	 * 对返回的HTML，第一次过滤所用的标签，请先设置type
	 */
	private String resultTagName;

	/**
	 * CLASS / ID / SELECTION
	 * 设置resultTagName的类型，默认为ID 
	 */
	private int type = ID ;
	
	/**
	 *GET / POST
	 * 请求的类型，默认GET
	 */
	private int requestMoethod = GET ; 
	
	public final static int GET = 0 ;
	public final static int POST = 1 ;
	

	public final static int CLASS = 0;
	public final static int ID = 1;
	public final static int SELECTION = 2;

	public Rule()
	{
	}

	
	public Rule(String url,String resultTagName, int type, int requestMoethod)
	{
		super();
		this.url = url;
		this.resultTagName = resultTagName;
		this.type = type;
		this.requestMoethod = requestMoethod;
	}


	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}


	public String getResultTagName()
	{
		return resultTagName;
	}

	public void setResultTagName(String resultTagName)
	{
		this.resultTagName = resultTagName;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getRequestMoethod()
	{
		return requestMoethod;
	}

	public void setRequestMoethod(int requestMoethod)
	{
		this.requestMoethod = requestMoethod;
	}


	
	

}
