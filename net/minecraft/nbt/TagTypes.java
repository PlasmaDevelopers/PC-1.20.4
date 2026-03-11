/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ public class TagTypes {
/*  4 */   private static final TagType<?>[] TYPES = new TagType[] { EndTag.TYPE, ByteTag.TYPE, ShortTag.TYPE, IntTag.TYPE, LongTag.TYPE, FloatTag.TYPE, DoubleTag.TYPE, ByteArrayTag.TYPE, StringTag.TYPE, ListTag.TYPE, CompoundTag.TYPE, IntArrayTag.TYPE, LongArrayTag.TYPE };
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
/*    */   public static TagType<?> getType(int $$0) {
/* 21 */     if ($$0 < 0 || $$0 >= TYPES.length) {
/* 22 */       return TagType.createInvalid($$0);
/*    */     }
/*    */     
/* 25 */     return TYPES[$$0];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\TagTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */