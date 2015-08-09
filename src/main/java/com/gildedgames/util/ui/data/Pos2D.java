package com.gildedgames.util.ui.data;

public class Pos2D
{
	
	private final int x, y;
	
	private Pos2D(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int x()
	{
		return this.x;
	}
	
	public int y()
	{
		return this.y;
	}
	
	@Override
	public String toString()
	{
		return "Position() X: '" + this.x + "', Y: '" + this.y + "'";
	}
	
	public Pos2DBuilder clone()
	{
		return new Pos2DBuilder(this);
	}
	
	public static Pos2D flush()
	{
		return new Pos2DBuilder().flush();
	}
	
	public static Pos2D flush(int x, int y)
	{
		return new Pos2D(x, y);
	}
	
	public static Pos2DBuilder build()
	{
		return new Pos2DBuilder();
	}
	
	public static class Pos2DBuilder
	{
		
		private int x, y;
		
		private Pos2DBuilder()
		{
			
		}
		
		private Pos2DBuilder(Pos2D pos)
		{
			this.x = pos.x;
			this.y = pos.y;
		}
		
		private Pos2DBuilder(Pos2DBuilder builder)
		{
			this.x = builder.x;
			this.y = builder.y;
		}
		
		public Pos2DBuilder x(int x)
		{
			this.x = x;
			
			return this;
		}
		
		public Pos2DBuilder y(int y)
		{
			this.y = y;
			
			return this;
		}
		
		public Pos2DBuilder addX(int x)
		{
			return this.add(x, 0);
		}
		
		public Pos2DBuilder addY(int y)
		{
			return this.add(0, y);
		}
		
		public Pos2DBuilder subtractX(int x)
		{
			return this.subtract(x, 0);
		}
		
		public Pos2DBuilder subtractY(int y)
		{
			return this.subtract(0, y);
		}
		
		public Pos2DBuilder subtract(int x, int y)
		{
			return this.add(-x, -y);
		}
		
		public Pos2DBuilder subtract(Pos2D pos)
		{
			if (pos == null)
			{
				return this;
			}
			
			return this.add(-pos.x, -pos.y);
		}

		public Pos2DBuilder add(int x, int y)
		{
			this.x += x;
			this.y += y;
			
			return this;
		}
		
		public Pos2DBuilder add(Pos2D pos)
		{
			if (pos == null)
			{
				return this;
			}
			
			return this.add(pos.x, pos.y);
		}
		
		public Pos2D flush()
		{
			return new Pos2D(this.x, this.y);
		}
		
	}
	
}
