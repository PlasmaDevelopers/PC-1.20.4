/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
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
/*    */ @Deprecated
/*    */ public class EnumCodec<E extends Enum<E> & StringRepresentable>
/*    */   extends StringRepresentable.StringRepresentableCodec<E>
/*    */ {
/*    */   private final Function<String, E> resolver;
/*    */   
/*    */   public EnumCodec(E[] $$0, Function<String, E> $$1) {
/* 55 */     super($$0, $$1, $$0 -> ((Enum)$$0).ordinal());
/* 56 */     this.resolver = $$1;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public E byName(@Nullable String $$0) {
/* 61 */     return this.resolver.apply($$0);
/*    */   }
/*    */   
/*    */   public E byName(@Nullable String $$0, E $$1) {
/* 65 */     return (E)Objects.<Enum>requireNonNullElse((Enum)byName($$0), (Enum)$$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\StringRepresentable$EnumCodec.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */