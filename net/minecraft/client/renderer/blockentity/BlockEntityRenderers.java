/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ 
/*    */ 
/*    */ public class BlockEntityRenderers
/*    */ {
/* 12 */   private static final Map<BlockEntityType<?>, BlockEntityRendererProvider<?>> PROVIDERS = Maps.newHashMap();
/*    */   
/*    */   static {
/* 15 */     register(BlockEntityType.SIGN, SignRenderer::new);
/* 16 */     register(BlockEntityType.HANGING_SIGN, HangingSignRenderer::new);
/* 17 */     register(BlockEntityType.MOB_SPAWNER, SpawnerRenderer::new);
/* 18 */     register(BlockEntityType.PISTON, PistonHeadRenderer::new);
/* 19 */     register(BlockEntityType.CHEST, ChestRenderer::new);
/* 20 */     register(BlockEntityType.ENDER_CHEST, ChestRenderer::new);
/* 21 */     register(BlockEntityType.TRAPPED_CHEST, ChestRenderer::new);
/* 22 */     register(BlockEntityType.ENCHANTING_TABLE, EnchantTableRenderer::new);
/* 23 */     register(BlockEntityType.LECTERN, LecternRenderer::new);
/* 24 */     register(BlockEntityType.END_PORTAL, TheEndPortalRenderer::new);
/* 25 */     register(BlockEntityType.END_GATEWAY, TheEndGatewayRenderer::new);
/* 26 */     register(BlockEntityType.BEACON, BeaconRenderer::new);
/* 27 */     register(BlockEntityType.SKULL, SkullBlockRenderer::new);
/* 28 */     register(BlockEntityType.BANNER, BannerRenderer::new);
/* 29 */     register(BlockEntityType.STRUCTURE_BLOCK, StructureBlockRenderer::new);
/* 30 */     register(BlockEntityType.SHULKER_BOX, ShulkerBoxRenderer::new);
/* 31 */     register(BlockEntityType.BED, BedRenderer::new);
/* 32 */     register(BlockEntityType.CONDUIT, ConduitRenderer::new);
/* 33 */     register(BlockEntityType.BELL, BellRenderer::new);
/* 34 */     register(BlockEntityType.CAMPFIRE, CampfireRenderer::new);
/* 35 */     register(BlockEntityType.BRUSHABLE_BLOCK, BrushableBlockRenderer::new);
/* 36 */     register(BlockEntityType.DECORATED_POT, DecoratedPotRenderer::new);
/* 37 */     register(BlockEntityType.TRIAL_SPAWNER, TrialSpawnerRenderer::new);
/*    */   }
/*    */   
/*    */   private static <T extends net.minecraft.world.level.block.entity.BlockEntity> void register(BlockEntityType<? extends T> $$0, BlockEntityRendererProvider<T> $$1) {
/* 41 */     PROVIDERS.put($$0, $$1);
/*    */   }
/*    */   
/*    */   public static Map<BlockEntityType<?>, BlockEntityRenderer<?>> createEntityRenderers(BlockEntityRendererProvider.Context $$0) {
/* 45 */     ImmutableMap.Builder<BlockEntityType<?>, BlockEntityRenderer<?>> $$1 = ImmutableMap.builder();
/* 46 */     PROVIDERS.forEach(($$2, $$3) -> {
/*    */           try {
/*    */             $$0.put($$2, $$3.create($$1));
/* 49 */           } catch (Exception $$4) {
/*    */             throw new IllegalStateException("Failed to create model for " + BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey($$2), $$4);
/*    */           } 
/*    */         });
/* 53 */     return (Map<BlockEntityType<?>, BlockEntityRenderer<?>>)$$1.build();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\BlockEntityRenderers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */