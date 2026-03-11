/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Types
/*    */   implements StringRepresentable
/*    */ {
/* 37 */   WORLD_SURFACE_WG("WORLD_SURFACE_WG", Heightmap.Usage.WORLDGEN, Heightmap.NOT_AIR),
/* 38 */   WORLD_SURFACE("WORLD_SURFACE", Heightmap.Usage.CLIENT, Heightmap.NOT_AIR),
/* 39 */   OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", Heightmap.Usage.WORLDGEN, Heightmap.MATERIAL_MOTION_BLOCKING),
/* 40 */   OCEAN_FLOOR("OCEAN_FLOOR", Heightmap.Usage.LIVE_WORLD, Heightmap.MATERIAL_MOTION_BLOCKING), MOTION_BLOCKING("OCEAN_FLOOR", Heightmap.Usage.LIVE_WORLD, Heightmap.MATERIAL_MOTION_BLOCKING), MOTION_BLOCKING_NO_LEAVES("OCEAN_FLOOR", Heightmap.Usage.LIVE_WORLD, Heightmap.MATERIAL_MOTION_BLOCKING); public static final Codec<Types> CODEC; private final String serializationKey; static {
/* 41 */     MOTION_BLOCKING = new Types("MOTION_BLOCKING", 4, "MOTION_BLOCKING", Heightmap.Usage.CLIENT, $$0 -> ($$0.blocksMotion() || !$$0.getFluidState().isEmpty()));
/* 42 */     MOTION_BLOCKING_NO_LEAVES = new Types("MOTION_BLOCKING_NO_LEAVES", 5, "MOTION_BLOCKING_NO_LEAVES", Heightmap.Usage.LIVE_WORLD, $$0 -> (($$0.blocksMotion() || !$$0.getFluidState().isEmpty()) && !($$0.getBlock() instanceof net.minecraft.world.level.block.LeavesBlock)));
/*    */   }
/*    */   private final Heightmap.Usage usage; private final Predicate<BlockState> isOpaque;
/*    */   static {
/* 46 */     CODEC = (Codec<Types>)StringRepresentable.fromEnum(Types::values);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Types(String $$0, Heightmap.Usage $$1, Predicate<BlockState> $$2) {
/* 53 */     this.serializationKey = $$0;
/* 54 */     this.usage = $$1;
/* 55 */     this.isOpaque = $$2;
/*    */   }
/*    */   
/*    */   public String getSerializationKey() {
/* 59 */     return this.serializationKey;
/*    */   }
/*    */   
/*    */   public boolean sendToClient() {
/* 63 */     return (this.usage == Heightmap.Usage.CLIENT);
/*    */   }
/*    */   
/*    */   public boolean keepAfterWorldgen() {
/* 67 */     return (this.usage != Heightmap.Usage.WORLDGEN);
/*    */   }
/*    */   
/*    */   public Predicate<BlockState> isOpaque() {
/* 71 */     return this.isOpaque;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 76 */     return this.serializationKey;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\Heightmap$Types.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */