package echiquier;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Echiquier
{
	static int[][] tab = new int[8][8];
	static int i = 0;
	
	public static void main(String[] args)
	{
		init();
		go();
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
	}
	
	static public void show()
	{
		System.out.println(++i);
		
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				System.out.print(tab[i][j] + " ");
			}
			
			System.out.println();
		}
	}
	
	static public void go()
	{
		go(0);
	}
	
	static private void go(int i)
	{		
		// Clause de finitude
		if(i == 8)
		{
			show();
			return;
		}
		
		// Pas récursif
		for (int j = 0; j < 8; j++)
		{				
			if(verify(i, j))
			{			
				tab[i][j] = 1;
				go(i + 1);
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
}
