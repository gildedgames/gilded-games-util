package com.gildedgames.util.ui.data;


public class Rotation2D
{

	private final float degrees;
	
	private final Pos2D origin;
	
	private Rotation2D(float degrees, Pos2D origin)
	{
		this.degrees = degrees;
		this.origin = origin;
	}
	
	public float degrees()
	{
		return this.degrees;
	}
	
	public Pos2D origin()
	{
		return this.origin;
	}
	
	public double originX()
	{
		return this.origin.x();
	}
	
	public double originY()
	{
		return this.origin.y();
	}
	
	public Rotation2DBuilder clone()
	{
		return new Rotation2DBuilder(this);
	}
	
	public Rotation2DBuildWith buildWith(Rotation2D rotation)
	{
		return new Rotation2DBuildWith(Rotation2D.build(), rotation);
	}
	
	public static Rotation2D flush()
	{
		return new Rotation2D(0.0F, Pos2D.flush());
	}
	
	public static Rotation2DBuilder build()
	{
		return new Rotation2DBuilder();
	}
	
	public static class Rotation2DBuilder
	{
		
		private float degrees;
		
		private Pos2D origin;
		
		private Rotation2DBuilder()
		{
			
		}
		
		private Rotation2DBuilder(Rotation2D rotation)
		{
			this.degrees = rotation.degrees;
			this.origin = rotation.origin;
		}
		
		public Rotation2DBuildWith buildWith(Rotation2D rotation)
		{
			return new Rotation2DBuildWith(this, rotation);
		}
		
		public Rotation2DBuilder degrees(float degrees)
		{
			this.degrees = degrees;
			
			return this;
		}
		
		public Rotation2DBuilder addDegrees(float degrees)
		{
			this.degrees += degrees;
			
			return this;
		}
		
		public Rotation2DBuilder subtractDegrees(float degrees)
		{
			this.degrees -= degrees;
			
			return this;
		}
		
		public Rotation2DBuilder origin(Pos2D origin)
		{
			this.origin = origin;
			
			return this;
		}
		
		public Rotation2DBuilder origin(float x, float y)
		{
			this.origin = this.origin.clone().x(x).y(y).flush();
			
			return this;
		} 
		
		public Rotation2DBuilder originX(float x)
		{
			this.origin = this.origin.clone().x(x).flush();
			
			return this;
		}
		
		public Rotation2DBuilder originY(float y)
		{
			this.origin = this.origin.clone().y(y).flush();
			
			return this;
		}
		
		public Rotation2D flush()
		{
			return new Rotation2D(this.degrees, this.origin);
		}
		
	}
	
	public static class Rotation2DBuildWith
	{

		protected Rotation2DBuilder builder;

		protected Rotation2D buildWith;

		private Rotation2DBuildWith(Rotation2DBuilder builder, Rotation2D buildWith)
		{
			this.builder = builder;
			this.buildWith = buildWith;
		}

		public Rotation2DBuildWith degrees()
		{
			this.builder.degrees = this.buildWith.degrees;
			
			return this;
		}
		
		public Rotation2DBuildWith addDegrees()
		{
			this.builder.degrees += this.buildWith.degrees;
			
			return this;
		}
		
		public Rotation2DBuildWith subtractDegrees()
		{
			this.builder.degrees -= this.buildWith.degrees;
			
			return this;
		}
		
		public Rotation2DBuildWith origin()
		{
			this.builder.origin = this.buildWith.origin;
			
			return this;
		}
		
		public Rotation2DBuildWith originX()
		{
			this.builder.origin = this.builder.origin.clone().x(this.buildWith.origin.x()).flush();
			
			return this;
		}
		
		public Rotation2DBuildWith originY()
		{
			this.builder.origin = this.builder.origin.clone().y(this.buildWith.origin.y()).flush();
			
			return this;
		}
		
		public Rotation2DBuilder build()
		{
			return this.builder;
		}
		
		public Rotation2D flush()
		{
			return new Rotation2D(this.builder.degrees, this.builder.origin);
		}
		
	}
	
}
