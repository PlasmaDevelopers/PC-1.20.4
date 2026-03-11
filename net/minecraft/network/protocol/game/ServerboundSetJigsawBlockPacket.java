/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.entity.JigsawBlockEntity;
/*    */ 
/*    */ 
/*    */ public class ServerboundSetJigsawBlockPacket
/*    */   implements Packet<ServerGamePacketListener>
/*    */ {
/*    */   private final BlockPos pos;
/*    */   private final ResourceLocation name;
/*    */   private final ResourceLocation target;
/*    */   private final ResourceLocation pool;
/*    */   
/*    */   public ServerboundSetJigsawBlockPacket(BlockPos $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3, String $$4, JigsawBlockEntity.JointType $$5, int $$6, int $$7) {
/* 20 */     this.pos = $$0;
/* 21 */     this.name = $$1;
/* 22 */     this.target = $$2;
/* 23 */     this.pool = $$3;
/* 24 */     this.finalState = $$4;
/* 25 */     this.joint = $$5;
/* 26 */     this.selectionPriority = $$6;
/* 27 */     this.placementPriority = $$7;
/*    */   }
/*    */   private final String finalState; private final JigsawBlockEntity.JointType joint; private final int selectionPriority; private final int placementPriority;
/*    */   public ServerboundSetJigsawBlockPacket(FriendlyByteBuf $$0) {
/* 31 */     this.pos = $$0.readBlockPos();
/* 32 */     this.name = $$0.readResourceLocation();
/* 33 */     this.target = $$0.readResourceLocation();
/* 34 */     this.pool = $$0.readResourceLocation();
/* 35 */     this.finalState = $$0.readUtf();
/* 36 */     this.joint = JigsawBlockEntity.JointType.byName($$0.readUtf()).orElse(JigsawBlockEntity.JointType.ALIGNED);
/* 37 */     this.selectionPriority = $$0.readVarInt();
/* 38 */     this.placementPriority = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 43 */     $$0.writeBlockPos(this.pos);
/* 44 */     $$0.writeResourceLocation(this.name);
/* 45 */     $$0.writeResourceLocation(this.target);
/* 46 */     $$0.writeResourceLocation(this.pool);
/* 47 */     $$0.writeUtf(this.finalState);
/* 48 */     $$0.writeUtf(this.joint.getSerializedName());
/* 49 */     $$0.writeVarInt(this.selectionPriority);
/* 50 */     $$0.writeVarInt(this.placementPriority);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 55 */     $$0.handleSetJigsawBlock(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 59 */     return this.pos;
/*    */   }
/*    */   
/*    */   public ResourceLocation getName() {
/* 63 */     return this.name;
/*    */   }
/*    */   
/*    */   public ResourceLocation getTarget() {
/* 67 */     return this.target;
/*    */   }
/*    */   
/*    */   public ResourceLocation getPool() {
/* 71 */     return this.pool;
/*    */   }
/*    */   
/*    */   public String getFinalState() {
/* 75 */     return this.finalState;
/*    */   }
/*    */   
/*    */   public JigsawBlockEntity.JointType getJoint() {
/* 79 */     return this.joint;
/*    */   }
/*    */   
/*    */   public int getSelectionPriority() {
/* 83 */     return this.selectionPriority;
/*    */   }
/*    */   
/*    */   public int getPlacementPriority() {
/* 87 */     return this.placementPriority;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundSetJigsawBlockPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */