package uk.co.lastresorts.charcoalmod.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFlare extends ModelBase
{
  //fields
    ModelRenderer FlareTop;
    ModelRenderer FlareBottomHead;
    ModelRenderer FlareBottom;
    ModelRenderer FlareFront;
    ModelRenderer FlareBack;
    ModelRenderer FlareLeft;
    ModelRenderer FlareRight;
    ModelRenderer FlareTopHead;
    ModelRenderer FlareLeftHead;
    ModelRenderer FlareRightHead;
    ModelRenderer FlareFrontHead;
    ModelRenderer FlareBackHead;
  
  public ModelFlare()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      FlareTop = new ModelRenderer(this, 0, 0);
      FlareTop.addBox(-1F, -4F, -1F, 2, 10, 2);
      FlareTop.setRotationPoint(0F, -8F, 0F);
      FlareTop.setTextureSize(64, 32);
      FlareTop.mirror = true;
      setRotation(FlareTop, 0F, 0F, 0F);
      FlareBottomHead = new ModelRenderer(this, 0, 12);
      FlareBottomHead.addBox(-1.5F, -2F, -1.5F, 3, 2, 3);
      FlareBottomHead.setRotationPoint(0F, 4F, 0F);
      FlareBottomHead.setTextureSize(64, 32);
      FlareBottomHead.mirror = true;
      setRotation(FlareBottomHead, 0F, 0F, 0F);
      FlareBottom = new ModelRenderer(this, 0, 0);
      FlareBottom.addBox(-1F, -10F, -1F, 2, 10, 2);
      FlareBottom.setRotationPoint(0F, 12F, 0F);
      FlareBottom.setTextureSize(64, 32);
      FlareBottom.mirror = true;
      setRotation(FlareBottom, 0F, 0F, 0F);
      FlareFront = new ModelRenderer(this, 0, 0);
      FlareFront.addBox(-1F, -10F, -1F, 2, 10, 2);
      FlareFront.setRotationPoint(0F, 0F, -12F);
      FlareFront.setTextureSize(64, 32);
      FlareFront.mirror = true;
      setRotation(FlareFront, -1.570796F, 0F, 0F);
      FlareBack = new ModelRenderer(this, 0, 0);
      FlareBack.addBox(-1F, 0F, -1F, 2, 10, 2);
      FlareBack.setRotationPoint(0F, 0F, 12F);
      FlareBack.setTextureSize(64, 32);
      FlareBack.mirror = true;
      setRotation(FlareBack, -1.570796F, 0F, 0F);
      FlareLeft = new ModelRenderer(this, 0, 0);
      FlareLeft.addBox(-1F, 0F, -1F, 2, 10, 2);
      FlareLeft.setRotationPoint(12F, 0F, 0F);
      FlareLeft.setTextureSize(64, 32);
      FlareLeft.mirror = true;
      setRotation(FlareLeft, -1.570796F, 1.570796F, 0F);
      FlareRight = new ModelRenderer(this, 0, 0);
      FlareRight.addBox(-1F, 0F, -1F, 2, 10, 2);
      FlareRight.setRotationPoint(-12F, 0F, 0F);
      FlareRight.setTextureSize(64, 32);
      FlareRight.mirror = true;
      setRotation(FlareRight, -1.570796F, -1.570796F, 0F);
      FlareTopHead = new ModelRenderer(this, 0, 12);
      FlareTopHead.addBox(-1.5F, -2F, -1.5F, 3, 2, 3);
      FlareTopHead.setRotationPoint(0F, -4F, 0F);
      FlareTopHead.setTextureSize(64, 32);
      FlareTopHead.mirror = true;
      setRotation(FlareTopHead, 3.141593F, 0F, 0F);
      FlareLeftHead = new ModelRenderer(this, 0, 12);
      FlareLeftHead.addBox(-1.5F, 0F, -1.5F, 3, 2, 3);
      FlareLeftHead.setRotationPoint(4F, 0F, 0F);
      FlareLeftHead.setTextureSize(64, 32);
      FlareLeftHead.mirror = true;
      setRotation(FlareLeftHead, 0F, 0F, 1.570796F);
      FlareRightHead = new ModelRenderer(this, 0, 12);
      FlareRightHead.addBox(-1.5F, 0F, -1.5F, 3, 2, 3);
      FlareRightHead.setRotationPoint(-4F, 0F, 0F);
      FlareRightHead.setTextureSize(64, 32);
      FlareRightHead.mirror = true;
      setRotation(FlareRightHead, 0F, 0F, -1.570796F);
      FlareFrontHead = new ModelRenderer(this, 0, 12);
      FlareFrontHead.addBox(-1.5F, 0F, -1.5F, 3, 2, 3);
      FlareFrontHead.setRotationPoint(0F, 0F, -4F);
      FlareFrontHead.setTextureSize(64, 32);
      FlareFrontHead.mirror = true;
      setRotation(FlareFrontHead, 1.570796F, 0F, 0F);
      FlareBackHead = new ModelRenderer(this, 0, 12);
      FlareBackHead.addBox(-1.5F, 0F, -1.5F, 3, 2, 3);
      FlareBackHead.setRotationPoint(0F, 0F, 4F);
      FlareBackHead.setTextureSize(64, 32);
      FlareBackHead.mirror = true;
      setRotation(FlareBackHead, -1.570796F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int meta)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    switch(meta) {
    case 1:
    	FlareTop.render(f5);
    	FlareTopHead.render(f5);
    	break;
    case 2:
    	FlareBottom.render(f5);
    	FlareBottomHead.render(f5);
    	break;
    case 3:
    	FlareBack.render(f5);
    	FlareBackHead.render(f5);
    	break;
    case 4:
    	FlareFront.render(f5);
    	FlareFrontHead.render(f5);
    	break;
    case 5:
    	FlareRight.render(f5);
    	FlareRightHead.render(f5);
    	break;
    case 6:
    	FlareLeft.render(f5);
    	FlareLeftHead.render(f5);
    	break;
	default:
		break;
    }
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
