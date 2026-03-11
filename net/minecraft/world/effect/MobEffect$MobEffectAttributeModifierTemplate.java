/*     */ package net.minecraft.world.effect;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MobEffectAttributeModifierTemplate
/*     */   implements AttributeModifierTemplate
/*     */ {
/*     */   private final UUID id;
/*     */   private final double amount;
/*     */   private final AttributeModifier.Operation operation;
/*     */   
/*     */   public MobEffectAttributeModifierTemplate(UUID $$0, double $$1, AttributeModifier.Operation $$2) {
/* 148 */     this.id = $$0;
/* 149 */     this.amount = $$1;
/* 150 */     this.operation = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getAttributeModifierId() {
/* 155 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeModifier create(int $$0) {
/* 160 */     return new AttributeModifier(this.id, MobEffect.this.getDescriptionId() + " " + MobEffect.this.getDescriptionId(), this.amount * ($$0 + 1), this.operation);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\MobEffect$MobEffectAttributeModifierTemplate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */