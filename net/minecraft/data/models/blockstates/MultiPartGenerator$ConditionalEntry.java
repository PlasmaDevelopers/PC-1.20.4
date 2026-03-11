/*     */ package net.minecraft.data.models.blockstates;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.List;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
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
/*     */ class ConditionalEntry
/*     */   extends MultiPartGenerator.Entry
/*     */ {
/*     */   private final Condition condition;
/*     */   
/*     */   ConditionalEntry(Condition $$0, List<Variant> $$1) {
/*  93 */     super($$1);
/*  94 */     this.condition = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate(StateDefinition<?, ?> $$0) {
/*  99 */     this.condition.validate($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decorate(JsonObject $$0) {
/* 104 */     $$0.add("when", this.condition.get());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\MultiPartGenerator$ConditionalEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */