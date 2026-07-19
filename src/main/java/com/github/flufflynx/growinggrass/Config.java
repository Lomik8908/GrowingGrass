package com.github.flufflynx.growinggrass;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = GrowingGrass.MODID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue DO_GRASSBLOCK_GROWING = BUILDER
            .comment("Whether short grass grows from grass blocks")
            .define("growShortGrass", true);

    private static final ModConfigSpec.BooleanValue DO_SHORTGRASS_GROWING = BUILDER
            .comment("Whether short grass grows into tall grass")
            .define("growTallGrass", true);

    private static final ModConfigSpec.DoubleValue SHORTGRASS_CHANCE = BUILDER
            .comment("The chance of short grass growing.")
            .defineInRange("shortGrassChance", 0.01, 0, 1);

    private static final ModConfigSpec.DoubleValue TALLGRASS_CHANCE = BUILDER
            .comment("The chance of tall grass growing.")
            .defineInRange("tallGrassChance", 0.2, 0, 1);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean growShortGrass;
    public static boolean growTallGrass;
    public static double shortGrassChance;
    public static double tallGrassChance;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        growShortGrass = DO_GRASSBLOCK_GROWING.get();
        growTallGrass = DO_SHORTGRASS_GROWING.get();
        shortGrassChance = SHORTGRASS_CHANCE.get();
        tallGrassChance = TALLGRASS_CHANCE.get();
    }
}
