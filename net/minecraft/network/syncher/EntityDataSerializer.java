/*    */ package net.minecraft.network.syncher;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface EntityDataSerializer<T>
/*    */ {
/*    */   default EntityDataAccessor<T> createAccessor(int $$0) {
/* 14 */     return new EntityDataAccessor<>($$0, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public static interface ForValueType<T>
/*    */     extends EntityDataSerializer<T>
/*    */   {
/*    */     default T copy(T $$0) {
/* 22 */       return $$0;
/*    */     }
/*    */   }
/*    */   
/*    */   static <T> EntityDataSerializer<T> simple(final FriendlyByteBuf.Writer<T> writer, final FriendlyByteBuf.Reader<T> reader) {
/* 27 */     return new ForValueType<T>()
/*    */       {
/*    */         public void write(FriendlyByteBuf $$0, T $$1) {
/* 30 */           writer.accept($$0, $$1);
/*    */         }
/*    */ 
/*    */         
/*    */         public T read(FriendlyByteBuf $$0) {
/* 35 */           return (T)reader.apply($$0);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static <T> EntityDataSerializer<Optional<T>> optional(FriendlyByteBuf.Writer<T> $$0, FriendlyByteBuf.Reader<T> $$1) {
/* 41 */     return simple($$0.asOptional(), $$1.asOptional());
/*    */   }
/*    */   
/*    */   static <T extends Enum<T>> EntityDataSerializer<T> simpleEnum(Class<T> $$0) {
/* 45 */     return simple(FriendlyByteBuf::writeEnum, $$1 -> $$1.readEnum($$0));
/*    */   }
/*    */   
/*    */   static <T> EntityDataSerializer<T> simpleId(IdMap<T> $$0) {
/* 49 */     return simple(($$1, $$2) -> $$1.writeId($$0, $$2), $$1 -> $$1.readById($$0));
/*    */   }
/*    */   
/*    */   void write(FriendlyByteBuf paramFriendlyByteBuf, T paramT);
/*    */   
/*    */   T read(FriendlyByteBuf paramFriendlyByteBuf);
/*    */   
/*    */   T copy(T paramT);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\syncher\EntityDataSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */