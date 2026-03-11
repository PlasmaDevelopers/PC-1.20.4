/*    */ package net.minecraft.client.gui.screens.worldselection;
/*    */ 
/*    */ import net.minecraft.core.LayeredRegistryAccess;
/*    */ import net.minecraft.server.RegistryLayer;
/*    */ import net.minecraft.server.ReloadableServerResources;
/*    */ import net.minecraft.world.level.WorldDataConfiguration;
/*    */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*    */ import net.minecraft.world.level.levelgen.WorldOptions;
/*    */ 
/*    */ public final class WorldCreationContext extends Record {
/*    */   private final WorldOptions options;
/*    */   private final Registry<LevelStem> datapackDimensions;
/*    */   private final WorldDimensions selectedDimensions;
/*    */   private final LayeredRegistryAccess<RegistryLayer> worldgenRegistries;
/*    */   private final ReloadableServerResources dataPackResources;
/*    */   private final WorldDataConfiguration dataConfiguration;
/*    */   
/* 18 */   public WorldCreationContext(WorldOptions $$0, Registry<LevelStem> $$1, WorldDimensions $$2, LayeredRegistryAccess<RegistryLayer> $$3, ReloadableServerResources $$4, WorldDataConfiguration $$5) { this.options = $$0; this.datapackDimensions = $$1; this.selectedDimensions = $$2; this.worldgenRegistries = $$3; this.dataPackResources = $$4; this.dataConfiguration = $$5; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 18 */     //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext; } public WorldOptions options() { return this.options; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;
/* 18 */     //   0	8	1	$$0	Ljava/lang/Object; } public Registry<LevelStem> datapackDimensions() { return this.datapackDimensions; } public WorldDimensions selectedDimensions() { return this.selectedDimensions; } public LayeredRegistryAccess<RegistryLayer> worldgenRegistries() { return this.worldgenRegistries; } public ReloadableServerResources dataPackResources() { return this.dataPackResources; } public WorldDataConfiguration dataConfiguration() { return this.dataConfiguration; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldCreationContext(WorldGenSettings $$0, LayeredRegistryAccess<RegistryLayer> $$1, ReloadableServerResources $$2, WorldDataConfiguration $$3) {
/* 27 */     this($$0
/* 28 */         .options(), $$0
/* 29 */         .dimensions(), $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldCreationContext(WorldOptions $$0, WorldDimensions $$1, LayeredRegistryAccess<RegistryLayer> $$2, ReloadableServerResources $$3, WorldDataConfiguration $$4) {
/* 37 */     this($$0, $$2
/*    */         
/* 39 */         .getLayer(RegistryLayer.DIMENSIONS).registryOrThrow(Registries.LEVEL_STEM), $$1, $$2
/*    */         
/* 41 */         .replaceFrom(RegistryLayer.DIMENSIONS, new RegistryAccess.Frozen[0]), $$3, $$4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldCreationContext withSettings(WorldOptions $$0, WorldDimensions $$1) {
/* 48 */     return new WorldCreationContext($$0, this.datapackDimensions, $$1, this.worldgenRegistries, this.dataPackResources, this.dataConfiguration);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldCreationContext withOptions(OptionsModifier $$0) {
/* 55 */     return new WorldCreationContext($$0.apply(this.options), this.datapackDimensions, this.selectedDimensions, this.worldgenRegistries, this.dataPackResources, this.dataConfiguration);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldCreationContext withDimensions(DimensionsUpdater $$0) {
/* 62 */     return new WorldCreationContext(this.options, this.datapackDimensions, $$0.apply(worldgenLoadContext(), this.selectedDimensions), this.worldgenRegistries, this.dataPackResources, this.dataConfiguration);
/*    */   }
/*    */   
/*    */   public RegistryAccess.Frozen worldgenLoadContext() {
/* 66 */     return this.worldgenRegistries.compositeAccess();
/*    */   }
/*    */   
/*    */   public static interface OptionsModifier extends UnaryOperator<WorldOptions> {}
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface DimensionsUpdater extends BiFunction<RegistryAccess.Frozen, WorldDimensions, WorldDimensions> {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\WorldCreationContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */