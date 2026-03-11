/*    */ package net.minecraft.nbt.visitors;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ import java.util.Set;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.StreamTagVisitor;
/*    */ import net.minecraft.nbt.TagType;
/*    */ 
/*    */ public class CollectFields
/*    */   extends CollectToTag {
/*    */   private int fieldsToGetCount;
/*    */   private final Set<TagType<?>> wantedTypes;
/* 15 */   private final Deque<FieldTree> stack = new ArrayDeque<>();
/*    */   
/*    */   public CollectFields(FieldSelector... $$0) {
/* 18 */     this.fieldsToGetCount = $$0.length;
/*    */     
/* 20 */     ImmutableSet.Builder<TagType<?>> $$1 = ImmutableSet.builder();
/* 21 */     FieldTree $$2 = FieldTree.createRoot();
/* 22 */     for (FieldSelector $$3 : $$0) {
/* 23 */       $$2.addEntry($$3);
/* 24 */       $$1.add($$3.type());
/*    */     } 
/* 26 */     this.stack.push($$2);
/*    */     
/* 28 */     $$1.add(CompoundTag.TYPE);
/* 29 */     this.wantedTypes = (Set<TagType<?>>)$$1.build();
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult visitRootEntry(TagType<?> $$0) {
/* 34 */     if ($$0 != CompoundTag.TYPE) {
/* 35 */       return StreamTagVisitor.ValueResult.HALT;
/*    */     }
/* 37 */     return super.visitRootEntry($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.EntryResult visitEntry(TagType<?> $$0) {
/* 42 */     FieldTree $$1 = this.stack.element();
/* 43 */     if (depth() > $$1.depth()) {
/* 44 */       return super.visitEntry($$0);
/*    */     }
/* 46 */     if (this.fieldsToGetCount <= 0) {
/* 47 */       return StreamTagVisitor.EntryResult.HALT;
/*    */     }
/* 49 */     if (!this.wantedTypes.contains($$0)) {
/* 50 */       return StreamTagVisitor.EntryResult.SKIP;
/*    */     }
/* 52 */     return super.visitEntry($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.EntryResult visitEntry(TagType<?> $$0, String $$1) {
/* 57 */     FieldTree $$2 = this.stack.element();
/* 58 */     if (depth() > $$2.depth()) {
/* 59 */       return super.visitEntry($$0, $$1);
/*    */     }
/*    */     
/* 62 */     if ($$2.selectedFields().remove($$1, $$0)) {
/* 63 */       this.fieldsToGetCount--;
/* 64 */       return super.visitEntry($$0, $$1);
/*    */     } 
/*    */     
/* 67 */     if ($$0 == CompoundTag.TYPE) {
/* 68 */       FieldTree $$3 = $$2.fieldsToRecurse().get($$1);
/* 69 */       if ($$3 != null) {
/* 70 */         this.stack.push($$3);
/* 71 */         return super.visitEntry($$0, $$1);
/*    */       } 
/*    */     } 
/*    */     
/* 75 */     return StreamTagVisitor.EntryResult.SKIP;
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult visitContainerEnd() {
/* 80 */     if (depth() == ((FieldTree)this.stack.element()).depth()) {
/* 81 */       this.stack.pop();
/*    */     }
/* 83 */     return super.visitContainerEnd();
/*    */   }
/*    */   
/*    */   public int getMissingFieldCount() {
/* 87 */     return this.fieldsToGetCount;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\visitors\CollectFields.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */