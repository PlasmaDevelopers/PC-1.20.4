/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public class PoiTypeRenameFix extends AbstractPoiSectionFix {
/*    */   private final Function<String, String> renamer;
/*    */   
/*    */   public PoiTypeRenameFix(Schema $$0, String $$1, Function<String, String> $$2) {
/* 14 */     super($$0, $$1);
/* 15 */     this.renamer = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected <T> Stream<Dynamic<T>> processRecords(Stream<Dynamic<T>> $$0) {
/* 20 */     return $$0.map($$0 -> $$0.update("type", ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\PoiTypeRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */