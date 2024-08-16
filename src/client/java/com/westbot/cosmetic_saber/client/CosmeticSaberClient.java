package com.westbot.cosmetic_saber.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class CosmeticSaberClient implements ClientModInitializer {

    private static final LightsaberModel LIGHTSABER_MODEL = new LightsaberModel();

    @Override
    public void onInitializeClient() {

        ModelLoadingPlugin.register(LIGHTSABER_MODEL);
    }
}
