/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*    */ 
/*    */ public class Stat<T>
/*    */   extends ObjectiveCriteria {
/*    */   private final StatFormatter formatter;
/*    */   private final T value;
/*    */   private final StatType<T> type;
/*    */   
/*    */   protected Stat(StatType<T> $$0, T $$1, StatFormatter $$2) {
/* 16 */     super(buildName($$0, $$1));
/* 17 */     this.type = $$0;
/* 18 */     this.formatter = $$2;
/* 19 */     this.value = $$1;
/*    */   }
/*    */   
/*    */   public static <T> String buildName(StatType<T> $$0, T $$1) {
/* 23 */     return locationToKey(BuiltInRegistries.STAT_TYPE.getKey($$0)) + ":" + locationToKey(BuiltInRegistries.STAT_TYPE.getKey($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static <T> String locationToKey(@Nullable ResourceLocation $$0) {
/* 29 */     return $$0.toString().replace(':', '.');
/*    */   }
/*    */   
/*    */   public StatType<T> getType() {
/* 33 */     return this.type;
/*    */   }
/*    */   
/*    */   public T getValue() {
/* 37 */     return this.value;
/*    */   }
/*    */   
/*    */   public String format(int $$0) {
/* 41 */     return this.formatter.format($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 46 */     return (this == $$0 || ($$0 instanceof Stat && Objects.equals(getName(), ((Stat)$$0).getName())));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 51 */     return getName().hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return "Stat{name=" + getName() + ", formatter=" + this.formatter + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\stats\Stat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */