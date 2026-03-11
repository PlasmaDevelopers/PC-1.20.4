/*    */ package net.minecraft.world.level.block.state;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
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
/*    */ class null
/*    */   implements Function<Map.Entry<Property<?>, Comparable<?>>, String>
/*    */ {
/*    */   public String apply(@Nullable Map.Entry<Property<?>, Comparable<?>> $$0) {
/* 28 */     if ($$0 == null) {
/* 29 */       return "<NULL>";
/*    */     }
/*    */     
/* 32 */     Property<?> $$1 = $$0.getKey();
/* 33 */     return $$1.getName() + "=" + $$1.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   private <T extends Comparable<T>> String getName(Property<T> $$0, Comparable<?> $$1) {
/* 38 */     return $$0.getName($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\StateHolder$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */