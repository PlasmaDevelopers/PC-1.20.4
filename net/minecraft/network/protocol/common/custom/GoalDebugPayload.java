/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ 
/*    */ public final class GoalDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final int entityId;
/*    */   private final BlockPos pos;
/*    */   private final List<DebugGoal> goals;
/*    */   
/*  9 */   public GoalDebugPayload(int $$0, BlockPos $$1, List<DebugGoal> $$2) { this.entityId = $$0; this.pos = $$1; this.goals = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload; } public int entityId() { return this.entityId; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public BlockPos pos() { return this.pos; } public List<DebugGoal> goals() { return this.goals; }
/* 10 */    public static final ResourceLocation ID = new ResourceLocation("debug/goal_selector");
/*    */   
/*    */   public GoalDebugPayload(FriendlyByteBuf $$0) {
/* 13 */     this($$0
/* 14 */         .readInt(), $$0
/* 15 */         .readBlockPos(), $$0
/* 16 */         .readList(DebugGoal::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 22 */     $$0.writeInt(this.entityId);
/* 23 */     $$0.writeBlockPos(this.pos);
/* 24 */     $$0.writeCollection(this.goals, ($$0, $$1) -> $$1.write($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 29 */     return ID;
/*    */   }
/*    */   public static final class DebugGoal extends Record { private final int priority; private final boolean isRunning; private final String name;
/* 32 */     public DebugGoal(int $$0, boolean $$1, String $$2) { this.priority = $$0; this.isRunning = $$1; this.name = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #32	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #32	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #32	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal;
/* 32 */       //   0	8	1	$$0	Ljava/lang/Object; } public int priority() { return this.priority; } public boolean isRunning() { return this.isRunning; } public String name() { return this.name; }
/*    */      public DebugGoal(FriendlyByteBuf $$0) {
/* 34 */       this($$0
/* 35 */           .readInt(), $$0
/* 36 */           .readBoolean(), $$0
/* 37 */           .readUtf(255));
/*    */     }
/*    */ 
/*    */     
/*    */     public void write(FriendlyByteBuf $$0) {
/* 42 */       $$0.writeInt(this.priority);
/* 43 */       $$0.writeBoolean(this.isRunning);
/* 44 */       $$0.writeUtf(this.name);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\GoalDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */