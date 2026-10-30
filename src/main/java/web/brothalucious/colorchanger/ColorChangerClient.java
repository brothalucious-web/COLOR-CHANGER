package web.brothalucious.colorchanger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Identifier;
import web.brothalucious.colorchanger.mixin.GameRendererAccessor;

/** Client entry point for the Color Changer post-processing effect. */
public final class ColorChangerClient implements ClientModInitializer {
    public static final ColorProgression PROGRESSION = new ColorProgression();
    private static final Identifier GRAYSCALE = Identifier.of("color_changer", "grayscale");

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            PROGRESSION.tick(client);
            if (client.world != null && client.player != null) {
                if (!GRAYSCALE.equals(client.gameRenderer.getPostProcessorId())) {
                    ((GameRendererAccessor) client.gameRenderer).colorChanger$setPostProcessor(GRAYSCALE);
                }
            } else if (GRAYSCALE.equals(client.gameRenderer.getPostProcessorId())) {
                client.gameRenderer.clearPostProcessor();
            }
        });
    }
}
