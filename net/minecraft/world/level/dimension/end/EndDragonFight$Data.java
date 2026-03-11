/*    */ package net.minecraft.world.level.dimension.end;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function7;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.UUIDUtil;
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
/*    */ 
/*    */ 
/*    */ public final class Data
/*    */   extends Record
/*    */ {
/*    */   final boolean needsStateScanning;
/*    */   final boolean dragonKilled;
/*    */   final boolean previouslyKilled;
/*    */   final boolean isRespawning;
/*    */   final Optional<UUID> dragonUUID;
/*    */   final Optional<BlockPos> exitPortalLocation;
/*    */   final Optional<List<Integer>> gateways;
/*    */   public static final Codec<Data> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #67	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #67	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #67	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Data(boolean $$0, boolean $$1, boolean $$2, boolean $$3, Optional<UUID> $$4, Optional<BlockPos> $$5, Optional<List<Integer>> $$6) {
/* 67 */     this.needsStateScanning = $$0; this.dragonKilled = $$1; this.previouslyKilled = $$2; this.isRespawning = $$3; this.dragonUUID = $$4; this.exitPortalLocation = $$5; this.gateways = $$6; } public boolean needsStateScanning() { return this.needsStateScanning; } public boolean dragonKilled() { return this.dragonKilled; } public boolean previouslyKilled() { return this.previouslyKilled; } public boolean isRespawning() { return this.isRespawning; } public Optional<UUID> dragonUUID() { return this.dragonUUID; } public Optional<BlockPos> exitPortalLocation() { return this.exitPortalLocation; } public Optional<List<Integer>> gateways() { return this.gateways; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 76 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.BOOL.fieldOf("NeedsStateScanning").orElse(Boolean.valueOf(true)).forGetter(Data::needsStateScanning), (App)Codec.BOOL.fieldOf("DragonKilled").orElse(Boolean.valueOf(false)).forGetter(Data::dragonKilled), (App)Codec.BOOL.fieldOf("PreviouslyKilled").orElse(Boolean.valueOf(false)).forGetter(Data::previouslyKilled), (App)Codec.BOOL.optionalFieldOf("IsRespawning", Boolean.valueOf(false)).forGetter(Data::isRespawning), (App)UUIDUtil.CODEC.optionalFieldOf("Dragon").forGetter(Data::dragonUUID), (App)BlockPos.CODEC.optionalFieldOf("ExitPortalLocation").forGetter(Data::exitPortalLocation), (App)Codec.list((Codec)Codec.INT).optionalFieldOf("Gateways").forGetter(Data::gateways)).apply((Applicative)$$0, Data::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 87 */   public static final Data DEFAULT = new Data(true, false, false, false, Optional.empty(), Optional.empty(), Optional.empty());
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\end\EndDragonFight$Data.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */