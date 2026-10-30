package web.brothalucious.colorchanger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/** Client entry point. Rendering is intentionally separated from challenge state. */
public final class ColorChangerClient implements ClientModInitializer {
    public static final ColorProgression PROGRESSION = new ColorProgression();

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> PROGRESSION.tick(client));
    }
}
