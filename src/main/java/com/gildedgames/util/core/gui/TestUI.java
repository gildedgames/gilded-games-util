/**Flying Lotus - You're Dead
 * Freddie Gibbs & Madlib - Pinata
 * Busdriver - Perfect Hair
 * D'Angelo - Black Messiah
 * FKA twigs - LP1
 * Hundred Waters - The Moon Rang Like A Bell
 * Iceage - Plowing Into the Field of Love
 * Leon Vynehall - Music for the Uninvited
 * Mac Demarco - Salad Days
 * Mick Jenkins - The Water[s]
 * Open Mike Eagle - Dark Comedy
 * Owen Pallett - In Conflict
 * Run The Jewels - Run The Jewels 2
 * Snarky Puppy - We Like It Here
 * Snowmine - Dialects
 * Timber Timbre - Hot Dreams
 * 
 * St. Vincent - St. Vincent (Art Pop) 8.6
 * Todd Terje - It's Album Time (Nu-Disco) 8.6
 * Casualties of Cool - Casualties of Cool (Ambient Country) 8.5/8.6
 * The War On Drugs - Lost In The Dream (Indie Rock) 8.5
 * Liars - Mess (Alternative Dance) 8.3
 * Isaiah Rashad - Cilvia Demo (Hip Hop) 8.3
 * Kiasmos - Kiasmos (Microhouse) 8.3
 * Kairon; IRSE! - Ujubasajuba (Shoegaze) 8.3
 * Ought - More Than Any Other Day (Post Punk) 8.2
 * Ben Frost - A U R O R A (Electro-Industrial) 8.2
 * Damien Rice - My Favourite Faded Fantasy (Chamber Folk) 8.2
 * Darren Korb - Transistor OST (Downtempo) 8.2
 * Haywyre - Two Fold Pt.1 (Glitch Hop) 8.2
 * Moody Good - Moody Good (Dubstep) 8.1
 * Disasterpeace, Ian Snyder - The Floor is Jelly OST (Ambient) 8.1
 * Clap! Clap! - Tayi Bebba (Footwork) 8.1
 * A Winged Victory for the Sullen - Atomos (Ambient) 8.0
 * Swans - To Be Kind (Experimental Rock) 7.9
 * Grouper - Ruins (Ambient) 7.9
 * CunninLynguists - Strange Journey Volume Three (Hip Hop) 7.8
 * Real Estate - Atlas (Jangle Pop) 7.8
 * Krusseldorf - Fractal World (IDM) 7.7
 * Milo - A Toothpaste Suburb (Abstract Hip Hop) 7.7
 * De Huilende Rappers - Verkleed Als Sukkel (Dutch Hip Hop) 7.7
 * Royal Blood - Royal Blood (Hard Rock) 7.6
 * Perfume Genius - Too Bright (Art Pop) 7.6
 * BADBADNOTGOOD - III (Fusion Jazz) 7.6
 * Wild Beasts - Present Tense (Art Pop) 7.5
 * Ty Segall - Manipulator (Psychedelic Rock) 7.5
 * Damon Albarn - Everyday Robots (Art Pop) 7.5
 * Typhoon - Lobi Da Basi (Dutch Hip Hop) 7.5
 * Thee Silver Mt. Zion Memorial Orchestra - Fuck Off Get Free We Pour Light On Everything (Post-Rock) 7.4
 * Erik Truffaz - Being Human Being (Ambient Nu Jazz) 7.3
 * tUnE-yArDs - Nikki Nack (Art Pop) 7.2
 * Mr Oizo - The Church (Electro House) 7.2
 * Spoon - They Want My Soul (Indie Rock) 7.2
 * Damien Jurado - Brothers and Sisters of the Eternal Son (Folk) 7.2
 * Luke Abbott - Wysing Forest (IDM) 7.1
 * alt-J - This Is All Yours (Art Pop) 7.0
 * Have a Nice Life - The Unnatural World (Post Punk) 6.9
 * Actress - Ghettoville (IDM) 6.9
 * Deadmau5 - while(1<2) (Progressive House) 6.9 
 * Aphex Twin - Syro (IDM) 6.7
 * Goat - Commune 6.5
 * clipping - CLPPNG (Industrial Hip Hop) 5.9
 * Trent Reznor and Atticus Ross - Gone Girl OST (Ambient)
 * 
 * Kitty, Daniel Wilson, Noisia, Matt Lange, Vince Staples
 */

package com.gildedgames.util.core.gui;

import com.gildedgames.util.core.gui.util.UIFactoryUtil;
import com.gildedgames.util.ui.UIBasicAbstract;
import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.UIButtonList;
import com.gildedgames.util.ui.util.UIScrollBar;
import com.gildedgames.util.ui.util.decorators.UIScrollable;
import com.gildedgames.util.ui.util.factory.TestButtonFactory;
import com.gildedgames.util.ui.util.transform.ViewPositionerButton;

public class TestUI extends UIBasicAbstract
{

	public TestUI(UIBasicAbstract parent)
	{
		super(parent, new Dimensions2D());
	}

	@Override
	public void onInit(UIContainer container, InputProvider input)
	{
		super.onInit(container, input);

		UIFactoryUtil factory = new UIFactoryUtil();

		UIButtonList list = new UIButtonList(new Position2D(), 60, new ViewPositionerButton(), new TestButtonFactory());

		Dimensions2D dim = new Dimensions2D().setArea(60, 200);

		UIScrollBar scrollBar = factory.createScrollBar(new Position2D(), 200, dim.clone(), false);

		UIScrollable scrollable = new UIScrollable(dim, list, scrollBar);

		scrollable.getDimensions().setPos(new Position2D(20, 20));

		container.add(scrollable);

		//scrollable.getDimensions().setScale(0.5F).setPos(new Position2D(50, 50));
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

	}

}
