package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.Pos2D;

public class Dim2DFunnel
{
	
	protected List<Dim2DModifier> modifiers;
	
	private Dim2DFunnel(List<Dim2DModifier> modifiers)
	{
		this.modifiers = modifiers;
	}
	
	public static Dim2DFunnel collect(Dim2DHolder... holders)
	{
		List<Dim2DModifier> modifiers = new ArrayList<Dim2DModifier>();
		
		for (Dim2DHolder holder : holders)
		{
			modifiers.add(new Dim2DModifier(holder));
		}
			
		return new Dim2DFunnel(modifiers);
	}
	
	public static Dim2DFunnel collect(Dim2DModifier... modifiers)
	{
		return new Dim2DFunnel(Arrays.asList(modifiers));
	}

	public Dim2DFunnel resetPos()
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.resetPos();
		}

		return this;
	}

	public Dim2DFunnel scale(float scale)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.scale(scale);
		}

		return this;
	}

	public Dim2DFunnel height(int height)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.height(height);
		}
		
		return this;
	}

	public Dim2DFunnel width(int width)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.width(width);
		}

		return this;
	}

	public Dim2DFunnel pos(Pos2D position)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.pos(position);
		}

		return this;
	}

	public Dim2DFunnel pos(int x, int y)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.pos(x, y);
		}
		
		return this;
	}

	public Dim2DFunnel center(boolean centeredX, boolean centeredY)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.center(centeredX, centeredY);
		}
		
		return this;
	}

	public Dim2DFunnel centerX(boolean centeredX)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.centerX(centeredX);
		}

		return this;
	}

	public Dim2DFunnel centerY(boolean centeredY)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.centerY(centeredY);
		}

		return this;
	}

	public Dim2DFunnel area(int width, int height)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.area(width, height);
		}
		
		return this;
	}

	public Dim2DFunnel y(int y)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.y(y);
		}
		
		return this;
	}

	public Dim2DFunnel x(int x)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.x(x);
		}
		
		return this;
	}

	public Dim2DFunnel center(boolean centered)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.center(centered);
		}
		
		return this;
	}

	public Dim2DFunnel addScale(float scale)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.addScale(scale);
		}

		return this;
	}

	public Dim2DFunnel addWidth(int width)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.addWidth(width);
		}
		
		return this;
	}

	public Dim2DFunnel addHeight(int height)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.addHeight(height);
		}
		
		return this;
	}

	public Dim2DFunnel addArea(int width, int height)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.addArea(width, height);
		}
		
		return this;
	}

	public Dim2DFunnel addX(int x)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.addX(x);
		}
		
		return this;
	}

	public Dim2DFunnel addY(int y)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.addY(y);
		}
		
		return this;
	}

	public Dim2DFunnel addPos(Pos2D pos)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.addPos(pos);
		}
		
		return this;
	}

	public Dim2DFunnel clearModifiers(ModifierType... types)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.clearModifiers(types);
		}
		
		return this;
	}

	public Dim2DFunnel addModifier(Dim2DHolder holder, ModifierType mandatoryType, ModifierType... otherTypes)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.addModifier(holder, mandatoryType, otherTypes);
		}

		return this;
	}
	
	public Dim2DFunnel removeModifier(Dim2DHolder holder, ModifierType mandatoryType, ModifierType... otherTypes)
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.removeModifier(holder, mandatoryType, otherTypes);
		}

		return this;
	}

	public Dim2DFunnel flush()
	{
		for (Dim2DModifier modifier : this.modifiers)
		{
			modifier.compile();
		}
		
		return this;
	}
	
}