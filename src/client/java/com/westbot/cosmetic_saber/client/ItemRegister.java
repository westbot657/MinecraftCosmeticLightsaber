package com.westbot.cosmetic_saber.client;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemRegister {

    public static final Item LIGHTSABER_HILT = register(new LightsaberItem(), "lightsaber_hilt");


    public static Item register(Item item, String id) {
        return Registry.register(Registries.ITEM, Identifier.of("cosmetic_saber", id), item);
    }

    public static void init() {

    }

}
