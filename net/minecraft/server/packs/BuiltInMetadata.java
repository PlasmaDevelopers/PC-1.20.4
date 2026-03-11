/*    */ package net.minecraft.server.packs;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ 
/*    */ public class BuiltInMetadata
/*    */ {
/*  8 */   private static final BuiltInMetadata EMPTY = new BuiltInMetadata(Map.of());
/*    */   
/*    */   private final Map<MetadataSectionSerializer<?>, ?> values;
/*    */   
/*    */   private BuiltInMetadata(Map<MetadataSectionSerializer<?>, ?> $$0) {
/* 13 */     this.values = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T get(MetadataSectionSerializer<T> $$0) {
/* 18 */     return (T)this.values.get($$0);
/*    */   }
/*    */   
/*    */   public static BuiltInMetadata of() {
/* 22 */     return EMPTY;
/*    */   }
/*    */   
/*    */   public static <T> BuiltInMetadata of(MetadataSectionSerializer<T> $$0, T $$1) {
/* 26 */     return new BuiltInMetadata(Map.of($$0, $$1));
/*    */   }
/*    */   
/*    */   public static <T1, T2> BuiltInMetadata of(MetadataSectionSerializer<T1> $$0, T1 $$1, MetadataSectionSerializer<T2> $$2, T2 $$3) {
/* 30 */     return new BuiltInMetadata(Map.of($$0, $$1, $$2, $$3));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\BuiltInMetadata.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */