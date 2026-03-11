/*    */ package net.minecraft.world.level.saveddata.maps;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.nbt.Tag;
/*    */ 
/*    */ public class MapFrame
/*    */ {
/*    */   private final BlockPos pos;
/*    */   
/*    */   public MapFrame(BlockPos $$0, int $$1, int $$2) {
/* 13 */     this.pos = $$0;
/* 14 */     this.rotation = $$1;
/* 15 */     this.entityId = $$2;
/*    */   }
/*    */   private final int rotation; private final int entityId;
/*    */   public static MapFrame load(CompoundTag $$0) {
/* 19 */     BlockPos $$1 = NbtUtils.readBlockPos($$0.getCompound("Pos"));
/* 20 */     int $$2 = $$0.getInt("Rotation");
/* 21 */     int $$3 = $$0.getInt("EntityId");
/* 22 */     return new MapFrame($$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public CompoundTag save() {
/* 26 */     CompoundTag $$0 = new CompoundTag();
/* 27 */     $$0.put("Pos", (Tag)NbtUtils.writeBlockPos(this.pos));
/* 28 */     $$0.putInt("Rotation", this.rotation);
/* 29 */     $$0.putInt("EntityId", this.entityId);
/* 30 */     return $$0;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 34 */     return this.pos;
/*    */   }
/*    */   
/*    */   public int getRotation() {
/* 38 */     return this.rotation;
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 42 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 46 */     return frameId(this.pos);
/*    */   }
/*    */   
/*    */   public static String frameId(BlockPos $$0) {
/* 50 */     return "frame-" + $$0.getX() + "," + $$0.getY() + "," + $$0.getZ();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\saveddata\maps\MapFrame.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */