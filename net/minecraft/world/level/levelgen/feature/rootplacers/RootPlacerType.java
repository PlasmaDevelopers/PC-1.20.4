/*    */ package net.minecraft.world.level.levelgen.feature.rootplacers;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public class RootPlacerType<P extends RootPlacer>
/*    */ {
/*  9 */   public static final RootPlacerType<MangroveRootPlacer> MANGROVE_ROOT_PLACER = register("mangrove_root_placer", MangroveRootPlacer.CODEC);
/*    */   
/*    */   private static <P extends RootPlacer> RootPlacerType<P> register(String $$0, Codec<P> $$1) {
/* 12 */     return (RootPlacerType<P>)Registry.register(BuiltInRegistries.ROOT_PLACER_TYPE, $$0, new RootPlacerType<>($$1));
/*    */   }
/*    */   
/*    */   private final Codec<P> codec;
/*    */   
/*    */   private RootPlacerType(Codec<P> $$0) {
/* 18 */     this.codec = $$0;
/*    */   }
/*    */   
/*    */   public Codec<P> codec() {
/* 22 */     return this.codec;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\rootplacers\RootPlacerType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */