package com.gildedgames.util.ui.data.rect;

import java.util.Arrays;
import java.util.List;

import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.Rotation2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.rect.RectCollection;

public class Dim2D implements Rect
{

	final Pos2D pos;

	final Rotation2D rotation;

	final float width, height;

	final boolean centeredX, centeredY;

	final float scale;
	
	Dim2D()
	{
		this(new RectBuilder());
	}

	Dim2D(RectBuilder builder)
	{
		this.pos = builder.pos;

		this.width = builder.width;
		this.height = builder.height;

		this.scale = builder.scale;

		this.centeredX = builder.centeredX;
		this.centeredY = builder.centeredY;

		this.rotation = builder.rotation;
	}
	
	@Override
	public RectBuilder rebuild()
	{
		return Dim2D.build(this);
	}

	@Override
	public Rotation2D rotation()
	{
		return this.rotation;
	}

	@Override
	public float degrees()
	{
		return this.rotation().degrees();
	}

	@Override
	public Pos2D origin()
	{
		return this.rotation().origin();
	}

	@Override
	public float scale()
	{
		return this.scale;
	}

	@Override
	public Pos2D pos()
	{
		return this.pos;
	}

	@Override
	public Pos2D maxPos()
	{
		return this.pos().clone().add(this.width(), this.height()).flush();
	}

	@Override
	public float maxX()
	{
		return this.x() + this.width();
	}

	@Override
	public float maxY()
	{
		return this.y() + this.height();
	}

	@Override
	public float x()
	{
		return this.pos().x();
	}

	@Override
	public float y()
	{
		return this.pos().y();
	}

	@Override
	public float width()
	{
		return this.width;
	}

	@Override
	public float height()
	{
		return this.height;
	}

	@Override
	public boolean isCenteredX()
	{
		return this.centeredX;
	}

	@Override
	public boolean isCenteredY()
	{
		return this.centeredY;
	}

	@Override
	public boolean intersects(Pos2D pos)
	{
		return this.intersects(Dim2D.build().pos(pos).flush());
	}

	@Override
	public boolean intersects(Rect dim)
	{
		return dim.maxX() >= this.x() && dim.maxY() >= this.y() && dim.x() < this.maxX() && dim.y() < this.maxY();
	}
	
	@Override
	public boolean isHovered(InputProvider input)
	{
		Pos2D mousePos = Pos2D.flush(input.getMouseX(), input.getMouseY());
		
		return this.intersects(mousePos);
	} 
	
	@Override
	public RectBuilder clone()
	{
		return new RectBuilder(this);
	}

	public RectHolder toHolder()
	{
		return RectCollection.flush(this);
	}

	/**
	 * Creates an empty IRect object with default values
	 * @return
	 */
	public static Rect flush()
	{
		return new Dim2D();
	}

	public static RectBuilder build()
	{
		return new RectBuilder();
	}

	public static RectBuilder build(RectHolder holder)
	{
		return new RectBuilder(holder);
	}

	public static RectBuilder build(Rect dim)
	{
		return new RectBuilder(dim);
	}

	public static Rect combine(Rect... dimensions)
	{
		return Dim2D.combine(Arrays.asList(dimensions));
	}

	public static Rect combine(List<Rect> dimensions)
	{
		RectBuilder result = new RectBuilder();

		float overallScale = 0.0F;

		int validDimensions = 0;

		int centeredXCount = 0;
		int nonCenteredXCount = 0;

		int centeredYCount = 0;
		int nonCenteredYCount = 0;

		for (Rect dim : dimensions)
		{
			if (dim != null)
			{
				Rect preview = result.flush();

				float minX = Math.min(preview.x(), dim.x());
				float minY = Math.min(preview.y(), dim.y());

				float maxX = Math.max(preview.x() + preview.width(), dim.x() + dim.width());
				float maxY = Math.max(preview.y() + preview.height(), dim.y() + dim.height());

				result.pos(Pos2D.flush(minX, minY)).area(maxX - minY, maxY - minY);

				overallScale += dim.scale();

				validDimensions++;

				if (dim.isCenteredX())
				{
					centeredXCount++;
				}
				else
				{
					nonCenteredXCount++;
				}

				if (dim.isCenteredY())
				{
					centeredYCount++;
				}
				else
				{
					nonCenteredYCount++;
				}
			}
		}

		result.scale(overallScale / validDimensions);

		if (centeredXCount >= nonCenteredXCount)
		{
			result.centerX(true);
		}
		else
		{
			result.centerX(false);
		}

		if (centeredYCount >= nonCenteredYCount)
		{
			result.centerY(true);
		}
		else
		{
			result.centerY(false);
		}

		return result.flush();
	}

	@Override
	public String toString()
	{
		String link = ", ";

		return this.pos().toString() + link + "Area() Width: '" + this.width() + "', Height: '" + this.height() + "'" + link + "Centered() X: '" + this.centeredX + "', Y: '" + this.centeredY + "'" + link + "Scale() Value: '" + this.scale() + "'";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		Rect dim;

		if (obj instanceof Rect)
		{
			dim = (Rect)obj;
		}
		else if (obj instanceof RectHolder)
		{
			RectHolder holder = (RectHolder)obj;
			
			dim = holder.dim();
		}
		else
		{
			return false;
		}
		
		if (!dim.pos().equals(this.pos()) || dim.scale() != this.scale() || dim.isCenteredX() != this.centeredX || dim.isCenteredY() != this.centeredY || dim.width() != this.width() || dim.height() != this.height() || dim.rotation() != this.rotation())
		{
			return false;
		}
		
		return true;
	}
	
}
