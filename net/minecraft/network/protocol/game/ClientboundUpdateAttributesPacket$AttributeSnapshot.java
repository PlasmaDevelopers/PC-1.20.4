/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*    */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeSnapshot
/*    */ {
/*    */   private final Attribute attribute;
/*    */   private final double base;
/*    */   private final Collection<AttributeModifier> modifiers;
/*    */   
/*    */   public AttributeSnapshot(Attribute $$0, double $$1, Collection<AttributeModifier> $$2) {
/* 76 */     this.attribute = $$0;
/* 77 */     this.base = $$1;
/* 78 */     this.modifiers = $$2;
/*    */   }
/*    */   
/*    */   public Attribute getAttribute() {
/* 82 */     return this.attribute;
/*    */   }
/*    */   
/*    */   public double getBase() {
/* 86 */     return this.base;
/*    */   }
/*    */   
/*    */   public Collection<AttributeModifier> getModifiers() {
/* 90 */     return this.modifiers;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundUpdateAttributesPacket$AttributeSnapshot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */