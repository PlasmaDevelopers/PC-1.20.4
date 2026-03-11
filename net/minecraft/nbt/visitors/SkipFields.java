/*    */ package net.minecraft.nbt.visitors;
/*    */ 
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.StreamTagVisitor;
/*    */ import net.minecraft.nbt.TagType;
/*    */ 
/*    */ public class SkipFields extends CollectToTag {
/* 10 */   private final Deque<FieldTree> stack = new ArrayDeque<>();
/*    */   
/*    */   public SkipFields(FieldSelector... $$0) {
/* 13 */     FieldTree $$1 = FieldTree.createRoot();
/* 14 */     for (FieldSelector $$2 : $$0) {
/* 15 */       $$1.addEntry($$2);
/*    */     }
/* 17 */     this.stack.push($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.EntryResult visitEntry(TagType<?> $$0, String $$1) {
/* 22 */     FieldTree $$2 = this.stack.element();
/* 23 */     if ($$2.isSelected($$0, $$1)) {
/* 24 */       return StreamTagVisitor.EntryResult.SKIP;
/*    */     }
/*    */     
/* 27 */     if ($$0 == CompoundTag.TYPE) {
/* 28 */       FieldTree $$3 = $$2.fieldsToRecurse().get($$1);
/* 29 */       if ($$3 != null) {
/* 30 */         this.stack.push($$3);
/*    */       }
/*    */     } 
/*    */     
/* 34 */     return super.visitEntry($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult visitContainerEnd() {
/* 39 */     if (depth() == ((FieldTree)this.stack.element()).depth()) {
/* 40 */       this.stack.pop();
/*    */     }
/* 42 */     return super.visitContainerEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\visitors\SkipFields.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */