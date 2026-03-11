/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class EntityPaintingMotiveFix extends NamedEntityFix {
/*    */   public EntityPaintingMotiveFix(Schema $$0, boolean $$1) {
/* 17 */     super($$0, $$1, "EntityPaintingMotiveFix", References.ENTITY, "minecraft:painting");
/*    */   } private static final Map<String, String> MAP;
/*    */   static {
/* 20 */     MAP = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), $$0 -> {
/*    */           $$0.put("donkeykong", "donkey_kong");
/*    */           $$0.put("burningskull", "burning_skull");
/*    */           $$0.put("skullandroses", "skull_and_roses");
/*    */         });
/*    */   }
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 27 */     Optional<String> $$1 = $$0.get("Motive").asString().result();
/* 28 */     if ($$1.isPresent()) {
/* 29 */       String $$2 = ((String)$$1.get()).toLowerCase(Locale.ROOT);
/* 30 */       return $$0.set("Motive", $$0.createString((new ResourceLocation(MAP.getOrDefault($$2, $$2))).toString()));
/*    */     } 
/* 32 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 37 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityPaintingMotiveFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */