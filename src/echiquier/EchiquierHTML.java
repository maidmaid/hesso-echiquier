package echiquier;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class EchiquierHTML
{
	static int[][] tab = new int[8][8];
	static int i = 0;
	static StringBuilder html = new StringBuilder();
	
	public static void main(String[] args)
	{
		init();
		go();
		exportHTML();
		System.out.println("ok");
	}

	static public void init()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				tab[i][j] = 0;
			}
			
			System.out.println();
		}
		
		html.append("<script src=\"jquery.js\"></script>");
		html.append("<script src=\"jquery.jsPlumb-1.4.1-all-min.js\"></script>");
		html.append("<style>table{text-align:center;margin-top:50px;} tr{vertical-align:top;height: 30px;}</style>");
		html.append("<table id=\"root\"><tr><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td></tr><tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr></table>");
	}
	
	static public void go()
	{
		go(0);
	}
	
	static private void go(int i)
	{
		updateHTML();
		
		for (int j = 0; j < 8; j++)
		{	
			if(verify(i, j))
			{			
				tab[i][j] = 1;
				
				if(i == 7)
				{
					updateHTML();
				}
				else
				{
					go(i + 1);
				}
				
				tab[i][j] = 0;
			}
		}
	}
	
	static public boolean verify(int i, int j)
	{
		for (int x = 0; x < 8; x++)
		{
			int sum =
				tab[i][x] + // Vérifie en ligne -
				tab[x][j] + // Vérifie en colone |
				(i + x < 8 && j + x < 8 ? tab[i + x][j + x] : 0) + // Vérifie en digonale / haut
				(i - x >= 0 && j - x >= 0 ? tab[i - x][j - x] : 0) + // Vérifie en digonale / bas
				(i + x < 8 && j - x >= 0 ? tab[i + x][j - x] : 0) + // Vérifie en digonale \ haut
				(i - x >= 0 && j + x < 8 ? tab[i - x][j + x] : 0); // Vérifie en digonale \ bas
			
			if(sum > 0)
			{
				return false;				
			}
		}
		
		return true;
	}
	
	static public String getId()
	{
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if(tab[i][j] == 1)
				{
					sb.append(j);
				}
			}
		}
		
		return sb.toString();
	}
	
	static public String createTable(String id)
	{
		if(id.isEmpty())
			return "";
		
		String parentTable = id.length() > 1 ? id.substring(0, id.length() - 1) : "root";
		String parentValue = id.substring(id.length() - 1);
		String destination = "#" + parentTable + " tr:eq(1) > td:eq(" + parentValue + ")";
		
		String table = "<table id=\"" + id + "\"><tr><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td></tr><tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr></table>";
		
		StringBuilder script = new StringBuilder();
		script.append("<script> \r\n");
		script.append("$('#" + id + "').appendTo('" + destination + "') \r\n");
		script.append("jsPlumb.bind('ready', function() { \r\n");
		for (int i = 0; i < 8; i++)
		{
			script.append("jsPlumb.connect({source:$('#"+parentTable+" tr:eq(0) > td:eq("+parentValue+")'), target:$('#"+id+" tr:eq(0) > td:eq("+i+")'), paintStyle:{lineWidth:2, strokeStyle:'rgb(243,230,18)'}, anchors:['Bottom', 'Top'], endpointStyle:{radius:5}, connector:['Straight']}); \r\n ");
		}
		script.append("});");
		script.append("</script>");
		
		return table + "\r\n" + script.toString();
	}
	
	static public void updateHTML()
	{
		html.append(createTable(getId()));
		html.append("\r\n");
	}
	
	static public void exportHTML()
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter("c:/temp/dames/dames.html", "UTF-8");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		String export = html.toString();
		writer.println(export);
		writer.close();
		
		System.out.println(export);
	}
}
