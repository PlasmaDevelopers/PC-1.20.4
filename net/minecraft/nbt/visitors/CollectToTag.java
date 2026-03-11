/*     */ package net.minecraft.nbt.visitors;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.ByteArrayTag;
/*     */ import net.minecraft.nbt.ByteTag;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.DoubleTag;
/*     */ import net.minecraft.nbt.EndTag;
/*     */ import net.minecraft.nbt.FloatTag;
/*     */ import net.minecraft.nbt.IntArrayTag;
/*     */ import net.minecraft.nbt.IntTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.LongArrayTag;
/*     */ import net.minecraft.nbt.LongTag;
/*     */ import net.minecraft.nbt.ShortTag;
/*     */ import net.minecraft.nbt.StreamTagVisitor;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.nbt.TagType;
/*     */ 
/*     */ public class CollectToTag implements StreamTagVisitor {
/*  26 */   private String lastId = "";
/*     */   
/*     */   @Nullable
/*     */   private Tag rootTag;
/*  30 */   private final Deque<Consumer<Tag>> consumerStack = new ArrayDeque<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Tag getResult() {
/*  37 */     return this.rootTag;
/*     */   }
/*     */   
/*     */   protected int depth() {
/*  41 */     return this.consumerStack.size();
/*     */   }
/*     */   
/*     */   private void appendEntry(Tag $$0) {
/*  45 */     ((Consumer<Tag>)this.consumerStack.getLast()).accept($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visitEnd() {
/*  50 */     appendEntry((Tag)EndTag.INSTANCE);
/*  51 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visit(String $$0) {
/*  56 */     appendEntry((Tag)StringTag.valueOf($$0));
/*  57 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visit(byte $$0) {
/*  62 */     appendEntry((Tag)ByteTag.valueOf($$0));
/*  63 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visit(short $$0) {
/*  68 */     appendEntry((Tag)ShortTag.valueOf($$0));
/*  69 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visit(int $$0) {
/*  74 */     appendEntry((Tag)IntTag.valueOf($$0));
/*  75 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visit(long $$0) {
/*  80 */     appendEntry((Tag)LongTag.valueOf($$0));
/*  81 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visit(float $$0) {
/*  86 */     appendEntry((Tag)FloatTag.valueOf($$0));
/*  87 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visit(double $$0) {
/*  92 */     appendEntry((Tag)DoubleTag.valueOf($$0));
/*  93 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visit(byte[] $$0) {
/*  98 */     appendEntry((Tag)new ByteArrayTag($$0));
/*  99 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visit(int[] $$0) {
/* 104 */     appendEntry((Tag)new IntArrayTag($$0));
/* 105 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visit(long[] $$0) {
/* 110 */     appendEntry((Tag)new LongArrayTag($$0));
/* 111 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visitList(TagType<?> $$0, int $$1) {
/* 116 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.EntryResult visitElement(TagType<?> $$0, int $$1) {
/* 121 */     enterContainerIfNeeded($$0);
/* 122 */     return StreamTagVisitor.EntryResult.ENTER;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.EntryResult visitEntry(TagType<?> $$0) {
/* 127 */     return StreamTagVisitor.EntryResult.ENTER;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.EntryResult visitEntry(TagType<?> $$0, String $$1) {
/* 132 */     this.lastId = $$1;
/* 133 */     enterContainerIfNeeded($$0);
/* 134 */     return StreamTagVisitor.EntryResult.ENTER;
/*     */   }
/*     */   
/*     */   private void enterContainerIfNeeded(TagType<?> $$0) {
/* 138 */     if ($$0 == ListTag.TYPE) {
/* 139 */       ListTag $$1 = new ListTag();
/* 140 */       appendEntry((Tag)$$1);
/* 141 */       Objects.requireNonNull($$1); this.consumerStack.addLast($$1::add);
/* 142 */     } else if ($$0 == CompoundTag.TYPE) {
/* 143 */       CompoundTag $$2 = new CompoundTag();
/* 144 */       appendEntry((Tag)$$2);
/* 145 */       this.consumerStack.addLast($$1 -> $$0.put(this.lastId, $$1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visitContainerEnd() {
/* 151 */     this.consumerStack.removeLast();
/* 152 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult visitRootEntry(TagType<?> $$0) {
/* 157 */     if ($$0 == ListTag.TYPE) {
/* 158 */       ListTag $$1 = new ListTag();
/* 159 */       this.rootTag = (Tag)$$1;
/* 160 */       Objects.requireNonNull($$1); this.consumerStack.addLast($$1::add);
/* 161 */     } else if ($$0 == CompoundTag.TYPE) {
/* 162 */       CompoundTag $$2 = new CompoundTag();
/* 163 */       this.rootTag = (Tag)$$2;
/* 164 */       this.consumerStack.addLast($$1 -> $$0.put(this.lastId, $$1));
/*     */     } else {
/* 166 */       this.consumerStack.addLast($$0 -> this.rootTag = $$0);
/*     */     } 
/* 168 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\visitors\CollectToTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */