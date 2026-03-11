/*    */ package net.minecraft.server.dedicated;
/*    */ 
/*    */ import java.util.Properties;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.RegistryAccess;
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
/*    */ public class MutableValue<V>
/*    */   implements Supplier<V>
/*    */ {
/*    */   private final String key;
/*    */   private final V value;
/*    */   private final Function<V, String> serializer;
/*    */   
/*    */   MutableValue(String $$1, V $$2, Function<V, String> $$3) {
/* 34 */     this.key = $$1;
/* 35 */     this.value = $$2;
/* 36 */     this.serializer = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public V get() {
/* 41 */     return this.value;
/*    */   }
/*    */   
/*    */   public T update(RegistryAccess $$0, V $$1) {
/* 45 */     Properties $$2 = Settings.this.cloneProperties();
/* 46 */     $$2.put(this.key, this.serializer.apply($$1));
/* 47 */     return (T)Settings.this.reload($$0, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\dedicated\Settings$MutableValue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */