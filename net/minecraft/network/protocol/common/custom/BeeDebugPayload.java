/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import java.util.function.IntFunction;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.pathfinder.Path;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public final class BeeDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final BeeInfo beeInfo;
/*    */   
/* 17 */   public BeeDebugPayload(BeeInfo $$0) { this.beeInfo = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/BeeDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 17 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/BeeDebugPayload; } public BeeInfo beeInfo() { return this.beeInfo; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/BeeDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/BeeDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/BeeDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/BeeDebugPayload;
/* 18 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final ResourceLocation ID = new ResourceLocation("debug/bee");
/*    */   
/*    */   public BeeDebugPayload(FriendlyByteBuf $$0) {
/* 21 */     this(new BeeInfo($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 28 */     this.beeInfo.write($$0);
/*    */   }
/*    */   
/*    */   public ResourceLocation id()
/*    */   {
/* 33 */     return ID; } public static final class BeeInfo extends Record { private final UUID uuid; private final int id; private final Vec3 pos; @Nullable
/*    */     private final Path path; @Nullable
/*    */     private final BlockPos hivePos; @Nullable
/* 36 */     private final BlockPos flowerPos; private final int travelTicks; private final Set<String> goals; private final List<BlockPos> blacklistedHives; public BeeInfo(UUID $$0, int $$1, Vec3 $$2, @Nullable Path $$3, @Nullable BlockPos $$4, @Nullable BlockPos $$5, int $$6, Set<String> $$7, List<BlockPos> $$8) { this.uuid = $$0; this.id = $$1; this.pos = $$2; this.path = $$3; this.hivePos = $$4; this.flowerPos = $$5; this.travelTicks = $$6; this.goals = $$7; this.blacklistedHives = $$8; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/BeeDebugPayload$BeeInfo;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #36	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/BeeDebugPayload$BeeInfo; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/BeeDebugPayload$BeeInfo;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #36	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/BeeDebugPayload$BeeInfo;
/* 36 */       //   0	8	1	$$0	Ljava/lang/Object; } public UUID uuid() { return this.uuid; } public int id() { return this.id; } public Vec3 pos() { return this.pos; } @Nullable public Path path() { return this.path; } @Nullable public BlockPos hivePos() { return this.hivePos; } @Nullable public BlockPos flowerPos() { return this.flowerPos; } public int travelTicks() { return this.travelTicks; } public Set<String> goals() { return this.goals; } public List<BlockPos> blacklistedHives() { return this.blacklistedHives; }
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
/*    */     public BeeInfo(FriendlyByteBuf $$0) {
/* 48 */       this($$0
/* 49 */           .readUUID(), $$0
/* 50 */           .readInt(), $$0
/* 51 */           .readVec3(), (Path)$$0
/* 52 */           .readNullable(Path::createFromStream), (BlockPos)$$0
/* 53 */           .readNullable(FriendlyByteBuf::readBlockPos), (BlockPos)$$0
/* 54 */           .readNullable(FriendlyByteBuf::readBlockPos), $$0
/* 55 */           .readInt(), (Set<String>)$$0
/* 56 */           .readCollection(java.util.HashSet::new, FriendlyByteBuf::readUtf), $$0
/* 57 */           .readList(FriendlyByteBuf::readBlockPos));
/*    */     }
/*    */ 
/*    */     
/*    */     public void write(FriendlyByteBuf $$0) {
/* 62 */       $$0.writeUUID(this.uuid);
/* 63 */       $$0.writeInt(this.id);
/* 64 */       $$0.writeVec3(this.pos);
/* 65 */       $$0.writeNullable(this.path, ($$0, $$1) -> $$1.writeToStream($$0));
/* 66 */       $$0.writeNullable(this.hivePos, FriendlyByteBuf::writeBlockPos);
/* 67 */       $$0.writeNullable(this.flowerPos, FriendlyByteBuf::writeBlockPos);
/* 68 */       $$0.writeInt(this.travelTicks);
/* 69 */       $$0.writeCollection(this.goals, FriendlyByteBuf::writeUtf);
/* 70 */       $$0.writeCollection(this.blacklistedHives, FriendlyByteBuf::writeBlockPos);
/*    */     }
/*    */     
/*    */     public boolean hasHive(BlockPos $$0) {
/* 74 */       return Objects.equals($$0, this.hivePos);
/*    */     }
/*    */     
/*    */     public String generateName() {
/* 78 */       return DebugEntityNameGenerator.getEntityName(this.uuid);
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 83 */       return generateName();
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\BeeDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */