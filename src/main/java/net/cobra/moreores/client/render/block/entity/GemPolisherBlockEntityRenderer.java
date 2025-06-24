package net.cobra.moreores.client.render.block.entity;

import net.cobra.moreores.block.GemPolisherBlock;
import net.cobra.moreores.block.entity.GemPolisherBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class GemPolisherBlockEntityRenderer implements BlockEntityRenderer<GemPolisherBlockEntity> {
    public GemPolisherBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    private void renderItem(GemPolisherBlockEntity entity, ItemStack stack, MatrixStack matrices,
                            VertexConsumerProvider vertexConsumers, float x, float y, float z, float rotationAngle) {
        if (!stack.isEmpty()) {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            matrices.push();

            matrices.translate(0.5, 0, 0.5);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationAngle));
            matrices.translate(-0.5, 0, -0.5);

            matrices.translate(x, y, z);
            matrices.scale(0.25f, 0.25f, 0.25f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(270));

            itemRenderer.renderItem(stack, ItemDisplayContext.GUI, getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            matrices.pop();
        }
    }

    private float getRotationAngle(GemPolisherBlockEntity entity) {
        if (entity.getWorld() != null) {
            return switch (entity.getCachedState().get(GemPolisherBlock.FACING)) {
                case NORTH -> 180f;
                case EAST -> 90f;
                case WEST -> -90f;
                default -> 0f;
            };
        }
        return 0f;
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }

    @Override
    public void render(GemPolisherBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        if(entity == null || entity.getWorld() == null) return;

        ItemStack inputStack = entity.getStack(GemPolisherBlockEntity.INGREDIENT_SLOT);
        ItemStack energyStack = entity.getStack(GemPolisherBlockEntity.ENERGY_SOURCE_SLOT);
        ItemStack outputStack = entity.getStack(GemPolisherBlockEntity.RESULT_SLOT);

        float rotationAngles = getRotationAngle(entity);

        renderItem(entity, inputStack, matrices, vertexConsumers, 0.75f, 0.9f, 0.25f, rotationAngles);
        renderItem(entity, energyStack, matrices, vertexConsumers, 0.25f, 0.9f, 0.25f, rotationAngles);
        renderItem(entity, outputStack, matrices, vertexConsumers, 0.5f, 0.9f, 0.685f, rotationAngles);
    }
}
