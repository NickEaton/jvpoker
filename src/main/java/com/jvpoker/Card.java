package com.jvpoker;

public class Card implements Comparable<Card>
{
	private int suite; // Club = 1, Heart = 2, Diamond = 3, Spade = 4
	private int number; // 2 to 10, J=11, Q=12, K=13, A=14

	public Card(int suite, int number)
	{
		this.suite = suite;
		this.number = number;
	}
	
	public int getSuite()
	{
		return this.suite;
	}

	public int getNumber()
	{
		return this.number;
	}
	
	private String getNumberLetter() {
		if (number < 11) {
			return String.valueOf(number);
		}
		else {
			switch(number) {
				case 11: return "J";
				case 12: return "Q";
				case 13: return "K";
				case 14: return "A";
				default: return "E";
			}
		}
	} 

	private char getSuiteLetter()
	{
		switch (suite)
		{
			case 1: return 'C';
			case 2: return 'H';
			case 3: return 'D';
			case 4: return 'S';
			default: return 'E';
		}
	}
 
	public int compareTo(Card card) {          

		if (this.number < card.number)
		{
			return -1;
		}
		else if (this.number == card.number)
		{
			if (this.suite < card.suite)
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
		else
		{
			return 1;
		}
	}


	public String toString()
	{
		StringBuilder temp = new StringBuilder();
		temp.append(getNumberLetter());
		temp.append(getSuiteLetter());
		return temp.toString();
	}
} 
