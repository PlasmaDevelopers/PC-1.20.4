/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*    */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
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
/*    */ public class ModifierBuilder
/*    */ {
/*    */   private final String name;
/*    */   private final Holder<Attribute> attribute;
/*    */   private final AttributeModifier.Operation operation;
/*    */   private final NumberProvider amount;
/* 70 */   private Optional<UUID> id = Optional.empty();
/* 71 */   private final Set<EquipmentSlot> slots = EnumSet.noneOf(EquipmentSlot.class);
/*    */   
/*    */   public ModifierBuilder(String $$0, Holder<Attribute> $$1, AttributeModifier.Operation $$2, NumberProvider $$3) {
/* 74 */     this.name = $$0;
/* 75 */     this.attribute = $$1;
/* 76 */     this.operation = $$2;
/* 77 */     this.amount = $$3;
/*    */   }
/*    */   
/*    */   public ModifierBuilder forSlot(EquipmentSlot $$0) {
/* 81 */     this.slots.add($$0);
/* 82 */     return this;
/*    */   }
/*    */   
/*    */   public ModifierBuilder withUuid(UUID $$0) {
/* 86 */     this.id = Optional.of($$0);
/* 87 */     return this;
/*    */   }
/*    */   
/*    */   public SetAttributesFunction.Modifier build() {
/* 91 */     return new SetAttributesFunction.Modifier(this.name, this.attribute, this.operation, this.amount, List.copyOf(this.slots), this.id);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetAttributesFunction$ModifierBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */