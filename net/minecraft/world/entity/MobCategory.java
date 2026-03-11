/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum MobCategory implements StringRepresentable {
/*  7 */   MONSTER("monster", 70, false, false, 128),
/*  8 */   CREATURE("creature", 10, true, true, 128),
/*  9 */   AMBIENT("ambient", 15, true, false, 128),
/*    */   
/* 11 */   AXOLOTLS("axolotls", 5, true, false, 128),
/* 12 */   UNDERGROUND_WATER_CREATURE("underground_water_creature", 5, true, false, 128),
/* 13 */   WATER_CREATURE("water_creature", 5, true, false, 128),
/* 14 */   WATER_AMBIENT("water_ambient", 20, true, false, 64),
/* 15 */   MISC("misc", -1, true, true, 128);
/*    */   
/*    */   static {
/* 18 */     CODEC = (Codec<MobCategory>)StringRepresentable.fromEnum(MobCategory::values);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   private final int noDespawnDistance = 32; public static final Codec<MobCategory> CODEC; private final int max;
/*    */   private final boolean isFriendly;
/*    */   
/*    */   MobCategory(String $$0, int $$1, boolean $$2, boolean $$3, int $$4) {
/* 28 */     this.name = $$0;
/* 29 */     this.max = $$1;
/* 30 */     this.isFriendly = $$2;
/* 31 */     this.isPersistent = $$3;
/* 32 */     this.despawnDistance = $$4;
/*    */   }
/*    */   private final boolean isPersistent; private final String name; private final int despawnDistance;
/*    */   public String getName() {
/* 36 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 41 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getMaxInstancesPerChunk() {
/* 45 */     return this.max;
/*    */   }
/*    */   
/*    */   public boolean isFriendly() {
/* 49 */     return this.isFriendly;
/*    */   }
/*    */   
/*    */   public boolean isPersistent() {
/* 53 */     return this.isPersistent;
/*    */   }
/*    */   
/*    */   public int getDespawnDistance() {
/* 57 */     return this.despawnDistance;
/*    */   }
/*    */   
/*    */   public int getNoDespawnDistance() {
/* 61 */     return 32;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\MobCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */