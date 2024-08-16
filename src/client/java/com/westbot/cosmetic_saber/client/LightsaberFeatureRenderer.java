package com.westbot.cosmetic_saber.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;


public class LightsaberFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private final Vec3d anchor_point;
    private final HiltPhysicsSimulator sim;
    private final BakedModel model;

    public LightsaberFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
        anchor_point = new Vec3d(-5.1/16f, 12.25/16f, 0);
        sim = new HiltPhysicsSimulator();
        model = MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(Identifier.of("cosmetic_saber:lightsaber_hilt"), ""));
    }

    private void renderAt(Vec3d pos, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Quaternionf rotation) {
        matrices.push();
        matrices.translate(pos.x, pos.y, pos.z);
        matrices.multiply(rotation);
        MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer().render(
            matrices.peek(),
            vertexConsumers.getBuffer(RenderLayer.getSolid()), null,
            model,
            0, 0, 0,
            light, 0
        );

        matrices.pop();
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.getName().getString().equals("Westbot") && !entity.isInvisible()) {

            Vec3d vel = entity.getVelocity();
            vel = vel.rotateY((float) Math.toRadians(entity.getBodyYaw()));

            if (entity.isFallFlying()) {
                vel = vel.multiply(0);
            }

            Quat4f rotation = sim.updateSimulation(tickDelta, new Vector3f((float) -vel.x, (float) vel.y, (float) vel.z));
            Quaternionf rot = new Quaternionf(rotation.x, rotation.y, rotation.z, rotation.w);
            Vec3d point = anchor_point;

            if (entity.isSneaking() && entity.isOnGround()) {
                point = point.add(0, 1/16f, 4/16f);
            }

            renderAt(
                point,
                matrices,
                vertexConsumers,
                light,
                rot
            );
        }
    }
}
