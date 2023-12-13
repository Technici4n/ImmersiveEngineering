/*
 * BluSunrize
 * Copyright (c) 2023
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.common.register;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.common.entities.SkyhookUserData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

import java.util.function.Supplier;

public class IEDataAttachments
{
	public static final DeferredRegister<AttachmentType<?>> REGISTER = DeferredRegister.create(
			Keys.ATTACHMENT_TYPES, ImmersiveEngineering.MODID
	);

	public static final Supplier<AttachmentType<SkyhookUserData>> SKYHOOK_USER = REGISTER.register(
			"skyhook_user", () -> AttachmentType.builder(SkyhookUserData::new).build()
	);
}
