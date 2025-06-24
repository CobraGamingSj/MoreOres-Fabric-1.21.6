package net.cobra.moreores.screen;

import net.cobra.moreores.MoreOresModInitializer;
import net.cobra.moreores.block.data.GemPolisherData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlerType {

    public static final ScreenHandlerType<GemPolisherScreenHandler> GEM_POLISHING_SCREEN_HANDLER =
            register("gem_polisher", GemPolisherScreenHandler::new, GemPolisherData.PACKET_CODEC
            );

    private static <S extends ScreenHandler, D extends CustomPayload> ExtendedScreenHandlerType<S, D> register(String id, ExtendedScreenHandlerType.ExtendedFactory<S, D> factory, PacketCodec<? super RegistryByteBuf, D> packetCodec) {
        return Registry.register(Registries.SCREEN_HANDLER, MoreOresModInitializer.getId(id), new ExtendedScreenHandlerType<>(factory, packetCodec));
    }

    public static void register() {

    }
}
