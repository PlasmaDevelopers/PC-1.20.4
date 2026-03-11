/*    */ package net.minecraft.client.tutorial;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public enum TutorialSteps {
/*  6 */   MOVEMENT("movement", MovementTutorialStepInstance::new),
/*  7 */   FIND_TREE("find_tree", FindTreeTutorialStepInstance::new),
/*  8 */   PUNCH_TREE("punch_tree", PunchTreeTutorialStepInstance::new),
/*  9 */   OPEN_INVENTORY("open_inventory", OpenInventoryTutorialStep::new),
/* 10 */   CRAFT_PLANKS("craft_planks", CraftPlanksTutorialStep::new),
/* 11 */   NONE("none", CompletedTutorialStepInstance::new);
/*    */   
/*    */   private final String name;
/*    */   
/*    */   private final Function<Tutorial, ? extends TutorialStepInstance> constructor;
/*    */   
/*    */   <T extends TutorialStepInstance> TutorialSteps(String $$0, Function<Tutorial, T> $$1) {
/* 18 */     this.name = $$0;
/* 19 */     this.constructor = $$1;
/*    */   }
/*    */   
/*    */   public TutorialStepInstance create(Tutorial $$0) {
/* 23 */     return this.constructor.apply($$0);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 27 */     return this.name;
/*    */   }
/*    */   
/*    */   public static TutorialSteps getByName(String $$0) {
/* 31 */     for (TutorialSteps $$1 : values()) {
/* 32 */       if ($$1.name.equals($$0)) {
/* 33 */         return $$1;
/*    */       }
/*    */     } 
/* 36 */     return NONE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\tutorial\TutorialSteps.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */