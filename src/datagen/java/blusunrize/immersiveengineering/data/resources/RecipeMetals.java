/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */

package blusunrize.immersiveengineering.data.resources;

import blusunrize.immersiveengineering.api.EnumMetals;
import blusunrize.immersiveengineering.api.IETags;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static blusunrize.immersiveengineering.api.utils.TagUtils.createItemWrapper;
import static blusunrize.immersiveengineering.data.recipes.IERecipeProvider.getTagCondition;

/**
 * An Enum of vanilla, IE and other common metals, along with alloys. Used for generating ArcFurnace & Crusher recipes
 */
public enum RecipeMetals
{
	// Vanilla
	IRON("iron", true, true, new SecondaryOutput(IETags.getDust("nickel"), .1f)),
	GOLD("gold", true, true),

	// IE
	COPPER("copper", true, true, new SecondaryOutput(IETags.getDust("gold"), .1f)),
	ALUMINUM("aluminum", true, true),
	LEAD("lead", true, true, new SecondaryOutput(IETags.getDust("silver"), .1f)),
	SILVER("silver", true, true, new SecondaryOutput(IETags.getDust("lead"), .1f)),
	NICKEL("nickel", true, true, new SecondaryOutput(IETags.getDust("platinum"), .1f)),
	URANIUM("uranium", true, true, new SecondaryOutput(IETags.getDust("lead"), .1f)),
	CONSTANTAN("constantan", true,
			new AlloyProperties(2, new IngredientWithSize(IETags.getTagsFor(EnumMetals.COPPER).ingot),
					new IngredientWithSize(IETags.getTagsFor(EnumMetals.NICKEL).ingot))
	),
	ELECTRUM("electrum", true,
			new AlloyProperties(2, new IngredientWithSize(IETags.getTagsFor(EnumMetals.GOLD).ingot),
					new IngredientWithSize(IETags.getTagsFor(EnumMetals.SILVER).ingot))
	),
	STEEL("steel", true, false),

	// Compat
	TIN("tin", false, true),
	ZINC("zinc", false, true),
	PLATINUM("platinum", false, true, new SecondaryOutput(IETags.getDust("nickel"), .1f)),
	TUNGSTEN("tungsten", false, true),
	OSMIUM("osmium", false, true),
	COBALT("cobalt", false, true),
	ARDITE("ardite", false, true),
	BRONZE("bronze", false,
			new AlloyProperties(4, new IngredientWithSize(IETags.getTagsFor(EnumMetals.COPPER).ingot, 3),
					new IngredientWithSize(createItemWrapper(IETags.getIngot("tin"))))
					.addConditions(getTagCondition(IETags.getIngot("tin")))
	),
	BRASS("brass", false,
			new AlloyProperties(2, new IngredientWithSize(IETags.getTagsFor(EnumMetals.COPPER).ingot),
					new IngredientWithSize(createItemWrapper(IETags.getIngot("zinc"))))
					.addConditions(getTagCondition(IETags.getIngot("zinc")))
	),
	INVAR("invar", false,
			new AlloyProperties(3, new IngredientWithSize(IETags.getTagsFor(EnumMetals.IRON).ingot, 2),
					new IngredientWithSize(IETags.getTagsFor(EnumMetals.NICKEL).ingot))
	),
	ROSE_GOLD("rose_gold", false,
			new AlloyProperties(4, new IngredientWithSize(IETags.getTagsFor(EnumMetals.COPPER).ingot, 3),
					new IngredientWithSize(IETags.getTagsFor(EnumMetals.GOLD).ingot))
	),
	MANYULLYN("manyullyn", false,
			new AlloyProperties(4, new IngredientWithSize(createItemWrapper(IETags.getIngot("cobalt")), 3),
					new IngredientWithSize(Ingredient.of(Items.NETHERITE_SCRAP)))
					.addConditions(getTagCondition(IETags.getIngot("cobalt")))
	);

	private final String name;
	private final boolean isNative;
	private final TagKey<Item> ingot;
	private final TagKey<Item> dust;
	private final TagKey<Item> ore;
	private final TagKey<Item> rawOre;
	private final TagKey<Item> rawBlock;
	private final AlloyProperties alloyProperties;
	private final SecondaryOutput[] secondaryOutputs;

	RecipeMetals(String name, boolean isNative, boolean hasOre, AlloyProperties alloyProperties, SecondaryOutput... secondaryOutputs)
	{
		this.name = name;
		this.ingot = createItemWrapper(IETags.getIngot(name));
		this.dust = createItemWrapper(IETags.getDust(name));
		this.isNative = isNative;
		this.ore = !hasOre?null: createItemWrapper(IETags.getOre(name));
		this.rawOre = !hasOre?null: createItemWrapper(IETags.getRawOre(name));
		this.rawBlock = !hasOre?null: createItemWrapper(IETags.getRawBlock(name));
		this.alloyProperties = alloyProperties;
		this.secondaryOutputs = secondaryOutputs;
	}

	// Simple
	RecipeMetals(String name, boolean isNative, boolean hasOre, SecondaryOutput... secondaryOutputs)
	{
		this(name, isNative, hasOre, null, secondaryOutputs);
	}

	// Alloy
	RecipeMetals(String name, boolean isNative, AlloyProperties alloyProperties)
	{
		this(name, isNative, false, alloyProperties);
	}

	public String getName()
	{
		return name;
	}

	public boolean isNative()
	{
		return isNative;
	}

	public TagKey<Item> getIngot()
	{
		return ingot;
	}

	public TagKey<Item> getDust()
	{
		return dust;
	}

	public TagKey<Item> getOre()
	{
		return ore;
	}

	public TagKey<Item> getRawOre()
	{
		return rawOre;
	}

	public TagKey<Item> getRawBlock()
	{
		return rawBlock;
	}

	public AlloyProperties getAlloyProperties()
	{
		return alloyProperties;
	}

	public SecondaryOutput[] getSecondaryOutputs()
	{
		return secondaryOutputs;
	}

	public static class AlloyProperties
	{
		private final int outputSize;
		private final IngredientWithSize[] alloyIngredients;
		private final List<ICondition> conditions = new ArrayList<>();

		AlloyProperties(int outputSize, IngredientWithSize... alloyIngredients)
		{
			this.outputSize = outputSize;
			this.alloyIngredients = alloyIngredients;
		}

		public AlloyProperties addConditions(ICondition... conditions)
		{
			Collections.addAll(this.conditions, conditions);
			return this;
		}

		public int getOutputSize()
		{
			return outputSize;
		}

		public IngredientWithSize[] getAlloyIngredients()
		{
			return alloyIngredients;
		}

		public List<ICondition> getConditions()
		{
			return conditions;
		}

		public boolean isSimple()
		{
			return alloyIngredients.length==2;
		}
	}
}
