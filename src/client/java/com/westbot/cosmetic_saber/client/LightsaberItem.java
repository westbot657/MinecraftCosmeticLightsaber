package com.westbot.cosmetic_saber.client;

import net.minecraft.item.Item;

public class LightsaberItem extends Item {
    public LightsaberItem() {
        super(new Settings().maxCount(1).fireproof());
    }
}
