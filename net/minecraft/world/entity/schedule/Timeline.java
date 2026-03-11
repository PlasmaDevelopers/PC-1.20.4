/*    */ package net.minecraft.world.entity.schedule;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Lists;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Timeline
/*    */ {
/* 12 */   private final List<Keyframe> keyframes = Lists.newArrayList();
/*    */   private int previousIndex;
/*    */   
/*    */   public ImmutableList<Keyframe> getKeyframes() {
/* 16 */     return ImmutableList.copyOf(this.keyframes);
/*    */   }
/*    */   
/*    */   public Timeline addKeyframe(int $$0, float $$1) {
/* 20 */     this.keyframes.add(new Keyframe($$0, $$1));
/* 21 */     sortAndDeduplicateKeyframes();
/* 22 */     return this;
/*    */   }
/*    */   
/*    */   public Timeline addKeyframes(Collection<Keyframe> $$0) {
/* 26 */     this.keyframes.addAll($$0);
/* 27 */     sortAndDeduplicateKeyframes();
/* 28 */     return this;
/*    */   }
/*    */   
/*    */   private void sortAndDeduplicateKeyframes() {
/* 32 */     Int2ObjectAVLTreeMap int2ObjectAVLTreeMap = new Int2ObjectAVLTreeMap();
/* 33 */     this.keyframes.forEach($$1 -> $$0.put($$1.getTimeStamp(), $$1));
/*    */     
/* 35 */     this.keyframes.clear();
/* 36 */     this.keyframes.addAll((Collection<? extends Keyframe>)int2ObjectAVLTreeMap.values());
/*    */     
/* 38 */     this.previousIndex = 0;
/*    */   }
/*    */   
/*    */   public float getValueAt(int $$0) {
/* 42 */     if (this.keyframes.size() <= 0) {
/* 43 */       return 0.0F;
/*    */     }
/*    */     
/* 46 */     Keyframe $$1 = this.keyframes.get(this.previousIndex);
/* 47 */     Keyframe $$2 = this.keyframes.get(this.keyframes.size() - 1);
/* 48 */     boolean $$3 = ($$0 < $$1.getTimeStamp());
/*    */     
/* 50 */     int $$4 = $$3 ? 0 : this.previousIndex;
/* 51 */     float $$5 = $$3 ? $$2.getValue() : $$1.getValue();
/*    */     
/* 53 */     for (int $$6 = $$4; $$6 < this.keyframes.size(); $$6++) {
/* 54 */       Keyframe $$7 = this.keyframes.get($$6);
/* 55 */       if ($$7.getTimeStamp() > $$0) {
/*    */         break;
/*    */       }
/* 58 */       this.previousIndex = $$6;
/* 59 */       $$5 = $$7.getValue();
/*    */     } 
/*    */     
/* 62 */     return $$5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\schedule\Timeline.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */