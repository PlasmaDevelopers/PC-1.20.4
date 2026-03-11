/*     */ package net.minecraft.world.level.block.state;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
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
/*     */ public class Builder<O, S extends StateHolder<O, S>>
/*     */ {
/*     */   private final O owner;
/* 119 */   private final Map<String, Property<?>> properties = Maps.newHashMap();
/*     */   
/*     */   public Builder(O $$0) {
/* 122 */     this.owner = $$0;
/*     */   }
/*     */   
/*     */   public Builder<O, S> add(Property<?>... $$0) {
/* 126 */     for (Property<?> $$1 : $$0) {
/* 127 */       validateProperty($$1);
/* 128 */       this.properties.put($$1.getName(), $$1);
/*     */     } 
/* 130 */     return this;
/*     */   }
/*     */   
/*     */   private <T extends Comparable<T>> void validateProperty(Property<T> $$0) {
/* 134 */     String $$1 = $$0.getName();
/* 135 */     if (!StateDefinition.NAME_PATTERN.matcher($$1).matches()) {
/* 136 */       throw new IllegalArgumentException("" + this.owner + " has invalidly named property: " + this.owner);
/*     */     }
/*     */     
/* 139 */     Collection<T> $$2 = $$0.getPossibleValues();
/* 140 */     if ($$2.size() <= 1) {
/* 141 */       throw new IllegalArgumentException("" + this.owner + " attempted use property " + this.owner + " with <= 1 possible values");
/*     */     }
/*     */     
/* 144 */     for (Comparable comparable : $$2) {
/* 145 */       String $$4 = $$0.getName(comparable);
/* 146 */       if (!StateDefinition.NAME_PATTERN.matcher($$4).matches()) {
/* 147 */         throw new IllegalArgumentException("" + this.owner + " has property: " + this.owner + " with invalidly named value: " + $$1);
/*     */       }
/*     */     } 
/*     */     
/* 151 */     if (this.properties.containsKey($$1)) {
/* 152 */       throw new IllegalArgumentException("" + this.owner + " has duplicate property: " + this.owner);
/*     */     }
/*     */   }
/*     */   
/*     */   public StateDefinition<O, S> create(Function<O, S> $$0, StateDefinition.Factory<O, S> $$1) {
/* 157 */     return new StateDefinition<>($$0, this.owner, $$1, this.properties);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\StateDefinition$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */