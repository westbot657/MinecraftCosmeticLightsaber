package com.westbot.cosmetic_saber.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LightsaberModel implements ModelLoadingPlugin {

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.addModels(Identifier.of("cosmetic_saber", "lightsaber_hilt"));

    }


}
