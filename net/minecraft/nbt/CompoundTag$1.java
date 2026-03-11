/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInput;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
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
/*     */   implements TagType.VariableSize<CompoundTag>
/*     */ {
/*     */   public CompoundTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  61 */     $$1.pushDepth();
/*     */     try {
/*  63 */       return loadCompound($$0, $$1);
/*     */     } finally {
/*  65 */       $$1.popDepth();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static CompoundTag loadCompound(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  70 */     $$1.accountBytes(48L);
/*     */     
/*  72 */     Map<String, Tag> $$2 = Maps.newHashMap();
/*     */     byte $$3;
/*  74 */     while (($$3 = $$0.readByte()) != 0) {
/*  75 */       String $$4 = readString($$0, $$1);
/*  76 */       Tag $$5 = CompoundTag.readNamedTagData(TagTypes.getType($$3), $$4, $$0, $$1);
/*  77 */       if ($$2.put($$4, $$5) == null) {
/*  78 */         $$1.accountBytes(36L);
/*     */       }
/*     */     } 
/*  81 */     return new CompoundTag($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  86 */     $$2.pushDepth();
/*     */     try {
/*  88 */       return parseCompound($$0, $$1, $$2);
/*     */     } finally {
/*  90 */       $$2.popDepth();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static StreamTagVisitor.ValueResult parseCompound(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  95 */     $$2.accountBytes(48L);
/*     */ 
/*     */     
/*     */     byte $$3;
/*     */     
/* 100 */     while (($$3 = $$0.readByte()) != 0) {
/* 101 */       TagType<?> $$4 = TagTypes.getType($$3);
/*     */       
/* 103 */       switch (CompoundTag.null.$SwitchMap$net$minecraft$nbt$StreamTagVisitor$EntryResult[$$1.visitEntry($$4).ordinal()]) {
/*     */         case 1:
/* 105 */           return StreamTagVisitor.ValueResult.HALT;
/*     */         case 2:
/* 107 */           StringTag.skipString($$0);
/* 108 */           $$4.skip($$0, $$2);
/*     */           break;
/*     */         case 3:
/* 111 */           StringTag.skipString($$0);
/* 112 */           $$4.skip($$0, $$2);
/*     */           continue;
/*     */       } 
/*     */       
/* 116 */       String $$5 = readString($$0, $$2);
/* 117 */       switch (CompoundTag.null.$SwitchMap$net$minecraft$nbt$StreamTagVisitor$EntryResult[$$1.visitEntry($$4, $$5).ordinal()]) {
/*     */         case 1:
/* 119 */           return StreamTagVisitor.ValueResult.HALT;
/*     */         case 2:
/* 121 */           $$4.skip($$0, $$2);
/*     */           break;
/*     */         case 3:
/* 124 */           $$4.skip($$0, $$2);
/*     */           continue;
/*     */       } 
/*     */       
/* 128 */       $$2.accountBytes(36L);
/* 129 */       switch (CompoundTag.null.$SwitchMap$net$minecraft$nbt$StreamTagVisitor$ValueResult[$$4.parse($$0, $$1, $$2).ordinal()]) {
/*     */         case 1:
/* 131 */           return StreamTagVisitor.ValueResult.HALT;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 137 */     if ($$3 != 0) {
/* 138 */       while (($$3 = $$0.readByte()) != 0) {
/* 139 */         StringTag.skipString($$0);
/* 140 */         TagTypes.getType($$3).skip($$0, $$2);
/*     */       } 
/*     */     }
/*     */     
/* 144 */     return $$1.visitContainerEnd();
/*     */   }
/*     */   
/*     */   private static String readString(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 148 */     String $$2 = $$0.readUTF();
/* 149 */     $$1.accountBytes(28L);
/* 150 */     $$1.accountBytes(2L, $$2.length());
/* 151 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 156 */     $$1.pushDepth();
/*     */     try {
/*     */       byte $$2;
/* 159 */       while (($$2 = $$0.readByte()) != 0) {
/* 160 */         StringTag.skipString($$0);
/* 161 */         TagTypes.getType($$2).skip($$0, $$1);
/*     */       } 
/*     */     } finally {
/* 164 */       $$1.popDepth();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 170 */     return "COMPOUND";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrettyName() {
/* 175 */     return "TAG_Compound";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\CompoundTag$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */