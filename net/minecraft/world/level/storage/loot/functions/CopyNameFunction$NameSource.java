/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
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
/*    */ 
/*    */ 
/*    */ public enum NameSource
/*    */   implements StringRepresentable
/*    */ {
/*    */   public static final Codec<NameSource> CODEC;
/* 55 */   THIS("this", LootContextParams.THIS_ENTITY),
/* 56 */   KILLER("killer", LootContextParams.KILLER_ENTITY),
/* 57 */   KILLER_PLAYER("killer_player", LootContextParams.LAST_DAMAGE_PLAYER),
/* 58 */   BLOCK_ENTITY("block_entity", LootContextParams.BLOCK_ENTITY);
/*    */   static {
/* 60 */     CODEC = (Codec<NameSource>)StringRepresentable.fromEnum(NameSource::values);
/*    */   }
/*    */   private final String name;
/*    */   final LootContextParam<?> param;
/*    */   
/*    */   NameSource(String $$0, LootContextParam<?> $$1) {
/* 66 */     this.name = $$0;
/* 67 */     this.param = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 72 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\CopyNameFunction$NameSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */