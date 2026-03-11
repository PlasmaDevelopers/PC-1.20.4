/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ServerboundInteractPacket implements Packet<ServerGamePacketListener> {
/*     */   private final int entityId;
/*     */   private final Action action;
/*     */   private final boolean usingSecondaryAction;
/*     */   
/*     */   private ServerboundInteractPacket(int $$0, boolean $$1, Action $$2) {
/*  19 */     this.entityId = $$0;
/*  20 */     this.action = $$2;
/*  21 */     this.usingSecondaryAction = $$1;
/*     */   }
/*     */   
/*     */   public static ServerboundInteractPacket createAttackPacket(Entity $$0, boolean $$1) {
/*  25 */     return new ServerboundInteractPacket($$0.getId(), $$1, ATTACK_ACTION);
/*     */   }
/*     */   
/*     */   public static ServerboundInteractPacket createInteractionPacket(Entity $$0, boolean $$1, InteractionHand $$2) {
/*  29 */     return new ServerboundInteractPacket($$0.getId(), $$1, new InteractionAction($$2));
/*     */   }
/*     */   
/*     */   public static ServerboundInteractPacket createInteractionPacket(Entity $$0, boolean $$1, InteractionHand $$2, Vec3 $$3) {
/*  33 */     return new ServerboundInteractPacket($$0.getId(), $$1, new InteractionAtLocationAction($$2, $$3));
/*     */   }
/*     */   
/*     */   public ServerboundInteractPacket(FriendlyByteBuf $$0) {
/*  37 */     this.entityId = $$0.readVarInt();
/*  38 */     ActionType $$1 = (ActionType)$$0.readEnum(ActionType.class);
/*  39 */     this.action = $$1.reader.apply($$0);
/*  40 */     this.usingSecondaryAction = $$0.readBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  45 */     $$0.writeVarInt(this.entityId);
/*  46 */     $$0.writeEnum(this.action.getType());
/*  47 */     this.action.write($$0);
/*  48 */     $$0.writeBoolean(this.usingSecondaryAction);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ServerGamePacketListener $$0) {
/*  53 */     $$0.handleInteract(this);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity getTarget(ServerLevel $$0) {
/*  58 */     return $$0.getEntityOrPart(this.entityId);
/*     */   }
/*     */   
/*     */   public boolean isUsingSecondaryAction() {
/*  62 */     return this.usingSecondaryAction;
/*     */   }
/*     */   
/*     */   public void dispatch(Handler $$0) {
/*  66 */     this.action.dispatch($$0);
/*     */   }
/*     */   
/*     */   private enum ActionType {
/*  70 */     INTERACT((String)InteractionAction::new),
/*  71 */     ATTACK((String)($$0 -> ServerboundInteractPacket.ATTACK_ACTION)),
/*  72 */     INTERACT_AT((String)InteractionAtLocationAction::new);
/*     */     
/*     */     final Function<FriendlyByteBuf, ServerboundInteractPacket.Action> reader;
/*     */ 
/*     */     
/*     */     ActionType(Function<FriendlyByteBuf, ServerboundInteractPacket.Action> $$0) {
/*  78 */       this.reader = $$0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class InteractionAction
/*     */     implements Action
/*     */   {
/*     */     private final InteractionHand hand;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     InteractionAction(InteractionHand $$0) {
/* 102 */       this.hand = $$0;
/*     */     }
/*     */     
/*     */     private InteractionAction(FriendlyByteBuf $$0) {
/* 106 */       this.hand = (InteractionHand)$$0.readEnum(InteractionHand.class);
/*     */     }
/*     */ 
/*     */     
/*     */     public ServerboundInteractPacket.ActionType getType() {
/* 111 */       return ServerboundInteractPacket.ActionType.INTERACT;
/*     */     }
/*     */ 
/*     */     
/*     */     public void dispatch(ServerboundInteractPacket.Handler $$0) {
/* 116 */       $$0.onInteraction(this.hand);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 121 */       $$0.writeEnum((Enum)this.hand);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class InteractionAtLocationAction implements Action {
/*     */     private final InteractionHand hand;
/*     */     private final Vec3 location;
/*     */     
/*     */     InteractionAtLocationAction(InteractionHand $$0, Vec3 $$1) {
/* 130 */       this.hand = $$0;
/* 131 */       this.location = $$1;
/*     */     }
/*     */     
/*     */     private InteractionAtLocationAction(FriendlyByteBuf $$0) {
/* 135 */       this.location = new Vec3($$0.readFloat(), $$0.readFloat(), $$0.readFloat());
/* 136 */       this.hand = (InteractionHand)$$0.readEnum(InteractionHand.class);
/*     */     }
/*     */ 
/*     */     
/*     */     public ServerboundInteractPacket.ActionType getType() {
/* 141 */       return ServerboundInteractPacket.ActionType.INTERACT_AT;
/*     */     }
/*     */ 
/*     */     
/*     */     public void dispatch(ServerboundInteractPacket.Handler $$0) {
/* 146 */       $$0.onInteraction(this.hand, this.location);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 151 */       $$0.writeFloat((float)this.location.x);
/* 152 */       $$0.writeFloat((float)this.location.y);
/* 153 */       $$0.writeFloat((float)this.location.z);
/* 154 */       $$0.writeEnum((Enum)this.hand);
/*     */     }
/*     */   }
/*     */   
/* 158 */   static final Action ATTACK_ACTION = new Action()
/*     */     {
/*     */       public ServerboundInteractPacket.ActionType getType() {
/* 161 */         return ServerboundInteractPacket.ActionType.ATTACK;
/*     */       }
/*     */ 
/*     */       
/*     */       public void dispatch(ServerboundInteractPacket.Handler $$0) {
/* 166 */         $$0.onAttack();
/*     */       }
/*     */       
/*     */       public void write(FriendlyByteBuf $$0) {}
/*     */     };
/*     */   
/*     */   private static interface Action {
/*     */     ServerboundInteractPacket.ActionType getType();
/*     */     
/*     */     void dispatch(ServerboundInteractPacket.Handler param1Handler);
/*     */     
/*     */     void write(FriendlyByteBuf param1FriendlyByteBuf);
/*     */   }
/*     */   
/*     */   public static interface Handler {
/*     */     void onInteraction(InteractionHand param1InteractionHand);
/*     */     
/*     */     void onInteraction(InteractionHand param1InteractionHand, Vec3 param1Vec3);
/*     */     
/*     */     void onAttack();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundInteractPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */