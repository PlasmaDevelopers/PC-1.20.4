/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInput;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements TagType.VariableSize<ListTag>
/*     */ {
/*     */   public ListTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  29 */     $$1.pushDepth();
/*     */     try {
/*  31 */       return loadList($$0, $$1);
/*     */     } finally {
/*  33 */       $$1.popDepth();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ListTag loadList(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  38 */     $$1.accountBytes(37L);
/*  39 */     byte $$2 = $$0.readByte();
/*  40 */     int $$3 = $$0.readInt();
/*  41 */     if ($$2 == 0 && $$3 > 0) {
/*  42 */       throw new NbtFormatException("Missing type on ListTag");
/*     */     }
/*  44 */     $$1.accountBytes(4L, $$3);
/*  45 */     TagType<?> $$4 = TagTypes.getType($$2);
/*  46 */     List<Tag> $$5 = Lists.newArrayListWithCapacity($$3);
/*  47 */     for (int $$6 = 0; $$6 < $$3; $$6++) {
/*  48 */       $$5.add((Tag)$$4.load($$0, $$1));
/*     */     }
/*  50 */     return new ListTag($$5, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  55 */     $$2.pushDepth();
/*     */     try {
/*  57 */       return parseList($$0, $$1, $$2);
/*     */     } finally {
/*  59 */       $$2.popDepth();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static StreamTagVisitor.ValueResult parseList(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  64 */     $$2.accountBytes(37L);
/*  65 */     TagType<?> $$3 = TagTypes.getType($$0.readByte());
/*  66 */     int $$4 = $$0.readInt();
/*  67 */     switch (ListTag.null.$SwitchMap$net$minecraft$nbt$StreamTagVisitor$ValueResult[$$1.visitList($$3, $$4).ordinal()]) {
/*     */       case 1:
/*  69 */         return StreamTagVisitor.ValueResult.HALT;
/*     */       case 2:
/*  71 */         $$3.skip($$0, $$4, $$2);
/*  72 */         return $$1.visitContainerEnd();
/*     */     } 
/*     */     
/*  75 */     $$2.accountBytes(4L, $$4);
/*     */     
/*     */     int $$5;
/*  78 */     for ($$5 = 0; $$5 < $$4; $$5++) {
/*  79 */       switch (ListTag.null.$SwitchMap$net$minecraft$nbt$StreamTagVisitor$EntryResult[$$1.visitElement($$3, $$5).ordinal()]) {
/*     */         case 1:
/*  81 */           return StreamTagVisitor.ValueResult.HALT;
/*     */         case 2:
/*  83 */           $$3.skip($$0, $$2);
/*     */           break;
/*     */         case 3:
/*  86 */           $$3.skip($$0, $$2);
/*     */           break;
/*     */         
/*     */         default:
/*  90 */           switch (ListTag.null.$SwitchMap$net$minecraft$nbt$StreamTagVisitor$ValueResult[$$3.parse($$0, $$1, $$2).ordinal()]) {
/*     */             case 1:
/*  92 */               return StreamTagVisitor.ValueResult.HALT;
/*     */             case 2:
/*     */               break;
/*     */           }  break;
/*     */       } 
/*  97 */     }  int $$6 = $$4 - 1 - $$5;
/*  98 */     if ($$6 > 0) {
/*  99 */       $$3.skip($$0, $$6, $$2);
/*     */     }
/* 101 */     return $$1.visitContainerEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 106 */     $$1.pushDepth();
/*     */     try {
/* 108 */       TagType<?> $$2 = TagTypes.getType($$0.readByte());
/* 109 */       int $$3 = $$0.readInt();
/* 110 */       $$2.skip($$0, $$3, $$1);
/*     */     } finally {
/* 112 */       $$1.popDepth();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 118 */     return "LIST";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrettyName() {
/* 123 */     return "TAG_List";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\ListTag$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */