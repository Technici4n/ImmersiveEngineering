/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */

package blusunrize.immersiveengineering.data.resources;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.conditions.ICondition;

import static blusunrize.immersiveengineering.api.utils.TagUtils.createItemWrapper;
import static blusunrize.immersiveengineering.data.recipes.IERecipeProvider.getTagCondition;

public class SecondaryOutput
{
	private final IngredientWithSize item;
	private final float chance;
	private ICondition[] conditions;

	public SecondaryOutput(IngredientWithSize item, float chance)
	{
		this.item = item;
		this.chance = chance;
		this.conditions = new ICondition[0];
	}

	public SecondaryOutput(TagKey<Item> tag, float chance)
	{
		this(new IngredientWithSize(tag), chance);
		this.conditions = new ICondition[]{getTagCondition(tag)};
	}

	public SecondaryOutput(ResourceLocation tag, float chance)
	{
		this(createItemWrapper(tag), chance);
	}

	public IngredientWithSize getItem()
	{
		return item;
	}

	public float getChance()
	{
		return chance;
	}

	public SecondaryOutput setConditions(ICondition[] conditions)
	{
		this.conditions = conditions;
		return this;
	}

	public ICondition[] getConditions()
	{
		return conditions;
	}
}
