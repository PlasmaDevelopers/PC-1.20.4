/*    */ package net.minecraft.world.level.dimension;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
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
/*    */ public final class MonsterSettings
/*    */   extends Record
/*    */ {
/*    */   private final boolean piglinSafe;
/*    */   private final boolean hasRaids;
/*    */   private final IntProvider monsterSpawnLightTest;
/*    */   private final int monsterSpawnBlockLightLimit;
/*    */   public static final MapCodec<MonsterSettings> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #58	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #58	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #58	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public MonsterSettings(boolean $$0, boolean $$1, IntProvider $$2, int $$3) {
/* 58 */     this.piglinSafe = $$0; this.hasRaids = $$1; this.monsterSpawnLightTest = $$2; this.monsterSpawnBlockLightLimit = $$3; } public boolean piglinSafe() { return this.piglinSafe; } public boolean hasRaids() { return this.hasRaids; } public IntProvider monsterSpawnLightTest() { return this.monsterSpawnLightTest; } public int monsterSpawnBlockLightLimit() { return this.monsterSpawnBlockLightLimit; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 64 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.BOOL.fieldOf("piglin_safe").forGetter(MonsterSettings::piglinSafe), (App)Codec.BOOL.fieldOf("has_raids").forGetter(MonsterSettings::hasRaids), (App)IntProvider.codec(0, 15).fieldOf("monster_spawn_light_level").forGetter(MonsterSettings::monsterSpawnLightTest), (App)Codec.intRange(0, 15).fieldOf("monster_spawn_block_light_limit").forGetter(MonsterSettings::monsterSpawnBlockLightLimit)).apply((Applicative)$$0, MonsterSettings::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\DimensionType$MonsterSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */