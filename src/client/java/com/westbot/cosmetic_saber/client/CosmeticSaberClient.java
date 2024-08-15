package com.westbot.cosmetic_saber.client;

import net.fabricmc.api.ClientModInitializer;

public class CosmeticSaberClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemRegister.init();
    }
}
