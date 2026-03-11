/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*    */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*    */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*    */ 
/*    */ public class ClientboundUpdateAttributesPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int entityId;
/*    */   private final List<AttributeSnapshot> attributes;
/*    */   
/*    */   public ClientboundUpdateAttributesPacket(int $$0, Collection<AttributeInstance> $$1) {
/* 20 */     this.entityId = $$0;
/*    */     
/* 22 */     this.attributes = Lists.newArrayList();
/* 23 */     for (AttributeInstance $$2 : $$1) {
/* 24 */       this.attributes.add(new AttributeSnapshot($$2.getAttribute(), $$2.getBaseValue(), $$2.getModifiers()));
/*    */     }
/*    */   }
/*    */   
/*    */   public ClientboundUpdateAttributesPacket(FriendlyByteBuf $$0) {
/* 29 */     this.entityId = $$0.readVarInt();
/*    */     
/* 31 */     this.attributes = $$0.readList($$0 -> {
/*    */           ResourceLocation $$1 = $$0.readResourceLocation();
/*    */           Attribute $$2 = (Attribute)BuiltInRegistries.ATTRIBUTE.get($$1);
/*    */           double $$3 = $$0.readDouble();
/*    */           List<AttributeModifier> $$4 = $$0.readList(());
/*    */           return new AttributeSnapshot($$2, $$3, $$4);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 44 */     $$0.writeVarInt(this.entityId);
/*    */     
/* 46 */     $$0.writeCollection(this.attributes, ($$0, $$1) -> {
/*    */           $$0.writeResourceLocation(BuiltInRegistries.ATTRIBUTE.getKey($$1.getAttribute()));
/*    */           $$0.writeDouble($$1.getBase());
/*    */           $$0.writeCollection($$1.getModifiers(), ());
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 59 */     $$0.handleUpdateAttributes(this);
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 63 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public List<AttributeSnapshot> getValues() {
/* 67 */     return this.attributes;
/*    */   }
/*    */   
/*    */   public static class AttributeSnapshot {
/*    */     private final Attribute attribute;
/*    */     private final double base;
/*    */     private final Collection<AttributeModifier> modifiers;
/*    */     
/*    */     public AttributeSnapshot(Attribute $$0, double $$1, Collection<AttributeModifier> $$2) {
/* 76 */       this.attribute = $$0;
/* 77 */       this.base = $$1;
/* 78 */       this.modifiers = $$2;
/*    */     }
/*    */     
/*    */     public Attribute getAttribute() {
/* 82 */       return this.attribute;
/*    */     }
/*    */     
/*    */     public double getBase() {
/* 86 */       return this.base;
/*    */     }
/*    */     
/*    */     public Collection<AttributeModifier> getModifiers() {
/* 90 */       return this.modifiers;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundUpdateAttributesPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */